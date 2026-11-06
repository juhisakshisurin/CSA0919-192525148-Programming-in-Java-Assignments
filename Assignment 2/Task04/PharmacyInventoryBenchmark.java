import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class PharmacyInventoryBenchmark {
    private static final int[] SIZES = {1000, 10000, 50000};
    private static final int LOOKUPS = 100000;
    private static final int REPETITIONS = 5;
    private static volatile long blackhole;

    static class Medicine {
        final String code;
        final String name;
        int stock;

        Medicine(String code, String name, int stock) {
            this.code = code;
            this.name = name;
            this.stock = stock;
        }

        @Override
        public String toString() {
            return code + " | " + name + " | stock=" + stock;
        }
    }

    static class Inventory {
        final Map<String, Medicine> map;
        final List<String> insertionOrder;

        Inventory(String type, List<Medicine> data) {
            if ("HashMap".equals(type)) {
                map = new HashMap<String, Medicine>();
            } else if ("TreeMap".equals(type)) {
                map = new TreeMap<String, Medicine>();
            } else {
                map = new LinkedHashMap<String, Medicine>();
            }
            insertionOrder = new ArrayList<String>();
            for (Medicine medicine : data) {
                map.put(medicine.code, medicine);
                insertionOrder.add(medicine.code);
            }
        }
    }

    static class Result {
        String mapType, operation, order;
        int size, repetition;
        long elapsedNanos, checksum;
        Result(String mapType, int size, String operation, String order,
               int repetition, long elapsedNanos, long checksum) {
            this.mapType=mapType; this.size=size; this.operation=operation;
            this.order=order; this.repetition=repetition;
            this.elapsedNanos=elapsedNanos; this.checksum=checksum;
        }
        double millis() { return elapsedNanos / 1_000_000.0; }
        double nanosPerOperation() {
            long count = "Lookup100000".equals(operation) ? LOOKUPS :
                         ("Put".equals(operation) ? size : size);
            return elapsedNanos / (double) count;
        }
    }

    private static List<Medicine> createData(int n) {
        List<Medicine> data = new ArrayList<Medicine>();
        // Permuted insertion order makes insertion order visibly different from code order.
        for (int i = 0; i < n; i++) {
            int id = (i * 7919) % n;
            String code = String.format(Locale.ROOT, "MED%06d", id);
            data.add(new Medicine(code, "Medicine-" + id, 50 + (id % 450)));
        }
        return data;
    }

    private static int[] createLookupIndexes(int n) {
        int[] indexes = new int[LOOKUPS];
        Random random = new Random(20261106L + n);
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = random.nextInt(n);
        }
        return indexes;
    }

    private static long lookup(Inventory inventory, List<Medicine> data,
                               int[] indexes) {
        long sum = 0;
        for (int index : indexes) {
            String code = data.get(index).code;
            Medicine found = inventory.map.get(code);
            if (found != null) sum += found.stock + found.code.length();
        }
        return sum;
    }

    private static long codeOrderReport(Inventory inventory, String type) {
        long checksum = 0;
        if ("TreeMap".equals(type)) {
            for (Map.Entry<String, Medicine> entry : inventory.map.entrySet()) {
                checksum += entry.getKey().hashCode() + entry.getValue().stock;
            }
        } else {
            List<Map.Entry<String, Medicine>> entries =
                new ArrayList<Map.Entry<String, Medicine>>(inventory.map.entrySet());
            Collections.sort(entries, new Comparator<Map.Entry<String, Medicine>>() {
                public int compare(Map.Entry<String, Medicine> a,
                                   Map.Entry<String, Medicine> b) {
                    return a.getKey().compareTo(b.getKey());
                }
            });
            for (Map.Entry<String, Medicine> entry : entries) {
                checksum += entry.getKey().hashCode() + entry.getValue().stock;
            }
        }
        return checksum;
    }

    private static long insertionOrderReport(Inventory inventory, String type) {
        long checksum = 0;
        if ("LinkedHashMap".equals(type)) {
            for (Map.Entry<String, Medicine> entry : inventory.map.entrySet()) {
                checksum += entry.getKey().hashCode() + entry.getValue().stock;
            }
        } else {
            for (String code : inventory.insertionOrder) {
                Medicine medicine = inventory.map.get(code);
                checksum += code.hashCode() + medicine.stock;
            }
        }
        return checksum;
    }

    private static void record(List<Result> results, String type, int n,
                               String operation, String order, int rep,
                               long start, long checksum) {
        long elapsed = System.nanoTime() - start;
        blackhole = blackhole * 31 + checksum;
        results.add(new Result(type,n,operation,order,rep,elapsed,checksum));
    }

    private static void benchmarkMap(String type, int n, List<Medicine> data,
                                     int[] indexes, List<Result> results) {
        Inventory inventory = new Inventory(type, data);

        // Warm-up lookup and report paths
        lookup(inventory, data, indexes);
        codeOrderReport(inventory, type);
        insertionOrderReport(inventory, type);

        for (int rep=1; rep<=REPETITIONS; rep++) {
            long start=System.nanoTime();
            long sum=lookup(inventory,data,indexes);
            record(results,type,n,"Lookup100000","-",rep,start,sum);

            start=System.nanoTime();
            sum=codeOrderReport(inventory,type);
            record(results,type,n,"CodeOrderReport","Code ascending",rep,start,sum);

            start=System.nanoTime();
            sum=insertionOrderReport(inventory,type);
            record(results,type,n,"InsertionOrderReport","Insertion order",rep,start,sum);
        }

        // Measure population time as supplementary evidence.
        for (int rep=1; rep<=REPETITIONS; rep++) {
            long start=System.nanoTime();
            Inventory fresh=new Inventory(type,data);
            long sum=fresh.map.size();
            record(results,type,n,"Put","-",rep,start,sum);
        }
    }

    private static boolean verifyOrders(int n, List<Medicine> data) {
        Inventory hash = new Inventory("HashMap",data);
        Inventory tree = new Inventory("TreeMap",data);
        Inventory linked = new Inventory("LinkedHashMap",data);

        String previous=null;
        for (String key: tree.map.keySet()) {
            if (previous!=null && previous.compareTo(key)>0) return false;
            previous=key;
        }

        int i=0;
        for (String key: linked.map.keySet()) {
            if (!key.equals(data.get(i++).code)) return false;
        }

        // HashMap report order is verified after sorting its keys.
        List<String> keys=new ArrayList<String>(hash.map.keySet());
        Collections.sort(keys);
        previous=null;
        for (String key:keys) {
            if (previous!=null && previous.compareTo(key)>0) return false;
            previous=key;
        }
        return true;
    }

    private static void writeCsv(String path, List<Result> results)
            throws IOException {
        BufferedWriter writer=new BufferedWriter(new FileWriter(path));
        try {
            writer.write("map,size,operation,report_order,repetition,elapsed_ns,elapsed_ms,ns_per_operation,checksum");
            writer.newLine();
            for (Result r:results) {
                writer.write(r.mapType+","+r.size+","+r.operation+","+r.order+","
                    +r.repetition+","+r.elapsedNanos+","
                    +String.format(Locale.US,"%.6f",r.millis())+","
                    +String.format(Locale.US,"%.3f",r.nanosPerOperation())+","
                    +r.checksum);
                writer.newLine();
            }
        } finally { writer.close(); }
    }

    private static void printSummary(List<Result> results) {
        System.out.println("\nAVERAGE MEASUREMENTS ("+REPETITIONS+" repetitions)");
        System.out.printf("%-14s %-8s %-22s %14s %14s%n",
            "Map","Medicines","Operation","Average ms","Average ns/op");
        for (String type:new String[]{"HashMap","TreeMap","LinkedHashMap"}) {
            for (int n:SIZES) {
                for (String op:new String[]{"Put","Lookup100000",
                    "CodeOrderReport","InsertionOrderReport"}) {
                    long total=0; int count=0; long ops=op.equals("Lookup100000")?LOOKUPS:n;
                    for (Result r:results) {
                        if (r.mapType.equals(type)&&r.size==n&&r.operation.equals(op)) {
                            total+=r.elapsedNanos; count++;
                        }
                    }
                    if (count>0) {
                        double avg=total/(double)count;
                        System.out.printf(Locale.US,"%-14s %-8d %-22s %14.4f %14.2f%n",
                            type,n,op,avg/1_000_000.0,avg/ops);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String csvPath=args.length>0?args[0]:"task4_results.csv";
        List<Result> results=new ArrayList<Result>();
        System.out.println("Assignment 2 (CO2) - Q4: Pharmacy Map Benchmark");
        System.out.println("Java version: "+System.getProperty("java.version"));
        System.out.println("Inventory sizes: 1000, 10000, 50000");
        System.out.println("Lookups per measured run: "+LOOKUPS);
        System.out.println("Measured repetitions: "+REPETITIONS);

        for (int n:SIZES) {
            List<Medicine> data=createData(n);
            int[] indexes=createLookupIndexes(n);
            System.out.println("\n===== MEDICINES: "+n+" =====");
            System.out.println("Report order verification: "
                +(verifyOrders(n,data)?"PASS":"FAIL"));
            for (String type:new String[]{"HashMap","TreeMap","LinkedHashMap"}) {
                benchmarkMap(type,n,data,indexes,results);
            }
        }

        printSummary(results);
        try {
            writeCsv(csvPath,results);
            System.out.println("\nRaw measurements saved to: "+csvPath);
            System.out.println("Checksum: "+blackhole);
        } catch(IOException e) {
            System.err.println("CSV write failed: "+e.getMessage());
        }
        System.out.println("Note: timings depend on machine, JVM and system load.");
    }
}
