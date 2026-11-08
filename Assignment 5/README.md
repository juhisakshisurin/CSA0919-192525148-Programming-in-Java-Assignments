# CSA0919 – Programming in Java
# Assignment 5 – Persistence Design & Implementation

## Student Details

| Field | Details |
|---|---|
| **Name** | Juhi Surin |
| **Register Number** | 192525148 |
| **Course Code** | CSA0919 |
| **Course Name** | Programming in Java |
| **Assignment** | Assignment 5 |
| **Programming Language** | Java |

---

## 1. Introduction

Assignment 5 focuses on persistence design, file handling, database connectivity, data-access architecture, object serialization, transaction management and experimental investigation using Java.

The assignment is divided into two parts:

- **Part A – Persistence Design & Pseudocode:** Focuses on analysing persistence requirements, designing storage formats, developing algorithms and evaluating database and serialization approaches.
- **Part B – Implementation & Experimental Investigation:** Focuses on implementing file-storage systems, validating inputs, testing SQL injection, comparing JDBC execution techniques and developing database migration solutions.

Together, these two parts provide practical and theoretical understanding of how applications store, retrieve, validate and manage persistent data safely and efficiently.

## 2. Objectives

- Understand different persistence mechanisms in Java.
- Design structured text and binary file formats.
- Develop algorithms for storing, searching and retrieving records.
- Analyse buffered file-reading techniques and memory efficiency.
- Understand DAO-based database architecture.
- Apply object serialization and deserialization concepts.
- Design reliable database transactions.
- Understand SQL injection vulnerabilities and prevention.
- Implement file-based record management systems.
- Perform input validation and damaged-file handling.
- Use JDBC for database operations.
- Compare Statement, PreparedStatement and batch execution.
- Analyse database performance using different dataset sizes.
- Apply commit and rollback for transaction safety.
- Develop database migration applications.
- Analyse time complexity, space complexity, reliability and scalability.
- Make engineering decisions based on implementation and experimental results.

## 3. Assignment Structure

| Part | Title | Number of Tasks | Main Focus |
|---|---|---:|---|
| Part A | Persistence Design & Pseudocode | 5 | Design, algorithms and analysis |
| Part B | Implementation & Experimental Investigation | 4 | Java implementation, testing and benchmarking |
| **Total** | **Assignment 5** | **9** | **Persistence and database engineering** |

---

# PART A – Persistence Design & Pseudocode

**Focus:** Designing efficient and reliable persistence mechanisms before implementation.

Part A consists of five tasks involving file formats, file-reading strategies, DAO architecture, object serialization and database transaction safety.

### Task A1 – Design a File Format

**Problem context:** Transport Office Bus-Pass Records.

Designed a structured format for storing seven-field bus-pass records using UTF-8 comma-delimited text and binary storage through DataOutputStream.

The task includes pseudocode for adding a pass, finding a pass by ID and listing passes by route.

**Main concepts:**
- Text and binary file storage.
- UTF-8 encoding.
- DataOutputStream.
- RandomAccessFile.
- Record structure and validation.
- File-handling algorithms.
- Time and space complexity.

**Deliverables:**
- `Assignment5_PartA_TaskA1_Report.pdf`
- `TaskA1_Pseudocode.txt`

### Task A2 – Don't Read What You Don't Need

**Problem context:** Large Attendance Log Processing.

Designed and analysed efficient approaches for processing a 50 MB attendance log.

Compared reading files byte by byte, buffered reading and loading the entire file using Files.readAllLines().

**Main concepts:**
- Buffered input.
- File I/O.
- InputStream.
- Memory efficiency.
- File processing performance.
- I/O overhead.
- Time and space complexity.

**Deliverables:**
- `Assignment5_PartA_TaskA2_Report.pdf`
- `TaskA2_Pseudocode.txt`

### Task A3 – Discover the Data-Access Design Pattern

**Problem context:** Student Database Management System.

Designed a reusable generic DAO interface to eliminate repeated database-access logic across StudentDB, CourseDB and MarksDB.

The task includes a StudentDao example and entity-to-table mapping.

**Main concepts:**
- JDBC.
- Data Access Object (DAO).
- Java Generics.
- CRUD operations.
- Database abstraction.
- Separation of concerns.
- Code reusability and maintainability.

**Deliverables:**
- `Assignment5_PartA_TaskA3_Report.pdf`
- `TaskA3_DAO_Design.txt`

### Task A4 – What Should Be Serialized?

**Problem context:** Hostel Management System.

