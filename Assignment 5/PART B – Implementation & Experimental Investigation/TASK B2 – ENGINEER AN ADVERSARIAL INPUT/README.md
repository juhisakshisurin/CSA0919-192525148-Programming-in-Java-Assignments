# Assignment 5 – Part B – Task B2

**Name:** Juhi Surin  
**Reg No:** 192525148  
**Course:** CSA0919 Programming in Java

## Task
**B2 – Engineer an Adversarial Input (5 marks, K4 – Analyse)**

This task demonstrates:
- An invalid login accepted by the deliberately unsafe SQL string-concatenation implementation.
- Rejection of the same input by a `PreparedStatement`.
- Handling of a damaged bus-pass file with wrong field count, non-numeric amount and empty line.
- Why each adversarial input exposes a weakness.
- Why manual quote escaping or regular-expression validation is weaker than parameter binding.

## Project files
- `src/B2AdversarialTest.java` – JDBC login tests and damaged-file validator.
- `src/B2DamagedFileTest.java` – damaged-file test that runs without a database driver.
- `sql/create_test_database.sql` – schema and seed account reference.
- `input/damaged_passes.csv` – sample damaged file.
- `output/expected_run.txt` – expected full console transcript (not actual execution evidence).
- `output/file_validation_run.txt` – expected file-validation output.
- `lib/` – place the H2 JDBC JAR here.
- `data/` – local H2 database files are created here.

## Database
This implementation uses H2, a lightweight embedded JDBC database, solely as the local test database for this assignment. No MySQL server is needed for B2. The Java program creates/resets the `users` table and inserts one test account (`student01` / `pass123`) on each run.

Download H2 2.3.232:
https://repo.maven.apache.org/maven2/com/h2database/h2/2.3.232/h2-2.3.232.jar

Save it as `lib/h2-2.3.232.jar`.

## Run in Windows PowerShell / VS Code
Open this folder in VS Code. In the terminal:

```powershell
New-Item -ItemType Directory -Force output, data
javac -d output src\B2AdversarialTest.java
java -cp "output;lib\h2-2.3.232.jar" B2AdversarialTest
```

### Run the damaged-file test separately (no H2 required)
```powershell
javac -d output src\B2DamagedFileTest.java
java -cp output B2DamagedFileTest
```

## Run in Linux / macOS
```bash
mkdir -p output data
javac -d output src/B2AdversarialTest.java
java -cp "output:lib/h2-2.3.232.jar" B2AdversarialTest
```

For the standalone file test:
```bash
javac -d output src/B2DamagedFileTest.java
java -cp output B2DamagedFileTest
```

Use Java 11 or later. The H2 JAR is needed only to run the JDBC login demonstration. If you run the full program repeatedly, the test table is reset on each run. The database is local and for this assignment only.

## Test input and expected result

Attack ID:
```text
' OR '1'='1' -- 
```
Wrong password:
```text
definitely-wrong
```

Expected:
- `UNSAFE_LOGIN=true` – the concatenated query is vulnerable.
- `PREPARED_LOGIN=false` – the payload is bound as a literal ID value.
- `VALID_LOGIN=true` – the real test account credentials match.

The unsafe query becomes:
```sql
SELECT * FROM users WHERE id='' OR '1'='1' -- ' AND pwd='definitely-wrong'
```
The always-true expression can match a row, and the comment marker causes the remaining password condition to be ignored by H2's SQL parser.

## Damaged-file cases

The bus-pass file has five fields: pass ID, register number, name, route number and amount.

| Line | Input condition | Expected handling |
|---|---|---|
| 1 | Valid five-field record | Accepted |
| 2 | Only four fields | Skipped: wrong field count |
| 3 | Amount `three-eight` | Skipped: invalid numeric amount |
| 4 | Empty line | Skipped: empty |
| 5 | Valid five-field record | Accepted |

Expected summary: 2 valid rows accepted; 1 wrong-field-count row, 1 invalid-amount row and 1 empty line skipped. The scan continues after each damaged row.

## What the inputs reveal
- SQL injection: concatenating untrusted input mixes executable SQL with user data and permits a caller to change the `WHERE` condition.
- Wrong field count: the row does not match the five-field record schema and cannot safely be mapped.
- Non-numeric amount: the data violates the numeric field requirement and could otherwise cause parsing failures or invalid financial values.
- Empty line: blank input must be identified and skipped rather than treated as a record.
- Continue after damaged rows: fault isolation preserves later valid records and lets the program report errors.

## Alternative: escaping or regular-expression validation
Quote escaping is database- and context-dependent. Incomplete or inconsistent escaping can leave injection vulnerabilities. Regex validation is brittle when accepted formats evolve and cannot safely account for every SQL syntax context. `PreparedStatement` is stronger because the SQL structure remains fixed and user values are bound separately.

## Complexity analysis
Let `m` be the length of the supplied input and `n` the number of user rows.
- Manual escaping or regex validation takes `O(m)` time per query, in addition to the database lookup.
- Parameter binding also processes the parameter data in `O(m)`, but avoids manually rewriting SQL.
- Database lookup depends on the query plan: typically `O(log n)` with a suitable index, or `O(n)` for a full scan.
- PreparedStatement is a security and correctness improvement; it does not change the asymptotic lookup complexity.

## Submission evidence
Capture your actual terminal output and place screenshots in `evidence/`. The expected transcript is provided for comparison only and is not a substitute for running the program.

Keep this folder as `TaskB2` in repository `CSA09-192525148-Assignment5`. Include the source, SQL, input, output, report and actual execution screenshots. Commit this task separately and add the GitHub link as required by the assignment.
