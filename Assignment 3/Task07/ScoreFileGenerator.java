import java.io.IOException;
import java.nio.file.*;
import java.util.Locale;

/** Creates deterministic score data for 10,000 expected file names. */
public class ScoreFileGenerator {
    private static final int DEFAULT_TOTAL = 10_000;

    public static void main(String[] args) throws IOException {
        Path directory = args.length == 0 ? Paths.get("score_data") : Paths.get(args[0]);
        int total = args.length >= 2 ? Integer.parseInt(args[1]) : DEFAULT_TOTAL;
        Files.createDirectories(directory);

        for (int i = 1; i <= total; i++) {
            String name = String.format(Locale.ROOT, "score_%05d.txt", i);
            Path file = directory.resolve(name);

            // Missing file cases: 100 files (indices divisible by 100).
            if (i % 100 == 0) {
                Files.deleteIfExists(file);
                continue;
            }

            // Corrupt content cases: 100 files (indices divisible by 97, excluding missing).
            if (i % 97 == 0) {
                Files.write(file, ("85\nnot-a-number\n90\n").getBytes());
                continue;
            }

            // Out-of-range score cases: 100 files (indices divisible by 89, excluding above).
            if (i % 89 == 0) {
                Files.write(file, ("85\n140\n90\n").getBytes());
                continue;
            }

            int a = 50 + (i % 51);
            int b = 45 + ((i * 3) % 56);
            int c = 40 + ((i * 7) % 61);
            String content = a + "\n" + b + "\n" + c + "\n";
            Files.write(file, content.getBytes());
        }

        System.out.println("Generated deterministic dataset for " + total +
                " expected files in: " + directory.toAbsolutePath());
        System.out.println("Injected missing files: indices divisible by 100.");
        System.out.println("Injected corrupt files: indices divisible by 97 or 89 (except missing).");
    }
}
