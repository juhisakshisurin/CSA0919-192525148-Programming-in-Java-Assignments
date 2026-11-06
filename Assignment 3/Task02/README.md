# Assignment 3 – Task 2

## Student Details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
**Q2 – User-Defined Exceptions and Propagation in an Online Examination Portal**  
7 Marks | Bloom's Level: K4 – Analyse

## Files
- `ExamPortalDemo.java` – complete layered portal demonstration with custom exceptions, propagation and exception chaining.
- `StorageDemo.java` – the assignment's supplied `Complete the Code` program with TODO 1 completed.
- `test-cases.md` – scenarios, expected results and test evidence checklist.
- `run_output.txt` – sample output from a successful local execution.
- `Task2_Report.pdf` – analytical report (personal details intentionally omitted).

## Java version
Run `java -version` and record the installed version here before submission.

## Compile and run
Open PowerShell or Command Prompt inside the `Task02` folder.

### Main portal demonstration
```powershell
javac ExamPortalDemo.java
java ExamPortalDemo
```

### Exact TODO program from the assignment
```powershell
javac StorageDemo.java
java StorageDemo
```

## Expected result
`StorageDemo` should print:
```text
Saved answer01.pdf
Error: Could not store answer02.pdf
Cause: Disk quota exceeded
```

`ExamPortalDemo` demonstrates a valid submission, a closed exam window, a student who is not registered, and an oversized answer file. The oversized-file case displays the original `IOException` through `getCause()` and prints the chained stack trace.

## GitHub submission
Create/use repository `CSA09-192525148-Assignment3`, place this folder in the repository, add the actual screenshots from your own execution, and commit Task02 separately. Do not submit only the generated sample log as proof; capture your own terminal output.
