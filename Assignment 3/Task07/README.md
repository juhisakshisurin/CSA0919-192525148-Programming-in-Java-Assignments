# Assignment 3 – Task 7

## Student details
- Name: Juhi Surin
- Register Number: 192525148
- Course: CSA0919 Programming in Java
- Java version: Run `java -version` and record the version installed on your computer.

## Task 7
Design a concurrent, fault-tolerant processor for 10,000 semester score files. The program uses:
- `ExecutorService` with a fixed thread pool.
- `Callable<ResultRecord>` to validate each score file independently.
- `Future<ResultRecord>` to collect completed task results.
- `ConcurrentLinkedQueue<ResultRecord>` as the shared thread-safe result collection.
- Per-file exception handling so a missing/corrupt file is recorded without stopping other files.
- A sorted CSV audit report and a final success/failure summary.

## Files
- `Task07/ResultProcessor.java` – processing design and implementation.
- `Task07/ScoreFileGenerator.java` – creates a reproducible 10,000-file dataset with missing/corrupt/out-of-range examples.
- `Task07/compile_output.txt` – compilation result.
- `Task07/benchmark_results.csv` – sample measurements on the generation environment; rerun on your computer for your own measurements.
- `Task07/benchmark_summary.txt` – sample run summaries.
- `Task7_Report.pdf` – report without personal details.
- `sample_data/` – small hand-checkable sample (8 valid, 1 corrupt, 1 missing).

## Windows procedure
1. Extract the ZIP.
2. Open Command Prompt in `Assignment3_Task7\Task07`.
3. Compile:
   `javac *.java`
4. Generate the full dataset (run from the `Task07` folder):
   `java ScoreFileGenerator ..\score_data`
5. Run each required pool size:
   `java ResultProcessor ..\score_data 1 ..\results_pool_1.csv`
   `java ResultProcessor ..\score_data 2 ..\results_pool_2.csv`
   `java ResultProcessor ..\score_data 4 ..\results_pool_4.csv`
   `java ResultProcessor ..\score_data 8 ..\results_pool_8.csv`
6. Record elapsed time printed by each run. The CSV contains one row for each expected file.
7. Optional small hand-check: generate 10 files using `java ScoreFileGenerator ..\\small_data 10`, then run `java ResultProcessor ..\\small_data 1 ..\\small_results.csv 10`. For the provided `sample_data` folder (8 valid, 1 corrupt, 1 missing), run `java ResultProcessor ..\\sample_data 1 ..\\sample_results.csv 10`.

## Expected dataset behavior
The generator creates score files with three integer lines (each score must be 0–100). It intentionally omits every 100th file, injects non-numeric content for selected indices divisible by 97, and injects out-of-range scores for selected indices divisible by 89. The program counts every expected filename, so missing files are included as failures.
Expected summary: 10,000 expected records = successful + failed. Exact success/failure totals are printed by the program and should be verified after generation.

## Design notes
Each file is an independent task, so file reading and validation can run concurrently. The result queue is thread-safe. The main thread waits on every Future, then sorts the records and writes the final CSV after all tasks have completed. A task catches file-specific exceptions and returns a failure record, allowing the remaining tasks to continue.

## Submission reminders
- Run the benchmark with pool sizes 1, 2, 4 and 8 on your own computer; record your actual timings and machine/JDK details.
- Include console screenshots, the four CSV files, and any graph made from your raw measurements.
- Create at least one separate GitHub commit for Task07 in `CSA09-192525148-Assignment3`.
- This is pair work: coordinate with your partner, identify Student A/Student B sections in the joint report, and complete the contribution record from the assignment.
