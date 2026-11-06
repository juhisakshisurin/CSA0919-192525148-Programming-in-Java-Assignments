import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.List;

/**
 * Assignment 5 Part B - Task B2.
 * Demonstrates an intentionally vulnerable concatenated login query versus
 * a parameterized PreparedStatement, then validates a damaged pass file.
 * Uses H2 only as a local assignment test database.
 */
public class B2AdversarialTest {
    private static final String DB_URL =
            "jdbc:h2:file:./data/b2_security;DB_CLOSE_ON_EXIT=FALSE";

    public static void main(String[] args) throws Exception {
        Files.createDirectories(Path.of("data"));
        System.out.println("=== B2.1 SQL INJECTION TEST ===");

        try (Connection con = DriverManager.getConnection(DB_URL, "sa", "")) {
            createTestDatabase(con);

            String attackId = "' OR '1'='1' -- ";
            String wrongPassword = "definitely-wrong";

            System.out.println("ATTACK_ID=" + attackId);
            System.out.println("WRONG_PASSWORD=" + wrongPassword);
            System.out.println("UNSAFE_LOGIN=" +
                    unsafeLogin(con, attackId, wrongPassword));
            System.out.println("PREPARED_LOGIN=" +
                    preparedLogin(con, attackId, wrongPassword));
            System.out.println("VALID_LOGIN=" +
                    preparedLogin(con, "student01", "pass123"));
        }

        System.out.println();
        System.out.println("=== B2.2 DAMAGED BUS-PASS FILE TEST ===");
        Path file = Path.of("input", "damaged_passes.csv");
        createDamagedFile(file);
        inspectPassFile(file);
    }

    private static void createTestDatabase(Connection con) throws SQLException {
        try (Statement st = con.createStatement()) {
            st.execute("DROP TABLE IF EXISTS users");
            st.execute("CREATE TABLE users (" +
                    "id VARCHAR(50) PRIMARY KEY, " +
                    "pwd VARCHAR(100) NOT NULL)");
        }
        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(id, pwd) VALUES (?, ?)")) {
            ps.setString(1, "student01");
            ps.setString(2, "pass123");
            ps.executeUpdate();
        }
    }

    // Intentionally vulnerable: user-controlled values become SQL syntax.
    private static boolean unsafeLogin(Connection con, String id, String pwd)
            throws SQLException {
        String sql = "SELECT * FROM users WHERE id='" + id +
                "' AND pwd='" + pwd + "'";
        System.out.println("UNSAFE_SQL=" + sql);
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next();
        }
    }

    // Safe version: input values are bound separately from the SQL structure.
    private static boolean preparedLogin(Connection con, String id, String pwd)
            throws SQLException {
        String sql = "SELECT * FROM users WHERE id=? AND pwd=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, pwd);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static void createDamagedFile(Path file) throws IOException {
        Files.createDirectories(file.getParent());
        Files.write(file, List.of(
                "P2001,192525101,Asha,7,3800.0",
                "P2002,192525102,WrongFieldCount,7",
                "P2003,192525103,Ravi,7,three-eight",
                "",
                "P2004,192525104,Meena,12,4200.0"
        ), StandardCharsets.UTF_8);
    }

    private static void inspectPassFile(Path file) throws IOException {
        int valid = 0, wrongFields = 0, invalidRoute = 0;
        int invalidAmount = 0, empty = 0, lineNo = 0;

        try (BufferedReader reader =
                     Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
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
                } catch (NumberFormatException ex) {
                    invalidRoute++;
                    System.out.println("LINE_" + lineNo + "=SKIPPED_INVALID_ROUTE");
                    continue;
                }

                try {
                    double amount = Double.parseDouble(fields[4].trim());
                    if (!Double.isFinite(amount) || amount < 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    invalidAmount++;
                    System.out.println("LINE_" + lineNo +
                            "=SKIPPED_NON_NUMERIC_OR_INVALID_AMOUNT");
                    continue;
                }

                valid++;
                System.out.println("LINE_" + lineNo + "=ACCEPTED " +
                        String.join("|", fields));
            }
        }

        System.out.println("SUMMARY valid=" + valid +
                ", wrongFields=" + wrongFields +
                ", invalidRoute=" + invalidRoute +
                ", invalidAmount=" + invalidAmount +
                ", empty=" + empty);
    }
}
