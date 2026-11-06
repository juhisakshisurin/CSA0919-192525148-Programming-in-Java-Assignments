# Assignment 2 – Task 4: Choosing a Map for a Pharmacy Inventory

## Student Details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
Q4 – Choosing a Map for a Pharmacy Inventory (Hospital Pharmacy Stock Lookup).

## Files
- `Task04/PharmacyInventoryBenchmark.java` – compares HashMap, TreeMap and LinkedHashMap.
- `Task04/Task04_Report.pdf` – report without personal details.
- `Task04/task4_results.csv` – raw repeated benchmark measurements.
- `Task04/run_output.txt` – prepared execution output.
- `Task04/run.bat` – Windows compile-and-run helper.
- `Task04/output_screenshots/` – save your own VS Code screenshot here.

## Requirements
Java 8 or later. Prepared execution tested with Java 21.

## Compile and run
Open a terminal in the `Task04` folder:

```powershell
javac PharmacyInventoryBenchmark.java
java PharmacyInventoryBenchmark
```

No keyboard input is required. The program creates the CSV file automatically.

## Experimental design
The benchmark uses 1,000, 10,000 and 50,000 medicines. Each measured lookup trial performs 100,000 lookups. Five measured repetitions are collected for lookups and both report-generation operations. Map population time is also measured as supplementary evidence.

## Result
- `HashMap` offers average O(1) put/get but does not guarantee report order.
- `TreeMap` keeps keys sorted and naturally supports the medicine-code report, with O(log n) put/get.
- `LinkedHashMap` preserves insertion order and naturally supports the insertion-order report, with average O(1) put/get.
- Code-order reports from HashMap and LinkedHashMap sort a snapshot of entries. Insertion-order reports from HashMap and TreeMap use a separately retained key list.

## Submission reminder
Run the program on your own computer and capture your own successful execution screenshot. Timings vary with hardware, JVM and system load. Do not present prepared-run timings as measurements from your own computer. Commit Task04 separately in `CSA09-192525148-Assignment2`.
