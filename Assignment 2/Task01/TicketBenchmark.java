import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Assignment 2 (CO2), Task 1
 * Compares String concatenation, StringBuilder and StringBuffer for ticket text.
 *
 * Run from this folder:
 *   javac TicketBenchmark.java
 *   java TicketBenchmark
 *
 * An optional first argument can be used to choose the CSV output filename:
 *   java TicketBenchmark my_results.csv
 */
public class TicketBenchmark {

    private static final int MEASURED_REPETITIONS = 5;
    private static final int WARMUP_ROUNDS = 3;
    private static volatile long blackhole;

    private interface TicketBuilder {
        String build(String[] parts);
    }

    private static final TicketBuilder STRING_CONCAT = new TicketBuilder() {
        @Override
        public String build(String[] parts) {
            String ticket = "";
            for (int i = 0; i < parts.length; i++) {
                // Each iteration creates a new String containing the previous text
                // plus the next part. The previous intermediate String is discarded.
                ticket += parts[i];
            }
            return ticket;
        }
    };

    private static final TicketBuilder STRING_BUILDER = new TicketBuilder() {
        @Override
        public String build(String[] parts) {
            StringBuilder ticket = new StringBuilder();
            for (int i = 0; i < parts.length; i++) {
                ticket.append(parts[i]);
            }
            return ticket.toString();
        }
    };

    private static final TicketBuilder STRING_BUFFER = new TicketBuilder() {
        @Override
        public String build(String[] parts) {
            StringBuffer ticket = new StringBuffer();
            for (int i = 0; i < parts.length; i++) {
                ticket.append(parts[i]);
            }
            return ticket.toString();
        }
    };

    private static class Workload {
        final String name;
        final int ticketCount;
        final String[] parts;

        Workload(String name, int ticketCount, String[] parts) {
            this.name = name;
            this.ticketCount = ticketCount;
            this.parts = parts;
        }
    }

    private static class Measurement {
        final String workload;
        final String approach;
        final int repetition;
        final long elapsedNanos;
        final long heapBeforeBytes;
        final long heapAfterBytes;
        final long heapDeltaBytes;
        final long estimatedIntermediateStrings;
        final long estimatedTotalTicketStrings;
        final long checksum;

        Measurement(String workload, String approach, int repetition,
                    long elapsedNanos, long heapBeforeBytes, long heapAfterBytes,
                    long estimatedIntermediateStrings, long estimatedTotalTicketStrings,
                    long checksum) {
            this.workload = workload;
            this.approach = approach;
            this.repetition = repetition;
            this.elapsedNanos = elapsedNanos;
            this.heapBeforeBytes = heapBeforeBytes;
            this.heapAfterBytes = heapAfterBytes;
            this.heapDeltaBytes = heapAfterBytes - heapBeforeBytes;
            this.estimatedIntermediateStrings = estimatedIntermediateStrings;
            this.estimatedTotalTicketStrings = estimatedTotalTicketStrings;
            this.checksum = checksum;
        }
    }

    private static class Approach {
        final String name;
        final TicketBuilder builder;

        Approach(String name, TicketBuilder builder) {
            this.name = name;
            this.builder = builder;
        }
    }

    private static String[] makeTicketParts(int fieldCount) {
        String[] parts = new String[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            if (i == 0) {
                parts[i] = "PNR=PNR-2026-000001";
            } else if (i == 1) {
                parts[i] = " | Passenger=ARUN KUMAR";
            } else if (i == 2) {
                parts[i] = " | Train=CHENNAI EXPRESS";
            } else if (i == 3) {
                parts[i] = " | Coach=S2";
            } else if (i == 4) {
                parts[i] = " | Berth=24";
            } else if (i == 5) {
                parts[i] = " | Fare=850.00";
            } else {
                parts[i] = " | Field" + (i + 1) + "=VALUE" + (i + 1);
            }
        }
        return parts;
    }

    private static long usedHeapBytes() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static void warmUp(Workload workload, Approach approach) {
        int warmupTickets = Math.min(workload.ticketCount, 100);
        long checksum = 0;
        for (int i = 0; i < warmupTickets; i++) {
            checksum += approach.builder.build(workload.parts).hashCode();
        }
        blackhole ^= checksum;
    }

    private static Measurement measure(Workload workload, Approach approach, int repetition) {
        // Request collection before each measurement to reduce carry-over from the prior run.
        // System.gc() is a request, not a guarantee; heap readings remain approximate.
        System.gc();
        long heapBefore = usedHeapBytes();

        List<String> generatedTickets = new ArrayList<String>(workload.ticketCount);
        long start = System.nanoTime();
        for (int i = 0; i < workload.ticketCount; i++) {
            generatedTickets.add(approach.builder.build(workload.parts));
        }
        long elapsed = System.nanoTime() - start;
        long heapAfter = usedHeapBytes();

        // Consume the completed tickets after timing so the generated results remain observable.
        long checksum = 1;
        for (int i = 0; i < generatedTickets.size(); i++) {
            checksum = 31 * checksum + generatedTickets.get(i).hashCode();
        }
        blackhole ^= checksum;

        long totalTicketStrings;
        long intermediateStrings;
        if ("String".equals(approach.name)) {
            // One concatenation result is created per field; only the last result is retained.
            totalTicketStrings = (long) workload.ticketCount * workload.parts.length;
            intermediateStrings = (long) workload.ticketCount * (workload.parts.length - 1);
        } else {
            // Approximation counts the final String returned for each ticket, not builder objects
            // or internal character-array allocations.
            totalTicketStrings = workload.ticketCount;
            intermediateStrings = 0;
        }

        return new Measurement(workload.name, approach.name, repetition, elapsed,
                heapBefore, heapAfter, intermediateStrings, totalTicketStrings, checksum);
    }

