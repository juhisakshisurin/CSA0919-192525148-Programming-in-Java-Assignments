# CSA0919 – Programming in Java
## Assignment 5 – Part B: Implementation & Experimental Investigation

### Student Details

| Field | Details |
|---|---|
| **Name** | Juhi Surin |
| **Register Number** | 192525148 |
| **Course Code** | CSA0919 |
| **Course Name** | Programming in Java |
| **Assignment** | Assignment 5 |
| **Part** | Part B – Implementation & Experimental Investigation |
| **Programming Language** | Java |

---

## 1. Introduction

Part B of Assignment 5 focuses on implementing, testing and experimentally investigating persistence mechanisms and database operations using Java.

This part builds upon the file-storage and database-design concepts introduced in Part A. It involves developing practical file-processing applications, validating adversarial inputs, comparing JDBC execution techniques and making engineering decisions based on experimental observations.

The four tasks progressively cover file-store implementation, data validation, SQL injection testing, JDBC performance benchmarking, batch processing and large-scale database migration.

## 2. Objectives

- Implement persistent file storage using Java.
- Develop efficient file-reading and searching mechanisms.
- Instrument file operations and record experimental measurements.
- Validate records and handle damaged input files.
- Demonstrate SQL injection vulnerabilities using controlled test inputs.
- Prevent SQL injection using PreparedStatement.
- Compare Statement, PreparedStatement and batch execution.
- Understand JDBC transactions and auto-commit behaviour.
- Perform database insertion and retrieval operations.
- Collect benchmark results for different dataset sizes.
- Examine database metadata and schema information.
- Design reliable bulk-data migration procedures.
- Analyse correctness, performance, scalability and data safety.
- Make engineering decisions based on implementation and experimental evidence.

## 3. Task-wise Description

### Task B1 – Implement and Instrument the File Store

**Problem context:** Transport Office Bus-Pass Record Management.

Implemented a file-based persistence system for storing and managing bus-pass records.

The task develops the file-store operations and instruments their performance to investigate the effect of different reading and searching strategies.

The implementation includes validation and benchmarking programs to examine file-processing behaviour under different workloads.

**Concepts covered:**
- File handling
- Persistent storage
- Text-file processing
- Record management
- File input and output
- Buffered reading
- Record searching
- Input validation
- Performance instrumentation
- Benchmarking
- CSV-based measurements
- Time and space complexity

**Files included:**
- `PassStore.java` – Bus-pass file-store implementation.
- `B1Validation.java` – Validation implementation and tests.
- `LineCounterBenchmark.java` – Line-counting performance benchmark.
- `SearchGrowthBenchmark.java` – Search-performance growth benchmark.
- `Assignment5_PartB_B1_Report.pdf` – Detailed implementation and experimental report.
- `PassStore_output.txt` – File-store execution output.
- `B1Validation_output.txt` – Validation execution output.
- `buffering_benchmark.txt` – Buffering benchmark results.
- `search_growth_benchmark.txt` – Search-growth benchmark results.
- `measurements.csv` – Experimental measurements.

**Execution commands:**

Compile the Java source files:

```bash
javac PassStore.java B1Validation.java LineCounterBenchmark.java SearchGrowthBenchmark.java
```

Run the file-store application:

```bash
java PassStore
```

Run validation:

```bash
java B1Validation
```

Run the line-counting benchmark:

```bash
java LineCounterBenchmark
```

Run the search-growth benchmark:

```bash
java SearchGrowthBenchmark
```

**Result:** The file-store implementation and supporting validation and benchmark programs provide a practical way to evaluate persistent bus-pass record management, buffering efficiency and search performance.

**Note:** Verify the individual programs' arguments and file-path requirements before execution. The repository includes prepared output and measurement files for reference.

### Task B2 – Engineer an Adversarial Input

**Problem context:** Bus-Pass Login Validation and Damaged Record Processing.

Developed controlled tests to demonstrate SQL injection vulnerabilities in an unsafe login implementation and compare it with a secure PreparedStatement implementation.

The task also examines damaged bus-pass records containing incorrect field counts, invalid numeric amounts and empty lines.

The application validates the input records and continues processing after encountering invalid entries.

