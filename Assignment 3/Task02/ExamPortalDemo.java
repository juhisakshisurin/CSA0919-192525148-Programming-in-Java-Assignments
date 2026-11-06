import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Checked base exception for examination-portal failures.
class ExamException extends Exception {
    ExamException(String message) {
        super(message);
    }

    ExamException(String message, Throwable cause) {
        super(message, cause);
    }
}

class ExamWindowClosedException extends ExamException {
    ExamWindowClosedException(String message) {
        super(message);
    }
}

class CourseNotRegisteredException extends ExamException {
    CourseNotRegisteredException(String message) {
        super(message);
    }
}

class SubmissionStorageException extends ExamException {
    SubmissionStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Storage layer: converts a low-level IOException into a meaningful portal exception.
class SubmissionStorage {
    void save(String fileName, long sizeKb) throws SubmissionStorageException {
        try {
            if (sizeKb > 500) {
                throw new IOException("Disk quota exceeded");
            }
            Files.writeString(Path.of(fileName), "Sample answer submission");
            System.out.println("Saved " + fileName);
        } catch (IOException e) {
            throw new SubmissionStorageException("Could not store " + fileName, e);
        }
    }
}

// Service layer: validates exam rules, then calls the storage layer.
class ExamService {
    private final SubmissionStorage storage = new SubmissionStorage();

    void submit(String fileName, long sizeKb, boolean examOpen,
                boolean registered) throws ExamException {
        if (!examOpen) {
            throw new ExamWindowClosedException("Exam window has closed");
        }
        if (!registered) {
            throw new CourseNotRegisteredException(
                    "Student is not registered for this course");
        }
        storage.save(fileName, sizeKb);
    }
}

// Controller layer: catches the meaningful exception and displays a user-friendly message.
public class ExamPortalDemo {
    private static void runScenario(String label, boolean examOpen,
                                     boolean registered, long sizeKb) {
        System.out.println("\n--- " + label + " ---");
        ExamService service = new ExamService();

        try {
            service.submit("answer-" + label.replace(' ', '-') + ".pdf",
                    sizeKb, examOpen, registered);
            System.out.println("Submission accepted.");
        } catch (ExamWindowClosedException e) {
            System.out.println("Submission rejected: " + e.getMessage());
        } catch (CourseNotRegisteredException e) {
            System.out.println("Submission rejected: " + e.getMessage());
        } catch (SubmissionStorageException e) {
            System.out.println("Submission failed: " + e.getMessage());
            System.out.println("Cause: " + e.getCause().getMessage());
            System.out.println("Chained stack trace:");
            e.printStackTrace(System.out);
        } catch (ExamException e) {
            System.out.println("Exam error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        runScenario("Valid submission", true, true, 120);
        runScenario("Exam closed", false, true, 120);
        runScenario("Not registered", true, false, 120);
        runScenario("File too large", true, true, 800);
    }
}
