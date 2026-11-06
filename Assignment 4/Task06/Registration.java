import java.time.LocalDateTime;

public class Registration {
    private final long id;
    private final String studentId;
    private final String studentName;
    private final String email;
    private final String eventName;
    private final LocalDateTime registeredAt;

    public Registration(long id, String studentId, String studentName,
                        String email, String eventName, LocalDateTime registeredAt) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.eventName = eventName;
        this.registeredAt = registeredAt;
    }

    public long getId() { return id; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getEmail() { return email; }
    public String getEventName() { return eventName; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
}
