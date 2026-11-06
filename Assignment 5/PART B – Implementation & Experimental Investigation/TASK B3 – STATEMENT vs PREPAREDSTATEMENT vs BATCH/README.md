# Assignment 5 – Part B – Task B3
## Statement vs PreparedStatement vs Batch (8 Marks)

**Name:** Juhi Surin  
**Reg No:** 192525148  
**Course:** CSA0919 Programming in Java  
**Java:** Java 11+ (tested source compilation with Java 21)

## Exact task coverage
- **B3.1:** Insert 10 rows with Statement, PreparedStatement and PreparedStatement batching; verify SELECT results and ResultSetMetaData column names/types.
- **B3.2:** Benchmark 1,000, 10,000 and 100,000 generated rows with all three approaches.
- **B3.3:** Compare batch insertion with auto-commit enabled against one transaction. Inject an invalid amount at the start of batch 6 and count rows remaining after failure.
- **B3.4:** Interpret performance and choose approaches for nightly 100,000-row imports and single counter issuance.

## Project structure
- `src/B3Runner.java` – full JDBC experiments and timing.
- `src/PassLoader.java` – completed TODO 1–3 transaction batch loader.
- `sql/create_database_and_table.sql` – MySQL database/table setup.
- `input/generated_data_notes.txt` – test data and failure placement.
- `output/` – compile output and execution output after you run.
- `lib/` – MySQL Connector/J JAR goes here.
- `evidence/` – save your own screenshots here.
- `Assignment5_PartB_B3_Report.pdf` – report; no student details included.

## Requirements
Use MySQL Server 8.x (or a compatible MySQL server), Java 11+, and MySQL Connector/J. This task explicitly requires a MySQL table using JDBC. Do not use H2 for this task.

### 1. Create database and table
Open MySQL Workbench or MySQL command line and execute:
`sql/create_database_and_table.sql`

It creates database `assignment5_b3` and a `bus_pass` table with five columns matching the provided PassLoader starter code.

### 2. Add MySQL JDBC driver
Download MySQL Connector/J from the official MySQL page:
https://dev.mysql.com/downloads/connector/j/

Extract the archive and copy the `mysql-connector-j-<version>.jar` file into this project's `lib` folder. Rename it to:
`mysql-connector-j.jar`

### 3. Configure database credentials
Defaults in `B3Runner.java` are:
- URL: `jdbc:mysql://localhost:3306/assignment5_b3`
- username: `root`
- password: empty

If your MySQL username/password differ, set environment variables in PowerShell before running:
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/assignment5_b3"
$env:DB_USER="root"
$env:DB_PASSWORD="your_mysql_password"
```
Use your actual local MySQL password. Do not upload credentials to GitHub.

### 4. Compile in VS Code (Windows PowerShell)
Open the `Assignment5_PartB_B3` folder in VS Code. Start MySQL Server first, then run:
```powershell
New-Item -ItemType Directory -Force output
javac -cp "lib\mysql-connector-j.jar" -d output src\PassLoader.java src\B3Runner.java
```

### 5. Run
```powershell
java -cp "output;lib\mysql-connector-j.jar" B3Runner
```
Linux/macOS uses `:` in the classpath:
```bash
javac -cp "lib/mysql-connector-j.jar" -d output src/PassLoader.java src/B3Runner.java
java -cp "output:lib/mysql-connector-j.jar" B3Runner
```

## Expected behaviour and validation
- Each of the three methods inserts exactly 10 rows; metadata should report `pass_id`, `reg_no`, `name`, `route_no`, `amount` and their MySQL types.
- The benchmark prints CSV rows for 1,000 / 10,000 / 100,000 records. It clears the table between methods and checks the final row count.
- The injected `NOT_A_NUMBER` amount is deliberately invalid. In auto-commit mode, the first five 1,000-row batches should remain committed (5,000 rows); the failing sixth batch is not added because the invalid amount is rejected before it is queued. In the single-transaction case, rollback should leave 0 rows.
- Actual timing depends on your machine, MySQL version, server settings, indexes, Connector/J version and storage. Use the measurements printed on your own computer in the report; do not copy fabricated timings.

## Complexity and engineering interpretation
For n inserted rows:
- Statement: n SQL statements are built and executed; overall work is O(n), with repeated SQL parsing/statement execution overhead.
- PreparedStatement: n parameter sets are executed; overall work is O(n), but the SQL structure is reused and values are bound separately.
- PreparedStatement batch: n parameter sets are accumulated and sent in groups of b; overall work remains O(n), while round trips are reduced approximately to O(ceil(n/b)) batch submissions. Pending client-side batch memory is O(b) when flushed every b rows.
- Transaction: a single transaction provides all-or-nothing behaviour but can hold locks/log resources longer; commit once after the import. Auto-commit commits successful work incrementally, so earlier batches can remain after a later failure.

## B3.4 recommendation
- **Nightly import of 100,000 passes:** PreparedStatement batching inside one transaction, because it reduces round trips and provides atomic rollback.
- **One pass at the counter:** PreparedStatement with one executeUpdate, because it safely binds user values and avoids building a batch for one row.
- Statement is included as a comparison baseline, not recommended for untrusted input.

## Evidence to capture
1. SQL table creation in MySQL Workbench.
2. B3.1 output showing each method's row count and ResultSetMetaData types.
3. B3.2 timing output for all three data sizes and all three methods.
4. B3.3 output showing the auto-commit row count and transaction rollback row count.
5. Terminal showing compile and run commands.

## Submission
Keep this folder as `TaskB3` in repository `CSA09-192525148-Assignment5`. Commit source, SQL, README, report, output data and screenshots. Add your repository link to the assignment cover page as required.