Designed an object-serialization strategy for Hostel, Room and Resident objects.

Identified the fields that should be persisted and the runtime or sensitive fields that should be excluded.

**Main concepts:**
- Java Serialization.
- Serializable interface.
- Deserialization.
- Object graphs.
- Transient variables.
- serialVersionUID.
- Serialization compatibility.
- CSV and JSON alternatives.

**Deliverables:**
- `Assignment5_PartA_TaskA4_Report.pdf`
- `TaskA4_Serialization_Design.txt`

### Task A5 – Design for Correctness and Safety

**Problem context:** Hostel and Canteen Wallet Transaction System.

Designed a safe ₹500 wallet-transfer transaction using JDBC-style pseudocode.

The task also analyses SQL injection and demonstrates the use of PreparedStatement for parameterized database queries.

**Main concepts:**
- JDBC transactions.
- Auto-commit.
- Commit and rollback.
- Database consistency.
- PreparedStatement.
- SQL injection prevention.
- Exception handling.
- Complexity analysis.

**Deliverables:**
- `Assignment5_PartA_TaskA5_Report.pdf`
- `TaskA5_Pseudocode.txt`

### Part A Outcome

Part A establishes the theoretical foundation for persistence and database engineering. It develops the ability to select suitable storage mechanisms, design reusable database-access components, process large files efficiently and ensure reliable database operations.

**Note:** Part A primarily requires design documentation, pseudocode and analytical reports rather than complete executable Java applications.

---

# PART B – Implementation & Experimental Investigation

**Focus:** Implementing persistence solutions and evaluating their performance, correctness and reliability.

Part B consists of four tasks involving Java file storage, adversarial input testing, JDBC benchmarking and database migration.

### Task B1 – Implement and Instrument the File Store

**Problem context:** Transport Office Bus-Pass Record Management.

Implemented a file-based bus-pass storage system and developed supporting validation and performance-benchmarking programs.

The task investigates file processing, buffering efficiency, line counting and search-performance growth.

**Main concepts:**
- Java file handling.
- Persistent storage.
- Record management.
- Buffered reading.
- Input validation.
- File searching.
- Performance instrumentation.
- CSV measurements.
- Time and space complexity.

**Deliverables:**
- `PassStore.java`
- `B1Validation.java`
- `LineCounterBenchmark.java`
- `SearchGrowthBenchmark.java`
- `Assignment5_PartB_B1_Report.pdf`
- `PassStore_output.txt`
- `B1Validation_output.txt`
- `buffering_benchmark.txt`
- `search_growth_benchmark.txt`
- `measurements.csv`

### Task B2 – Engineer an Adversarial Input

**Problem context:** Bus-Pass Login Validation and Damaged Record Processing.

Implemented controlled tests to demonstrate SQL injection vulnerabilities in an unsafe login implementation and compare them with a secure PreparedStatement implementation.

The task also validates damaged bus-pass records containing incorrect field counts, invalid numeric amounts and empty lines.

**Main concepts:**
- JDBC.
- SQL injection.
- Statement and PreparedStatement.
- Parameter binding.
- Input validation.
- CSV file processing.
- Exception handling.
- Damaged-file handling.
- Database testing.

**Deliverables:**
- `B2AdversarialTest.java`
- `B2DamagedFileTest.java`
- `Assignment5_PartB_B2_Report.pdf`
- `create_test_database.sql`
- `damaged_passes.csv`
- `compile_check.txt`
- `expected_run.txt`

**Expected security comparison:**

| Implementation | Expected Result |
|---|---|
| Unsafe SQL concatenation | Vulnerable to the controlled injection payload |
| PreparedStatement | Rejects the injected payload as a literal parameter |
| Valid login credentials | Accepted |

### Task B3 – Statement vs PreparedStatement vs Batch

**Problem context:** Bus-Pass Database Insertion and Performance Benchmarking.

Implemented and compared three JDBC database-insertion techniques: Statement, PreparedStatement and PreparedStatement batch execution.

The task also compares transaction behaviour when an invalid record is encountered during batch insertion.

**Main concepts:**
- JDBC.
- Statement.
- PreparedStatement.
- Batch execution.
- executeBatch().
- ResultSet and ResultSetMetaData.
- MySQL.
- Database transactions.
- Auto-commit.
- Commit and rollback.
- Performance benchmarking.
- Bulk data insertion.

**Deliverables:**
- `B3Runner.java`
- `PassLoader.java`
- `Assignment5_PartB_B3_Report.pdf`
- `create_database_and_table.sql`
- `generated_data_notes.txt`
- `compile_check.txt`

