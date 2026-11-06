import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

/**
 * Assignment 5 Part B - Task B4.
 * Reads five-field CSV pass records, validates each row, then inserts valid rows
 * in batches of 1,000 inside one JDBC transaction.
 *
 * Usage: java B4Migration [input-file] [campus-code]
 */
public class B4Migration {
    private static final int BATCH_SIZE = 1000;
    private static final String DB_URL = env("DB_URL",
            "jdbc:mysql://localhost:3306/assignment5_b4?useSSL=false&serverTimezone=UTC");
    private static final String DB_USER = env("DB_USER", "root");
    private static final String DB_PASSWORD = env("DB_PASSWORD", "");

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value == null ? fallback : value;
    }

    private static class Pass {
        String passId, regNo, name;
        int routeNo;
        BigDecimal amount;
        int lineNo;
        Pass(String[] f, int lineNo) {
            this.passId = f[0].trim();
            this.regNo = f[1].trim();
            this.name = f[2].trim();
            this.routeNo = Integer.parseInt(f[3].trim());
            this.amount = new BigDecimal(f[4].trim());
            this.lineNo = lineNo;
        }
    }

    private static class Rejected {
        int lineNo;
        String raw, reason;
        Rejected(int lineNo, String raw, String reason) {
            this.lineNo = lineNo; this.raw = raw; this.reason = reason;
        }
    }

    public static void main(String[] args) {
        Path input = Paths.get(args.length > 0 ? args[0] : "input/passes_b4.csv");
        String campus = args.length > 1 ? args[1] : "CAMPUS01";
        Path report = Paths.get("output/validation_failures.csv");

        try {
            Files.createDirectories(report.getParent());
            List<Pass> valid = new ArrayList<>();
            List<Rejected> rejected = validateFile(input, valid);
            writeFailureReport(report, rejected);

            System.out.println("Input file: " + input.toAbsolutePath());
            System.out.println("Campus code: " + campus);
            System.out.println("Rows accepted for migration: " + valid.size());
            System.out.println("Rows rejected during validation: " + rejected.size());
            System.out.println("Validation report: " + report.toAbsolutePath());

            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                createTableIfNeeded(con);
                int inserted = migrate(con, valid, campus);
                System.out.println("Rows committed to database: " + inserted);
                inspectDatabase(con);
                System.out.println("Migration status: SUCCESS");
            }
        } catch (Exception e) {
            System.err.println("Migration failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static List<Rejected> validateFile(Path file, List<Pass> valid)
            throws IOException {
        List<Rejected> rejected = new ArrayList<>();
        Set<String> seenIds = new HashSet<>();
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            int lineNo = 0;
            while ((line = reader.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) {
                    rejected.add(new Rejected(lineNo, line, "Empty record"));
                    continue;
                }
                String[] f = line.split(",", -1);
                if (f.length != 5) {
                    rejected.add(new Rejected(lineNo, line, "Expected exactly 5 comma-separated fields"));
                    continue;
                }
                try {
                    if (f[0].trim().isEmpty() || f[1].trim().isEmpty() || f[2].trim().isEmpty())
                        throw new IllegalArgumentException("Pass ID, register number and name are required");
                    int route = Integer.parseInt(f[3].trim());
                    if (route <= 0) throw new IllegalArgumentException("Route number must be positive");
                    BigDecimal amount = new BigDecimal(f[4].trim());
                    if (amount.signum() < 0) throw new IllegalArgumentException("Amount cannot be negative");
                    if (!seenIds.add(f[0].trim()))
                        throw new IllegalArgumentException("Duplicate pass ID in input file");
                    valid.add(new Pass(f, lineNo));
                } catch (NumberFormatException ex) {
                    rejected.add(new Rejected(lineNo, line, "Route number or amount is not numeric"));
                } catch (IllegalArgumentException ex) {
                    rejected.add(new Rejected(lineNo, line, ex.getMessage()));
                }
            }
        }
        return rejected;
    }

    private static void createTableIfNeeded(Connection con) throws SQLException {
        String ddl = "CREATE TABLE IF NOT EXISTS bus_pass (" +
                "pass_id VARCHAR(24) NOT NULL, reg_no VARCHAR(20) NOT NULL, " +
                "name VARCHAR(80) NOT NULL, route_no INT NOT NULL, " +
                "amount DECIMAL(10,2) NOT NULL, campus_code VARCHAR(20) NOT NULL, " +
                "PRIMARY KEY (campus_code, pass_id), INDEX idx_pass_reg(reg_no), " +
                "INDEX idx_pass_route(route_no)) ENGINE=InnoDB";
        try (Statement st = con.createStatement()) { st.executeUpdate(ddl); }
    }

    private static int migrate(Connection con, List<Pass> rows, String campus)
            throws SQLException {
        String sql = "INSERT INTO bus_pass(pass_id, reg_no, name, route_no, amount, campus_code) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        boolean oldAutoCommit = con.getAutoCommit();
        int inserted = 0;
        con.setAutoCommit(false);
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (Pass p : rows) {
                ps.setString(1, p.passId);
                ps.setString(2, p.regNo);
                ps.setString(3, p.name);
                ps.setInt(4, p.routeNo);
                ps.setBigDecimal(5, p.amount);
                ps.setString(6, campus);
                ps.addBatch();
                if (++inserted % BATCH_SIZE == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            if (inserted % BATCH_SIZE != 0) ps.executeBatch();
            con.commit();
            return inserted;
        } catch (SQLException | RuntimeException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(oldAutoCommit);
        }
    }

    private static void writeFailureReport(Path file, List<Rejected> rejected)
            throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            w.write("line_number,reason,original_record");
            w.newLine();
            for (Rejected r : rejected) {
                w.write(r.lineNo + ",\"" + csv(r.reason) + "\",\"" + csv(r.raw) + "\"");
                w.newLine();
            }
        }
    }

    private static String csv(String s) { return s.replace("\"", "\"\""); }

    private static void inspectDatabase(Connection con) throws SQLException {
        DatabaseMetaData md = con.getMetaData();
        System.out.println("\n--- DATABASE METADATA ---");
        System.out.println("Database product: " + md.getDatabaseProductName());
        System.out.println("Database version: " + md.getDatabaseProductVersion());
        System.out.println("JDBC driver: " + md.getDriverName());
        System.out.println("Driver version: " + md.getDriverVersion());
        System.out.println("Tables:");
        try (ResultSet rs = md.getTables(con.getCatalog(), null, "%", new String[]{"TABLE"})) {
            while (rs.next()) {
                System.out.println(" - " + rs.getString("TABLE_NAME"));
            }
        }
    }
}
