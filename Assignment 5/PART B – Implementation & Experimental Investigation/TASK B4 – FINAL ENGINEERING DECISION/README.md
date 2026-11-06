# Assignment 5 – Part B – Task B4

## Student details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
**B4 – Final Engineering Decision (5 marks, K5 – Evaluate)**

This solution:
1. Reads a five-field pass CSV file.
2. Validates each row and writes rejected records with line number and reason to `output/validation_failures.csv`.
3. Inserts accepted rows using `PreparedStatement`, batches of 1,000 and one transaction.
4. Uses `DatabaseMetaData` to print database product/version, JDBC driver/version and table names.
5. Evaluates the production storage choice for 12 campuses and multiple counters.

## Requirements
- JDK 11 or later
- MySQL Server 8.x (or compatible)
- MySQL Connector/J JAR

Download Connector/J from: https://dev.mysql.com/downloads/connector/j/  
Place the downloaded JAR in `lib/` and rename it to `mysql-connector-j.jar`.

## Database setup
1. Start MySQL Server.
2. Open MySQL Workbench or the MySQL command line.
3. Run `sql/create_database_and_table.sql`.
4. The Java program also creates the table if it does not exist.

The table uses `(campus_code, pass_id)` as a composite primary key. This allows the same local pass ID to exist at different campuses while preventing duplicates within one campus. Indexes support register-number and route searches.

## Compile and run (Windows PowerShell)
Open the project folder in VS Code, then run:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/assignment5_b4?useSSL=false&serverTimezone=UTC"
$env:DB_USER="root"
$env:DB_PASSWORD="your_mysql_password"

New-Item -ItemType Directory -Force output
javac -cp "lib\mysql-connector-j.jar" -d output src\B4Migration.java
java -cp "output;lib\mysql-connector-j.jar" B4Migration input\passes_b4.csv CAMPUS01
```

If your MySQL root account has no password, set `$env:DB_PASSWORD=""`. If the driver JAR has a different filename, update the classpath.

### Linux/macOS
Use `:` instead of `;` in the Java classpath:

```bash
javac -cp "lib/mysql-connector-j.jar" -d output src/B4Migration.java
java -cp "output:lib/mysql-connector-j.jar" B4Migration input/passes_b4.csv CAMPUS01
```

## Input format
Each valid line has exactly five comma-separated fields:

`pass_id,reg_no,name,route_no,amount`

The sample file contains valid records and intentional invalid cases: non-numeric route, non-numeric amount, negative amount, missing field, and duplicate pass ID. The current simple CSV parser assumes commas are not embedded inside names. If names may contain commas, use a proper CSV parser or an escaped/quoted format.

## Expected behaviour
- Valid rows are loaded.
- Invalid rows are skipped and recorded in `output/validation_failures.csv`.
- All accepted rows are inserted in one transaction. If a database insert fails, the transaction is rolled back; the program does not leave a partial import.
- Database metadata is printed after the migration.

With the supplied sample input and an empty table, expected validation counts are:
- Input lines: 16
- Accepted rows: 11
- Rejected rows: 5

The committed row count should be 11. If you run the same file again for `CAMPUS01`, the composite primary key will detect duplicates and the insert transaction will roll back. For a fresh demonstration, clear the table in the test database first:

```sql
USE assignment5_b4;
DELETE FROM bus_pass WHERE campus_code = 'CAMPUS01';
```

## B4.2 – Why metadata matters
`DatabaseMetaData` exposes the database product/version and JDBC driver/version at runtime, rather than assuming a particular server or driver. Listing tables helps detect schema/environment differences during deployment and troubleshooting. This is useful when moving the application to another server because the database product, driver, catalog and available tables may differ.

## B4.3 – Production decision
**Recommendation: one shared relational database**, not separate text files or Java serialized files, for the 12-campus production system.

- **Concurrent counters:** a database supports transactions, row-level locking and constraints. Independent text/serialized files need external locking and careful coordination to prevent lost updates or duplicate pass IDs.
- **Searching:** indexed database lookups can use B-tree indexes; a scan across separate campus files requires checking each file. For `n` total passes, a full scan is `O(n)`; indexed lookup is typically `O(log n)` in the index (exact performance depends on the database and index).
- **Safety:** database transactions, primary keys, constraints, access control and backups provide stronger multi-user integrity and operational controls.
- **Text files:** simple and portable, but weak for concurrent writes and multi-criteria searches at this scale.
- **Serialization:** convenient for saving Java object graphs, but tightly coupled to Java class versions and not suitable as a shared multi-user record store.

### Alternative: one serialized file per campus
Each campus could read and search its own serialized collection. A global search must query all 12 campus files; with `n_i` passes at campus `i`, total scan work is `O(sum n_i) = O(n)`. If every file is loaded into memory, space is `O(n)`; streaming/deserializing one campus at a time reduces working memory but still requires visiting all files. Concurrent counters at one campus would need file locks or a single-writer service; cross-campus uniqueness and updates would require distributed coordination. This is more complex and less reliable than a shared database.

## Measurements and evidence
Do not invent timings or claim a successful database run without executing it. Record your own actual output and timings on your computer. The assignment requires a working migration, failure report and metadata output. Add screenshots to `evidence/` and paste your actual terminal output into `output/`.

## Suggested verification SQL
```sql
USE assignment5_b4;
SELECT COUNT(*) AS campus_pass_count
FROM bus_pass
WHERE campus_code = 'CAMPUS01';

SELECT pass_id, reg_no, name, route_no, amount, campus_code
FROM bus_pass
WHERE campus_code = 'CAMPUS01'
ORDER BY pass_id;
```

## GitHub submission reminder
Create/use the required repository `CSA09-192525148-Assignment5`, place this folder inside it, commit the task, and share the repository with faculty. Add the working GitHub link to your assignment submission and report.
