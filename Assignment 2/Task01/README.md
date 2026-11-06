# Assignment 2 - CO2 - Task 1

## Student details

- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Java environment

- Java version used for the supplied benchmark results: **OpenJDK 21.0.12.1**
- Compiler: `javac 21.0.12.1`
- The source uses standard Java APIs and can be run with Java 8 or later; the supplied run command has been tested with Java 21.

## Folder contents

```text
Assignment2_Task1/
├── README.md
└── Task01/
    ├── TicketBenchmark.java
    ├── Task01_Report.pdf
    ├── task1_results.csv
    ├── run_output.txt
    ├── run.bat
    └── output_screenshots/
        ├── benchmark_output_reference.png
        └── SCREENSHOT_INSTRUCTIONS.txt
```

- `TicketBenchmark.java` implements all three approaches: `String`, `StringBuilder` and `StringBuffer`.
- `task1_results.csv` contains the raw measurements from five measured repetitions for each approach and workload.
- `run_output.txt` contains the console output from the supplied execution.
- `output_screenshots/benchmark_output_reference.png` is a visual reference generated from that console output; capture and add a screenshot from your own run before final submission.
- `run.bat` is a Windows convenience script to compile and execute the program.
- `Task01_Report.pdf` contains the analysis and the measured results. It intentionally does not contain student-identifying details.
- The workload inputs are generated inside the Java program, so no separate input file is required.

## How to compile and run

1. Install a JDK (Java 8 or later; Java 21 was used for the supplied results).
2. Open a terminal or Command Prompt.
3. Change the directory to `Task01`.
4. Compile the program:

   ```bash
   javac TicketBenchmark.java
   ```

5. Run the program:

   ```bash
   java -Xms256m -Xmx1g TicketBenchmark
   ```

6. The program prints the sample ticket, verifies that all three approaches produce identical text, runs the three workloads, and writes a fresh `task1_results.csv` in the current directory.

   To choose a different CSV filename:

   ```bash
   java -Xms256m -Xmx1g TicketBenchmark my_task1_results.csv
   ```

7. Open the generated CSV in Excel or another spreadsheet application. Keep the raw rows; do not replace them with only averages.
8. Capture a screenshot of your own successful console execution and save it inside `Task01/output_screenshots/`. Include the output-equivalence check and the average-time summary in the screenshot. The text file `run_output.txt` is provided as a reference, but a screenshot from your own execution is still recommended for submission.

## What the program tests

| Workload | Tickets | Fields per ticket | Purpose |
|---|---:|---:|---|
| Short ticket | 1 | 6 | Small workload; differences are expected to be negligible |
| High load | 10,000 | 50 | Many tickets with a moderate number of fields |
| Wide ticket | 1,000 | 200 | Additional workload with many fields per ticket |

Each approach receives the same pre-built field parts. The benchmark excludes input generation and file I/O from the timed section. It performs three warm-up rounds and five measured repetitions. The order of the approaches is rotated between repetitions.

## Result note

In the supplied OpenJDK 21 run, the three approaches had similar times for one short ticket. For the larger workloads, repeated `String` concatenation took longer and generated many more estimated intermediate `String` objects. `StringBuilder` and `StringBuffer` performed substantially better in these measurements. Exact timings and heap readings depend on the computer, JVM, garbage collection and system load; rerun the program on the computer used for submission and retain those raw results.

The heap delta in the CSV is an approximate before/after heap snapshot, not a direct peak-memory measurement. Intermediate-object counts are estimates based on the source-level concatenation operations; they are not JVM allocation-profiler counts.

## GitHub submission procedure

The assignment specifies this repository naming pattern:

```text
CSA09-192525148-Assignment2
```

1. Create a GitHub repository with that name (public, or private with the course faculty added as a collaborator).
2. Upload the `README.md` and `Task01` folder, including the source, report, CSV and your output screenshot.
3. Commit the Task 1 work separately. Suggested commit message:

   ```text
   Add Task 1 ticket string benchmark and report
   ```

4. Copy the working repository URL into the assignment cover page and submission field.

The assignment requires progressive commits (at least one per task), so do not upload all tasks in one final commit.
