# Assignment 3 – Task 1: Bank ATM Simulation

## Student details
- Name: Juhi Surin
- Register Number: 192525148
- Course: CSA0919 Programming in Java

## Java version
Use JDK 17 or later. Check your installed version using `java -version` and `javac -version`.

## Files
- `ATMWithdrawal.java` – ATM withdrawal program.
- `card-reader.txt` – sample card-reader file required for a normal transaction.
- `test-cases.md` – test inputs and expected behaviour.
- `run_output.txt` – sample successful run; add your own screenshots and observed outputs.

## Compile and run (Windows Command Prompt)
1. Open Command Prompt.
2. Change to the Task01 folder. Example:
   `cd /d C:\Users\<YourUser>\Downloads\Assignment3_Task1\Task01`
3. Compile: `javac ATMWithdrawal.java`
4. Run: `java ATMWithdrawal`
5. Enter account number and withdrawal amount when prompted. Keep `card-reader.txt` in the same folder.
6. Uncaught-exception demonstration: `java ATMWithdrawal --uncaught`

## Test procedure
Run every case in `test-cases.md` separately. For the missing-file test, temporarily rename `card-reader.txt`, run the program, and then restore its name.

## Result
The program demonstrates checked exception handling for card-reader file access, unchecked exception handling for invalid input and transactions, multi-catch, and finally-based cleanup.

## Submission reminder
Commit this Task01 folder to your individual repository named `CSA09-192525148-Assignment3`. The assignment requires each student to execute every question individually and provide screenshots/evidence.
