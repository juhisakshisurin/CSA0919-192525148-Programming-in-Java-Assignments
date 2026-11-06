import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Assignment 2 (CO2), Task 2: ArrayList vs LinkedList and the Up Next queue.
 *
 * Run from this folder:
 *   javac PlaylistBenchmark.java
 *   java PlaylistBenchmark
 *
 * Optional CSV path:
 *   java PlaylistBenchmark task2_results.csv
 */
public class PlaylistBenchmark {
    private static final int[] PLAYLIST_SIZES = {500, 5000, 20000};
    private static final int MEASURED_REPETITIONS = 5;
    private static final int WARMUP_REPETITIONS = 1;
    private static final int POSITIONAL_OPERATIONS = 400;
    private static final int UP_NEXT_CYCLES = 500;
    private static volatile long blackhole;

    private static final class Song {
        final int id;
        final String title;

        Song(int id) {
            this.id = id;
            this.title = "Song-" + id;
        }
    }

    private static final class Measurement {
        final String structure;
        final int playlistSize;
        final String operation;
        final int repetition;
        final int operationCount;
        final long elapsedNanos;
        final long checksum;

        Measurement(String structure, int playlistSize, String operation,
                    int repetition, int operationCount, long elapsedNanos,
                    long checksum) {
            this.structure = structure;
            this.playlistSize = playlistSize;
            this.operation = operation;
            this.repetition = repetition;
            this.operationCount = operationCount;
            this.elapsedNanos = elapsedNanos;
            this.checksum = checksum;
        }

        double nanosPerOperation() {
            return operationCount == 0 ? 0.0
                    : elapsedNanos / (double) operationCount;
        }
    }

    private interface TimedWork {
        long run();
    }

    private interface UpNextQueue {
        void addFirst(Song song);
        void addLast(Song song);
        Song removeFirst();
    }

    private static List<Song> newList(boolean linked, int size) {
        List<Song> list = linked ? new LinkedList<Song>()
                                 : new ArrayList<Song>();
        for (int i = 0; i < size; i++) {
            list.add(new Song(i));
        }
        return list;
    }

    private static Measurement measure(String structure, int size,
                                       String operation, int repetition,
                                       int operationCount, TimedWork work) {
        long start = System.nanoTime();
        long checksum = work.run();
        long elapsed = System.nanoTime() - start;
        blackhole = blackhole * 31L + checksum;
        return new Measurement(structure, size, operation, repetition,
                               operationCount, elapsed, checksum);
    }

    private static void executeListTrial(boolean linked, int n,
                                         String operation, int repetition,
                                         boolean keep,
                                         List<Measurement> measurements) {
        String structure = linked ? "LinkedList" : "ArrayList";
        Measurement result;

        if ("AddAtEnd".equals(operation)) {
            final List<Song> list = newList(linked, 0);
            result = measure(structure, n, operation, repetition, n,
                    new TimedWork() {
                        public long run() {
                            long sum = 0;
                            for (int i = 0; i < n; i++) {
                                Song song = new Song(i);
                                list.add(song);
                                sum += song.id;
                            }
                            return sum + list.size();
                        }
                    });
        } else if ("InsertFront".equals(operation)) {
            final List<Song> list = newList(linked, n);
            result = measure(structure, n, operation, repetition,
                    POSITIONAL_OPERATIONS, new TimedWork() {
                        public long run() {
                            long sum = 0;
                            for (int i = 0; i < POSITIONAL_OPERATIONS; i++) {
                                Song song = new Song(-i - 1);
                                list.add(0, song);
                                sum += song.id;
                            }
                            return sum + list.size();
                        }
                    });
        } else if ("RemoveFront".equals(operation)) {
            final List<Song> list = newList(linked, n + POSITIONAL_OPERATIONS);
            result = measure(structure, n, operation, repetition,
                    POSITIONAL_OPERATIONS, new TimedWork() {
                        public long run() {
                            long sum = 0;
                            for (int i = 0; i < POSITIONAL_OPERATIONS; i++) {
                                sum += list.remove(0).id;
                            }
                            return sum + list.size();
                        }
                    });
        } else if ("InsertMiddle".equals(operation)) {
            final List<Song> list = newList(linked, n);
            result = measure(structure, n, operation, repetition,
                    POSITIONAL_OPERATIONS, new TimedWork() {
                        public long run() {
                            long sum = 0;
                            for (int i = 0; i < POSITIONAL_OPERATIONS; i++) {
                                Song song = new Song(-i - 1);
                                list.add(list.size() / 2, song);
                                sum += song.id;
                            }
                            return sum + list.size();
                        }
                    });
        } else if ("RemoveMiddle".equals(operation)) {
            final List<Song> list = newList(linked, n + POSITIONAL_OPERATIONS);
            result = measure(structure, n, operation, repetition,
                    POSITIONAL_OPERATIONS, new TimedWork() {
                        public long run() {
                            long sum = 0;
                            for (int i = 0; i < POSITIONAL_OPERATIONS; i++) {
                                sum += list.remove(list.size() / 2).id;
                            }
                            return sum + list.size();
                        }
                    });
        } else if ("GetKth".equals(operation)) {
            final List<Song> list = newList(linked, n);
            final int accesses = Math.max(100, 200000 / n);
            result = measure(structure, n, operation, repetition,
                    accesses, new TimedWork() {
                        public long run() {
                            long sum = 0;
                            for (int i = 0; i < accesses; i++) {
                                int index = (int) (((long) i * 7919L) % n);
                                sum += list.get(index).id;
                            }
                            return sum;
                        }
                    });
        } else if ("IterateAll".equals(operation)) {
            final List<Song> list = newList(linked, n);
            final int passes = Math.max(1, 100000 / n);
            result = measure(structure, n, operation, repetition,
                    n * passes, new TimedWork() {
                        public long run() {
                            long sum = 0;
                            for (int pass = 0; pass < passes; pass++) {
                                for (Song song : list) {
                                    sum += song.id;
                                }
                            }
                            return sum;
                        }
                    });
        } else {
            throw new IllegalArgumentException("Unknown operation: " + operation);
        }

        if (keep) {
            measurements.add(result);
            System.out.printf(Locale.US,
                    "  %-10s %-13s run %d: %9.3f ms (%8.2f ns/op)%n",
                    structure, operation, repetition,
                    result.elapsedNanos / 1_000_000.0,
                    result.nanosPerOperation());
        }
    }

