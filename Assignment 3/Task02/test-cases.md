# Task 2 – Test Cases and Evidence

| Test | Inputs / condition | Expected handling | Expected result |
|---|---|---|---|
| 1. Valid submission | examOpen=true, registered=true, size=120 KB | No exception | Submission saved and accepted |
| 2. Exam window closed | examOpen=false, registered=true, size=120 KB | `ExamWindowClosedException` caught by controller | Submission rejected: Exam window has closed |
| 3. Course registration missing | examOpen=true, registered=false, size=120 KB | `CourseNotRegisteredException` caught by controller | Submission rejected: Student is not registered for this course |
| 4. File exceeds limit | examOpen=true, registered=true, size=800 KB | `IOException` wrapped as `SubmissionStorageException`; controller catches it | Meaningful error plus original cause `Disk quota exceeded` |
| 5. Exact assignment valid file | `StorageDemo`: answer01.pdf, 120 KB | No exception | `Saved answer01.pdf` |
| 6. Exact assignment oversized file | `StorageDemo`: answer02.pdf, 800 KB | `SubmissionStorageException` propagated to main and caught as `ExamException` | `Error: Could not store answer02.pdf`; cause line reports `Disk quota exceeded` |

## Evidence to capture
1. Successful compilation and output of `ExamPortalDemo`.
2. Output showing the exam-window exception.
3. Output showing the course-registration exception.
4. Output showing `SubmissionStorageException`, `getCause()` and the chained stack trace.
5. Successful compilation and exact expected output of `StorageDemo`.

Replace this expected-results table with observed results if your environment behaves differently. Include your own screenshots in the GitHub `Task02` folder.
