import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static final String URL =
            System.getenv().getOrDefault("EVENT_DB_URL",
                    "jdbc:mysql://localhost:3306/campus_events?useSSL=false&serverTimezone=UTC");
    private static final String USER =
            System.getenv().getOrDefault("EVENT_DB_USER", "root");
    private static final String PASSWORD =
            System.getenv().getOrDefault("EVENT_DB_PASSWORD", "");

    private Database() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