**Concepts covered:**
- JDBC
- SQL injection
- Statement
- PreparedStatement
- Parameter binding
- User-input validation
- File handling
- CSV record validation
- Exception handling
- Damaged-file processing
- Error reporting
- Database testing
- Time and space complexity

**Files included:**
- `B2AdversarialTest.java` – JDBC login tests and damaged-file validation.
- `B2DamagedFileTest.java` – Standalone damaged-file validation.
- `Assignment5_PartB_B2_Report.pdf` – Detailed report and security analysis.
- `create_test_database.sql` – Test database and account setup script.
- `damaged_passes.csv` – Sample damaged bus-pass file.
- `compile_check.txt` – Compilation-check information.
- `expected_run.txt` – Reference execution transcript.

**Requirements:**
- JDK 11 or later.
- H2 JDBC database driver for the login demonstration.
- No external MySQL server is required for this task.

**Database setup:**

Download H2 version 2.3.232 and save the JAR as:

`lib/h2-2.3.232.jar`

Create the required output and data folders in Windows PowerShell:

```powershell
New-Item -ItemType Directory -Force output, data
```

**Compile and run the main demonstration:**

```powershell
javac -d output src\B2AdversarialTest.java
java -cp "output;lib\h2-2.3.232.jar" B2AdversarialTest
```

**Run the standalone damaged-file test:**

```powershell
javac -d output src\B2DamagedFileTest.java
java -cp output B2DamagedFileTest
```

**Adversarial login input:**

```text
' OR '1'='1' -- 
```

Wrong password:

```text
definitely-wrong
```

**Expected login results:**

```text
UNSAFE_LOGIN=true
PREPARED_LOGIN=false
VALID_LOGIN=true
```

The unsafe login demonstrates how concatenating untrusted input into an SQL statement can alter its condition. The PreparedStatement implementation binds the supplied input as a literal parameter.

**Damaged-file test cases:**

| Test case | Input condition | Expected behaviour |
|---|---|---|
| Valid record | Five valid fields | Accepted |
| Wrong field count | Only four fields | Rejected |
| Invalid amount | Non-numeric amount | Rejected |
| Empty line | Blank record | Skipped |
| Valid record after errors | Five valid fields | Accepted |

Expected summary:

- Valid rows accepted: 2
- Wrong-field-count rows skipped: 1
- Invalid-amount rows skipped: 1
- Empty lines skipped: 1

**Result:** SQL injection was demonstrated using a controlled login test. PreparedStatement prevented the supplied payload from changing the SQL query structure. The damaged-file validator also identified invalid records while continuing to process subsequent valid records.

### Task B3 – Statement vs PreparedStatement vs Batch

**Problem context:** Bus-Pass Database Insertion and Performance Benchmarking.

Implemented and compared three JDBC database-insertion approaches: Statement, PreparedStatement and PreparedStatement batch execution.

The task examines their execution performance with different numbers of bus-pass records and investigates transaction behaviour when an invalid record is encountered during batch processing.

It also evaluates ResultSetMetaData to inspect database column names and data types.

**Concepts covered:**
- JDBC
- Statement
- PreparedStatement
- Batch processing
- addBatch()
- executeBatch()
- ResultSet
- ResultSetMetaData
- SQL insertion
- Database transactions
- Auto-commit
- Commit and rollback
- Exception handling
- Performance benchmarking
- Bulk data processing
- Time and space complexity

**Files included:**
- `B3Runner.java` – Main JDBC experiments and performance measurements.
- `PassLoader.java` – Completed transaction-based batch loader.
- `Assignment5_PartB_B3_Report.pdf` – Detailed report and experimental analysis.
- `create_database_and_table.sql` – MySQL database and table creation script.
- `generated_data_notes.txt` – Generated data and failure-placement information.
- `compile_check.txt` – Compilation-check information.

**Requirements:**
- Java 11 or later.
- MySQL Server 8.x or compatible.
- MySQL Connector/J JDBC driver.

**Step 1 – Database setup**

Execute:

`create_database_and_table.sql`

The script creates the `assignment5_b3` database and the `bus_pass` table.

