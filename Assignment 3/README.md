# CSA0919 – Programming in Java
## Assignment 3: Exception Handling, Multithreading and Concurrency

### Student Details

| Field | Details |
|---|---|
| **Name** | Juhi Surin |
| **Register Number** | 192525148 |
| **Course Code** | CSA0919 |
| **Course Name** | Programming in Java |
| **Assignment** | Assignment 3 |
| **Programming Language** | Java |

---

## 1. Introduction

This assignment focuses on implementing and analysing exception handling, resource management, multithreading and concurrency concepts using Java.

The tasks cover practical programming scenarios such as bank ATM transactions, online examination portals, sales report processing, scanner station management, cinema seat booking, digital wallet transfers and concurrent processing of student examination results.

The assignment consists of seven tasks that progressively explore robust error handling, resource safety, thread creation, synchronisation, inter-thread communication and concurrent application development.

## 2. Objectives

- Understand checked and unchecked exceptions in Java.
- Implement user-defined exceptions and exception propagation.
- Apply exception chaining and multi-catch statements.
- Manage files and resources safely using try-with-resources.
- Understand thread creation and the Java thread life cycle.
- Demonstrate race conditions in concurrent applications.
- Apply synchronisation to ensure thread safety.
- Understand inter-thread communication using `wait()`, `notify()` and `notifyAll()`.
- Prevent deadlocks through consistent lock ordering.
- Implement concurrent file processing using ExecutorService.
- Analyse performance, correctness and resource utilisation.
- Develop robust applications through testing and validation.

## 3. Task-wise Description

### Task 1 – Bank ATM Simulation

**Problem context:** Bank ATM Withdrawal System.

Implemented an ATM withdrawal application that validates withdrawal requests and handles different exception scenarios.

The program demonstrates file-based card-reader operations, checked exceptions, unchecked exceptions and proper cleanup using finally.

**Concepts covered:**
- Exception handling
- Checked exceptions
- Unchecked exceptions
- try-catch-finally
- Multi-catch
- File handling
- Input validation
- Exception propagation

**Files included:**
- `ATMWithdrawal.java` – ATM withdrawal application.
- `card-reader.txt` – Sample card-reader file required for a normal transaction.
- `Task1_Report.pdf` – Detailed task report.
- `test-cases.md` – Test scenarios and expected behaviour.
- `Java assignment 3 task01 output.png` – Execution screenshot.

**Java requirement:** JDK 17 or later.

**Compilation and execution:**

```bash
javac ATMWithdrawal.java
java ATMWithdrawal
```

For the uncaught-exception demonstration:

```bash
java ATMWithdrawal --uncaught
```

**Result:** The ATM simulation demonstrates exception handling for file access, invalid inputs and transaction failures. It also demonstrates the use of finally for cleanup.

### Task 2 – User-Defined Exceptions and Propagation

**Problem context:** Online Examination Portal.

Developed an examination portal that handles student registration, examination-window validation and answer-file submission using custom exceptions.

The task demonstrates how exceptions can be created, thrown, propagated and chained across different application layers.

**Concepts covered:**
- User-defined exceptions
- Checked exceptions
- Exception propagation
- Exception chaining
- `throw` and `throws`
- IOException
- `getCause()`
- Layered application design
- File-storage exception handling

**Files included:**
- `ExamPortalDemo.java` – Layered examination portal demonstration.
- `StorageDemo.java` – Completed assignment-provided program.
- `Task2_Report.pdf` – Detailed analytical report.
- `test-cases.md` – Test scenarios and evidence checklist.
- `run_output.txt` – Sample execution output.
- `answer-Valid-submission.pdf` – Sample answer submission file.
- `Java assignment 3 task02 output.png` – Execution screenshot.

**Compilation and execution:**

Main portal demonstration:

```bash
javac ExamPortalDemo.java
java ExamPortalDemo
```

Storage demonstration:

```bash
javac StorageDemo.java
java StorageDemo
```

**Expected output for StorageDemo:**

```text
Saved answer01.pdf
Error: Could not store answer02.pdf
Cause: Disk quota exceeded
```

**Result:** Custom exceptions were implemented to represent examination-related failures. Exception propagation and chaining were demonstrated while preserving the original cause of an error.

