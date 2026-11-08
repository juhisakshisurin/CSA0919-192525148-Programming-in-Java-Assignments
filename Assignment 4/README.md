# CSA0919 – Programming in Java
## Assignment 4: GUI Programming, Event Handling and Database Connectivity

### Student Details

| Field | Details |
|---|---|
| **Name** | Juhi Surin |
| **Register Number** | 192525148 |
| **Course Code** | CSA0919 |
| **Course Name** | Programming in Java |
| **Assignment** | Assignment 4 |
| **Programming Language** | Java |

---

## 1. Introduction

This assignment focuses on developing graphical user interfaces and event-driven applications using Java.

It explores the design and implementation of desktop applications using Abstract Window Toolkit (AWT) and Swing. It also covers event handling, listener interfaces, adapter classes, inner classes, lambda expressions and database connectivity using JDBC.

The assignment consists of six tasks that progressively develop GUI design skills, event-driven programming techniques, component organisation and database-integrated applications.

Practical scenarios include a college fee-payment system, a hospital outpatient appointment desk, a campus library search screen, an interactive drawing application, a currency converter and a campus event registration application.

## 2. Objectives

- Understand GUI design principles and event-driven programming.
- Identify and correct problems in poorly designed GUI applications.
- Separate user-interface logic from business logic.
- Design component hierarchies and event-handling models.
- Develop GUI applications using AWT and Swing.
- Understand event delegation and event listener interfaces.
- Implement event handling using different programming techniques.
- Apply mouse, keyboard and window event handling.
- Understand layout managers and resizable GUI components.
- Connect Java applications to databases using JDBC.
- Perform database operations using PreparedStatement.
- Apply input validation and duplicate-record prevention.
- Implement responsive GUI applications using background tasks.
- Analyse alternative designs and their advantages and limitations.

## 3. Task-wise Description

### Task 1 – GUI Design Review

**Problem context:** College Fee Payment System.

Analysed an inefficient AWT fee-payment application and redesigned it by separating the user interface from the payment-processing logic.

The implementation uses separate `FeeWindow` and `FeeService` classes. User inputs are validated before database operations are performed.

The application connects to MySQL using JDBC and stores successful fee payments in a database table.

**Concepts covered:**
- AWT
- GUI design
- Event handling
- Event listeners
- Separation of concerns
- Input validation
- JDBC
- MySQL connectivity
- PreparedStatement
- Database operations
- Background processing
- Error handling

**Files included:**
- `FeeWindow.java` – AWT user interface and event handlers.
- `FeeService.java` – Payment validation and JDBC operations.
- `create_fees_table.sql` – Database and table creation script.
- `Task1_Report.pdf` – Detailed design and analysis report.
- `Java assignment 4 task01 output.png` – Execution screenshot.

**Requirements:**
- JDK 8 or later.
- MySQL Server.
- MySQL Connector/J JDBC driver.

**Database setup:**

Execute `create_fees_table.sql` using MySQL Workbench or the MySQL command line.

Configure the database connection details in `FeeService.java`:

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

Add the MySQL Connector/J JAR to the classpath.

**Compilation and execution – Windows:**

```bat
javac -cp ".;mysql-connector-j-<version>.jar" FeeService.java FeeWindow.java
java -cp ".;mysql-connector-j-<version>.jar" FeeWindow
```

**Result:** The fee-payment application separates GUI components from payment logic, validates user input, uses parameterized SQL queries and performs database operations outside the AWT event thread.

### Task 2 – From Requirement to Event-Driven Design

**Problem context:** Hospital Outpatient Appointment Desk.

Designed an event-driven system for a hospital outpatient appointment desk by identifying GUI components, user interactions, event sources and corresponding event handlers.

The task focuses on converting application requirements into a structured event-driven design.

**Concepts covered:**
- Event-driven programming
- GUI component hierarchy
- Event sources
- Event listeners
- Event modelling
- Event-handler pseudocode
- Event sequence and tracing
- Application workflow
- Alternative design analysis
- Time and space complexity

**Files included:**
- `Task2_Design.md` – Editable design document containing the event model, component hierarchy, pseudocode, event trace and complexity analysis.
- `Task2_Report.pdf` – Formatted analytical report.

