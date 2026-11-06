import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class RegistrationService {
    private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final RegistrationRepository repository = new RegistrationRepository();

    public void register(String studentId, String studentName,
                         String email, String eventName) throws Exception {
        studentId = studentId == null ? "" : studentId.trim();
        studentName = studentName == null ? "" : studentName.trim();
        email = email == null ? "" : email.trim();
        eventName = eventName == null ? "" : eventName.trim();

        if (studentId.isEmpty() || studentName.isEmpty()
                || email.isEmpty() || eventName.isEmpty()) {
            throw new IllegalArgumentException("All fields are required.");
        }
        if (!EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }

        try {
            repository.insert(studentId, studentName, email, eventName);
        } catch (SQLException ex) {
            // MySQL duplicate-key error for UNIQUE(student_id, event_name).
            if ("23000".equals(ex.getSQLState())) {
                throw new IllegalArgumentException(
                        "This student is already registered for this event.");
            }
            throw ex;
        }
    }

    public List<Registration> getPage(String event, int limit, int offset)
            throws SQLException {
        return repository.findPage(event, limit, offset);
    }

    public int getCount(String event) throws SQLException {
        return repository.count(event);
    }

    public List<String> getEventNames() throws SQLException {
        return repository.findEventNames();
    }

    public void export(String event, java.io.File file) throws Exception {
        repository.exportAll(event, file);
    }
}