**Benchmark dataset sizes:**
- 1,000 records.
- 10,000 records.
- 100,000 records.

**Engineering recommendation:**

- Use PreparedStatement batching inside a single transaction for large imports.
- Use a single PreparedStatement execution for inserting one bus pass.
- Avoid SQL concatenation with untrusted input.

### Task B4 – Final Engineering Decision

**Problem context:** Multi-Campus Bus-Pass Migration System.

Developed a Java migration application that reads bus-pass records from a CSV file, validates them and inserts accepted records into a MySQL database.

Rejected records are recorded with line numbers and rejection reasons. Accepted records are inserted using PreparedStatement batches inside a single transaction.

The task also uses DatabaseMetaData to inspect the database environment and evaluates storage architecture for a multi-campus system.

**Main concepts:**
- Java file handling.
- CSV processing.
- Input validation.
- JDBC.
- PreparedStatement.
- Batch processing.
- Database transactions.
- DatabaseMetaData.
- MySQL.
- Composite primary keys.
- Database indexing.
- Bulk migration.
- Production architecture.
- Scalability and reliability.

**Deliverables:**
- `B4Migration.java`
- `Assignment5_PartB_B4_Report.pdf`
- `create_database_and_table.sql`
- `passes_b4.csv`

**Expected sample validation results:**

| Parameter | Expected Count |
|---|---:|
| Input lines | 16 |
| Accepted records | 11 |
| Rejected records | 5 |
| Committed records | 11 |

**Final engineering decision:**

A shared relational database is recommended for the 12-campus bus-pass management system because it supports concurrent operations, indexing, transaction safety, data integrity and centralised management.

### Part B Outcome

Part B provides practical experience in developing file-storage applications, testing database security, comparing SQL execution methods, measuring performance and implementing reliable database migration.

---

## 4. Technologies and Tools Used

| Technology / Tool | Purpose |
|---|---|
| Java | Main programming language |
| JDK 11+ | Compilation and execution |
| Java File I/O | File reading and writing |
| UTF-8 | Text-file encoding |
| CSV | Structured input and output |
| DataOutputStream | Binary storage design |
| Buffered Input | Efficient file processing |
| Java Serialization | Object persistence design |
| JDBC | Java database connectivity |
| DAO Pattern | Database-access abstraction |
| H2 Database | Lightweight database for B2 testing |
| MySQL Server | Database operations for B3 and B4 |
| MySQL Connector/J | JDBC connectivity to MySQL |
| Statement | Standard SQL execution |
| PreparedStatement | Parameterized SQL execution |
| Batch Processing | Bulk database insertion |
| DatabaseMetaData | Database and driver information |
| SQL | Database schema and operations |
| Visual Studio Code | Development environment |
| PowerShell / Command Prompt | Compilation and execution |
| Git and GitHub | Version control and submission |
| PDF / Document Editor | Report preparation |

## 5. Repository Structure

```text
CSA0919-192525148-Programming-in-Java-Assignments/
│
└── Assignment 5/
    │
    ├── README.md
    │
    ├── PART A - Persistence Design & Pseudocode/
    │   │
    │   ├── README.md
    │   │
    │   ├── TASK A1 – DESIGN A FILE FORMAT/
    │   ├── TASK A2 – DON’T READ WHAT YOU DON’T NEED/
    │   ├── TASK A3 – DISCOVER THE DATA-ACCESS DESIGN PATTERN/
    │   ├── TASK A4 – WHAT SHOULD BE SERIALIZED?/
    │   └── TASK A5 – DESIGN FOR CORRECTNESS AND SAFETY/
    │
    └── PART B – Implementation & Experimental Investigation/
        │
        ├── README.md
        │
        ├── TASK B1 – IMPLEMENT AND INSTRUMENT THE FILE STORE/
        ├── TASK B2 – ENGINEER AN ADVERSARIAL INPUT/
        ├── TASK B3 – STATEMENT vs PREPAREDSTATEMENT vs BATCH/
        └── TASK B4 – FINAL ENGINEERING DECISION/
```

Each task folder contains its relevant source files or design documents, reports, input data, SQL scripts, expected outputs and supporting files as applicable.

## 6. Execution Requirements

### Part A

Part A contains design and pseudocode deliverables. Complete Java execution is not required for these tasks.

### Part B

Part B requires Java compilation and execution. The exact requirements depend on the task.

