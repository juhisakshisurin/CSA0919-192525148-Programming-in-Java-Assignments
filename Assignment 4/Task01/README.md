# Assignment 4 – Task 1: GUI Design Review

## Student details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task overview
This task diagnoses a poor AWT fee-payment event-handling design and redesigns it using separate `FeeWindow` and `FeeService` classes.

## Files
- `FeeWindow.java` – AWT interface and event handlers.
- `FeeService.java` – input validation and JDBC payment operation.
- `create_fees_table.sql` – creates the database and `fees` table.
- `Task1_Report.pdf` – written answers and complexity analysis.

## Requirements
- JDK 8 or later.
- MySQL Server.
- MySQL Connector/J JDBC driver.
- A database user with permission to create/use the `college` database and insert into `fees`.

## Database setup
1. Start MySQL.
2. Execute `create_fees_table.sql` in MySQL Workbench or the MySQL command line.
3. Open `FeeService.java`.
4. Set `DB_URL`, `DB_USER`, and `DB_PASSWORD` to match your MySQL setup.
5. Ensure the MySQL Connector/J JAR is available on the classpath.

## Compile and run

### Windows Command Prompt
Place `FeeWindow.java`, `FeeService.java`, and the MySQL Connector/J JAR in the same working folder.

```bat
javac -cp ".;mysql-connector-j-<version>.jar" FeeService.java FeeWindow.java
java -cp ".;mysql-connector-j-<version>.jar" FeeWindow
```

Replace `<version>` with the actual Connector/J JAR version in your folder.

### macOS/Linux
```bash
javac -cp ".:mysql-connector-j-<version>.jar" FeeService.java FeeWindow.java
java -cp ".:mysql-connector-j-<version>.jar" FeeWindow
```

## Test cases
1. **Valid payment:** Enter a register number and a positive numeric amount; select Pay. A success confirmation should appear and a row should be inserted into `fees`.
2. **Empty register number:** Leave the register number blank and select Pay. The service should show a validation message.
3. **Empty amount:** Leave the amount blank and select Pay. The service should show a validation message.
4. **Non-numeric amount:** Enter text such as `abc`; the service should reject it.
5. **Zero/negative amount:** Enter `0` or a negative number; the service should reject it.
6. **Clear:** Select Clear; both fields should become empty.
7. **Receipt:** After a successful payment, select Receipt to display the last successful payment confirmation. Before a successful payment, the window reports that no receipt is available.
8. **Responsiveness:** While payment processing is in progress, the database work runs on a worker thread rather than blocking the AWT event thread.

## Result
The redesigned program separates the interface from payment logic, validates user input, uses a parameterized SQL statement, handles database errors, gives feedback, and performs database work outside the GUI event thread.

## GitHub submission reminder
The assignment instructions require a repository named `CSA09-192525148-Assignment4`, with a separate folder for each task, progressive commits, source files, screenshots, and a README. Add your faculty member as a collaborator if the repository is private. Add the working repository URL to the report cover page and submit it as instructed.