    private static void runUpNextTrial(final String structure, final int n,
                                       int repetition, boolean keep,
                                       List<Measurement> measurements) {
        final UpNextQueue queue;
        if ("ArrayList".equals(structure)) {
            final ArrayList<Song> list = new ArrayList<Song>();
            for (int i = 0; i < n; i++) list.add(new Song(i));
            queue = new UpNextQueue() {
                public void addFirst(Song song) { list.add(0, song); }
                public void addLast(Song song) { list.add(song); }
                public Song removeFirst() { return list.remove(0); }
            };
        } else if ("LinkedList".equals(structure)) {
            final LinkedList<Song> list = new LinkedList<Song>();
            for (int i = 0; i < n; i++) list.add(new Song(i));
            queue = new UpNextQueue() {
                public void addFirst(Song song) { list.addFirst(song); }
                public void addLast(Song song) { list.addLast(song); }
                public Song removeFirst() { return list.removeFirst(); }
            };
        } else {
            final ArrayDeque<Song> deque = new ArrayDeque<Song>();
            for (int i = 0; i < n; i++) deque.addLast(new Song(i));
            queue = new UpNextQueue() {
                public void addFirst(Song song) { deque.addFirst(song); }
                public void addLast(Song song) { deque.addLast(song); }
                public Song removeFirst() { return deque.removeFirst(); }
            };
        }

        Measurement result = measure(structure, n, "UpNextCycle", repetition,
                UP_NEXT_CYCLES * 4, new TimedWork() {
                    public long run() {
                        long sum = 0;
                        for (int i = 0; i < UP_NEXT_CYCLES; i++) {
                            queue.addFirst(new Song(-i - 1));
                            queue.addLast(new Song(i + 100000));
                            sum += queue.removeFirst().id;
                            sum += queue.removeFirst().id;
                        }
                        return sum;
                    }
                });

        if (keep) {
            measurements.add(result);
            System.out.printf(Locale.US,
                    "  %-10s %-13s run %d: %9.3f ms (%8.2f ns/op)%n",
                    structure, "UpNextCycle", repetition,
                    result.elapsedNanos / 1_000_000.0,
                    result.nanosPerOperation());
        }
    }

    private static void warmUpAll() {
        for (int n : PLAYLIST_SIZES) {
            for (boolean linked : new boolean[] {false, true}) {
                for (String op : new String[] {"AddAtEnd", "InsertFront",
                        "RemoveFront", "InsertMiddle", "RemoveMiddle",
                        "GetKth", "IterateAll"}) {
                    for (int w = 0; w < WARMUP_REPETITIONS; w++) {
                        executeListTrial(linked, n, op, 0, false,
                                new ArrayList<Measurement>());
                    }
                }
            }
            for (String structure : new String[] {"ArrayList", "LinkedList",
                    "ArrayDeque"}) {
                runUpNextTrial(structure, n, 0, false,
                        new ArrayList<Measurement>());
            }
        }
    }