| Task | Java Version | Additional Requirement |
|---|---|---|
| B1 | Java 11+ recommended | Local file storage and benchmark inputs |
| B2 | Java 11+ | H2 JDBC JAR for the login demonstration |
| B3 | Java 11+ | MySQL Server and MySQL Connector/J |
| B4 | Java 11+ | MySQL Server and MySQL Connector/J |

### Check Java Installation

```bash
java -version
javac -version
```

### General Java Compilation

```bash
javac FileName.java
java ClassName
```

### JDBC Execution

For Windows PowerShell, the classpath uses a semicolon:

```powershell
javac -cp "lib\mysql-connector-j.jar" -d output src\MainClass.java
java -cp "output;lib\mysql-connector-j.jar" MainClass
```

For Linux and macOS, use a colon instead.

**Database security note:** Configure your local database credentials through environment variables. Never commit passwords or other confidential credentials to GitHub.

Refer to the individual Part B task README files for complete setup, compilation and execution instructions.

## 7. Experimental Investigation and Analysis

Experimental investigation is an important part of Assignment 5, particularly in Part B.

The main evaluation areas include:

- Buffered file-reading performance.
- File-search performance as dataset size increases.
- Record-validation correctness.
- SQL injection resistance.
- JDBC insertion performance.
- Statement versus PreparedStatement execution.
- Batch insertion efficiency.
- Auto-commit versus transaction-based insertion.
- Rollback behaviour after database failures.
- Database metadata inspection.
- Bulk migration correctness.
- Storage architecture scalability.

### Complexity Analysis

The assignment explores time and space complexity across different persistence approaches.

Examples include:

| Operation | General Complexity |
|---|---|
| Sequential file search | O(n) |
| Reading all records | O(n) |
| Processing records with a fixed-size buffer | O(n) time and O(b) auxiliary buffer space |
| Indexed database lookup | Typically O(log n), depending on the index and database |
| Inserting n records individually | O(n) database operations |
| Batch submission of n records in groups of b | Approximately O(ceil(n/b)) batch submissions |

Actual performance depends on the dataset, hardware, JVM, database configuration, indexes, JDBC driver and system load.

Use actual local execution results in the reports instead of inventing or assuming benchmark timings.

## 8. Testing and Validation

The assignment covers multiple testing and validation approaches.

| Area | Validation Focus |
|---|---|
| File storage | Record creation, reading and searching |
| File validation | Correct handling of invalid records |
| Buffered processing | Efficient reading of large files |
| DAO design | Reusable and separated data-access logic |
| Serialization | Correct persistence of required object fields |
| Transaction design | Commit and rollback behaviour |
| SQL security | Rejection of controlled injection payloads |
| JDBC insertion | Successful insertion and row-count verification |
| Batch execution | Performance and failure behaviour |
| Database migration | Accepted and rejected record counts |
| Database metadata | Product, driver and table information |
| Data integrity | Duplicate prevention and transactional correctness |

## 9. Learning Outcomes

After completing Assignment 5, the following learning outcomes are addressed:

- Understanding persistence concepts in Java.
- Designing structured data-storage formats.
- Implementing file-based storage mechanisms.
- Applying buffered reading for efficient file processing.
- Evaluating file and database performance.
- Understanding DAO architecture and reusable database operations.
- Applying Java serialization concepts.
- Managing sensitive and transient object fields.
- Designing safe database transactions.
- Preventing SQL injection using PreparedStatement.
- Developing JDBC applications.
- Comparing different SQL execution methods.
- Implementing batch database operations.
- Using commit and rollback for data consistency.
- Validating large input datasets.
- Developing database migration solutions.
- Evaluating scalable storage architectures.
- Making technical decisions based on experimental evidence.

## 10. Overall Conclusion

Assignment 5 provides a comprehensive understanding of persistence design, file processing, database management and data reliability using Java.

Part A establishes the design foundation through file-format selection, efficient reading strategies, DAO architecture, serialization and transaction-safety analysis.

Part B extends these concepts into practical implementation and experimental investigation through file-store development, input validation, SQL injection testing, JDBC benchmarking and database migration.

The two parts together demonstrate the importance of selecting appropriate persistence mechanisms, writing secure database code, managing transactions correctly and evaluating application performance.

Overall, Assignment 5 strengthens practical software-engineering skills in Java, file handling, JDBC, database security, performance analysis, data integrity and scalable application design.

---

**Course:** CSA0919 – Programming in Java  
**Assignment:** Assignment 5 – Persistence Design & Implementation  
**Student:** Juhi Surin  
**Register Number:** 192525148
