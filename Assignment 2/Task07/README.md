# Assignment 2 – CO2 – Task 7

## Student details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
**Q7 – Diagnose, Redesign and Validate (13 Marks)**

## Selected case
The Task 7 redesign uses the Q1 railway e-ticket construction problem. The original approach repeatedly concatenates immutable `String` objects in a loop. The revised approach uses a local, pre-sized `StringBuilder` for each ticket.

## Files
- `Task07/TicketRedesign.java` – original and redesigned implementations, output-equivalence validation, repeated timing tests.
- `Task07/Task07_Report.pdf` – report without student-identifying details.
- `Task07/task7_results.csv` – raw measurements from five repetitions for both approaches and two workloads.
- `Task07/run_output.txt` – prepared console output.
- `Task07/run.bat` – Windows compile-and-run helper.
- `Task07/output_screenshots/` – save your own execution screenshot here.

## Compile and run
Open a terminal in the `Task07` folder:

```powershell
javac TicketRedesign.java
java TicketRedesign
```

No keyboard input is required. The program generates `task7_results.csv` automatically. On Windows, you can also run `run.bat`.

## Experimental design
- HighLoad: 10,000 tickets × 50 fields.
- WideTicket: 1,000 tickets × 200 fields.
- Three warm-up rounds and five measured repetitions.
- The measurement order alternates between original and redesigned implementations.
- `System.nanoTime()` measures ticket generation; output equality is checked before timing.
- A checksum consumes generated output.
- Raw measurements are written to CSV.

## Main redesign
Original: repeated `String` concatenation with `+=` in a loop.

Redesigned: calculate the total field length, create `StringBuilder(capacity)`, append every field, and call `toString()` once.

For bounded-size fields, repeated concatenation copies a growing prefix and has O(n²) character-copying cost. StringBuilder performs O(L) work, where L is the total output length (O(n) for bounded field lengths). The redesign uses a builder buffer plus the final String, and avoids the estimated per-field intermediate String objects.

## Important evidence note
The PDF contains the prepared run's repeated measurements. Timing is JVM- and hardware-dependent. Re-run the Java program on your own computer before submission; retain the newly generated CSV and your own screenshot. Do not claim the prepared measurements as if they were captured on your personal computer.

## Screenshot
After running the program, capture the terminal showing both `Output equivalence: PASS` checks, average timings, speed-up and the CSV saved message. Save it as `Task07/output_screenshots/Task07_Output.png`.

## GitHub
Repository name required by the assignment:

`CSA09-192525148-Assignment2`

Commit Task07 separately and include the report, source, raw CSV, README and your execution screenshot.
