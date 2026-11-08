# CSA0919 – Programming in Java
## Assignment 2: Java Collections, Data Structures and Performance Analysis

### Student Details

| Field | Details |
|---|---|
| **Name** | Juhi Surin |
| **Register Number** | 192525148 |
| **Course Code** | CSA0919 |
| **Course Name** | Programming in Java |
| **Assignment** | Assignment 2 |
| **Programming Language** | Java |

---

## 1. Introduction

This assignment focuses on applying Java Collections, data structures, generics, object comparison and performance analysis to practical programming problems.

The assignment explores different approaches to storing, managing, searching, sorting and processing data efficiently. Each task presents a real-world scenario and requires selecting suitable Java concepts and data structures based on their behaviour, performance, memory requirements and application needs.

The assignment consists of seven tasks, progressing from string construction and collection benchmarking to hashing, map selection, generic ranking, priority queues and software redesign.

## 2. Objectives

- Understand the performance differences between Java String handling techniques.
- Compare ArrayList and LinkedList for different collection operations.
- Understand the contract between `equals()` and `hashCode()`.
- Select appropriate Map implementations for practical applications.
- Implement reusable generic components using type parameters and comparators.
- Apply priority queues and ordering strategies to real-world problems.
- Analyse time and space complexity of different data structures.
- Conduct performance benchmarking using repeated measurements.
- Interpret experimental results and justify suitable design decisions.
- Redesign inefficient Java implementations and validate their correctness.

## 3. Task-wise Description

### Task 1 – Compare String, StringBuilder and StringBuffer

**Problem context:** Railway E-Ticket Generation.

Compared three approaches for constructing railway e-ticket strings: immutable `String` concatenation, mutable `StringBuilder` and synchronized `StringBuffer`.

The benchmark evaluates their performance under different workloads and measures execution time, estimated intermediate-object creation and approximate heap changes.

**Concepts covered:**
- String immutability
- String concatenation
- StringBuilder
- StringBuffer
- Performance benchmarking
- Memory usage
- `System.nanoTime()`
- CSV-based result collection
- Time and space complexity

**Files included:**
- `TicketBenchmark.java` – Implements the three approaches and benchmark.
- `Task01_Report.pdf` – Detailed experimental analysis and findings.
- `task1_results.csv` – Raw benchmark measurements.
- `run_output.txt` – Console execution output.
- `benchmark_output_reference.png` – Benchmark output reference.
- `Java Assignment 2 task01 output.png` – Execution screenshot.

**Execution commands:**

```bash
javac TicketBenchmark.java
java -Xms256m -Xmx1g TicketBenchmark
```

**Result:** StringBuilder and StringBuffer performed substantially better than repeated String concatenation for larger workloads in the prepared benchmark. The three approaches were also checked for output equivalence.

### Task 2 – Compare ArrayList and LinkedList

**Problem context:** Music Playlist Editor.

Compared ArrayList and LinkedList for playlist operations, including adding songs, inserting and removing songs at different positions, indexed access and iteration.

The task also examines ArrayDeque as an alternative for the Up Next queue requirement.

**Concepts covered:**
- Java Collections Framework
- ArrayList
- LinkedList
- ArrayDeque
- List operations
- Indexed access
- Iteration
- Queue operations
- Performance benchmarking
- Time and space complexity

**Files included:**
- `PlaylistBenchmark.java` – Implements playlist operations and performance comparisons.
- `Task02_Report.pdf` – Detailed report containing analysis and recommendations.
- `task2_results.csv` – Raw benchmark measurements.
- `run_output.txt` – Console execution output.
- `Java Assignment 2 task02 output.png` – Execution screenshot.

**Execution commands:**

```bash
javac PlaylistBenchmark.java
java PlaylistBenchmark
```

No manual input is required. The program generates playlist data automatically.

**Result:** ArrayList is suitable for the main playlist when indexed access and iteration are frequent. LinkedList supports efficient modifications at the ends, while ArrayDeque is a suitable choice for the Up Next queue.

### Task 3 – Engineer the hashCode–equals Contract

