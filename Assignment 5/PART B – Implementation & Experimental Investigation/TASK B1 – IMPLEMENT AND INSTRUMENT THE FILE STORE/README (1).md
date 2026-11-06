# Assignment 5 – CO5 | Part B – Task B1

## Student details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
**B1 – Implement and Instrument the File Store** (7 Marks, K4 – Analyse)

## Files and folders
- `Assignment5_PartB_B1_Report.pdf` – report with validation, actual benchmark measurements and interpretation. Student details are intentionally excluded.
- `src/PassStore.java` – completed supplied starter program; TODO 1 is implemented.
- `src/B1Validation.java` – add/find/list validation tests, including missing and damaged IDs.
- `src/LineCounterBenchmark.java` – unbuffered and 8 KiB buffered line counter; generates exact-size 1/10/50 MiB files and measures three runs (median).
- `src/SearchGrowthBenchmark.java` – measures find() at 1,000, 10,000, 50,000 and 100,000 records, target at end of file.
- `input/validation_passes.csv` – generated validation file.
- `output/` – captured console output and benchmark results.
- `evidence/` – place your own VS Code/terminal screenshots here.

## Important schema note
The Part A1 scenario defines **seven fields** (pass ID, register number, name, route number, boarding stop, validity date, amount paid). However, the supplied Part B B1 `PassStore` starter code, `add()` method, TODO comment and expected output explicitly use **five fields** (pass ID, register number, name, route, amount). This implementation follows the supplied B1 starter and expected output. If your faculty expects all seven A1 fields in the B1 file store, add boarding stop and validity date consistently to the source, test data and report before submission.

The simple starter comma-delimited format does not escape commas inside names. Use names without commas for the required tests; a production version should use a proper CSV parser or a documented escaping/quoting rule.

## Java version and environment
- Java used for the recorded run: **OpenJDK 21.0.12.1**
- Linux x86_64, kernel 6.18.57 (execution environment used for the report measurements).
- Java 21 is suitable; the source uses APIs available in Java 11+.

## Compile and run (Windows PowerShell / VS Code terminal)
Open a terminal in this `TaskB1` folder, then run:

```powershell
javac -d output src\PassStore.java src\B1Validation.java src\LineCounterBenchmark.java src\SearchGrowthBenchmark.java
java -cp output PassStore
java -cp output B1Validation
java -cp output LineCounterBenchmark
java -cp output SearchGrowthBenchmark
```

If `output` does not exist, create it first:

```powershell
New-Item -ItemType Directory -Force output
```

The line counter creates its own files under `input/line_benchmarks/`. The growth benchmark creates `input/growth_passes.csv`. These files are generated automatically; they do not need to be manually typed.

## Compile and run (Linux / macOS terminal)
```bash
mkdir -p output
javac -d output src/PassStore.java src/B1Validation.java src/LineCounterBenchmark.java src/SearchGrowthBenchmark.java
java -cp output PassStore
java -cp output B1Validation
java -cp output LineCounterBenchmark
java -cp output SearchGrowthBenchmark
```

## Expected output for the supplied PassStore main()
```text
[P1003, 192311003, Divya, 7, 3800.0]
null
```

## Validation summary
- Existing pass ID `P1003` returns its five fields.
- Missing pass ID `P9999` returns `null`.
- Damaged line `P1002,DAMAGED LINE` has only two fields and is skipped; searching it returns `null`.
- `listByRoute(7)` returns the valid Divya record.
- The damaged row does not stop the scan because `find()` continues to the next line when `fields.length != 5`.

## Recorded measurement results
Measurements below were actually executed in the report's Linux/OpenJDK environment. File sizes are MiB (1 MiB = 1,048,576 bytes). The timing values are environment-specific; re-run them on your own computer and add your own results/screenshots if required by your faculty.

| File | Unbuffered median | Buffered median (8 KiB) |
|---|---:|---:|
| 1 MiB | 215.032 ms | 7.001 ms |
| 10 MiB | 2,015.105 ms | 65.127 ms |
| 50 MiB | 10,547.707 ms | 306.928 ms |

Search target is the last record (worst-case successful scan); five runs, median:

| Records | Median find time |
|---:|---:|
| 1,000 | 2.169 ms |
| 10,000 | 9.853 ms |
| 50,000 | 12.167 ms |
| 100,000 | 21.397 ms |

## Complexity and alternative
- `add()`: append operation, O(record length) to write the record.
- `find()`: O(n) time because it scans up to n records; O(1) extra memory apart from line/field buffers.
- `listByRoute()`: O(n) time and O(k) output memory for k matching records.
- Alternative HashMap index: build O(n) time and O(n) memory once; average O(1) lookup. For q searches, total average cost O(n + q), versus O(q × n) for repeated scans.

## Submission notes
1. Execute all four commands on your own machine and save terminal screenshots in `evidence/`.
2. Keep the output text and measurement data; screenshots alone are not sufficient.
3. Add this folder as `TaskB1` inside repository `CSA09-192525148-Assignment5`.
4. Suggested commit: `Implement and benchmark Assignment 5 Part B Task B1`.
5. Add the working GitHub repository link to the report cover page before submission.