**Java implementation:** This task requires event modelling and design documentation. A separate executable Java implementation is not specified.

**Result:** The Hospital Outpatient Appointment Desk was modelled using an event-driven approach. The component hierarchy, event-handling responsibilities, execution sequence and alternative design were documented.

### Task 3 – Campus Library Search Screen

**Problem context:** Campus Library Management System.

Developed an AWT-based library search application that allows users to search and browse available books.

The application contains a search field, category selector, results area, navigation buttons and a status display.

The interface supports searching by all fields, book title, author or category. It also supports pagination and reservation-request feedback.

**Concepts covered:**
- AWT
- GUI components
- Frame
- Panel
- TextField
- Choice
- Button
- List
- BorderLayout
- FlowLayout
- Event handling
- Search functionality
- Pagination
- GUI resizing
- Component organisation

**Files included:**
- `LibrarySearch.java` – Complete AWT library-search implementation.
- `Task3_Report.pdf` – Detailed report and analysis.
- `README.md` – Setup and execution instructions.
- `Java assignment 4 task03 output 1.png` – Execution screenshot.
- `Java assignment 4 task03 output 2.png` – Additional execution screenshot.

**Requirements:**
- JDK 8 or later.
- Visual Studio Code or another Java-compatible IDE.

**Compilation and execution:**

```bash
javac LibrarySearch.java
java LibrarySearch
```

**Test cases:**

1. Leave the search field empty and click Search to display all sample books.
2. Enter `java`, select Title and click Search.
3. Select Author and search for `silberschatz`.
4. Use Next and Prev to test pagination.
5. Select a book and click Reserve.
6. Click Reserve without selecting a book to check validation feedback.
7. Resize the application window and verify that the results area adjusts correctly.

**Screenshot requirement:**

The task requires screenshots at three different window sizes:

- `output_small.png` – Small window size.
- `output_medium.png` – Medium window size.
- `output_large.png` – Maximised or enlarged window size.

These should be genuine screenshots captured from the running application.

**Result:** A resizable library search interface was implemented using nested BorderLayout and FlowLayout panels. The results area expands and contracts with the window size while the search and navigation controls remain organised.

### Task 4 – Event-Handling Forensics

**Problem context:** Interactive Drawing Pad.

Analysed and corrected an AWT drawing application that demonstrates mouse-event handling and GUI repainting.

The corrected application registers mouse clicks, stores their positions and draws dots on the screen. A status label displays the number of clicks.

The task also contains an intentionally incorrect program to demonstrate the compiler error caused by an invalid method override.

**Concepts covered:**
- AWT
- MouseListener
- MouseAdapter
- Mouse events
- Event handling
- `@Override`
- Method overriding
- Graphics
- Point objects
- `paint(Graphics)`
- Repainting
- GUI state management
- Compiler-error analysis

**Files included:**
- `DrawPad.java` – Corrected interactive drawing application.
- `BrokenDrawPad_task4_download.java` – Intentionally incorrect version used to demonstrate the override compiler error. It is stored under this filename in the repository.
- `Task4_Report.pdf` – Detailed report and analysis.
- `compile_check_task4.txt` – Compilation check information.
- `override_error_expected_task4.txt` – Expected compiler-error reference.
- `Java assignment 4 task04 output.png` – Execution screenshot.

**Requirements:**
- JDK installed.
- A graphical desktop environment.

**Compilation and execution:**

Compile the corrected program:

```bash
javac DrawPad.java
```

Run:

```bash
java DrawPad
```

**Compiler-error demonstration:**

The repository's faulty file is named `BrokenDrawPad_task4_download.java`. To compile it as a standalone Java source file while keeping its public class and filename compatible, first follow the original demonstration's intended filename `BrokenDrawPad.java` by making a copy or renaming the file.

Then execute:

```bash
javac BrokenDrawPad.java
```

The expected diagnostic is:

```text
method does not override or implement a method from a supertype
```

The incorrect version is used only for demonstrating the compiler error. The corrected `DrawPad.java` should be used for normal execution.

**Test cases:**