**Problem context:** Event Attendance De-duplication.

Examined how HashSet identifies duplicate student attendance records using the `hashCode()` and `equals()` methods.

The task demonstrates the problems that can occur when these methods are not implemented consistently and provides a corrected Student class.

**Concepts covered:**
- HashSet
- Hashing
- `hashCode()`
- `equals()`
- Object equality
- Hash collisions
- Duplicate removal
- Mutable objects and hashing
- Contract between equality and hashing

**Files included:**
- `Attendance.java` – Java implementation demonstrating the tests and corrected class.
- `Task03_Report.pdf` – Detailed report and analysis.
- `task3_test_results.csv` – Test predictions and observed results.
- `run_output.txt` – Console execution output.
- `Java assignment 2 task03 output.png` – Execution screenshot.

**Execution commands:**

```bash
javac Attendance.java
java Attendance
```

**Result:** The corrected Student class uses the registration number in both `equals()` and `hashCode()`, allowing HashSet to identify duplicate student records correctly. The expected corrected result is:

```text
Unique students: 2
```

The task also demonstrates why equal objects must always produce the same hash code.

### Task 4 – Choose a Map for a Pharmacy Inventory

**Problem context:** Hospital Pharmacy Stock Lookup.

Compared HashMap, TreeMap and LinkedHashMap for managing a pharmacy inventory.

The benchmark evaluates medicine lookup, insertion and report-generation operations under different inventory sizes.

**Concepts covered:**
- Map interface
- HashMap
- TreeMap
- LinkedHashMap
- Key-value pairs
- Searching and insertion
- Sorting and ordering
- Collection traversal
- Performance benchmarking
- Time and space complexity

**Files included:**
- `PharmacyInventoryBenchmark.java` – Implements and compares the three Map approaches.
- `Task04_Report.pdf` – Detailed analysis, experimental results and recommendations.
- `task4_results.csv` – Raw benchmark measurements.
- `run_output.txt` – Console execution output.
- `Java assignment 2 task04 output.png` – Execution screenshot.

**Execution commands:**

```bash
javac PharmacyInventoryBenchmark.java
java PharmacyInventoryBenchmark
```

No manual input is required. The program generates inventory data and benchmark results automatically.

**Result:**
- HashMap provides average O(1) lookup and insertion without guaranteeing ordering.
- TreeMap maintains sorted key order with O(log n) lookup and insertion.
- LinkedHashMap preserves insertion order with average O(1) lookup and insertion.

The appropriate Map implementation depends on the required ordering and lookup behaviour.

### Task 5 – Implement a Generic Ranking Component

**Problem context:** Generic Team Leaderboard.

Developed a reusable generic leaderboard component that ranks teams based on multiple criteria using Comparator.

The ranking system considers points, goal difference and team name to determine the final order. The task also includes different test datasets and performance measurements.

**Ranking rules:**
1. Higher points rank first.
2. If points are equal, higher goal difference ranks first.
3. If both are equal, team name is considered alphabetically (A–Z).

**Concepts covered:**
- Java Generics
- Generic classes
- Type parameters
- Comparator
- Collections sorting
- Sorting algorithms
- Wildcards
- Comparator contract
- Transitivity
- Integer overflow in comparisons
- Performance benchmarking

**Files included:**
- `LeaderboardDemo.java` – Complete generic leaderboard implementation, tests and benchmark.
- `Task05_Report.pdf` – Detailed design and experimental report.
- `task5_results.csv` – Raw sorting benchmark measurements.
- `run_output.txt` – Console execution output.
- `Java Assignment 2 task05 output.png` – Execution screenshot.

**Execution commands:**

```bash
javac LeaderboardDemo.java
java LeaderboardDemo
```

No manual input is required. The program generates the test datasets and benchmark measurements automatically.

**Result:** A reusable generic leaderboard was implemented using Comparator. The design separates the Team data class from the ranking policy, supports custom ranking criteria and returns a sorted copy without modifying the original insertion order.

### Task 6 – Priority, Order and Fairness in a Queue

**Problem context:** Hospital Emergency Triage.

