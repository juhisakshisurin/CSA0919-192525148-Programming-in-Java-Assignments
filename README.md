# CSA0919 – Programming in Java
## Complete Assignment Portfolio (Assignments 1–5)

### Student Details

| Field | Details |
|---|---|
| **Name** | Juhi Surin |
| **Register Number** | 192525148 |
| **Course Code** | CSA0919 |
| **Course Name** | Programming in Java |
| **Programming Language** | Java |

---

## 1. Introduction

This repository contains the Java programming coursework and practical assignments completed for **CSA0919 – Programming in Java**.

It brings together five assignments that progressively cover Java fundamentals, object-oriented programming, collections and data structures, exception handling, multithreading, GUI and event-driven programming, file persistence, JDBC, database security and experimental performance analysis.

The assignments use practical scenarios to connect programming concepts with application requirements. They progress from designing and implementing an object-oriented parking system to evaluating data structures, managing concurrency, developing desktop applications and designing and implementing persistence solutions.

## 2. Assignment Overview

| Assignment | Theme | Tasks | Main Focus |
|---|---|---:|---|
| [Assignment 1](./Assignment%201/) | Campus Smart-Parking System | 12 | Java fundamentals and object-oriented programming |
| [Assignment 2](./Assignment%202/) | Java Collections, Data Structures and Performance Analysis | 7 | Collections, hashing, maps, queues, generics and benchmarking |
| [Assignment 3](./Assignment%203/) | Exception Handling and Multithreading | 7 | Exceptions, resource safety, concurrency and fault tolerance |
| [Assignment 4](./Assignment%204/) | GUI and Event-Driven Programming | 6 | AWT, Swing, event handling, JDBC and desktop application design |
| [Assignment 5](./Assignment%205/) | Persistence Design & Implementation | 9 | File storage, serialization, JDBC, database security and migration |
| **Total** | **Java Programming Portfolio** | **41** | **Core and applied Java programming** |

---

## 3. Assignment-wise Description

### Assignment 1 – Campus Smart-Parking System

[Open Assignment 1](./Assignment%201/)

**12 Tasks | Focus: Java Fundamentals and Object-Oriented Programming**

Designed and developed an object-oriented Campus Smart-Parking System for managing a multi-level parking facility used by students, staff and visitors.

The system considers different vehicle categories, parking-slot allocation, vehicle entry and exit, ticket generation, parking-fee calculation, monthly-pass validation and electric-vehicle charging fees.

**Task coverage:**

1. Understand the Problem and Identify the Objects.
2. Choose the Data Representation.
3. Design the Control Logic.
4. Design Classes, Objects and Constructors.
5. Apply Static Members and the Object Life Cycle.
6. Design the Inheritance Hierarchy.
7. Override and Specialise Behaviour.
8. Apply Polymorphism.
9. Introduce Abstraction.
10. Enforce Encapsulation through Packages and Access Modifiers.
11. Implement, Test and Defend the Design.
12. Re-evaluate and Adapt the Design.

**Key concepts:** Classes, objects, constructors, arrays, control structures, inheritance, method overriding, polymorphism, abstraction, interfaces, encapsulation, packages, access modifiers and exception handling.

**Overall outcome:** Built an object-oriented parking-system design and extended it to accommodate additional vehicle types, visitor passes and a multi-floor facility with varying parking capacities.

### Assignment 2 – Java Collections, Data Structures and Performance Analysis

[Open Assignment 2](./Assignment%202/)

**7 Tasks | Focus: Java Collections and Performance Evaluation**

Applied Java Collections and data structures to practical scenarios involving e-ticket generation, playlist management, attendance de-duplication, pharmacy inventory, team ranking and emergency triage.

The assignment compares alternative implementations and evaluates performance, memory behaviour and suitability for different operations.

**Task coverage:**

1. Compare String, StringBuilder and StringBuffer.
2. Compare ArrayList and LinkedList.
3. Engineer the hashCode–equals Contract.
4. Choose a Map for a Pharmacy Inventory.
5. Implement a Generic Ranking Component.
6. Priority, Order and Fairness in a Queue.
7. Diagnose, Redesign and Validate.

**Key concepts:** String immutability, StringBuilder, StringBuffer, ArrayList, LinkedList, ArrayDeque, HashSet, equals(), hashCode(), HashMap, TreeMap, LinkedHashMap, Java Generics, Comparator, PriorityQueue and benchmarking.

**Overall outcome:** Compared alternative data structures and collection implementations, analysed their performance and selected suitable approaches based on the requirements of each scenario.

### Assignment 3 – Exception Handling and Multithreading

[Open Assignment 3](./Assignment%203/)

**7 Tasks | Focus: Robustness, Concurrency and Thread Management**

Applied Java exception handling, resource management and multithreading to real-world scenarios such as ATM withdrawals, examination submissions, sales-report processing, scanner stations, cinema seat booking, wallet transfers and score-file processing.

