import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

/**
 * Assignment 2 - CO2 - Q7: Diagnose, Redesign and Validate.
 * Case selected from Q1: repeated String concatenation in ticket generation.
 *
 * Compile: javac TicketRedesign.java
 * Run:     java TicketRedesign
 */
public class TicketRedesign {
    private static final int REPETITIONS = 5;
    private static final int WARMUPS = 3;
    private static volatile long blackhole;

    interface Builder {
        String build(String[] fields);
    }

    // Original design from Q1: immutable String concatenation in a loop.
    static final Builder ORIGINAL_STRING = fields -> {
        String result = "";
        for (String field : fields) {
            result += field;
        }
        return result;
    };

    // Redesigned approach: mutable builder with capacity pre-sized to exact output length.
    static final Builder REDESIGNED_BUILDER = fields -> {
        int capacity = 0;
        for (String field : fields) capacity += field.length();

        StringBuilder result = new StringBuilder(capacity);
        for (String field : fields) result.append(field);
        return result.toString();
    };

    static String[] createFields(int count) {
        String[] fields = new String[count];
        for (int i = 0; i < count; i++) {
            if (i == 0) fields[i] = "PNR=PNR-2026-000001";
            else if (i == 1) fields[i] = " | Passenger=ARUN KUMAR";
            else if (i == 2) fields[i] = " | Train=CHENNAI EXPRESS";
            else if (i == 3) fields[i] = " | Coach=S2";
            else if (i == 4) fields[i] = " | Berth=24";
            else if (i == 5) fields[i] = " | Fare=850.00";
            else fields[i] = " | Field" + (i + 1) + "=VALUE" + (i + 1);
        }
        return fields;
    }

    static long run(Builder builder, String[] fields, int tickets) {
        long checksum = 0;
        for (int i = 0; i < tickets; i++) {
            String ticket = builder.build(fields);
            checksum = checksum * 31 + ticket.hashCode();
        }
        return checksum;
    }

    static long measure(Builder builder, String[] fields, int tickets) {
        long start = System.nanoTime();
        long sum = run(builder, fields, tickets);
        long elapsed = System.nanoTime() - start;
        blackhole ^= sum;
        return elapsed;
    }

    static void benchmark(String workload, int tickets, int fieldCount,
                          FileWriter csv) throws IOException {
        String[] fields = createFields(fieldCount);

        // Verify the two approaches return exactly the same text.
        String oldResult = ORIGINAL_STRING.build(fields);
        String newResult = REDESIGNED_BUILDER.build(fields);
        if (!oldResult.equals(newResult)) {
            throw new IllegalStateException("Output mismatch in " + workload);
        }
        System.out.println("\n" + workload + " (" + tickets
                + " tickets x " + fieldCount + " fields)");
        System.out.println("Output equivalence: PASS");

        // Warm up both approaches, not included in measurements.
        for (int i = 0; i < WARMUPS; i++) {
            blackhole ^= run(ORIGINAL_STRING, fields, Math.min(tickets, 100));
            blackhole ^= run(REDESIGNED_BUILDER, fields, Math.min(tickets, 100));
        }

        long[] oldTimes = new long[REPETITIONS];
        long[] newTimes = new long[REPETITIONS];

        try {
            for (int r = 0; r < REPETITIONS; r++) {
                // Alternate measurement order to reduce fixed-order bias.
                if (r % 2 == 0) {
                    oldTimes[r] = measure(ORIGINAL_STRING, fields, tickets);
                    newTimes[r] = measure(REDESIGNED_BUILDER, fields, tickets);
                } else {
                    newTimes[r] = measure(REDESIGNED_BUILDER, fields, tickets);
                    oldTimes[r] = measure(ORIGINAL_STRING, fields, tickets);
                }
                csv.write(workload + ",String(original)," + (r+1) + ","
                        + tickets + "," + fieldCount + "," + oldTimes[r] + ","
                        + (oldTimes[r]/1_000_000.0) + ","
                        + ((long)tickets * (fieldCount-1)) + "\n");
                csv.write(workload + ",StringBuilder(redesign)," + (r+1) + ","
                        + tickets + "," + fieldCount + "," + newTimes[r] + ","
                        + (newTimes[r]/1_000_000.0) + ",0\n");
            }
        } catch (IOException e) {
            throw e;
        }

        double oldAvg = average(oldTimes) / 1_000_000.0;
        double newAvg = average(newTimes) / 1_000_000.0;
        System.out.printf(Locale.US, "Original String average: %.4f ms%n", oldAvg);
        System.out.printf(Locale.US, "Redesigned StringBuilder average: %.4f ms%n", newAvg);
        System.out.printf(Locale.US, "Speed-up: %.2fx%n", oldAvg / newAvg);
        System.out.println("Estimated intermediate Strings: original="
                + ((long)tickets * (fieldCount-1)) + ", redesign=0");
    }

    static double average(long[] values) {
        long total = 0;
        for (long value : values) total += value;
        return total / (double) values.length;
    }

    public static void main(String[] args) {
        String file = args.length > 0 ? args[0] : "task7_results.csv";
        System.out.println("ASSIGNMENT 2 - Q7: DIAGNOSE, REDESIGN AND VALIDATE");
        System.out.println("Selected limitation: Q1 repeated String concatenation.");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("Measured repetitions: " + REPETITIONS
                + "; warm-up rounds: " + WARMUPS);

        try (FileWriter csv = new FileWriter(file)) {
            csv.write("workload,approach,repetition,tickets,fields,elapsed_ns,elapsed_ms,estimated_intermediate_strings\n");
            benchmark("HighLoad", 10000, 50, csv);
            benchmark("WideTicket", 1000, 200, csv);
            System.out.println("\nRaw measurements saved to: " + file);
            System.out.println("Checksum: " + blackhole);
            System.out.println("Note: timings vary by computer/JVM; use local measurements in final submission.");
        } catch (IOException e) {
            System.err.println("Could not write CSV: " + e.getMessage());
            System.exit(1);
        }
    }
}
