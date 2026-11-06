import java.io.*;
import java.nio.file.*;
import java.util.*;

/** Five-field CSV store matching the supplied B1 starter code. */
public class PassStore {
    private final Path file;
    PassStore(Path file) { this.file = file; }

    public void add(String passId, String regNo, String name, int route, double amount) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            w.write(String.join(",", passId, regNo, name, String.valueOf(route), String.valueOf(amount)));
            w.newLine();
        }
    }

    /** TODO 1 completed: skip damaged rows and return matching five-field row, else null. */
    public String[] find(String passId) throws IOException {
        if (!Files.exists(file)) return null;
        try (BufferedReader r = Files.newBufferedReader(file)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length != 5) continue;
                if (fields[0].equals(passId)) return fields;
            }
        }
        return null;
    }

    public List<String[]> listByRoute(int route) throws IOException {
        List<String[]> result = new ArrayList<>();
        if (!Files.exists(file)) return result;
        try (BufferedReader r = Files.newBufferedReader(file)) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length != 5) continue;
                try {
                    if (Integer.parseInt(fields[3]) == route) result.add(fields);
                } catch (NumberFormatException ignored) { }
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        Path f = Files.createTempFile("passes", ".csv");
        PassStore store = new PassStore(f);
        store.add("P1001", "192311001", "Arun", 12, 4500);
        Files.writeString(f, "P1002,DAMAGED LINE\n", StandardOpenOption.APPEND);
        store.add("P1003", "192311003", "Divya", 7, 3800);
        System.out.println(Arrays.toString(store.find("P1003")));
        System.out.println(Arrays.toString(store.find("P1002")));
        Files.delete(f);
    }
}