### Task 3 – Resource Safety, Debugging and Robust Code

**Problem context:** Multi-Store Sales Report Processing.

Implemented a sales-report application that reads data from multiple store files and calculates the total sales.

Compared manual resource closing with try-with-resources to demonstrate the importance of safe resource management when exceptions occur.

**Concepts covered:**
- File handling
- BufferedReader
- Resource management
- try-catch-finally
- try-with-resources
- Automatic resource closing
- Debugging
- Exception handling
- Resource leakage
- Robust programming

**Files included:**
- `SalesReportDemo.java` – Implements manual-close and try-with-resources approaches.
- `Task3_Report.pdf` – Detailed analytical report.
- `test-cases.md` – Test plan and observations.
- `run_output.txt` – Sample execution output.
- `Java assignment 3 task03 output.png` – Execution screenshot.
- `input/` – Sample store input files.

**Input files:**
- `store1.csv`
- `store2.csv`
- `store4.csv`

`store3_missing.csv` is intentionally absent to demonstrate a resource-management failure. It should not be created for that test.

**Compilation and execution:**

```bash
javac SalesReportDemo.java
java SalesReportDemo
```

Keep the input folder in the same Task03 directory while executing the program.

**Expected observations:**

- Successful processing generates a report with total sales of 500 across three stores.
- The manual-close approach demonstrates how an exception can prevent resources from being closed.
- The try-with-resources approach automatically closes opened resources, even when an exception occurs.

**Result:** The comparison demonstrates that try-with-resources provides safer resource management and prevents the resource leak shown by the manual-close failure test.

### Task 4 – Thread Creation and the Thread Life Cycle

**Problem context:** Scanner Station Management System.

Developed a scanner-station application to demonstrate Java thread creation and the thread life cycle.

Implemented scanner stations using both Thread subclassing and the Runnable interface. Explored thread scheduling, waiting, completion and alternative execution using ExecutorService.

**Concepts covered:**
- Multithreading
- Thread class
- Runnable interface
- `start()`
- `run()`
- `sleep()`
- `join()`
- `isAlive()`
- Thread priorities
- Daemon threads
- Thread life cycle
- ExecutorService
- Callable and Future

**Files included:**
- `ScannerLifecycleDemo.java` – Demonstrates thread creation and life-cycle operations.
- `ScannerExecutorAlternative.java` – Implements an alternative using ExecutorService.
- `Task4_Report.pdf` – Detailed report with thread life-cycle explanations and analysis.
- `run_output.txt` – Sample execution output.
- `executor_output.txt` – ExecutorService execution output.
- `experiment-log-template.csv` – Template for recording repeated experiments.
- `Java assignment 3 task04 output 1.png` – Execution screenshot.
- `Java assignment 3 task04 output 2.png` – Additional execution screenshot.

**Compilation and execution:**

Main thread demonstration:

```bash
javac ScannerLifecycleDemo.java
java ScannerLifecycleDemo
```

ExecutorService alternative:

```bash
javac ScannerExecutorAlternative.java
java ScannerExecutorAlternative
```

**Result:** Thread creation, execution, waiting and termination were demonstrated. The task also illustrates the difference between calling `run()` directly and starting a new thread using `start()`.

Thread priorities are scheduling hints and do not guarantee a particular execution order.

### Task 5 – Race Conditions in a Cinema Seat-Booking System

**Problem context:** Cinema Seat Booking System.

Developed a seat-booking application to demonstrate race conditions that occur when multiple threads attempt to book the same available seat.

Compared unsynchronised execution with synchronized methods, synchronized blocks and an AtomicBoolean-based alternative.

**Concepts covered:**
- Multithreading
- Race conditions
- Thread safety
- Synchronisation
- Synchronized methods
- Synchronized blocks
- AtomicBoolean
- compareAndSet()
- Concurrent execution
- Performance measurement

**Files included:**
- `SeatBooking.java` – Completed assignment-provided synchronized seat-booking program.
- `SeatBookingDemo.java` – Demonstrates race conditions and synchronization techniques.
- `AtomicSeatBooking.java` – Implements the AtomicBoolean alternative.
- `Task5_Report.pdf` – Detailed report and analysis.
- `Task5_test-cases.md` – Test cases and interleaving explanation.
- `run_output.txt` – Sample execution output.
- `Java assignment 3 task05 output 1.png` – Execution screenshot.
- `Java assignment 3 task05 output 2.png` – Additional execution screenshot.
- `Java assignment 3 task05 output 3.png` – Additional execution screenshot.

