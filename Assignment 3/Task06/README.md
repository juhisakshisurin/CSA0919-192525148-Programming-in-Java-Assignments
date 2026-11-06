# Assignment 3 – Task 6

## Student details
- Name: Juhi Surin
- Register Number: 192525148
- Course: CSA0919 Programming in Java
- Java version: Run `java -version` and record the installed version.

## Files
- `Task06/WalletTransferDemo.java` – deliberately unsafe opposite-order wallet locking and safe lock ordering.
- `Task06/TokenCounter.java` – completed TODO 1 and TODO 2 using synchronized, wait(), and notifyAll().
- `Task06/ArrayBlockingQueueAlternative.java` – bounded queue alternative with capacity 5.
- `Task06/TokenCounterWhileVsIf.java` – why while is required instead of if.
- Output logs, test cases, and PDF report.

## Windows procedure
1. Open Command Prompt.
2. Enter the Task06 folder, for example:
   `cd C:\Users\ashis\Downloads\Assignment3_Task6\Task06`
3. Compile all programs: `javac *.java`
4. Run: `java TokenCounter`
5. Run: `java WalletTransferDemo`
6. Run: `java ArrayBlockingQueueAlternative`
7. Run: `java TokenCounterWhileVsIf`

## Expected checks
- TokenCounter final sum: 210.
- Every reported buffer size remains between 0 and 5.
- Unsafe opposite-order wallet transfer can deadlock.
- Consistent lock-ordering transfers complete and preserve total balance.
- ArrayBlockingQueue alternative finishes with sum 210.

## Submission reminders
Execute every program yourself, capture source/output screenshots, record your own Java version and measurements, and commit Task06 separately to `CSA09-192525148-Assignment3`. Sample output logs are references, not screenshots from your computer.