**Step 2 – Add MySQL Connector/J**

Download the JDBC driver and place it inside the `lib` folder.

Use the filename:

`mysql-connector-j.jar`

**Step 3 – Configure database credentials**

For Windows PowerShell:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/assignment5_b3"
$env:DB_USER="root"
$env:DB_PASSWORD="your_mysql_password"
```

Replace the password with your local MySQL password. Do not upload database credentials to GitHub.

**Step 4 – Compile**

```powershell
New-Item -ItemType Directory -Force output

javac -cp "lib\mysql-connector-j.jar" -d output src\PassLoader.java src\B3Runner.java
```

**Step 5 – Run**

```powershell
java -cp "output;lib\mysql-connector-j.jar" B3Runner
```

For Linux or macOS, use `:` instead of `;` in the classpath.

**Experimental design:**

The benchmark compares all three insertion approaches using:

- 1,000 records.
- 10,000 records.
- 100,000 records.

Each implementation is evaluated using insertion measurements and database row-count verification.

**Transaction experiment:**

An invalid amount is deliberately introduced at the start of batch 6.

The task compares two approaches:

1. Batch insertion with auto-commit enabled.
2. Batch insertion inside a single transaction.

Expected behaviour:

- In auto-commit mode, the first five successful batches can remain committed, resulting in 5,000 rows.
- In a single transaction, the failure should trigger rollback and leave zero inserted rows.

**Result:** The task compares the performance and reliability of Statement, PreparedStatement and batch insertion. It demonstrates how transaction management affects data consistency when a database operation fails.

**Engineering recommendation:**

- For importing 100,000 records, PreparedStatement batching within a single transaction is recommended.
- For inserting one bus pass at a counter, PreparedStatement with a single executeUpdate operation is suitable.
- Statement is included as a comparison baseline rather than the preferred option for untrusted input.

### Task B4 – Final Engineering Decision

**Problem context:** Multi-Campus Bus-Pass Migration System.

Developed a migration application that reads bus-pass records from a CSV file, validates them and inserts accepted records into a MySQL database.

Rejected records are written into a separate validation-failure report containing their line numbers and rejection reasons.

The application performs accepted-record insertion using PreparedStatement batches of 1,000 records within a single transaction.

It also retrieves database and JDBC driver information using DatabaseMetaData.

The task concludes by evaluating a suitable storage architecture for a production system operating across 12 campuses and multiple counters.

**Concepts covered:**
- Java file handling
- CSV processing
- Input validation
- JDBC
- PreparedStatement
- Batch processing
- Database transactions
- Commit and rollback
- DatabaseMetaData
- MySQL
- Primary keys
- Composite keys
- Database indexing
- Bulk data migration
- Fault handling
- Production architecture
- Performance analysis
- Scalability and maintainability

**Files included:**
- `B4Migration.java` – Bus-pass migration implementation.
- `Assignment5_PartB_B4_Report.pdf` – Final engineering decision and analysis report.
- `create_database_and_table.sql` – Database and table creation script.
- `passes_b4.csv` – Sample migration input file.
- `README.md` – Execution and verification instructions.

**Requirements:**
- JDK 11 or later.
- MySQL Server 8.x or compatible.
- MySQL Connector/J JDBC driver.

**Step 1 – Database setup**

Execute:

`create_database_and_table.sql`

The database uses `(campus_code, pass_id)` as a composite primary key.

This allows the same local pass ID to exist at different campuses while preventing duplicates within one campus.

**Step 2 – Add JDBC driver**

Place the MySQL Connector/J JAR inside the `lib` folder and name it:

`mysql-connector-j.jar`

**Step 3 – Configure database credentials**

For Windows PowerShell:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/assignment5_b4?useSSL=false&serverTimezone=UTC"
$env:DB_USER="root"
$env:DB_PASSWORD="your_mysql_password"
```

**Step 4 – Compile**

```powershell
New-Item -ItemType Directory -Force output

javac -cp "lib\mysql-connector-j.jar" -d output src\B4Migration.java
```

**Step 5 – Run**

```powershell
java -cp "output;lib\mysql-connector-j.jar" B4Migration input\passes_b4.csv CAMPUS01
```

