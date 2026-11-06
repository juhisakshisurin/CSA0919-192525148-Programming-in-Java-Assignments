# Assignment 3 – Task 3

## Student Details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
**Q3 – Resource Safety, Debugging and Robust Code**  
7 Marks | Bloom's Level: K4 – Analyse

## Files
- `Task03/SalesReportDemo.java` – manual-close and try-with-resources comparison, including a deliberately failing input sequence.
- `Task03/input/store1.csv`, `store2.csv`, `store4.csv` – sample store input files.
- `Task03/test-cases.md` – test plan, observations to record, and screenshot checklist.
- `Task03/run_output.txt` – sample output from local execution.
- `Task03/Task3_Report.pdf` – analysis report (personal details intentionally omitted).

`store3_missing.csv` is intentionally absent. Its absence creates a failure after the first two files have been opened, which makes the manual-close leak visible. Do not create this file for the failure demonstration.

## Java version
Run `java -version` and record the installed version here before submission.

## Compile and run
Open PowerShell in the `Task03` directory:
```powershell
javac SalesReportDemo.java
java SalesReportDemo
```
Keep the `input` folder in the same `Task03` directory while running.

## Expected observations
- On successful input, both implementations should generate a report with total sales 500 across 3 stores.
- In the manual-close failure test, the third file is missing. The close loop is skipped, leaving two tracked readers open.
- With try-with-resources and the same failing input, the reader for each completed file is closed automatically; the open-reader count returns to zero.

The tracked reader counter is used to make resource ownership observable in this demonstration. It is an experimental proxy for open resources, not an operating-system-wide file descriptor measurement.

## GitHub submission
Create/use repository `CSA09-192525148-Assignment3`. Add this folder, capture your own terminal screenshots, and commit Task03 separately. Include the actual debugging evidence and measurements requested by the assignment.