**Compilation and execution:**

Exact assignment program:

```bash
javac SeatBooking.java
java SeatBooking
```

Expected output:

```text
Seats sold: 10
```

Synchronization experiments:

```bash
javac SeatBookingDemo.java
java SeatBookingDemo
```

AtomicBoolean alternative:

```bash
javac AtomicSeatBooking.java
java AtomicSeatBooking
```

**Result:** Race conditions were demonstrated through concurrent seat-booking operations. Synchronization and atomic operations were used to ensure that a seat is not booked more than once.

### Task 6 – Synchronisation, Deadlock and Inter-Thread Communication

**Problem context:** Digital Wallet Transfers and Token Producer-Consumer System.

Implemented multiple Java programs to demonstrate safe thread coordination, wallet transfers, bounded buffers and deadlock prevention.

Compared unsafe opposite-order locking with consistent lock ordering and explored inter-thread communication using `wait()` and `notifyAll()`.

**Concepts covered:**
- Synchronisation
- Deadlock
- Deadlock prevention
- Lock ordering
- Inter-thread communication
- `wait()`
- `notifyAll()`
- Producer-consumer problem
- Bounded buffers
- Synchronized methods
- ArrayBlockingQueue
- while versus if in wait conditions
- Concurrency testing

**Files included:**
- `WalletTransferDemo.java` – Demonstrates unsafe and safe wallet transfer strategies.
- `TokenCounter.java` – Completed synchronized token-counter implementation.
- `ArrayBlockingQueueAlternative.java` – Implements a bounded queue alternative.
- `TokenCounterWhileVsIf.java` – Demonstrates why while is required instead of if when checking wait conditions.
- `Task6_Report.pdf` – Detailed report and analysis.
- `test-cases.md` – Test cases and validation.
- `compile_output.txt` – Compilation output.
- `TokenCounter_output.txt` – Token-counter execution output.
- `WalletTransferDemo_output.txt` – Wallet-transfer execution output.
- `ArrayBlockingQueueAlternative_output.txt` – Alternative queue execution output.
- `Java assignment 3 task06 output 1.png` – Execution screenshot.
- `Java assignment 3 task06 output 2.png` – Additional execution screenshot.
- `Java assignment 3 task06 output 3.png` – Additional execution screenshot.
- `Java assignment 3 task06 output 4.png` – Additional execution screenshot.

**Compilation and execution:**

Compile all Java programs:

```bash
javac *.java
```

Run the token counter:

```bash
java TokenCounter
```

Run the wallet transfer demonstration:

```bash
java WalletTransferDemo
```

Run the bounded queue alternative:

```bash
java ArrayBlockingQueueAlternative
```

Run the while-versus-if demonstration:

```bash
java TokenCounterWhileVsIf
```

**Expected checks:**

- TokenCounter final sum is 210.
- Buffer size remains between 0 and 5.
- Unsafe opposite-order wallet locking can produce a deadlock.
- Consistent lock ordering allows transfers to complete while preserving the total balance.
- The ArrayBlockingQueue alternative completes with a sum of 210.

**Result:** The task demonstrates safe synchronization, deadlock prevention and inter-thread communication. It also compares a manually synchronized bounded buffer with Java's ArrayBlockingQueue implementation.

### Task 7 – Concurrent and Fault-Tolerant Score File Processing

**Problem context:** Semester Score File Processing System.

Designed a concurrent processing application to process 10,000 semester score files using a fixed thread pool.

Each file is processed independently. The application validates score records, handles missing or corrupt files, collects results and generates a sorted CSV audit report.

The implementation also compares performance using different thread-pool sizes.

**Concepts covered:**
- Multithreading
- ExecutorService
- Fixed thread pools
- Callable interface
- Future interface
- ConcurrentLinkedQueue
- Concurrent collections
- File handling
- Exception handling
- Fault tolerance
- Data validation
- CSV generation
- Performance benchmarking
- Concurrent task processing