Designed a priority-based patient management system in which patients are treated according to severity rather than simple arrival order.

Severity 1 represents the highest priority and severity 5 represents the lowest priority. Patients with equal severity are served in their arrival order.

Compared PriorityQueue with sorted LinkedList and five separate FIFO queues.

**Concepts covered:**
- Queue interface
- PriorityQueue
- Comparator
- LinkedList
- FIFO
- Priority-based processing
- Fairness
- Starvation
- Queue operations
- Performance benchmarking
- Time and space complexity

**Implemented priority rule:**
- Lower severity number receives higher priority.
- Patients with the same severity are served in FIFO order.
- A monotonically increasing arrival counter maintains the correct order among patients with equal severity.

**Files included:**
- `Triage.java` – Implements the priority-based triage system and alternative queue strategies.
- `Task06_Report.pdf` – Detailed design, testing, fairness analysis and complexity comparison.
- `task6_results.csv` – Raw benchmark measurements.
- `run_output.txt` – Console execution output.
- `Java Assignment 2 task06 output.png` – Execution screenshot.

**Execution commands:**

```bash
javac Triage.java
java Triage
```

No manual input is required.

**Result:** The priority-based queue successfully processes patients according to severity and arrival order. PriorityQueue provides O(log n) insertion and removal of the highest-priority patient, while peek is O(1).

The task also discusses the possibility of starvation and introduces priority aging as a potential design enhancement.

### Task 7 – Diagnose, Redesign and Validate

**Problem context:** Railway E-Ticket Construction.

Selected the inefficient String concatenation approach from Task 1 and redesigned it using StringBuilder.

The original implementation repeatedly creates new String objects during concatenation. The redesigned implementation uses a pre-sized StringBuilder to construct ticket information efficiently.

The two implementations are compared for output correctness and performance.

**Concepts covered:**
- String immutability
- StringBuilder
- Efficient string construction
- Software redesign
- Performance optimisation
- Output equivalence
- Benchmarking
- `System.nanoTime()`
- CSV-based measurements
- Time and space complexity

**Files included:**
- `TicketRedesign.java` – Contains the original and redesigned implementations, validation and benchmark.
- `Task07_Report.pdf` – Detailed redesign and experimental report.
- `task7_results.csv` – Raw benchmark measurements.
- `run_output.txt` – Console execution output.
- `Java Assignment 2 task07 output.png` – Execution screenshot.

**Execution commands:**

```bash
javac TicketRedesign.java
java TicketRedesign
```

No manual input is required. The program generates the workload and benchmark results automatically.

**Result:** The redesigned StringBuilder implementation produces output equivalent to the original implementation while reducing unnecessary intermediate String creation and improving performance for larger workloads.

The redesign demonstrates how an inefficient implementation can be improved without changing its intended output.

---

## 4. Technologies and Tools Used

| Technology / Tool | Purpose |
|---|---|
| Java | Programming language |
| JDK | Compilation and execution |
| Visual Studio Code | Source-code development |
| Command Prompt / Terminal | Program execution |
| Java Collections Framework | Data storage and management |
| CSV | Recording benchmark measurements |
| PDF Editor / Document Editor | Preparation of reports |
| Git and GitHub | Version control and assignment submission |

## 5. Repository Structure