**Input format:**

Each valid CSV record contains five fields:

```text
pass_id,reg_no,name,route_no,amount
```

The supplied sample includes valid records and deliberately invalid cases such as non-numeric route numbers, non-numeric amounts, negative amounts, missing fields and duplicate pass IDs.

The current parser assumes commas are not embedded inside names. Records containing embedded commas require a proper CSV parser or an escaped and quoted format.

**Expected validation results:**

| Parameter | Expected count |
|---|---:|
| Total input lines | 16 |
| Accepted records | 11 |
| Rejected records | 5 |
| Committed database records | 11 |

**Database metadata:**

The application uses DatabaseMetaData to display:

- Database product name and version.
- JDBC driver name and version.
- Available database tables.

**Transaction handling:**

- Valid records are added using PreparedStatement batches.
- All accepted records are inserted within one transaction.
- Invalid input records are written to `output/validation_failures.csv`.
- A database insertion failure triggers transaction rollback.
- Partial imports are avoided.

**Production engineering decision:**

A shared relational database is recommended for a multi-campus bus-pass management system.

The reasons include:

- Transaction support for concurrent registration counters.
- Index-based searches for registration numbers and routes.
- Primary-key and integrity constraints.
- Better multi-user data consistency.
- Centralised backups and operational management.
- Improved support for concurrent updates across campuses.

Separate text files or serialized Java objects may be suitable for smaller standalone applications, but are less convenient for concurrent multi-campus operations and multi-criteria searching.

**Result:** A database migration application was designed to validate incoming bus-pass records, process accepted records in batches, preserve transaction correctness and report database metadata. A shared relational database was selected as the production storage approach.

---

## 4. Technologies and Tools Used

| Technology / Tool | Purpose |
|---|---|
| Java | Programming language |
| JDK | Compilation and execution |
| File I/O | Reading and processing persistent records |
| CSV | Bus-pass input and experimental output |
| JDBC | Java database connectivity |
| H2 Database | Lightweight database for B2 testing |
| MySQL | Database management for B3 and B4 |
| MySQL Connector/J | Java-MySQL connectivity |
| Statement | Standard SQL execution |
| PreparedStatement | Parameterized SQL execution |
| Batch Processing | Bulk database insertion |
| DatabaseMetaData | Database and driver information |
| SQL | Schema and database operations |
| Visual Studio Code | Source-code development |
| PowerShell / Command Prompt | Compilation and execution |
| Git and GitHub | Version control and submission |
| PDF Editor / Document Editor | Report preparation |

## 5. Repository Structure

```text
CSA0919-192525148-Programming-in-Java-Assignments/
│
└── Assignment 5/
    │
    └── PART B – Implementation & Experimental Investigation/
        │
        ├── README.md
        │
        ├── TASK B1 – IMPLEMENT AND INSTRUMENT THE FILE STORE/
        │   ├── README.md
        │   ├── PassStore.java
        │   ├── B1Validation.java
        │   ├── LineCounterBenchmark.java
        │   ├── SearchGrowthBenchmark.java
        │   ├── Assignment5_PartB_B1_Report.pdf
        │   ├── PassStore_output.txt
        │   ├── B1Validation_output.txt
        │   ├── buffering_benchmark.txt
        │   ├── search_growth_benchmark.txt
        │   └── measurements.csv
        │
        ├── TASK B2 – ENGINEER AN ADVERSARIAL INPUT/
        │   ├── README.md
        │   ├── B2AdversarialTest.java
        │   ├── B2DamagedFileTest.java
        │   ├── Assignment5_PartB_B2_Report.pdf
        │   ├── create_test_database.sql
        │   ├── damaged_passes.csv
        │   ├── compile_check.txt
        │   └── expected_run.txt
        │
        ├── TASK B3 – STATEMENT vs PREPAREDSTATEMENT vs BATCH/
        │   ├── README.md
        │   ├── B3Runner.java
        │   ├── PassLoader.java
        │   ├── Assignment5_PartB_B3_Report.pdf
        │   ├── create_database_and_table.sql
        │   ├── generated_data_notes.txt
        │   └── compile_check.txt
        │
        └── TASK B4 – FINAL ENGINEERING DECISION/
            ├── README.md
            ├── B4Migration.java
            ├── Assignment5_PartB_B4_Report.pdf
            ├── create_database_and_table.sql
            └── passes_b4.csv
```

