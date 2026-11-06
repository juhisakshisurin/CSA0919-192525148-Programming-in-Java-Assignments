# Task 7 – Test Plan and Design Analysis

## Test cases

| ID | Scenario | Expected handling |
|---|---|---|
| TC01 | Valid file with three integer scores in range | Success record; total is sum of three scores |
| TC02 | Expected file is missing | Failure record: IOException / Missing score file |
| TC03 | File contains non-numeric score | Failure record: IllegalArgumentException / NumberFormatException cause |
| TC04 | File contains score greater than 100 | Failure record: out-of-range validation |
| TC05 | File has fewer or more than three lines | Failure record: invalid line count |
| TC06 | One file fails while other tasks are running | Other files continue; one failure is counted |
| TC07 | Run with pool sizes 1, 2, 4, 8 | Each run reports 10,000 records and writes a per-file CSV |
| TC08 | Compare summary counts with generated pattern | Success + failure equals 10,000 |

## Stage 1 – Interpret the problem

- Parallel work: independent file existence checks, reading, parsing, range validation and result calculation.
- Shared data: the result records collected from tasks. The input files are read-only.
- Failures: missing file, unreadable file/permission error, malformed line count, non-numeric score, out-of-range score, unexpected task failure, interrupted main thread, and output CSV write failure.
- Measurable requirements: exactly 10,000 expected filenames are considered; every expected file produces one success/failure record; success + failure = 10,000; one bad input does not stop other tasks; pool sizes 1/2/4/8 are measured; CSV has a header and 10,000 data rows.

## Stage 2 – Design

Class relationships:
- `ResultProcessor` submits `ScoreTask` objects to `ExecutorService`.
- `ScoreTask implements Callable<ResultRecord>`.
- Each `Future<ResultRecord>` represents one task's eventual result.
- The main thread collects results into `ConcurrentLinkedQueue<ResultRecord>`.
- `RunSummary` stores final counts, total marks and elapsed time.

Pseudocode:
1. Create fixed thread pool of p workers.
2. For expected IDs 1..10,000, submit one Callable that reads, validates and computes a result.
3. Inside each Callable, catch expected file/data exceptions and return a failure record.
4. Store each Future.
5. Call get() on every Future and add each returned record to the concurrent queue.
6. Shut down the executor and await termination.
7. Sort records, count success/failure, sum successful marks and write CSV.
8. Verify success + failure = 10,000.

## Why ConcurrentLinkedQueue
It supports safe concurrent insertion without external synchronization. A task can return its record to the main thread through Future.get(), so the main thread is the only queue writer in this implementation; the concurrent collection is retained to satisfy the shared-result design and allow future tasks to publish results directly. A synchronized ArrayList would also work but requires explicit locking.

## Fault tolerance
Known file/data failures are caught inside each Callable and converted to failure records. This isolates expected failures to one file. `Future.get()` is still wrapped in `ExecutionException` handling to account for an unexpected task failure. Output/report failures are treated as run-level failures because the final audit file cannot be guaranteed.

## Stage 3 – Measurement

Run the same generated dataset with fixed thread pools of 1, 2, 4 and 8. Record wall-clock milliseconds, success count, failure count, total records, JDK version and machine details. Do not assume that more threads always improve performance: this workload includes disk I/O, filesystem metadata operations, scheduling and contention.

## Stage 4 – Evaluation

A pool size of 4 is a reasonable starting recommendation for a typical multi-core machine, but the final choice must be based on the student's own measurements. If storage is the bottleneck, additional workers can increase contention without improving throughput. The design guarantees a record per expected filename because it submits one task per expected name and waits for every Future. Remaining limitation: the program retains 10,000 Future references and result records in memory; for much larger datasets, use bounded submission/batching and stream the report.

## Alternative approaches

### Parallel streams
`files.parallelStream()` is concise and uses the common ForkJoinPool by default. A per-file try/catch is still needed so one malformed file does not abort the pipeline. Exceptions escaping the stream can terminate the terminal operation. Pool control and per-task result tracking are less explicit.

### One thread per file
Creating 10,000 threads has high overhead and stack-memory cost. If each thread reserves S bytes of stack, thread stacks alone can approach O(10,000 × S), in addition to thread objects and task data. It risks resource exhaustion and context-switch overhead. A fixed pool bounds active worker threads to p, with O(p × S) stack memory.

### Complexity
Let F be the number of expected files and L the maximum number of score lines per file.
- Per file: O(L) parsing/validation; here L=3, effectively O(1).
- Total CPU work: O(F × L), or O(F) for fixed three-score records.
- Result storage and Futures: O(F).
- Fixed-pool worker stack: O(p × S), where S is per-thread stack size.
- Parallel execution time ideally approaches O(F/p) batches, but real time depends on I/O and contention.
- CSV sorting: O(F log F); writing: O(F).