```text
CSA0919-192525148-Programming-in-Java-Assignments/
│
└── Assignment 2/
    │
    ├── README.md
    │
    ├── Task01/
    │   ├── README.md
    │   ├── TicketBenchmark.java
    │   ├── Task01_Report.pdf
    │   ├── task1_results.csv
    │   ├── run_output.txt
    │   ├── benchmark_output_reference.png
    │   └── Java Assignment 2 task01 output.png
    │
    ├── Task02/
    │   ├── README.md
    │   ├── PlaylistBenchmark.java
    │   ├── Task02_Report.pdf
    │   ├── task2_results.csv
    │   ├── run_output.txt
    │   └── Java Assignment 2 task02 output.png
    │
    ├── Task03/
    │   ├── README.md
    │   ├── Attendance.java
    │   ├── Task03_Report.pdf
    │   ├── task3_test_results.csv
    │   ├── run_output.txt
    │   └── Java assignment 2 task03 output.png
    │
    ├── Task04/
    │   ├── README.md
    │   ├── PharmacyInventoryBenchmark.java
    │   ├── Task04_Report.pdf
    │   ├── task4_results.csv
    │   ├── run_output.txt
    │   └── Java assignment 2 task04 output.png
    │
    ├── Task05/
    │   ├── README.md
    │   ├── LeaderboardDemo.java
    │   ├── Task05_Report.pdf
    │   ├── task5_results.csv
    │   ├── run_output.txt
    │   └── Java Assignment 2 task05 output.png
    │
    ├── Task06/
    │   ├── README.md
    │   ├── Triage.java
    │   ├── Task06_Report.pdf
    │   ├── task6_results.csv
    │   ├── run_output.txt
    │   └── Java Assignment 2 task06 output.png
    │
    └── Task07/
        ├── README.md
        ├── TicketRedesign.java
        ├── Task07_Report.pdf
        ├── task7_results.csv
        ├── run_output.txt
        └── Java Assignment 2 task07 output.png
```

*Note: This is a summary of the main files in each task folder. Compiled `.class` files and additional support files may also be present.*

## 6. Compilation and Execution

The assignment programs can be compiled and executed using the Java Development Kit through Visual Studio Code, Command Prompt or any Java-compatible IDE.

### General execution procedure

1. Open the required task folder.
2. Open a terminal inside that folder.
3. Compile the Java source file.
4. Execute the class containing the `main()` method.
5. Verify the displayed output.
6. Check the generated CSV file wherever benchmarking is included.

### General commands

```bash
javac FileName.java
java ClassName
```

### Example

To execute Task 1:

```bash
cd Task01
javac TicketBenchmark.java
java -Xms256m -Xmx1g TicketBenchmark
```

To execute Task 7:

```bash
cd Task07
javac TicketRedesign.java
java TicketRedesign
```

The exact compilation command depends on the task and its source files.

## 7. Benchmarking and Experimental Analysis

Performance benchmarking is an important part of this assignment.

The tasks use repeated measurements to compare different implementations and data structures under varying workloads.

The benchmark results are stored in CSV files for further analysis.

The following aspects are considered:

- Execution time for different operations.
- Performance under increasing input sizes.
- Differences between alternative data structures.
- Memory usage and object creation where applicable.
- Correctness and output equivalence.
- Time and space complexity.
- Practical advantages and limitations of each approach.

**Note:** Benchmark timings depend on the processor, JVM, operating system, garbage collection and system load. Prepared measurements should not be treated as universal performance values. The programs should be rerun on the submission computer when fresh experimental evidence is required.

## 8. Learning Outcomes

Through this assignment, the following learning outcomes were addressed:

- Understanding and applying Java's String handling mechanisms.
- Selecting suitable List, Set, Map and Queue implementations.
- Understanding object equality and hashing contracts.
- Implementing reusable generic classes and comparators.
- Applying sorting and priority-based processing.
- Conducting controlled performance experiments.
- Recording and analysing benchmark results.
- Identifying inefficient programming approaches.
- Optimising implementations while preserving output correctness.
- Evaluating algorithms through time and space complexity.

## 9. Conclusion

Assignment 2 provides practical experience in applying Java Collections, data structures, generics and performance optimisation to real-world programming scenarios.

Across the seven tasks, different approaches are implemented and evaluated for string construction, playlist management, attendance de-duplication, pharmacy inventory, generic ranking, emergency triage and software redesign.

The assignment highlights the importance of selecting suitable data structures, maintaining object contracts, designing reusable components and validating program correctness through testing and benchmarking.

Overall, the tasks strengthen the understanding of Java programming, collection management, algorithm efficiency, experimental analysis and software optimisation.

---

**Course:** CSA0919 – Programming in Java  
**Assignment:** 2 – Java Collections, Data Structures and Performance Analysis  
**Student:** Juhi Surin  
**Register Number:** 192525148