**Task coverage:**

1. Bank ATM Simulation.
2. User-Defined Exceptions and Propagation.
3. Resource Safety, Debugging and Robust Code.
4. Thread Creation and the Thread Life Cycle.
5. Race Conditions in a Cinema Seat-Booking System.
6. Synchronisation, Deadlock and Inter-Thread Communication.
7. Concurrent and Fault-Tolerant Score File Processing.

**Key concepts:** Checked and unchecked exceptions, custom exceptions, exception propagation, try-with-resources, file handling, Thread, Runnable, ExecutorService, race conditions, synchronization, atomic operations, locks, deadlocks, wait(), notifyAll(), producer-consumer coordination and thread pools.

**Overall outcome:** Developed and analysed programs that handle exceptional conditions, protect shared resources, coordinate concurrent operations and process independent files using controlled thread pools.

### Assignment 4 – GUI and Event-Driven Programming

[Open Assignment 4](./Assignment%204/)

**6 Tasks | Focus: Desktop Interfaces and Event Handling**

Designed and implemented GUI-based applications using Java AWT and Swing. The tasks cover event-driven design, interface behaviour, event listener implementations, database connectivity and layered application architecture.

**Task coverage:**

1. GUI Design Review.
2. From Requirement to Event-Driven Design.
3. Campus Library Search Screen.
4. Event-Handling Forensics.
5. Same Screen, Different Event-Handling Styles.
6. Campus Event Registration Desktop Application.

**Key concepts:** AWT, Swing, GUI design, event sources, event listeners, ActionListener, MouseListener, event delegation, inner classes, anonymous classes, lambda expressions, window events, repainting, JDBC, MySQL, PreparedStatement, service and repository layers, pagination and CSV export.

**Overall outcome:** Developed desktop application designs and implementations for fee payments, library searching, event handling, currency conversion and campus event registration.

### Assignment 5 – Persistence Design & Implementation

[Open Assignment 5](./Assignment%205/)

**9 Tasks | Focus: Persistence, Database Engineering and Experimental Investigation**

Explored how Java applications store, retrieve, validate and manage persistent data. The assignment is organised into two parts.

**Part A – Persistence Design & Pseudocode (5 Tasks)**

[Open Part A](./Assignment%205/PART%20A%20-%20Persistence%20Design%20%26%20Pseudocode/)

1. Design a File Format.
2. Don't Read What You Don't Need.
3. Discover the Data-Access Design Pattern.
4. What Should Be Serialized?
5. Design for Correctness and Safety.

**Part A concepts:** Text and binary file formats, UTF-8, DataOutputStream, buffered reading, DAO pattern, Java Generics, serialization, transient fields, serialVersionUID, JDBC transaction design, commit, rollback and PreparedStatement.

**Part B – Implementation & Experimental Investigation (4 Tasks)**

[Open Part B](./Assignment%205/PART%20B%20%E2%80%93%20Implementation%20%26%20Experimental%20Investigation/)

1. Implement and Instrument the File Store.
2. Engineer an Adversarial Input.
3. Statement vs PreparedStatement vs Batch.
4. Final Engineering Decision.

**Part B concepts:** Java file handling, record validation, performance instrumentation, CSV processing, SQL injection testing, H2, JDBC, MySQL, Statement, PreparedStatement, batch insertion, ResultSetMetaData, transactions, DatabaseMetaData and database migration.

**Overall outcome:** Designed and implemented file-persistence and database workflows, evaluated JDBC techniques, tested security and transaction behaviour, and justified a relational database architecture for a multi-campus bus-pass system.

---

## 4. Technologies and Tools

| Technology / Tool | Application Areas |
|---|---|
| Java | All five assignments |
| JDK / javac | Compilation and execution |
| Java Collections Framework | Lists, sets, maps and queues |
| Java Generics | Reusable components and ranking |
| Exception Handling | Input validation and fault management |
| Multithreading | Concurrent processing and synchronization |
| AWT and Swing | GUI and desktop applications |
| Event Listeners | Event-driven interaction |
| JDBC | Database connectivity |
| MySQL | Database-backed applications and migration |
| H2 Database | Controlled JDBC security testing |
| Java File I/O | File processing and persistence |
| CSV | Input files, benchmark results and exports |
| Serialization | Object persistence design |
| SQL | Database schema and operations |
| Visual Studio Code | Java development environment |
| PowerShell / Terminal | Build and execution |
| Git and GitHub | Version control and assignment submission |
| PDF / Document Editor | Technical reports and documentation |

## 5. Repository Structure

