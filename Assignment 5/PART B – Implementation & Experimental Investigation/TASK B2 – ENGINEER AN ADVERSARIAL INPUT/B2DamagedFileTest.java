import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class B2DamagedFileTest {
    public static void main(String[] args) throws IOException {
        Path file = Path.of("input", "damaged_passes.csv");
        int valid = 0, wrongFields = 0, invalidAmount = 0, empty = 0, lineNo = 0;

        try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.isBlank()) {
                    empty++;
                    System.out.println("LINE_" + lineNo + "=SKIPPED_EMPTY");
                    continue;
                }

                String[] fields = line.split(",", -1);
                if (fields.length != 5) {
                    wrongFields++;
                    System.out.println("LINE_" + lineNo +
                            "=SKIPPED_WRONG_FIELD_COUNT(" + fields.length + ")");
                    continue;
                }

                try {
                    Integer.parseInt(fields[3].trim());
                    double amount = Double.parseDouble(fields[4].trim());
                    if (!Double.isFinite(amount) || amount < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    invalidAmount++;
                    System.out.println("LINE_" + lineNo + "=SKIPPED_INVALID_NUMERIC_FIELD");
                    continue;
                }

                valid++;
                System.out.println("LINE_" + lineNo + "=ACCEPTED " +
                        String.join("|", fields));
            }
        }

        System.out.println("SUMMARY valid=" + valid +
                ", wrongFields=" + wrongFields +
                ", invalidNumeric=" + invalidAmount + ", empty=" + empty);
    }
}