*Note: The structure lists the main files identified in the repository. Additional output, input, library and compiled files may be created during execution.*

## 6. Compilation and Execution

The programs can be compiled and executed using the Java Development Kit through Visual Studio Code, Command Prompt or PowerShell.

### General execution procedure

1. Open the required task folder.
2. Check the installed Java version.
3. Ensure that required input files and folders are available.
4. Set up the database if the task requires JDBC.
5. Add the appropriate JDBC driver to the classpath.
6. Configure database credentials using environment variables.
7. Compile the required Java source files.
8. Execute the main class.
9. Verify the program output and generated files.
10. Record actual benchmark measurements and capture screenshots.

### Check Java version

```bash
java -version
javac -version
```

### General Java execution

```bash
javac FileName.java
java ClassName
```

### Windows JDBC classpath example

```powershell
javac -cp "lib\mysql-connector-j.jar" -d output src\MainClass.java
java -cp "output;lib\mysql-connector-j.jar" MainClass
```

For Linux and macOS, use `:` instead of `;` in the classpath.

## 7. Experimental Investigation and Performance Analysis

Experimental investigation is an important component of Part B.

The tasks examine the behaviour of file operations and database insertion techniques using different workloads.

The main performance and correctness factors include:

- File-reading performance.
- Buffering efficiency.
- Search performance as the number of records increases.
- SQL execution overhead.
- PreparedStatement reuse.
- Batch insertion performance.
- Database transaction behaviour.
- Rollback and data consistency.
- Validation and rejected-record counts.
- Memory consumption.
- Scalability and production suitability.

Benchmark measurements depend on hardware, JVM, database configuration, JDBC driver version, indexes and system load.

Use measurements collected on the submission computer when reporting experimental results. Reference outputs are not substitutes for actual execution evidence.

## 8. Testing and Validation

The programs include different tests to evaluate implementation correctness and database reliability.

The main validation areas include:

- File-store operations.
- Valid and invalid bus-pass records.
- Damaged CSV records.
- SQL injection using controlled adversarial input.
- PreparedStatement rejection of malicious login input.
- Statement and PreparedStatement insertion.
- Batch insertion and row-count verification.
- Transaction rollback after invalid data.
- Database metadata inspection.
- Bulk migration of accepted records.
- Duplicate-record prevention.
- Validation-failure reporting.
- Final committed-record verification.

## 9. Learning Outcomes

Through Part B of Assignment 5, the following learning outcomes were addressed:

- Implementing file-based persistence systems.
- Measuring file-reading and searching performance.
- Developing robust input-validation mechanisms.
- Understanding SQL injection through controlled testing.
- Applying PreparedStatement for secure SQL operations.
- Implementing JDBC database operations.
- Comparing different SQL execution approaches.
- Understanding batch insertion and transaction management.
- Applying commit and rollback for data consistency.
- Inspecting database metadata.
- Processing large CSV datasets.
- Developing transactional migration applications.
- Evaluating database architecture for multi-campus systems.
- Making engineering decisions using experimental results.

## 10. Conclusion

Part B of Assignment 5 provides practical experience in implementing and evaluating persistence systems and database operations using Java.

Across the four tasks, the assignment progresses from file-store implementation and performance instrumentation to adversarial-input testing, JDBC insertion comparisons and large-scale database migration.

The experiments demonstrate the importance of buffered file processing, secure parameterized SQL, efficient batch insertion, transaction management and reliable data validation.

The final engineering decision evaluates the suitability of a shared relational database for a multi-campus bus-pass management system based on concurrency, searching, integrity and maintainability requirements.

Overall, Part B strengthens the understanding of Java file handling, JDBC, database security, performance benchmarking, transaction safety and practical software engineering decisions.

---

**Course:** CSA0919 – Programming in Java  
**Assignment:** 5 – Part B: Implementation & Experimental Investigation  
**Student:** Juhi Surin  
**Register Number:** 192525148
