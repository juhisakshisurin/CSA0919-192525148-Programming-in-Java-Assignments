import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Assignment 5 - Part B - Task B3
 * MySQL JDBC comparison: Statement, PreparedStatement and PreparedStatement batch.
 *
 * Configure with environment variables:
 *   DB_URL  (default jdbc:mysql://localhost:3306/assignment5_b3)
 *   DB_USER (default root)
 *   DB_PASSWORD (default empty)
 */
public class B3Runner {
    private static final String DB_URL = env("DB_URL", "jdbc:mysql://localhost:3306/assignment5_b3");
    private static final String DB_USER = env("DB_USER", "root");
    private static final String DB_PASSWORD = env("DB_PASSWORD", "");

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value == null ? fallback : value;
    }

    public static void main(String[] args) throws Exception {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("DATABASE=" + con.getMetaData().getURL());
            createTable(con);
            runSmallCorrectnessTests(con);
            runBenchmarks(con);
            runAutoCommitVsTransaction(con);
        }
    }

    private static void createTable(Connection con) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS bus_pass (" +
                "pass_id VARCHAR(24) PRIMARY KEY," +
                "reg_no VARCHAR(20) NOT NULL," +
                "name VARCHAR(80) NOT NULL," +
                "route_no INT NOT NULL," +
                "amount DECIMAL(10,2) NOT NULL)";
        try (Statement st = con.createStatement()) {
            st.executeUpdate(sql);
        }
    }

    private static void clearTable(Connection con) throws SQLException {
        try (Statement st = con.createStatement()) {
            st.executeUpdate("DELETE FROM bus_pass");
        }
    }

    private static List<String[]> generatePasses(int count, String prefix) {
        List<String[]> rows = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            rows.add(new String[] {
                prefix + String.format("%06d", i),
                String.format("1925%06d", i % 1000000),
                "Student" + i,
                String.valueOf((i % 20) + 1),
                String.format("%.2f", 2500.0 + (i % 500) * 10.0)
            });
        }
        return rows;
    }

    private static void runSmallCorrectnessTests(Connection con) throws SQLException {
        System.out.println("\n=== B3.1 SMALL-DATA CORRECTNESS: 10 ROWS EACH ===");

        clearTable(con);
        List<String[]> ten = generatePasses(10, "ST");
        insertWithStatement(con, ten);
        verifyTable(con, "Statement", 10);

        clearTable(con);
        insertWithPreparedStatement(con, ten);
        verifyTable(con, "PreparedStatement", 10);

        clearTable(con);
        PassLoader.loadAll(con, ten);
        verifyTable(con, "PreparedStatement batch", 10);
    }

    private static void verifyTable(Connection con, String label, int expected) throws SQLException {
        int count = 0;
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT pass_id, reg_no, name, route_no, amount FROM bus_pass ORDER BY pass_id")) {
            ResultSetMetaData md = rs.getMetaData();
            System.out.println("VERIFY_METHOD=" + label);
            for (int i = 1; i <= md.getColumnCount(); i++) {
                System.out.println("COLUMN_" + i + "=" + md.getColumnName(i) +
                        " | TYPE=" + md.getColumnTypeName(i) +
                        " | JAVA_CLASS=" + md.getColumnClassName(i));
            }
            while (rs.next()) {
                count++;
                if (count <= 2) {
                    System.out.println("ROW=" + rs.getString("pass_id") + "," +
                            rs.getString("reg_no") + "," + rs.getString("name") + "," +
                            rs.getInt("route_no") + "," + rs.getBigDecimal("amount"));
                }
            }
        }
        System.out.println("ROW_COUNT=" + count + " | EXPECTED=" + expected +
                " | PASS=" + (count == expected));
    }

    private static void insertWithStatement(Connection con, List<String[]> rows) throws SQLException {
        String sql = "INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES ('%s','%s','%s',%s,%s)";
        try (Statement st = con.createStatement()) {
            for (String[] p : rows) {
                // Generated values contain no apostrophes; real user input must not be concatenated.
                String q = String.format(sql, p[0], p[1], p[2], p[3], p[4]);
                st.executeUpdate(q);
            }
        }
    }

    private static void insertWithPreparedStatement(Connection con, List<String[]> rows) throws SQLException {
        String sql = "INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (String[] p : rows) {
                bind(ps, p);
                ps.executeUpdate();
            }
        }
    }

    private static void bind(PreparedStatement ps, String[] p) throws SQLException {
        ps.setString(1, p[0]);
        ps.setString(2, p[1]);
        ps.setString(3, p[2]);
        ps.setInt(4, Integer.parseInt(p[3]));
        ps.setBigDecimal(5, new java.math.BigDecimal(p[4]));
    }

    private static long timed(RunnableSql action) throws SQLException {
        long start = System.nanoTime();
        action.run();
        return (System.nanoTime() - start) / 1_000_000;
    }

    @FunctionalInterface
    private interface RunnableSql { void run() throws SQLException; }

    private static void runBenchmarks(Connection con) throws SQLException {
        System.out.println("\n=== B3.2 INSERT PERFORMANCE (MILLISECONDS) ===");
        System.out.println("SIZE,STATEMENT_MS,PREPARED_MS,BATCH_MS");
        for (int size : new int[] {1000, 10000, 100000}) {
            List<String[]> rows = generatePasses(size, "N" + size + "_");
            clearTable(con);
            long statementMs = timed(() -> insertWithStatement(con, rows));
            clearTable(con);
            long preparedMs = timed(() -> insertWithPreparedStatement(con, rows));
            clearTable(con);
            long batchMs = timed(() -> PassLoader.loadAll(con, rows));
            System.out.println(size + "," + statementMs + "," + preparedMs + "," + batchMs);
            verifyCountOnly(con, size);
        }
    }

    private static void verifyCountOnly(Connection con, int expected) throws SQLException {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM bus_pass")) {
            rs.next();
            System.out.println("BENCHMARK_VERIFY expected=" + expected + " actual=" + rs.getInt(1));
        }
    }

    private static void runAutoCommitVsTransaction(Connection con) throws SQLException {
        System.out.println("\n=== B3.3 AUTO-COMMIT VS SINGLE TRANSACTION ===");
        final int total = 10000;
        final int batchSize = 1000;
        List<String[]> rows = generatePasses(total, "FAIL_");
        // Place an invalid amount at the first row of batch 6 (after 5 committed batches).
        rows.set(5000, new String[] {"FAIL_INVALID", "192500000", "InvalidAmount", "7", "NOT_A_NUMBER"});

        clearTable(con);
        con.setAutoCommit(true);
        try {
            insertInBatches(con, rows, batchSize);
            System.out.println("AUTOCOMMIT_FAILURE=NOT_TRIGGERED");
        } catch (SQLException ex) {
            System.out.println("AUTOCOMMIT_ERROR=" + ex.getClass().getSimpleName());
            System.out.println("AUTOCOMMIT_MESSAGE=" + safeMessage(ex));
        }
        System.out.println("AUTOCOMMIT_ROWS_REMAINING=" + countRows(con));

        clearTable(con);
        boolean oldAutoCommit = con.getAutoCommit();
        con.setAutoCommit(false);
        try {
            insertInBatches(con, rows, batchSize);
            con.commit();
            System.out.println("TRANSACTION_FAILURE=NOT_TRIGGERED");
        } catch (SQLException | RuntimeException ex) {
            con.rollback();
            System.out.println("TRANSACTION_ERROR=" + ex.getClass().getSimpleName());
            System.out.println("TRANSACTION_MESSAGE=" + ex.getClass().getSimpleName());
        } finally {
            con.setAutoCommit(oldAutoCommit);
        }
        System.out.println("TRANSACTION_ROWS_REMAINING=" + countRows(con));
        System.out.println("EXPECTED_PATTERN: autocommit retains earlier committed batches; transaction rollback leaves 0 rows.");
    }

    private static void insertInBatches(Connection con, List<String[]> rows, int batchSize) throws SQLException {
        String sql = "INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            int pending = 0;
            for (String[] p : rows) {
                bind(ps, p); // invalid decimal throws before this row is added
                ps.addBatch();
                pending++;
                if (pending == batchSize) {
                    ps.executeBatch();
                    ps.clearBatch();
                    pending = 0;
                }
            }
            if (pending > 0) ps.executeBatch();
        }
    }

    private static int countRows(Connection con) throws SQLException {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM bus_pass")) {
            rs.next();
            return rs.getInt(1);
        }
    }

    private static String safeMessage(SQLException ex) {
        String m = ex.getMessage();
        return m == null ? "(no message)" : m.replace('\n', ' ');
    }
}
