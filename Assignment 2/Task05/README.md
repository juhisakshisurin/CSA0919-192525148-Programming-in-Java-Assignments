# Assignment 2 – Task 5: Generic Ranking Component

## Student Details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
Q5 – Derive a Generic Ranking Component from a Principle (7 Marks).

## Files
- `Task05/LeaderboardDemo.java` – complete generic leaderboard implementation, tests and benchmark.
- `Task05/Task05_Report.pdf` – report without student-identifying details.
- `Task05/task5_results.csv` – raw sort benchmark data (five repetitions per size).
- `Task05/run_output.txt` – output from the prepared execution.
- `Task05/run.bat` – Windows compile-and-run helper.
- `Task05/output_screenshots/` – save your own execution screenshot here.

## Java requirement
Java 8 or later. The program uses Java 8 language/library features.

## Compile and run
Open a terminal in the `Task05` folder and execute:

```powershell
javac LeaderboardDemo.java
java LeaderboardDemo
```

No keyboard input is required. The program writes `task5_results.csv` automatically.

## Ranking rule
1. Higher points rank first.
2. If points are equal, higher goal difference ranks first.
3. If both are equal, team name is alphabetical (A–Z).

## Design summary
`Team` does not implement `Comparable<Team>` because the ranking policy is supplied externally through a `Comparator<Team>`. This keeps the data class independent from one particular ranking policy. `Leaderboard<T>` accepts `Comparator<? super T>`, stores generic values, supports `addAll(List<? extends T>)`, and returns a sorted copy so its internal insertion order is not changed.

## Tests
- Test 1: assignment-provided dataset and expected output.
- Test 2: a student-designed dataset containing ties at points and goal difference.
- Test 3: a deliberately faulty points-only comparator to expose missing tie-breakers.
- An overflow demonstration shows why subtraction should not be used in comparators.
- A transitivity check is performed on the second dataset.

## Benchmark note
The benchmark sorts 1,000, 10,000 and 50,000 generated teams, with five measured repetitions per size. Timing depends on CPU, JVM, operating system and system load. Re-run locally and use your own results and screenshot as final evidence; prepared timings are not a substitute for your own execution.

## Submission
Place `Task05` in the repository `CSA09-192525148-Assignment2`. Commit this task separately. Capture a screenshot of your own successful execution and add it to `Task05/output_screenshots/`.
