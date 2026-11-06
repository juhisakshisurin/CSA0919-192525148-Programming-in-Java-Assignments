# Assignment 3 – Task 4

## Student Details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
**Q4 – Thread Creation and the Thread Life Cycle**  
7 Marks | Bloom's Level: K4 – Analyse

## Files
- `ScannerLifecycleDemo.java` – implements the four scanner stations using both `Thread` subclassing and `Runnable`; demonstrates `start()`, `sleep()`, `join()`, `isAlive()`, run-vs-start, ten completion-order runs, priorities and a daemon station.
- `ScannerExecutorAlternative.java` – fixed thread-pool alternative using `ExecutorService`, `Callable` and `Future`.
- `Task4_Report.pdf` – report with lifecycle diagram, explanations, experiment design and complexity analysis; personal details intentionally omitted.
- `run_output.txt` – sample output from local execution.
- `experiment-log-template.csv` – blank table for recording your own ten runs and priority experiments.

## Java version
Run `java -version` and record your installed version here before submission.

## Compile and run
Open PowerShell in this folder:
```powershell
javac ScannerLifecycleDemo.java
java ScannerLifecycleDemo
```

Run the ExecutorService alternative:
```powershell
javac ScannerExecutorAlternative.java
java ScannerExecutorAlternative
```

## Important experiment notes
- Run `ScannerLifecycleDemo` at least ten separate times if your instructor expects ten program executions. The program also performs ten completion-order trials during one execution; record your own observed results.
- Thread priorities are scheduling hints, not guarantees. Do not claim that a higher-priority station must finish first.
- The daemon demonstration intentionally does not call `join()` on the daemon. The JVM can exit when only daemon threads remain; the daemon's final completion message may or may not appear.
- `run()` is invoked directly to demonstrate that it executes on the calling main thread. `start()` asks the JVM to schedule a new thread.

## GitHub submission
Create/use repository `CSA09-192525148-Assignment3`, add this Task04 folder, capture your own terminal screenshots, complete the experiment log with observed results, and commit Task04 separately.
