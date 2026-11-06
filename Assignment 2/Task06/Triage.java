import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayDeque;

/*
 * Assignment 2 - Q6: Priority, Order and Fairness in a Queue
 * Compile: javac Triage.java
 * Run:     java Triage
 */
class Patient {
    final String name;
    final int severity;
    final long arrival;

    Patient(String name, int severity, long arrival) {
        this.name = name;
        this.severity = severity;
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return name + "(S" + severity + ")";
    }
}

public class Triage {
    private long counter = 0;

    // Lowest severity number (1 = critical) first; FIFO for equal severity.
    private final PriorityQueue<Patient> queue =
        new PriorityQueue<Patient>(
            Comparator.comparingInt((Patient p) -> p.severity)
                      .thenComparingLong(p -> p.arrival)
        );

    void admit(String name, int severity) {
        if (severity < 1 || severity > 5) {
            throw new IllegalArgumentException(
                "Severity must be between 1 and 5");
        }
        queue.add(new Patient(name, severity, counter++));
    }

    Patient next() {
        return queue.poll();
    }

    // A sorted snapshot for display. Do not print PriorityQueue directly:
    // its iterator exposes heap layout, not sorted treatment order.
    List<Patient> waitingListInTreatmentOrder() {
        PriorityQueue<Patient> copy =
            new PriorityQueue<Patient>(queue);
        List<Patient> ordered = new ArrayList<Patient>();
        while (!copy.isEmpty()) {
            ordered.add(copy.poll());
        }
        return ordered;
    }

    private static void print(String title, List<Patient> patients) {
        System.out.println("\n" + title);
        System.out.println(patients);
    }

    private static boolean verify(List<Patient> actual,
                                  String[] expected) {
        if (actual.size() != expected.length) return false;
        for (int i = 0; i < expected.length; i++) {
            if (!actual.get(i).toString().equals(expected[i])) {
                return false;
            }
        }
        return true;
    }

    // Alternative 1: LinkedList kept sorted on every admission.
    static class SortedLinkedQueue {
        private final LinkedList<Patient> items = new LinkedList<Patient>();

        void admit(Patient patient) {
            java.util.ListIterator<Patient> it = items.listIterator();
            while (it.hasNext()) {
                Patient current = it.next();
                if (patient.severity < current.severity
                        || (patient.severity == current.severity
                            && patient.arrival < current.arrival)) {
                    it.previous();
                    break;
                }
            }
            it.add(patient);
        }

        Patient next() {
            return items.pollFirst();
        }
    }

    // Alternative 2: Five FIFO queues, one for each severity.
    static class FiveLevelQueue {
        @SuppressWarnings("unchecked")
        private final Queue<Patient>[] levels =
            (Queue<Patient>[]) new Queue<?>[6];

        FiveLevelQueue() {
            for (int i = 1; i <= 5; i++) {
                levels[i] = new ArrayDeque<Patient>();
            }
        }

        void admit(Patient patient) {
            levels[patient.severity].add(patient);
        }

        Patient next() {
            for (int severity = 1; severity <= 5; severity++) {
                Patient p = levels[severity].poll();
                if (p != null) return p;
            }
            return null;
        }
    }

    private static List<Patient> makePatients(int n, long seed) {
        Random random = new Random(seed);
        List<Patient> patients = new ArrayList<Patient>();
        for (int i = 0; i < n; i++) {
            patients.add(new Patient(
                "P" + i, 1 + random.nextInt(5), i));
        }
        return patients;
    }

    private static long drainPriorityQueue(List<Patient> data) {
        PriorityQueue<Patient> q = new PriorityQueue<Patient>(
            Comparator.comparingInt((Patient p) -> p.severity)
                      .thenComparingLong(p -> p.arrival));
        long start = System.nanoTime();
        q.addAll(data);
        long admitted = System.nanoTime();
        while (q.poll() != null) { /* serve all */ }
        long finished = System.nanoTime();
        benchmarkAdmitNanos += admitted - start;
        benchmarkServeNanos += finished - admitted;
        return finished - start;
    }

    private static long benchmarkAdmitNanos;
    private static long benchmarkServeNanos;