```text
CSA0919-192525148-Programming-in-Java-Assignments/
│
├── README.md
│
├── Assignment 1/
│   ├── README.md
│   ├── Task01/
│   ├── Task02/
│   ├── Task03/
│   ├── Task04/
│   ├── Task05/
│   ├── Task06/
│   ├── Task07/
│   ├── Task08/
│   ├── Task09/
│   ├── Task10/
│   ├── Task11/
│   └── Task12/
│
├── Assignment 2/
│   ├── README.md
│   ├── Task01/
│   ├── Task02/
│   ├── Task03/
│   ├── Task04/
│   ├── Task05/
│   ├── Task06/
│   └── Task07/
│
├── Assignment 3/
│   ├── README.md
│   ├── Task01/
│   ├── Task02/
│   ├── Task03/
│   ├── Task04/
│   ├── Task05/
│   ├── Task06/
│   └── Task07/
│
├── Assignment 4/
│   ├── README.md
│   ├── Task01/
│   ├── Task02/
│   ├── Task03/
│   ├── Task04/
│   ├── Task05/
│   └── Task06/
│
└── Assignment 5/
    ├── README.md
    ├── PART A - Persistence Design & Pseudocode/
    │   ├── README.md
    │   ├── TASK A1/
    │   ├── TASK A2/
    │   ├── TASK A3/
    │   ├── TASK A4/
    │   └── TASK A5/
    │
    └── PART B – Implementation & Experimental Investigation/
        ├── README.md
        ├── TASK B1/
        ├── TASK B2/
        ├── TASK B3/
        └── TASK B4/
```

*Note: This is a high-level overview of the repository. Refer to the individual assignment and task folders for the exact source files, report names, input data, SQL scripts, output files and supporting materials.*

## 6. General Compilation and Execution

The Java programs in this repository can be compiled and executed using the Java Development Kit.

### Check Java installation

```bash
java -version
javac -version
```

### Compile and run a single Java file

```bash
javac FileName.java
java ClassName
```

### Compile multiple Java files

```bash
javac *.java
java MainClassName
```

### Compile files into an output folder

```bash
javac -d output *.java
java -cp output MainClassName
```

### JDBC applications

Some applications in Assignments 4 and 5 require a database server and an appropriate JDBC driver. Configure the database connection and classpath according to the README inside the relevant task folder.

**Important:** Compilation commands and input requirements vary by task. Always refer to the task-specific README before executing a program. Do not upload database passwords or other confidential credentials to the repository.

## 7. Testing and Validation

The assignments use different testing approaches to check correctness, reliability and performance.

| Assignment | Main Testing and Validation Areas |
|---|---|
| Assignment 1 | Class behaviour, parking allocation, ticket generation, fee calculation, validation and design extensions |
| Assignment 2 | Output correctness, collection behaviour, equality contracts, ordering, benchmark measurements and data-structure comparisons |
| Assignment 3 | Exception scenarios, resource cleanup, thread execution, race conditions, synchronization, deadlock handling and concurrent file processing |
| Assignment 4 | GUI interactions, mouse and keyboard events, invalid input handling, compiler diagnostics, database operations and registration validation |
| Assignment 5 | File validation, SQL injection tests, JDBC insertion, batch performance, transaction rollback, migration validation and database metadata |

Execution output files, benchmark results, reports and screenshots are stored in the corresponding task folders where applicable.

## 8. Learning Outcomes

The complete assignment portfolio develops the following skills:

- Applying Java programming fundamentals to practical problems.
- Designing classes and objects using object-oriented principles.
- Implementing inheritance, polymorphism, abstraction and encapsulation.
- Selecting suitable collections and data structures.
- Understanding hashing, equality and object comparison.
- Analysing algorithmic time and space complexity.
- Implementing robust exception-handling mechanisms.
- Managing resources safely.
- Developing concurrent applications with threads.
- Preventing race conditions and managing shared resources.
- Designing desktop interfaces using AWT and Swing.
- Implementing event-driven interaction.
- Connecting Java applications to relational databases using JDBC.
- Designing file-based persistence and serialization strategies.
- Applying parameterized SQL and transaction management.
- Performing experimental performance analysis.
- Documenting implementation decisions and technical findings.
- Maintaining organised source code and version history using Git and GitHub.

## 9. Conclusion

This repository presents the progression of Java programming work completed across Assignments 1 to 5 for CSA0919 – Programming in Java.

The coursework begins with Java fundamentals and object-oriented design, then advances to collections, data structures, performance benchmarking, exception handling, multithreading, GUI development, event-driven programming and database connectivity.

The final assignment brings together persistence design, file processing, database security, JDBC performance evaluation and migration engineering.

Across the five assignments, practical scenarios are used to develop programming, problem-solving, testing, analysis and documentation skills.

Overall, this portfolio demonstrates the application of Java concepts to progressively more complex software engineering problems, with an emphasis on correctness, maintainability, performance, reliability and secure data management.

---

**Course:** CSA0919 – Programming in Java  
**Assignments Covered:** 1, 2, 3, 4 and 5  
**Student:** Juhi Surin  
**Register Number:** 192525148
