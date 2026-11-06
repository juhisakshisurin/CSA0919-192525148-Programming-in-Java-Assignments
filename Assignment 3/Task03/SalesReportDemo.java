import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/*
 * Q3 – Daily Sales Report Generator
 * The tracked reader is used to make resource leaks observable in a repeatable
 * demonstration. It counts readers opened and closed by this program.
 */
public class SalesReportDemo {
    private static int openReaders = 0;

    static class TrackedReader implements AutoCloseable {
        private final BufferedReader reader;
        private boolean closed = false;

        TrackedReader(Path path) throws IOException {
            reader = Files.newBufferedReader(path);
            openReaders++;
        }

        String readLine() throws IOException {
            return reader.readLine();
        }

        @Override
        public void close() throws IOException {
            if (!closed) {
                closed = true;
                try {
                    reader.close();
                } finally {
                    openReaders--;
                }
            }
        }
    }

    private static void resetCounter() {
        openReaders = 0;
    }

    // Intentionally flawed manual-close version: close() is after the loop.
    // If opening/reading a later file fails, control jumps to catch and skips
    // the close loop. This reproduces the resource leak for debugging analysis.
    static void generateManual(List<Path> files, Path report) {
        List<TrackedReader> readers = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        int totalSales = 0;

        try {
            for (Path file : files) {
                System.out.println("Reading: " + file.getFileName());
                TrackedReader reader = new TrackedReader(file);
                readers.add(reader);

                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.isBlank() && !line.startsWith("item,")) {
                        String[] parts = line.split(",");
                        totalSales += Integer.parseInt(parts[1].trim());
                    }
                }
            }

            // Manual close is reached only if every file is processed.
            for (TrackedReader reader : readers) {
                reader.close();
            }
            lines.add("Total sales: " + totalSales);
            lines.add("Stores processed: " + files.size());
            Files.write(report, lines);
            System.out.println("Report written: " + report);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Manual version error: " + e.getMessage());
        } finally {
            System.out.println("Manual finally executed. Open readers: " + openReaders);
        }
    }

    // Correct version: each reader is automatically closed, including when a
    // later operation throws an exception.
    static void generateTryWithResources(List<Path> files, Path report) {
        List<String> lines = new ArrayList<>();
        int totalSales = 0;
        int processed = 0;

        try {
            for (Path file : files) {
                System.out.println("Reading: " + file.getFileName());
                try (TrackedReader reader = new TrackedReader(file)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.isBlank() && !line.startsWith("item,")) {
                            String[] parts = line.split(",");
                            totalSales += Integer.parseInt(parts[1].trim());
                        }
                    }
                }
                processed++;
            }

            lines.add("Total sales: " + totalSales);
            lines.add("Stores processed: " + processed);
            Files.write(report, lines);
            System.out.println("Report written: " + report);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Try-with-resources error: " + e.getMessage());
        } finally {
            System.out.println("Try-with-resources finally executed. Open readers: " + openReaders);
        }
    }

    public static void main(String[] args) {
        Path input = Path.of("input");
        List<Path> successfulFiles = List.of(
                input.resolve("store1.csv"),
                input.resolve("store2.csv"),
                input.resolve("store4.csv")
        );
        // store3_missing.csv is deliberately absent to force a mid-run failure.
        List<Path> failingFiles = List.of(
                input.resolve("store1.csv"),
                input.resolve("store2.csv"),
                input.resolve("store3_missing.csv"),
                input.resolve("store4.csv")
        );

        System.out.println("=== MANUAL CLOSE: SUCCESSFUL INPUT ===");
        resetCounter();
        generateManual(successfulFiles, Path.of("manual-success-report.txt"));

        System.out.println("\n=== TRY-WITH-RESOURCES: SUCCESSFUL INPUT ===");
        resetCounter();
        generateTryWithResources(successfulFiles, Path.of("twr-success-report.txt"));

        System.out.println("\n=== MANUAL CLOSE: FAILURE PART-WAY ===");
        resetCounter();
        generateManual(failingFiles, Path.of("manual-failure-report.txt"));

        System.out.println("\n=== TRY-WITH-RESOURCES: SAME FAILURE ===");
        resetCounter();
        generateTryWithResources(failingFiles, Path.of("twr-failure-report.txt"));
    }
}