**Files included:**
- `ResultProcessor.java` – Main concurrent file-processing implementation.
- `ScoreFileGenerator.java` – Generates a reproducible dataset of score files.
- `Task7_Report.pdf` – Detailed design and implementation report.
- `benchmark_results.csv` – Benchmark measurements.
- `Task7_benchmark_summary.txt` – Benchmark summary.
- `Task7_compile_output.txt` – Compilation output.
- `sample_handcheck_output.txt` – Sample hand-check execution output.
- `test-cases.md` – Testing and validation instructions.
- `sample_data/` – Small sample dataset containing valid, corrupt and missing-file scenarios.
- `Java assignment 3 task07 output.png` – Execution screenshot.

**Compilation:**

Open a terminal in the Task07 folder:

```bash
javac *.java
```

Generate the full 10,000-file dataset:

```bash
java ScoreFileGenerator ..\score_data
```

Execute with different thread-pool sizes:

```bash
java ResultProcessor ..\score_data 1 ..\results_pool_1.csv
java ResultProcessor ..\score_data 2 ..\results_pool_2.csv
java ResultProcessor ..\score_data 4 ..\results_pool_4.csv
java ResultProcessor ..\score_data 8 ..\results_pool_8.csv
```

**Expected behaviour:**

- The program processes 10,000 expected score-file records.
- Valid files are processed successfully.
- Missing and corrupt files are recorded as failures.
- Out-of-range scores are rejected.
- Processing continues even when individual files fail.
- Results are collected in a thread-safe collection.
- A sorted CSV audit report is generated.
- A final success and failure summary is displayed.

**Result:** A concurrent, fault-tolerant score-processing application was developed using ExecutorService, Callable, Future and ConcurrentLinkedQueue. Different thread-pool sizes can be compared to analyse processing performance.

---

## 4. Technologies and Tools Used

| Technology / Tool | Purpose |
|---|---|
| Java | Programming language |
| JDK | Compilation and execution |
| Visual Studio Code | Source-code development |
| Command Prompt / PowerShell | Compilation and execution |
| Java Thread API | Multithreading |
| ExecutorService | Thread-pool management |
| Concurrent Collections | Thread-safe data management |
| File I/O | Reading and writing files |
| CSV | Experimental data and audit reports |
| PDF Editor / Document Editor | Preparation of reports |
| Git and GitHub | Version control and assignment submission |

## 5. Repository Structure

```text
CSA0919-192525148-Programming-in-Java-Assignments/
│
└── Assignment 3/
    │
    ├── README.md
    │
    ├── Task01/
    │   ├── README.md
    │   ├── ATMWithdrawal.java
    │   ├── card-reader.txt
    │   ├── Task1_Report.pdf
    │   └── Java assignment 3 task01 output.png
    │
    ├── Task02/
    │   ├── README.md
    │   ├── ExamPortalDemo.java
    │   ├── StorageDemo.java
    │   ├── Task2_Report.pdf
    │   ├── test-cases.md
    │   ├── run_output.txt
    │   ├── answer-Valid-submission.pdf
    │   └── Java assignment 3 task02 output.png
    │
    ├── Task03/
    │   ├── README.md
    │   ├── SalesReportDemo.java
    │   ├── Task3_Report.pdf
    │   ├── run_output.txt
    │   ├── test-cases.md
    │   ├── input/
    │   │   ├── store1.csv
    │   │   ├── store2.csv
    │   │   └── store4.csv
    │   └── Java assignment 3 task03 output.png
    │
    ├── Task04/
    │   ├── README.md
    │   ├── ScannerLifecycleDemo.java
    │   ├── ScannerExecutorAlternative.java
    │   ├── Task4_Report.pdf
    │   ├── run_output.txt
    │   ├── executor_output.txt
    │   ├── experiment-log-template.csv
    │   ├── Java assignment 3 task04 output 1.png
    │   └── Java assignment 3 task04 output 2.png
    │
    ├── Task05/
    │   ├── README.md
    │   ├── SeatBooking.java
    │   ├── SeatBookingDemo.java
    │   ├── AtomicSeatBooking.java
    │   ├── Task5_Report.pdf
    │   ├── Task5_test-cases.md
    │   ├── run_output.txt
    │   ├── Java assignment 3 task05 output 1.png
    │   ├── Java assignment 3 task05 output 2.png
    │   └── Java assignment 3 task05 output 3.png
    │
    ├── Task06/
    │   ├── README.md
    │   ├── WalletTransferDemo.java
    │   ├── TokenCounter.java
    │   ├── ArrayBlockingQueueAlternative.java
    │   ├── TokenCounterWhileVsIf.java
    │   ├── Task6_Report.pdf
    │   ├── test-cases.md
    │   ├── compile_output.txt
    │   ├── TokenCounter_output.txt
    │   ├── WalletTransferDemo_output.txt
    │   ├── ArrayBlockingQueueAlternative_output.txt
    │   ├── Java assignment 3 task06 output 1.png
    │   ├── Java assignment 3 task06 output 2.png
    │   ├── Java assignment 3 task06 output 3.png
    │   └── Java assignment 3 task06 output 4.png
    │
    └── Task07/
        ├── README.md
        ├── ResultProcessor.java
        ├── ScoreFileGenerator.java
        ├── Task7_Report.pdf
        ├── Task7_compile_output.txt
        ├── Task7_benchmark_summary.txt
        ├── benchmark_results.csv
        ├── sample_handcheck_output.txt
        ├── test-cases.md
        └── sample_data/
```

