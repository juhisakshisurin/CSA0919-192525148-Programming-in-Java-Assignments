# CSA0919 – Programming in Java
## Assignment 5 – Part A: Persistence Design & Pseudocode

### Student Details

| Field | Details |
|---|---|
| **Name** | Juhi Surin |
| **Register Number** | 192525148 |
| **Course Code** | CSA0919 |
| **Course Name** | Programming in Java |
| **Assignment** | Assignment 5 |
| **Part** | Part A – Persistence Design & Pseudocode |
| **Programming Language** | Java |

---

## 1. Introduction

Part A of Assignment 5 focuses on persistence design, file handling, data-access architecture, object serialization and database transaction safety using Java.

The tasks emphasise designing reliable and efficient mechanisms for storing, retrieving and managing application data. They explore different file formats, buffered reading techniques, Data Access Object (DAO) patterns, Java serialization and safe database transactions.

This part consists of five tasks that progressively develop the understanding of persistence mechanisms, data-access design and database safety.

The primary deliverables are analytical reports, algorithmic pseudocode and design documentation rather than complete executable Java applications.

## 2. Objectives

- Understand different file formats for persistent data storage.
- Design structured text and binary file representations.
- Develop pseudocode for file operations.
- Analyse efficient file-reading techniques.
- Understand buffering and memory utilisation.
- Identify repeated JDBC operations and design reusable DAO components.
- Understand object serialization and deserialization.
- Identify transient and persistent object fields.
- Analyse serialVersionUID and serialization compatibility.
- Design reliable database transactions.
- Understand commit and rollback operations.
- Identify SQL injection vulnerabilities.
- Apply PreparedStatement for secure database operations.
- Compare alternative persistence approaches using time and space complexity.

## 3. Task-wise Description

### Task A1 – Design a File Format

**Problem context:** Transport Office Bus-Pass Records.

Designed a structured persistence format for maintaining bus-pass records in a transport office system.

The task defines a seven-field bus-pass record and explores both UTF-8 comma-delimited text storage and binary storage using DataOutputStream.

Pseudocode was developed for adding a pass, finding a pass using its ID and listing passes based on their route.

The design also considers damaged records, missing files, incomplete writes and alternative fixed-length records using RandomAccessFile.

**Concepts covered:**
- File handling
- Text-file persistence
- Binary-file persistence
- UTF-8 encoding
- CSV-style record representation
- DataOutputStream
- Record structure
- File validation
- Error handling
- Algorithmic pseudocode
- RandomAccessFile
- Time and space complexity

**Files included:**
- `Assignment5_PartA_TaskA1_Report.pdf` – Detailed report containing the file-format design, analysis and pseudocode.
- `TaskA1_Pseudocode.txt` – Plain-text algorithms for adding, finding and listing bus passes.

**Result:** A seven-field bus-pass record format was designed using text and binary representations. The algorithms for adding, searching and listing records were documented, along with error-handling strategies and alternative storage approaches.

**Implementation requirement:** This is a design and pseudocode task. A complete Java implementation is not required.

### Task A2 – Don't Read What You Don't Need

**Problem context:** Large Attendance Log Processing.

Analysed the performance of reading a large 50 MB attendance log one byte at a time.

Designed a more efficient buffered input approach and compared it with loading the entire file into memory using Files.readAllLines().

The task examines how buffering reduces unnecessary I/O operations and how memory usage changes with different reading strategies.

**Concepts covered:**
- File input and output
- Buffered reading
- InputStream
- Buffering
- File processing
- Memory efficiency
- I/O performance
- Files.readAllLines()
- Time complexity
- Space complexity
- Algorithmic pseudocode

**Files included:**
- `Assignment5_PartA_TaskA2_Report.pdf` – Detailed report covering the original approach, redesigned approach and complexity analysis.
- `TaskA2_Pseudocode.txt` – Plain-text buffered line-counter algorithm.

**Result:** A buffered reading strategy was proposed to process the attendance log more efficiently. The design also explains when buffering may not provide significant benefits and compares streaming with full-file loading.

**Implementation requirement:** No complete Java implementation or execution screenshot is required for this task. The main deliverables are design reasoning and pseudocode.

### Task A3 – Discover the Data-Access Design Pattern

**Problem context:** Student Database Management System.

Analysed repeated JDBC operations in StudentDB, CourseDB and MarksDB and identified the common database-access logic.

Designed a reusable generic DAO interface to separate database operations from application-specific entity handling.

The task also includes a StudentDao implementation example and a mapping between Student object fields and database table columns.