    private static void benchmark() {
        int[] sizes = {500, 2000, 5000};
        int repetitions = 5;
        System.out.println("\nGROWTH BENCHMARK (average milliseconds)");
        System.out.println("Strategy,Size,AverageAdmitMs,AverageServeMs");

        try (java.io.PrintWriter out =
                 new java.io.PrintWriter("task6_results.csv")) {
            out.println("strategy,size,repetition,admit_ns,serve_ns,admit_ms,serve_ms");

            for (int size : sizes) {
                long pqAdmit = 0, pqServe = 0;
                long sortedAdmit = 0, sortedServe = 0;
                long levelsAdmit = 0, levelsServe = 0;

                for (int rep = 1; rep <= repetitions; rep++) {
                    List<Patient> data = makePatients(size, 6000L + size);

                    // PriorityQueue
                    PriorityQueue<Patient> pq = new PriorityQueue<Patient>(
                        Comparator.comparingInt((Patient p) -> p.severity)
                                  .thenComparingLong(p -> p.arrival));
                    long start = System.nanoTime();
                    for (Patient p : data) pq.add(p);
                    long mid = System.nanoTime();
                    while (pq.poll() != null) { }
                    long end = System.nanoTime();
                    long a = mid - start, s = end - mid;
                    pqAdmit += a; pqServe += s;
                    out.printf(java.util.Locale.US,
                        "PriorityQueue,%d,%d,%d,%d,%.6f,%.6f%n",
                        size, rep, a, s, a/1e6, s/1e6);

                    // Sorted LinkedList
                    SortedLinkedQueue sorted = new SortedLinkedQueue();
                    start = System.nanoTime();
                    for (Patient p : data) sorted.admit(p);
                    mid = System.nanoTime();
                    while (sorted.next() != null) { }
                    end = System.nanoTime();
                    a = mid - start; s = end - mid;
                    sortedAdmit += a; sortedServe += s;
                    out.printf(java.util.Locale.US,
                        "SortedLinkedList,%d,%d,%d,%d,%.6f,%.6f%n",
                        size, rep, a, s, a/1e6, s/1e6);

                    // Five FIFO queues
                    FiveLevelQueue levels = new FiveLevelQueue();
                    start = System.nanoTime();
                    for (Patient p : data) levels.admit(p);
                    mid = System.nanoTime();
                    while (levels.next() != null) { }
                    end = System.nanoTime();
                    a = mid - start; s = end - mid;
                    levelsAdmit += a; levelsServe += s;
                    out.printf(java.util.Locale.US,
                        "FiveFIFOQueues,%d,%d,%d,%d,%.6f,%.6f%n",
                        size, rep, a, s, a/1e6, s/1e6);
                }

                System.out.printf(java.util.Locale.US,
                    "PriorityQueue,%d,%.4f,%.4f%n",
                    size, pqAdmit/5e6, pqServe/5e6);
                System.out.printf(java.util.Locale.US,
                    "SortedLinkedList,%d,%.4f,%.4f%n",
                    size, sortedAdmit/5e6, sortedServe/5e6);
                System.out.printf(java.util.Locale.US,
                    "FiveFIFOQueues,%d,%.4f,%.4f%n",
                    size, levelsAdmit/5e6, levelsServe/5e6);
            }
        } catch (java.io.IOException ex) {
            System.out.println("CSV write failed: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("ASSIGNMENT 2 - Q6: HOSPITAL EMERGENCY TRIAGE");

        Triage er = new Triage();
        er.admit("Ravi", 3);
        er.admit("Meena", 1);
        er.admit("John", 3);
        er.admit("Asha", 5);
        er.admit("Kumar", 1);
        er.admit("Lata", 2);

        // Two extra patients make the hand-check dataset eight patients.
        er.admit("Nila", 2);
        er.admit("Arun", 4);

        List<Patient> waiting = er.waitingListInTreatmentOrder();
        print("TEST 1 - EIGHT-PATIENT WAITING LIST (TREATMENT ORDER)",
              waiting);

        String[] expected = {
            "Meena(S1)", "Kumar(S1)", "Lata(S2)", "Nila(S2)",
            "Ravi(S3)", "John(S3)", "Arun(S4)", "Asha(S5)"
        };
        System.out.println("Hand-verified treatment order: "
            + (verify(waiting, expected) ? "PASS" : "FAIL"));

        System.out.println("\nTEST 2 - COMPLETE THE CODE DATASET");
        Triage supplied = new Triage();
        supplied.admit("Ravi", 3);
        supplied.admit("Meena", 1);
        supplied.admit("John", 3);
        supplied.admit("Asha", 5);
        supplied.admit("Kumar", 1);
        supplied.admit("Lata", 2);
        StringBuilder order = new StringBuilder();
        Patient p;
        while ((p = supplied.next()) != null) {
            order.append(p).append(' ');
        }
        System.out.println(order.toString().trim());
        System.out.println("Matches assignment expected output: "
            + order.toString().trim().equals(
                "Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) "
                + "John(S3) Asha(S5)"));

        System.out.println("\nTEST 3 - ALTERNATIVE QUEUE CONSISTENCY");
        List<Patient> data = makePatients(8, 88L);
        SortedLinkedQueue sorted = new SortedLinkedQueue();
        FiveLevelQueue levels = new FiveLevelQueue();
        PriorityQueue<Patient> pq = new PriorityQueue<Patient>(
            Comparator.comparingInt((Patient x) -> x.severity)
                      .thenComparingLong(x -> x.arrival));
        for (Patient x : data) {
            sorted.admit(x); levels.admit(x); pq.add(x);
        }
        List<String> pqOrder = new ArrayList<String>();
        List<String> sortedOrder = new ArrayList<String>();
        List<String> levelOrder = new ArrayList<String>();
        while (!pq.isEmpty()) pqOrder.add(pq.poll().toString());
        while (sorted.next() != null) { /* order checked below by rebuilding */ }
        SortedLinkedQueue sorted2 = new SortedLinkedQueue();
        for (Patient x : data) sorted2.admit(x);
        Patient x;
        while ((x = sorted2.next()) != null) sortedOrder.add(x.toString());
        while ((x = levels.next()) != null) levelOrder.add(x.toString());
        System.out.println("Sorted LinkedList matches PriorityQueue: "
            + pqOrder.equals(sortedOrder));
        System.out.println("Five FIFO queues match PriorityQueue: "
            + pqOrder.equals(levelOrder));

        System.out.println("\nWAITING-LIST DISPLAY NOTE");
        System.out.println("PriorityQueue.toString() is heap-layout order, "
            + "not guaranteed treatment order.");
        System.out.println("Use a copied PriorityQueue and repeatedly poll "
            + "to display the waiting list in treatment order.");

        System.out.println("\nFAIRNESS POLICY");
        System.out.println("Apply waiting-time aging: after each fixed waiting "
            + "interval, improve a patient's effective severity by one level, "
            + "up to severity 1; rebuild/reinsert to restore heap order.");

        benchmark();
        System.out.println("\nRaw benchmark data saved to task6_results.csv");
    }
}