*Note: This directory tree highlights the main files. Compiled `.class` files and additional supporting files are also present in the task folders.*

## 6. Compilation and Execution

All Java programs can be compiled and executed using the Java Development Kit through Visual Studio Code, Command Prompt or PowerShell.

### General execution procedure

1. Open the required task folder.
2. Open a terminal inside that folder.
3. Check the installed Java version.
4. Compile the required Java source file.
5. Execute the class containing the main method.
6. Provide the required inputs or ensure that supporting files are available.
7. Verify the displayed output against the expected results.
8. Capture the execution screenshot for submission.

### General commands

```bash
javac FileName.java
java ClassName
```

### Check Java version

```bash
java -version
javac -version
```

Some tasks require JDK 17 or later, while the other tasks use Java 8-compatible language and library features. Follow the individual task requirements.

## 7. Testing and Validation

Testing is an important part of Assignment 3.

The programs include different test scenarios to verify normal execution, invalid inputs, exception handling and concurrent behaviour.

The main testing areas include:

- Valid and invalid ATM withdrawal requests.
- Missing card-reader and answer-submission files.
- User-defined exception propagation.
- Resource cleanup after file-processing failures.
- Thread creation and completion behaviour.
- Race-condition reproduction in simultaneous bookings.
- Synchronization and thread safety.
- Deadlock scenarios and prevention.
- Producer-consumer buffer validation.
- Concurrent score-file processing.
- Detection of missing, corrupt and out-of-range score records.

Sample output files are provided as references. Actual screenshots and execution measurements should be captured from the computer used for submission.

## 8. Learning Outcomes

Through this assignment, the following learning outcomes were addressed:

- Applying exception handling to real-world applications.
- Creating and using custom exception classes.
- Understanding exception propagation and chaining.
- Managing files and resources safely.
- Understanding thread creation and thread scheduling.
- Identifying race conditions in concurrent programs.
- Applying synchronization to maintain data consistency.
- Preventing deadlocks through proper locking strategies.
- Implementing producer-consumer systems.
- Using Java concurrency utilities for parallel processing.
- Developing fault-tolerant applications.
- Evaluating concurrent program performance.

## 9. Conclusion

Assignment 3 provides practical experience in implementing robust and concurrent Java applications through real-world programming scenarios.

Across the seven tasks, different exception-handling mechanisms, resource-management techniques, thread models and concurrency utilities are explored and implemented.

The assignment progresses from basic exception handling in ATM and examination applications to resource-safe file processing, multithreading, race-condition prevention, deadlock avoidance and large-scale concurrent score processing.

Overall, this assignment strengthens the understanding of Java exception handling, file management, multithreading, synchronization, concurrency, debugging and robust software development.

---

**Course:** CSA0919 – Programming in Java  
**Assignment:** 3 – Exception Handling, Multithreading and Concurrency  
**Student:** Juhi Surin  
**Register Number:** 192525148