1. Click inside the drawing window several times.
2. Verify that a dot appears at each click.
3. Minimise and restore the application.
4. Resize or cover and uncover the window.
5. Verify that previously drawn dots remain visible.
6. Close the window.

**Screenshots to capture:**
- `output_before_minimize.png`
- `output_after_restore.png`
- `override_compile_error.png`

**Result:** The corrected drawing application stores mouse-click positions and redraws the stored points during repainting. This preserves the drawing even when the window is minimised, resized or restored.

### Task 5 – Same Screen, Different Event-Handling Styles

**Problem context:** Currency Converter Application.

Implemented the same currency-conversion interface using four different Java event-handling approaches.

Each implementation provides a conversion button, keyboard Enter-key handling, a result label and dialog-based feedback.

The task compares how event listeners can be implemented using the frame itself, named inner classes, anonymous inner classes and lambda expressions.

**Concepts covered:**
- AWT
- Event delegation
- ActionListener
- Named inner classes
- Anonymous inner classes
- Lambda expressions
- Event handling
- Keyboard events
- Input validation
- Dialog boxes
- WindowAdapter
- Functional interfaces
- Code reusability

**Files included:**
- `ConverterA_FrameListener.java` – Frame implementing ActionListener.
- `ConverterB_NamedInner.java` – Named inner-class event handler.
- `ConverterC_Anonymous.java` – Anonymous inner-class event handler.
- `Converter.java` – Lambda-based implementation.
- `Task5_Report.pdf` – Detailed comparison and analysis report.
- `compile_check.txt` – Compilation-check information.
- `Java assignment 4 task05 output.png` – Execution screenshot.

**Requirements:**
- JDK 8 or later.
- Java-compatible IDE or terminal.
- Graphical desktop environment.

**Compilation:**

```bash
javac ConverterA_FrameListener.java ConverterB_NamedInner.java ConverterC_Anonymous.java Converter.java
```

**Run each implementation separately:**

```bash
java ConverterA_FrameListener
```

```bash
java ConverterB_NamedInner
```

```bash
java ConverterC_Anonymous
```

```bash
java Converter
```

**Test cases:**

1. Enter `1000`, select USD and click Convert.
2. Enter `1000`, select EUR and press Enter.
3. Enter `abc` and verify invalid-amount feedback.
4. Enter a negative amount and verify validation.
5. Enter zero and verify that the application displays a zero conversion amount.

**Conversion rates used:**

| Currency | Conversion Rate |
|---|---:|
| USD | 0.012 |
| EUR | 0.011 |

The source currency is INR.

For an input of ₹1,000:

- USD result: 12.00 USD
- EUR result: 11.00 EUR

**Screenshots to capture:**
- `valid_conversion.png` – Successful conversion.
- `invalid_amount.png` – Invalid input and error dialog.
- `enter_key_conversion.png` – Optional keyboard-triggered conversion.

**Result:** Four event-handling approaches were implemented to provide identical conversion behaviour. The task demonstrates the flexibility of Java event handling and the use of lambda expressions to simplify event-listener code.

### Task 6 – Campus Event Registration Desktop Application

**Problem context:** Campus Event Registration System.

Developed a database-connected Swing desktop application that manages student registrations for campus events.

The application supports registration, validation, duplicate-registration prevention, event filtering, paginated table viewing and CSV export.

The application follows a layered design that separates the user interface, service logic, repository operations and database configuration.

**Concepts covered:**
- Java Swing
- GUI development
- Event handling
- JTable
- MenuBar
- Input validation
- JDBC
- MySQL
- PreparedStatement
- Database operations
- Service layer
- Repository pattern
- Database constraints
- SwingWorker
- Background processing
- Pagination
- CSV export

**Files included:**
- `EventRegistrationApp.java` – Swing user interface, menu, table, pagination and event handlers.
- `RegistrationService.java` – Input validation and application rules.
- `RegistrationRepository.java` – JDBC operations using PreparedStatement.
- `Registration.java` – Registration data model.
- `Database.java` – MySQL connection settings through environment variables.
- `schema.sql` – Database and table creation script.
- `Task6_Report.pdf` – Complete design, screen flow, class layers, pseudocode and testing report.
- `compile_check.txt` – Compilation-check information.
- `compile_check_copy.txt` – Additional compilation-check information.
- `compile_output.png` – Compilation evidence.

