import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationRepository {
    public void insert(String studentId, String studentName, String email,
                       String eventName) throws SQLException {
        String sql = "INSERT INTO registrations "
                + "(student_id, student_name, email, event_name) VALUES (?, ?, ?, ?)";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, studentName);
            ps.setString(3, email);
            ps.setString(4, eventName);
            ps.executeUpdate();
        }
    }

    public List<Registration> findPage(String eventFilter, int limit, int offset)
            throws SQLException {
        boolean all = eventFilter == null || eventFilter.equals("All events");
        String sql = "SELECT id, student_id, student_name, email, event_name, registered_at "
                + "FROM registrations "
                + (all ? "" : "WHERE event_name = ? ")
                + "ORDER BY registered_at DESC, id DESC LIMIT ? OFFSET ?";

        List<Registration> rows = new ArrayList<>();
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int p = 1;
            if (!all) ps.setString(p++, eventFilter);
            ps.setInt(p++, limit);
            ps.setInt(p, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp("registered_at");
                    rows.add(new Registration(
                            rs.getLong("id"),
                            rs.getString("student_id"),
                            rs.getString("student_name"),
                            rs.getString("email"),
                            rs.getString("event_name"),
                            ts == null ? null : ts.toLocalDateTime()));
                }
            }
        }
        return rows;
    }

    public int count(String eventFilter) throws SQLException {
        boolean all = eventFilter == null || eventFilter.equals("All events");
        String sql = "SELECT COUNT(*) FROM registrations "
                + (all ? "" : "WHERE event_name = ?");
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (!all) ps.setString(1, eventFilter);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public List<String> findEventNames() throws SQLException {
        List<String> names = new ArrayList<>();
        String sql = "SELECT DISTINCT event_name FROM registrations ORDER BY event_name";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) names.add(rs.getString(1));
        }
        return names;
    }

    public void exportAll(String eventFilter, java.io.File file) throws Exception {
        boolean all = eventFilter == null || eventFilter.equals("All events");
        String sql = "SELECT id, student_id, student_name, email, event_name, registered_at "
                + "FROM registrations " + (all ? "" : "WHERE event_name = ? ")
                + "ORDER BY registered_at DESC, id DESC";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            if (!all) ps.setString(1, eventFilter);
            try (ResultSet rs = ps.executeQuery();
                 java.io.PrintWriter out = new java.io.PrintWriter(
                         new java.io.OutputStreamWriter(
                                 new java.io.FileOutputStream(file), java.nio.charset.StandardCharsets.UTF_8))) {
                out.println("id,student_id,student_name,email,event_name,registered_at");
                while (rs.next()) {
                    out.println(csv(rs.getString("id")) + "," + csv(rs.getString("student_id")) + ","
                            + csv(rs.getString("student_name")) + "," + csv(rs.getString("email")) + ","
                            + csv(rs.getString("event_name")) + "," + csv(rs.getString("registered_at")));
                }
            }
        }
    }

    private String csv(String value) {
        if (value == null) return "";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