    private static void writeCsv(String path, List<Measurement> results)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        try {
            writer.write("structure,playlist_size,operation,repetition,"
                    + "operation_count,elapsed_ns,elapsed_ms,ns_per_operation,checksum");
            writer.newLine();
            for (Measurement m : results) {
                writer.write(m.structure + "," + m.playlistSize + ","
                        + m.operation + "," + m.repetition + ","
                        + m.operationCount + "," + m.elapsedNanos + ","
                        + String.format(Locale.US, "%.6f",
                                m.elapsedNanos / 1_000_000.0) + ","
                        + String.format(Locale.US, "%.3f",
                                m.nanosPerOperation()) + ","
                        + m.checksum);
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    private static void printSummary(List<Measurement> results) {
        System.out.println("\nAVERAGE TIME PER OPERATION (5 measured repetitions)");
        System.out.printf("%-10s %-7s %-15s %14s %14s%n",
                "Structure", "Size", "Operation", "Average ms", "Average ns/op");
        String[] structures = {"ArrayList", "LinkedList", "ArrayDeque"};
        int[] sizes = PLAYLIST_SIZES;
        String[] operations = {"AddAtEnd", "InsertFront", "RemoveFront",
                "InsertMiddle", "RemoveMiddle", "GetKth", "IterateAll",
                "UpNextCycle"};

        for (String structure : structures) {
            for (int size : sizes) {
                for (String operation : operations) {
                    if ("ArrayDeque".equals(structure)
                            && !"UpNextCycle".equals(operation)) continue;
                    if (!"ArrayDeque".equals(structure)
                            && "UpNextCycle".equals(operation)) {
                        // Up Next comparison includes the two List implementations.
                    }
                    long totalNanos = 0;
                    double totalNsPerOperation = 0.0;
                    int count = 0;
                    for (Measurement m : results) {
                        if (m.structure.equals(structure)
                                && m.playlistSize == size
                                && m.operation.equals(operation)) {
                            totalNanos += m.elapsedNanos;
                            totalNsPerOperation += m.nanosPerOperation();
                            count++;
                        }
                    }
                    if (count > 0) {
                        System.out.printf(Locale.US,
                                "%-10s %-7d %-15s %14.4f %14.2f%n",
                                structure, size, operation,
                                totalNanos / (double) count / 1_000_000.0,
                                totalNsPerOperation / count);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String csvPath = args.length > 0 ? args[0] : "task2_results.csv";
        List<Measurement> results = new ArrayList<Measurement>();

        System.out.println("Assignment 2 (CO2) - Task 2: Playlist collection benchmark");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("Playlist sizes: 500, 5000, 20000");
        System.out.println("Measured repetitions: " + MEASURED_REPETITIONS);
        System.out.println("Warm-up repetitions: " + WARMUP_REPETITIONS);
        System.out.println("Preparing warm-up trials...");
        warmUpAll();

        String[] operations = {"AddAtEnd", "InsertFront", "RemoveFront",
                "InsertMiddle", "RemoveMiddle", "GetKth", "IterateAll"};

        for (int n : PLAYLIST_SIZES) {
            System.out.println("\n===== PLAYLIST SIZE: " + n + " =====");
            for (boolean linked : new boolean[] {false, true}) {
                for (String operation : operations) {
                    for (int repetition = 1;
                         repetition <= MEASURED_REPETITIONS; repetition++) {
                        executeListTrial(linked, n, operation, repetition,
                                true, results);
                    }
                }
            }
            System.out.println("\n----- UP NEXT QUEUE: size " + n + " -----");
            for (String structure : new String[] {"ArrayList", "LinkedList",
                    "ArrayDeque"}) {
                for (int repetition = 1;
                     repetition <= MEASURED_REPETITIONS; repetition++) {
                    runUpNextTrial(structure, n, repetition, true, results);
                }
            }
        }

        try {
            writeCsv(csvPath, results);
            printSummary(results);
            System.out.println("\nRaw measurements saved to: " + csvPath);
            System.out.println("Checksum: " + blackhole);
            System.out.println("Note: timings are machine/JVM dependent; compare patterns, not absolute values.");
        } catch (IOException e) {
            System.err.println("Could not write CSV: " + e.getMessage());
            System.exit(1);
        }
    }
}