**Requirements:**
- JDK 8 or later.
- MySQL Server 8.x or compatible.
- MySQL Connector/J JDBC driver.
- Visual Studio Code or another Java-compatible IDE.

**Step 1 – Create the database**

Execute `schema.sql` in MySQL Workbench or the MySQL command line.

The database uses a unique constraint on `(student_id, event_name)` to prevent duplicate registrations.

**Step 2 – Configure database credentials**

For Windows PowerShell:

```powershell
$env:EVENT_DB_URL="jdbc:mysql://localhost:3306/campus_events?useSSL=false&serverTimezone=UTC"
$env:EVENT_DB_USER="root"
$env:EVENT_DB_PASSWORD="YOUR_MYSQL_PASSWORD"
```

Replace `YOUR_MYSQL_PASSWORD` with the actual password configured for your local MySQL account.

**Step 3 – Add MySQL Connector/J**

Download MySQL Connector/J and place its JAR in the Task06 folder, or provide its full path.

**Step 4 – Compile**

```powershell
javac -cp ".;mysql-connector-j-<version>.jar" *.java
```

**Step 5 – Run**

```powershell
java -cp ".;mysql-connector-j-<version>.jar" EventRegistrationApp
```

Replace the JAR placeholder with the actual Connector/J filename.

**Test cases:**

1. **Valid registration:** Enter a valid student ID, name, email and event. Verify successful registration.
2. **Duplicate registration:** Submit the same student and event combination again. Verify that the duplicate is rejected.
3. **Invalid email:** Enter an invalid email address and verify validation.
4. **Missing fields:** Leave one or more fields empty and verify validation feedback.
5. **Event filtering:** Select a particular event and verify that only matching registrations appear.
6. **Pagination:** Insert more than 50 test records and verify Next and Previous navigation.
7. **CSV export:** Export registrations matching the current event filter.
8. **Responsiveness:** Verify that database operations are performed using SwingWorker rather than blocking the Swing Event Dispatch Thread.

**Screenshots to capture:**
- `registration_success.png` – Successful registration.
- `invalid_input.png` – Validation error.
- `duplicate_registration.png` – Duplicate registration rejected.
- `filtered_table.png` – Filtered registrations.
- `csv_export.png` – Export confirmation or generated CSV.

**Result:** A database-connected campus event registration application was implemented using Swing and JDBC. The application separates UI, service and repository responsibilities, validates user input, prevents duplicate registrations and supports filtering, pagination and CSV export.

---

## 4. Technologies and Tools Used

| Technology / Tool | Purpose |
|---|---|
| Java | Programming language |
| JDK | Compilation and execution |
| AWT | GUI development and event handling |
| Swing | Desktop application development |
| JDBC | Database connectivity |
| MySQL | Database management |
| MySQL Connector/J | Java-MySQL communication |
| PreparedStatement | Parameterized SQL operations |
| SwingWorker | Background database processing |
| Visual Studio Code | Source-code development |
| Command Prompt / PowerShell | Compilation and execution |
| SQL | Database and table creation |
| PDF Editor / Document Editor | Report preparation |
| Git and GitHub | Version control and submission |

## 5. Repository Structure

