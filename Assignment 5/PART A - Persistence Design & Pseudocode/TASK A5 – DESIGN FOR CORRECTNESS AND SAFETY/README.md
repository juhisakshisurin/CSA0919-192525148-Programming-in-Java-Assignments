# Assignment 5 – CO5 | Part A – Task A5

## Student Details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
**A5 – Design for Correctness and Safety**  
**Marks:** 4 | **Bloom's Level:** K4 – Analyse

## Files Included
- `Assignment5_PartA_TaskA5_Report.pdf` – complete Task A5 report. Student-identifying details are intentionally excluded.
- `TaskA5_Pseudocode.txt` – editable copy of the transaction pseudocode, injection explanation, and complexity analysis.

## Requirements Covered
1. **A5.1 – Transaction Design:** JDBC-style pseudocode transfers ₹500 from the hostel wallet to the canteen wallet using auto-commit disabled, two checked updates, commit on success, rollback on failure, and restoration of the original auto-commit setting.
2. **A5.2 – Injection Analysis:** explains a crafted input against the concatenated SQL query and redesigns the login check using `PreparedStatement` placeholders and bound parameters.
3. **Alternative Concept and Complexity Analysis:** compares one transaction with two independent auto-commit updates, including failure outcome, time and space considerations.

## Is a complete Java implementation required?
No. Task A5 is part of **Part A – Persistence Design & Pseudocode**. The assignment explicitly says Part A requires pseudocode and reasoning and that Java code is not accepted in place of pseudocode. The report therefore gives the required algorithmic pseudocode as the main answer. A short Java `PreparedStatement` snippet is included only as a supplementary illustration of the safe query form. A complete runnable application and execution screenshot are not specifically requested for A5.

## Important Design Points
- Both wallet updates must use the same JDBC connection and transaction.
- Commit only after both updates succeed; roll back if either update fails.
- Verify that each update affects exactly one wallet row.
- Bind login inputs with `PreparedStatement`; do not concatenate user input into SQL.
- Big-O for two indexed wallet-row updates is O(1) for this fixed-size transfer; transaction latency has DBMS-dependent overhead and should be measured if experimental evidence is required.

## GitHub Submission
1. Create/use the repository `CSA09-192525148-Assignment5`.
2. Add this folder as `TaskA5`.
3. Commit this task separately. Suggested commit message:
   `Add Assignment 5 Part A Task A5 transaction and injection design`
4. Add the working GitHub repository link to the assignment cover page, as required by the assignment instructions.
5. The assignment's overall README requirement also asks for Java version, compile/run steps and results for implementation tasks. For this Part A design task, execution/version details are not applicable.

## Java Version
Not applicable to execution for this task; A5 is a design and pseudocode task.
