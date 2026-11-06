import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Concurrent, fault-tolerant semester result processor.
 * Each expected score file is processed independently by a Callable.
 */
public class ResultProcessor {
    private static final int EXPECTED_FILES = 10_000;
    private static final String PREFIX = "score_";
    private static final String SUFFIX = ".txt";

    static final class ResultRecord {
        final String fileName;
        final boolean success;
        final String message;
        final long total;
        ResultRecord(String fileName, boolean success, String message, long total) {
            this.fileName = fileName;
            this.success = success;
            this.message = message;
            this.total = total;
        }
        @Override public String toString() {
            return fileName + "," + (success ? "SUCCESS" : "FAILURE") + "," +
                    total + "," + message.replace(',', ';');
        }
    }

    static final class ScoreTask implements Callable<ResultRecord> {
        private final Path file;
        private final String expectedName;
        ScoreTask(Path file, String expectedName) {
            this.file = file;
            this.expectedName = expectedName;
        }

        @Override public ResultRecord call() {
            try {
                if (!Files.exists(file)) {
                    throw new IOException("Missing score file");
                }
                List<String> lines = Files.readAllLines(file);
                if (lines.size() != 3) {
                    throw new IllegalArgumentException("Expected exactly 3 score lines");
                }

                long total = 0;
                for (String line : lines) {
                    int score;
                    try {
                        score = Integer.parseInt(line.trim());
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Non-numeric score: " + line, ex);
                    }
                    if (score < 0 || score > 100) {
                        throw new IllegalArgumentException("Score outside 0..100: " + score);
                    }
                    total += score;
                }
                return new ResultRecord(expectedName, true, "Validated", total);
            } catch (IOException | IllegalArgumentException ex) {
                // Failure is converted into a result record; it does not cancel other files.
                return new ResultRecord(expectedName, false,
                        ex.getClass().getSimpleName() + ": " + ex.getMessage(), 0);
            } catch (Exception ex) {
                // Defensive boundary: one unexpected task failure is still recorded.
                return new ResultRecord(expectedName, false,
                        "Unexpected " + ex.getClass().getSimpleName() + ": " + ex.getMessage(), 0);
            }
        }
    }

    static final class RunSummary {
        final int expected, success, failure;
        final long totalMarks, elapsedMillis;
        RunSummary(int expected, int success, int failure, long totalMarks, long elapsedMillis) {
            this.expected = expected;
            this.success = success;
            this.failure = failure;
            this.totalMarks = totalMarks;
            this.elapsedMillis = elapsedMillis;
        }
        @Override public String toString() {
            return "Expected files: " + expected + "\nSuccessful: " + success +
                    "\nFailed: " + failure + "\nTotal marks from successful files: " +
                    totalMarks + "\nProcessed records: " + (success + failure) +
                    "\nElapsed time (ms): " + elapsedMillis;
        }
    }

    static RunSummary process(Path directory, int poolSize, int expectedFiles, Path reportFile)
            throws InterruptedException, IOException {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        ConcurrentLinkedQueue<ResultRecord> results = new ConcurrentLinkedQueue<>();
        List<Future<ResultRecord>> futures = new ArrayList<>(expectedFiles);
        long start = System.nanoTime();

        try {
            for (int i = 1; i <= expectedFiles; i++) {
                String name = PREFIX + String.format(Locale.ROOT, "%05d", i) + SUFFIX;
                Path file = directory.resolve(name);
                futures.add(executor.submit(new ScoreTask(file, name)));
            }

            // get() waits for each task and surfaces any unexpected task-level exception.
            for (Future<ResultRecord> future : futures) {
                try {
                    results.add(future.get());
                } catch (ExecutionException ex) {
                    Throwable cause = ex.getCause();
                    results.add(new ResultRecord("UNKNOWN_TASK", false,
                            "Task execution failure: " + cause, 0));
                }
            }
        } finally {
            executor.shutdown();
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
                executor.awaitTermination(1, TimeUnit.MINUTES);
            }
        }

        long elapsed = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        int success = 0, failure = 0;
        long totalMarks = 0;
        List<ResultRecord> ordered = new ArrayList<>(results);
        ordered.sort(Comparator.comparing(r -> r.fileName));

        for (ResultRecord r : ordered) {
            if (r.success) { success++; totalMarks += r.total; }
            else failure++;
        }

        // CSV provides a durable per-file audit trail.
        try (java.io.BufferedWriter writer = Files.newBufferedWriter(reportFile)) {
            writer.write("file,status,total,message");
            writer.newLine();
            for (ResultRecord r : ordered) {
                writer.write(r.toString());
                writer.newLine();
            }
        }

        return new RunSummary(expectedFiles, success, failure, totalMarks, elapsed);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2 || args.length > 4) {
            System.out.println("Usage: java ResultProcessor <input-folder> <pool-size> [report.csv] [expected-count]");
            System.out.println("Pool size must be one of: 1, 2, 4, 8");
            return;
        }
        Path directory = Paths.get(args[0]);
        int poolSize = Integer.parseInt(args[1]);
        if (!Arrays.asList(1, 2, 4, 8).contains(poolSize)) {
            throw new IllegalArgumentException("Pool size must be 1, 2, 4, or 8");
        }
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("Input folder does not exist: " + directory);
        }
        Path report = args.length >= 3 ? Paths.get(args[2]) :
                Paths.get("results_pool_" + poolSize + ".csv");
        int expectedFiles = args.length == 4 ? Integer.parseInt(args[3]) : EXPECTED_FILES;
        if (expectedFiles < 1) throw new IllegalArgumentException("Expected count must be positive");

        RunSummary summary = process(directory, poolSize, expectedFiles, report);
        System.out.println("=== SEMESTER RESULT PROCESSING SUMMARY ===");
        System.out.println(summary);
        System.out.println("Detailed CSV: " + report.toAbsolutePath());
        if (summary.expected != summary.success + summary.failure) {
            throw new IllegalStateException("Summary count mismatch");
        }
    }
}