    private static String csvCell(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private static void writeCsv(String filename, List<Measurement> measurements) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        try {
            writer.write("workload,approach,repetition,elapsed_ns,elapsed_ms,heap_before_bytes,heap_after_bytes,heap_delta_bytes,estimated_intermediate_strings,estimated_total_ticket_strings,checksum");
            writer.newLine();
            for (Measurement m : measurements) {
                writer.write(csvCell(m.workload) + ","
                        + csvCell(m.approach) + ","
                        + m.repetition + ","
                        + m.elapsedNanos + ","
                        + String.format(java.util.Locale.US, "%.6f", m.elapsedNanos / 1_000_000.0) + ","
                        + m.heapBeforeBytes + ","
                        + m.heapAfterBytes + ","
                        + m.heapDeltaBytes + ","
                        + m.estimatedIntermediateStrings + ","
                        + m.estimatedTotalTicketStrings + ","
                        + m.checksum);
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }

    private static void printSummary(List<Measurement> measurements, List<Workload> workloads,
                                     List<Approach> approaches) {
        System.out.println();
        System.out.println("AVERAGE TIME OVER " + MEASURED_REPETITIONS + " MEASURED RUNS");
        System.out.println("Workload | Approach | Average ms | Estimated intermediate Strings per run");
        for (int w = 0; w < workloads.size(); w++) {
            for (int a = 0; a < approaches.size(); a++) {
                long totalNanos = 0;
                long intermediateStrings = 0;
                int count = 0;
                for (int i = 0; i < measurements.size(); i++) {
                    Measurement m = measurements.get(i);
                    if (m.workload.equals(workloads.get(w).name)
                            && m.approach.equals(approaches.get(a).name)) {
                        totalNanos += m.elapsedNanos;
                        intermediateStrings = m.estimatedIntermediateStrings;
                        count++;
                    }
                }
                double averageMs = count == 0 ? 0.0
                        : (totalNanos / (double) count) / 1_000_000.0;
                System.out.printf(java.util.Locale.US, "%s | %s | %.6f | %d%n",
                        workloads.get(w).name, approaches.get(a).name,
                        averageMs, intermediateStrings);
            }
        }
    }

    private static void verifyEquivalentOutput() {
        String[] sampleParts = makeTicketParts(6);
        String fromString = STRING_CONCAT.build(sampleParts);
        String fromBuilder = STRING_BUILDER.build(sampleParts);
        String fromBuffer = STRING_BUFFER.build(sampleParts);
        if (!fromString.equals(fromBuilder) || !fromString.equals(fromBuffer)) {
            throw new IllegalStateException("The three approaches produced different ticket text.");
        }
        System.out.println("Output equivalence check: PASS");
        System.out.println("Sample ticket: " + fromBuilder);
    }

    public static void main(String[] args) {
        String outputFile = args.length > 0 ? args[0] : "task1_results.csv";

        List<Workload> workloads = new ArrayList<Workload>();
        workloads.add(new Workload("Short ticket - 1 x 6 fields", 1, makeTicketParts(6)));
        workloads.add(new Workload("High load - 10000 x 50 fields", 10000, makeTicketParts(50)));
        workloads.add(new Workload("Wide ticket - 1000 x 200 fields", 1000, makeTicketParts(200)));

        List<Approach> approaches = new ArrayList<Approach>();
        approaches.add(new Approach("String", STRING_CONCAT));
        approaches.add(new Approach("StringBuilder", STRING_BUILDER));
        approaches.add(new Approach("StringBuffer", STRING_BUFFER));

        List<Measurement> measurements = new ArrayList<Measurement>();

        System.out.println("Assignment 2 - CO2 - Task 1: Ticket text benchmark");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("JVM: " + ManagementFactory.getRuntimeMXBean().getVmName());
        System.out.println("Measured repetitions: " + MEASURED_REPETITIONS);
        verifyEquivalentOutput();

        for (int w = 0; w < workloads.size(); w++) {
            Workload workload = workloads.get(w);
            System.out.println();
            System.out.println("Running: " + workload.name);

            for (int a = 0; a < approaches.size(); a++) {
                for (int warmup = 0; warmup < WARMUP_ROUNDS; warmup++) {
                    warmUp(workload, approaches.get(a));
                }
            }

            // Rotate the starting approach on each repetition to reduce fixed-order bias.
            for (int repetition = 1; repetition <= MEASURED_REPETITIONS; repetition++) {
                for (int offset = 0; offset < approaches.size(); offset++) {
                    int index = (repetition - 1 + offset) % approaches.size();
                    Measurement result = measure(workload, approaches.get(index), repetition);
                    measurements.add(result);
                    System.out.printf(java.util.Locale.US,
                            "Run %d - %-12s : %10.3f ms | heap delta: %d bytes%n",
                            repetition, result.approach,
                            result.elapsedNanos / 1_000_000.0, result.heapDeltaBytes);
                }
            }
        }

        try {
            writeCsv(outputFile, measurements);
            printSummary(measurements, workloads, approaches);
            System.out.println();
            System.out.println("Raw measurements saved to: " + outputFile);
            System.out.println("Checksum (prevents unused-result measurements): " + blackhole);
            System.out.println("Note: heap deltas are approximate snapshots, not peak-memory measurements.");
        } catch (IOException e) {
            System.err.println("Could not write CSV file: " + e.getMessage());
            System.exit(1);
        }
    }
}