```text
CSA0919-192525148-Programming-in-Java-Assignments/
│
└── Assignment 4/
    │
    ├── README.md
    │
    ├── Task01/
    │   ├── README.md
    │   ├── FeeWindow.java
    │   ├── FeeService.java
    │   ├── create_fees_table.sql
    │   ├── Task1_Report.pdf
    │   └── Java assignment 4 task01 output.png
    │
    ├── Task02/
    │   ├── README.md
    │   ├── Task2_Design.md
    │   └── Task2_Report.pdf
    │
    ├── Task03/
    │   ├── README.md
    │   ├── README (1).md
    │   ├── LibrarySearch.java
    │   ├── Task3_Report.pdf
    │   ├── Java assignment 4 task03 output 1.png
    │   └── Java assignment 4 task03 output 2.png
    │
    ├── Task04/
    │   ├── README.md
    │   ├── DrawPad.java
    │   ├── BrokenDrawPad_task4_download.java
    │   ├── Task4_Report.pdf
    │   ├── compile_check_task4.txt
    │   ├── override_error_expected_task4.txt
    │   └── Java assignment 4 task04 output.png
    │
    ├── Task05/
    │   ├── README.md
    │   ├── Converter.java
    │   ├── ConverterA_FrameListener.java
    │   ├── ConverterB_NamedInner.java
    │   ├── ConverterC_Anonymous.java
    │   ├── Task5_Report.pdf
    │   ├── compile_check.txt
    │   └── Java assignment 4 task05 output.png
    │
    └── Task06/
        ├── README.md
        ├── Database.java
        ├── EventRegistrationApp.java
        ├── Registration.java
        ├── RegistrationRepository.java
        ├── RegistrationService.java
        ├── Task6_Report.pdf
        ├── schema.sql
        ├── compile_check.txt
        ├── compile_check_copy.txt
        └── compile_output.png
```

*Note: This is a summary of the main repository files. Additional compiled `.class` files and supporting resources may also be present.*

## 6. Compilation and Execution

The Java applications can be compiled and executed using the JDK through Visual Studio Code, Command Prompt or PowerShell.

### General execution procedure

1. Open the required task folder.
2. Check the installed Java version.
3. Open a terminal inside the task folder.
4. Compile the required Java source files.
5. Execute the class containing the main method.
6. Provide any required database configuration or supporting files.
7. Verify the application behaviour against the test cases.
8. Capture genuine screenshots of the running application.

### Check Java version

```bash
java -version
javac -version
```

### General compilation command

```bash
javac FileName.java
java ClassName
```

For GUI applications, ensure that the system has a graphical desktop environment.

For database-connected applications, ensure that MySQL Server is running and that the JDBC driver and valid credentials are configured.

## 7. Testing and Validation

Testing is an important part of Assignment 4.

The applications are evaluated using different test scenarios to verify correct event handling, user-interface behaviour, database operations and input validation.

The main testing areas include:

- Valid and invalid payment details.
- Database insertion and error handling.
- Hospital appointment event modelling.
- Library searching and pagination.
- Responsive GUI resizing.
- Mouse-event handling and repainting.
- Correct and incorrect event-handler overrides.
- Currency conversion using button and keyboard events.
- Input validation and error dialogs.
- Database-connected event registration.
- Duplicate-registration prevention.
- Event filtering and pagination.
- CSV export and background database processing.

Screenshots should represent actual execution results rather than illustrative or recreated interfaces.

## 8. Learning Outcomes

Through this assignment, the following learning outcomes were addressed:

- Understanding graphical user-interface development in Java.
- Applying event-driven programming principles.
- Designing and organising GUI components.
- Implementing event listeners and adapter classes.
- Using nested panels and layout managers.
- Handling mouse, keyboard and window events.
- Comparing different event-handling approaches.
- Understanding AWT and Swing.
- Integrating Java applications with MySQL using JDBC.
- Applying SQL queries and database constraints.
- Separating presentation, service and repository layers.
- Developing responsive applications using background processing.
- Testing and evaluating GUI applications.

## 9. Conclusion

Assignment 4 provides practical experience in developing event-driven desktop applications using Java AWT and Swing.

Across the six tasks, different GUI components, event-handling mechanisms, listener implementations and layout managers are explored.

The assignment progresses from analysing GUI design problems to developing interactive library and drawing applications, comparing event-handling techniques and implementing database-integrated applications.

The final task demonstrates the integration of Java Swing, JDBC and MySQL to create a practical campus event registration system with validation, duplicate prevention, filtering, pagination and CSV export.

Overall, this assignment strengthens the understanding of GUI programming, event-driven design, desktop application development, database connectivity and software architecture.

---

**Course:** CSA0919 – Programming in Java  
**Assignment:** 4 – GUI Programming, Event Handling and Database Connectivity  
**Student:** Juhi Surin  
**Register Number:** 192525148
