# Assignment 2 – CO2 – Task 6

## Student details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
**Q6 – Priority, Order and Fairness in a Queue (8 Marks)**  
Problem context: Hospital Emergency Triage.

## Java environment
Java 8 or later; compile with `javac`.

## Files
- `Task06/Triage.java` – complete priority-queue implementation, hand-check tests, alternative implementations and benchmark.
- `Task06/Task06_Report.pdf` – report without student-identifying details.
- `Task06/task6_results.csv` – raw measurements for three queue sizes and five repetitions per strategy.
- `Task06/run_output.txt` – prepared console output.
- `Task06/run.bat` – Windows compile-and-run helper.
- `Task06/output_screenshots/` – add your own execution screenshot here.

## Compile and run
Open a terminal in the `Task06` folder:

```powershell
javac Triage.java
java Triage
```

No keyboard input is required. The program generates `task6_results.csv`. You can also run `run.bat`.

## Implemented rule
Severity 1 is most urgent and severity 5 is least urgent. The comparator orders by severity ascending, then arrival sequence ascending. A monotonically increasing counter provides FIFO order among patients with equal severity.

## Tests and measurements
- Eight-patient hand-verifiable waiting list.
- Exact six-patient example and expected output from the assignment.
- Comparison of PriorityQueue, sorted LinkedList and five FIFO queues.
- Growth measurements at 500, 2,000 and 5,000 patients, with five repetitions.
- CSV records admission and serving time separately.

## Design and complexity
A plain FIFO queue cannot select the most severe patient if a more severe patient arrives later. PriorityQueue provides O(log n) insertion and removal of the next patient; peek is O(1), and storage is O(n). Printing a PriorityQueue directly does not guarantee treatment order because its iterator exposes heap layout. A copied queue drained with poll() displays treatment order.

A sorted LinkedList has O(n) admission and O(1) removal from its head. Five FIFO queues have O(1) admission and O(1) dequeue with a fixed five-level scan, and use O(n) total storage.

## Fairness and deployment
Strict severity priority can starve severity-5 patients during continuous severe arrivals. A possible aging policy periodically improves effective severity for patients exceeding a wait threshold, up to severity 1. The heap must be rebuilt or affected patients reinserted after priority changes; do not mutate comparator keys in-place inside a heap.

Five thousand arrivals/day averages about 0.058 arrivals/second over 24 hours. The queue algorithm is computationally suitable as a component, but this average does not prove peak capacity. Production deployment also requires peak-load tests, staffing/service-time analysis, persistence, auditability, concurrency controls, recovery and clinical validation. The current code implements core priority ordering; aging is discussed as a design extension, not claimed as a fully time-driven clinical system.

## Screenshot and submission
Run locally and capture a screenshot showing PASS checks and benchmark output. Save it as `Task06/output_screenshots/Task06_Output.png`. Commit this task separately in `CSA09-192525148-Assignment2`.