**Concepts covered:**
- JDBC
- Data Access Object (DAO)
- Generic interfaces
- Java Generics
- Database abstraction
- Separation of concerns
- CRUD operations
- SQL queries
- Entity-to-table mapping
- Reusability
- Maintainability
- Design patterns
- Alternative architecture analysis

**Files included:**
- `Assignment5_PartA_TaskA3_Report.pdf` – Detailed DAO design and analytical report.
- `TaskA3_DAO_Design.txt` – Editable design containing the generic DAO interface, StudentDao example and database table mapping.

**Result:** A generic DAO interface was designed to provide reusable data-access operations. The design separates database interaction from application and service logic, improving modularity, reusability and maintainability.

**Implementation requirement:** This is a design task. The Java-style code snippets illustrate the DAO interface and implementation approach; a complete runnable application is not required.

### Task A4 – What Should Be Serialized?

**Problem context:** Hostel Management System.

Designed a serialization strategy for storing hostel, room and resident information.

The task identifies which fields should be persisted and which should be excluded from serialization. It also examines object-graph serialization, compatibility between class versions and alternative persistence formats such as CSV and JSON.

**Concepts covered:**
- Java serialization
- Deserialization
- Serializable interface
- Object graphs
- Transient variables
- serialVersionUID
- Serialization compatibility
- InvalidClassException
- Persistent and runtime data
- CSV and JSON
- Schema evolution
- Time and space complexity

**Files included:**
- `Assignment5_PartA_TaskA4_Report.pdf` – Complete serialization design and analysis report.
- `TaskA4_Serialization_Design.txt` – Editable notes covering transient fields, serialization, serialVersionUID and alternative formats.

**Important design decisions:**

- Persist Hostel and Room information.
- Persist Resident name and registration number.
- Mark Resident password as transient.
- Mark live JDBC connection objects as transient.
- Use an explicit serialVersionUID to support controlled class-version compatibility.
- Consider CSV and JSON when portability and schema evolution are important.

When a new `phoneNumber` field is introduced with a compatible serialVersionUID, an older serialized object that does not contain that field receives the default value `null`. An incompatible serialVersionUID can cause InvalidClassException.

**Result:** A suitable serialization strategy was designed to preserve important application data while excluding sensitive credentials and runtime resources.

**Implementation requirement:** This is a design and analysis task. A complete executable Java program is not required.

### Task A5 – Design for Correctness and Safety

**Problem context:** Hostel and Canteen Wallet Transaction System.

Designed a safe database transaction for transferring ₹500 from a hostel wallet to a canteen wallet.

The task uses JDBC-style pseudocode to demonstrate transaction management, commit and rollback operations.

It also analyses SQL injection vulnerabilities caused by concatenating user input into SQL queries and redesigns the login operation using PreparedStatement.

**Concepts covered:**
- JDBC transactions
- Transaction management
- Auto-commit
- Commit
- Rollback
- Database consistency
- PreparedStatement
- SQL injection
- Parameterized queries
- Input handling
- Database constraints
- Exception handling
- Time and space complexity

**Files included:**
- `Assignment5_PartA_TaskA5_Report.pdf` – Detailed transaction-design and security analysis.
- `TaskA5_Pseudocode.txt` – Editable transaction pseudocode, SQL injection explanation and complexity analysis.

**Transaction design:**

1. Establish a database connection.
2. Disable auto-commit.
3. Verify the source and destination wallet records.
4. Deduct ₹500 from the hostel wallet.
5. Add ₹500 to the canteen wallet.
6. Verify that each update affects exactly one wallet record.
7. Commit the transaction if both updates succeed.
8. Roll back if either update fails.
9. Restore the original auto-commit setting.

**SQL injection prevention:**

The task explains how concatenating user input into SQL queries can create vulnerabilities.

The redesigned login approach uses PreparedStatement placeholders and bound parameters instead of directly inserting user input into an SQL statement.

**Result:** A transaction-safe wallet-transfer design was developed using commit and rollback. A parameterized query approach was also designed to prevent SQL injection and improve database security.

**Implementation requirement:** This is a Part A design and pseudocode task. A complete Java application or execution screenshot is not required.

---

## 4. Technologies and Concepts Used

