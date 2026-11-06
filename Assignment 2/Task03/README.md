# Assignment 2 – Task 3

## Student Details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
Q3 – Engineer the hashCode–equals Contract (Event Attendance De-duplication).

## Files
- `Task03/Attendance.java` – executable Java implementation covering three tests and the corrected class.
- `Task03/Task03_Report.pdf` – report; intentionally excludes student-identifying details.
- `Task03/task3_test_results.csv` – test predictions and observed results.
- `Task03/run_output.txt` – console output from the prepared execution.
- `Task03/output_screenshots/` – place your own VS Code execution screenshot here.

## Requirements
Java 8 or later (tested with OpenJDK 21).

## Compile and run
Open a terminal in the `Task03` folder and run:

```bash
javac Attendance.java
java Attendance
```

On Windows, you can also double-click `run.bat`.

## Result
The tests demonstrate that `HashSet` uses `hashCode()` to select a bucket and `equals()` to compare candidates within that bucket. Equal objects must return the same hash code. The corrected `Student` class uses `regNo` in both `equals()` and `hashCode()`, producing the expected output `Unique students: 2`.

## Important experimental note
The supplied incomplete class uses the default identity-based `Object.hashCode()`. Its simple three-insertion example can produce different observed sizes depending on runtime identity hash values and bucket placement. The program therefore includes a repeated-reference test and a controlled different-bucket test to demonstrate both outcomes. The test constructs pairs dynamically rather than assuming identity hash values.

## Submission reminder
Add your own screenshot after compiling and running the program on your computer. Commit this task separately in your GitHub repository `CSA09-192525148-Assignment2`.
