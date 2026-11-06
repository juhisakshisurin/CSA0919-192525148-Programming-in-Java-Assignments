# Assignment 4 – Task 6: Campus Event Registration Desktop Application

## Student details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Files
- `EventRegistrationApp.java` – Swing user interface, menu, table, paging and event handlers.
- `RegistrationService.java` – input validation and application rules.
- `RegistrationRepository.java` – JDBC operations using PreparedStatement.
- `Registration.java` – registration data model.
- `Database.java` – MySQL connection settings from environment variables.
- `schema.sql` – database and table creation, including duplicate-registration constraint.
- `Task6_Report.pdf` – design, screen flow, class layers, pseudocode, test plan and alternative analysis. Student details are omitted from the report.

## Requirements
- JDK 8 or later.
- MySQL Server 8.x (or compatible).
- MySQL Connector/J JAR.
- VS Code or another Java IDE.

## 1. Create the database
Open MySQL Workbench or MySQL command line and execute all statements in `schema.sql`.

The unique constraint on `(student_id, event_name)` is the final protection against duplicate registrations, even if two registration requests happen at nearly the same time.

## 2. Configure database credentials (Windows PowerShell)
Set these environment variables in the same terminal before running:
```powershell
$env:EVENT_DB_URL="jdbc:mysql://localhost:3306/campus_events?useSSL=false&serverTimezone=UTC"
$env:EVENT_DB_USER="root"
$env:EVENT_DB_PASSWORD="YOUR_MYSQL_PASSWORD"
```
Replace `YOUR_MYSQL_PASSWORD` with your local MySQL password. If your root account has no password, use an empty value.

## 3. Add MySQL Connector/J
Download/install MySQL Connector/J from the official MySQL site. Copy the JAR into this folder or note its path.

Assume the JAR is named `mysql-connector-j-<version>.jar`. Replace that placeholder with the exact filename you have.

## 4. Compile
From the Task06 folder:
```powershell
javac -cp ".;mysql-connector-j-<version>.jar" *.java
```

## 5. Run
```powershell
java -cp ".;mysql-connector-j-<version>.jar" EventRegistrationApp
```

## 6. Test cases
1. **Valid registration:** Student ID `S1001`, name `Asha Kumar`, email `asha@example.com`, event `Tech Symposium`. Expected: success dialog and a row in the table.
2. **Duplicate registration:** Submit the same student ID and event again. Expected: duplicate-registration error; only one database row exists for that student/event.
3. **Invalid email:** Enter `asha@` and submit. Expected: validation error before database insertion.
4. **Missing field:** Leave any field empty. Expected: clear validation message.
5. **Filter:** Choose `Cultural Fest`. Expected: only that event's registrations appear.
6. **Pagination:** Insert more than 50 test rows, then use Next and Previous. Each page contains up to 50 records.
7. **Export:** File > Export CSV. Choose a destination. Expected: a CSV file containing all rows matching the current event filter.
8. **Responsiveness:** Load or export records while interacting with the window. Database work runs in SwingWorker's background thread, not the Event Dispatch Thread.

## 7. Screenshots to capture
Capture real screenshots from your local MySQL-connected run:
- `registration_success.png` – valid registration confirmation.
- `invalid_input.png` – validation error.
- `duplicate_registration.png` – duplicate attempt rejected.
- `filtered_table.png` – table filtered to one event.
- `csv_export.png` – export completion or opened CSV.
Do not submit illustrative/mock screenshots as execution evidence.

## Notes and limitations
- The application uses sample event names in the event selector. Edit the list in `EventRegistrationApp.java` to match your committee's actual events.
- Export intentionally exports all rows matching the current filter, while the JTable displays one page at a time.
- `COUNT(*)` and page retrieval are separate SQL queries. For very large live systems, use an index and consider keyset pagination if deep OFFSET pages become slow.
- The application must be run with the MySQL Connector/J JAR and valid database credentials. JDBC code can compile against the JDK, but a working database connection is required for live registration, filtering and export.

## Result
The solution separates Swing UI, service validation and JDBC repository logic; uses a database uniqueness constraint to prevent duplicate event registrations; performs database operations in SwingWorker; and supports event filtering, paginated viewing and CSV export.