| Technology / Concept | Purpose |
|---|---|
| Java | Programming language and design context |
| UTF-8 | Text-file encoding |
| CSV-style format | Structured text persistence |
| DataOutputStream | Binary record representation |
| RandomAccessFile | Fixed-length record access |
| Buffered Input | Efficient file reading |
| Files API | File-processing alternatives |
| JDBC | Database connectivity |
| DAO Pattern | Data-access abstraction |
| Java Generics | Reusable DAO interface |
| Java Serialization | Object persistence |
| Serializable | Supporting object serialization |
| serialVersionUID | Serialization compatibility |
| PreparedStatement | Safe parameterized SQL |
| SQL | Database operations |
| PDF Editor / Document Editor | Report preparation |
| Git and GitHub | Version control and submission |

## 5. Repository Structure

```text
CSA0919-192525148-Programming-in-Java-Assignments/
│
└── Assignment 5/
    │
    └── PART A - Persistence Design & Pseudocode/
        │
        ├── README.md
        │
        ├── TASK A1 – DESIGN A FILE FORMAT/
        │   ├── README.md
        │   ├── Assignment5_PartA_TaskA1_Report.pdf
        │   └── TaskA1_Pseudocode.txt
        │
        ├── TASK A2 – DON’T READ WHAT YOU DON’T NEED/
        │   ├── README.md
        │   ├── Assignment5_PartA_TaskA2_Report.pdf
        │   └── TaskA2_Pseudocode.txt
        │
        ├── TASK A3 – DISCOVER THE DATA-ACCESS DESIGN PATTERN/
        │   ├── README.md
        │   ├── Assignment5_PartA_TaskA3_Report.pdf
        │   └── TaskA3_DAO_Design.txt
        │
        ├── TASK A4 – WHAT SHOULD BE SERIALIZED?/
        │   ├── README.md
        │   ├── Assignment5_PartA_TaskA4_Report.pdf
        │   └── TaskA4_Serialization_Design.txt
        │
        └── TASK A5 – DESIGN FOR CORRECTNESS AND SAFETY/
            ├── README.md
            ├── Assignment5_PartA_TaskA5_Report.pdf
            └── TaskA5_Pseudocode.txt
```

*Note: This structure reflects the actual main files listed in the Part A repository folders.*

## 6. Design and Analysis Methodology

Each task follows a structured problem-solving approach:

1. **Problem Understanding:** Identify the real-world scenario and its persistence or database requirements.
2. **Design Analysis:** Identify suitable file formats, storage strategies, data-access mechanisms or transaction techniques.
3. **Pseudocode Development:** Prepare algorithmic steps for the required operations.
4. **Alternative Comparison:** Evaluate alternative approaches based on efficiency, reliability, memory usage and maintainability.
5. **Complexity Analysis:** Compare the proposed designs using appropriate time and space complexity.
6. **Documentation:** Present the final design decisions, reasoning and conclusions in the task reports.

## 7. Important Submission Notes

- Part A requires algorithmic pseudocode and design reasoning.
- Do not replace the requested pseudocode with complete Java source code.
- Keep the PDF reports and editable pseudocode/design files in their respective task folders.
- The task reports intentionally exclude student-identifying details.
- Each task should be committed separately to GitHub where progressive commits are required.
- Maintain the original task-folder structure for submission.

### Java Version and Execution

Java execution is not applicable to these five Part A tasks because their deliverables are design documents, pseudocode and analytical reports.

The implementation requirements and Java execution evidence belong to the corresponding implementation work in Part B.

## 8. Learning Outcomes

Through Part A of this assignment, the following learning outcomes were addressed:

- Designing efficient and structured persistence formats.
- Understanding text and binary file storage.
- Applying buffered reading strategies.
- Analysing I/O performance and memory consumption.
- Understanding reusable DAO-based database architecture.
- Separating database-access logic from application logic.
- Designing object serialization strategies.
- Managing transient and persistent object data.
- Understanding serialization compatibility.
- Designing database transactions for reliable data updates.
- Applying commit and rollback operations.
- Identifying and preventing SQL injection vulnerabilities.
- Comparing persistence approaches using time and space complexity.
- Developing structured technical documentation and pseudocode.

## 9. Conclusion

Part A of Assignment 5 provides a strong foundation in persistence design and database architecture using Java.

Across the five tasks, different methods of storing, retrieving and managing application data are analysed, including structured text files, binary storage, buffered input, DAO architecture and object serialization.

The final task focuses on database correctness and security through transaction management and SQL injection prevention.

Overall, this part strengthens the understanding of persistent data management, efficient file processing, reusable database architecture, serialization compatibility, transaction safety and secure database programming.

---

**Course:** CSA0919 – Programming in Java  
**Assignment:** 5 – Part A: Persistence Design & Pseudocode  
**Student:** Juhi Surin  
**Register Number:** 192525148
