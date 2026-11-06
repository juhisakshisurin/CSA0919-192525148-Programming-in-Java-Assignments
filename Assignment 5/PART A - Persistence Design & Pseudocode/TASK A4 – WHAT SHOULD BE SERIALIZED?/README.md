# Assignment 5 – CO5 | Part A – Task A4

## Student details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
**A4 – What Should Be Serialized?**  
**Marks:** 4 | **Bloom's Level:** K4 – Analyse

## Files included
- `Assignment5_PartA_TaskA4_Report.pdf` – complete report; student-identifying details are intentionally excluded.
- `TaskA4_Serialization_Design.txt` – editable notes covering transient fields, object-graph serialization, serialVersionUID, and CSV/JSON comparison.

## Requirements covered
1. **A4.1 – Decide What to Persist:** identifies durable fields and transient fields; explains recursive serialization of Hostel → Room → Resident.
2. **A4.2 – Plan for Change:** explains adding `phoneNumber`, default values for fields absent from an older stream, and the effect of `serialVersionUID`.
3. **Alternative Concept and Complexity Analysis:** compares Java serialization with CSV/JSON for file size, save/load time for n residents, memory use and schema evolution.

## Is Java implementation required?
No complete runnable Java implementation is requested in Task A4. This is a Part A design/analysis task. The report includes a short illustrative Java field declaration to show which fields should be `transient`; it is not a complete application. No compile/run command or output screenshot is required specifically for A4.

## Main design decision
- Persist `Hostel`, `Room`, resident `name` and `registerNumber`.
- Mark `Resident.password` transient because credentials should not be stored in a general serialized snapshot.
- Mark `Resident.connection` transient because a live JDBC connection is a runtime resource and must be recreated after loading.
- With a compatible explicit `serialVersionUID`, an added `String phoneNumber` absent from an old stream defaults to `null`.
- A serialVersionUID mismatch causes `InvalidClassException`.
- Java serialization, CSV and JSON all require O(n) time to save/load n records; streaming text parsing can reduce extra working memory.

## GitHub submission
1. Add this folder as `TaskA4` inside `CSA09-192525148-Assignment5`.
2. Commit the PDF and design notes.
3. Suggested commit message: `Add Assignment 5 Part A Task A4 serialization design`.
4. Add your working GitHub repository link to the assignment cover page as required by the assignment instructions.

## Java version
Not applicable to execution for this task; A4 is a design and analysis task.
