# Task 3 – Test Cases and Evidence

| Test | Input | Version | Expected observation |
|---|---|---|---|
| 1 | store1.csv, store2.csv, store4.csv | Manual close | Report total = 500; 3 stores processed; no readers remain open |
| 2 | store1.csv, store2.csv, store4.csv | Try-with-resources | Same report contents as manual version |
| 3 | store1.csv, store2.csv, missing store3_missing.csv, store4.csv | Manual close | Failure occurs part-way; manual close loop is skipped; 2 tracked readers remain open |
| 4 | Same failing list | Try-with-resources | Failure occurs at missing file; previously opened readers are closed; open-reader count = 0 |
| 5 | Malformed row (for example `Pens,abc`) | Both | NumberFormatException is reported; try-with-resources still closes the active reader |

## Why the manual version leaks
The manual implementation stores opened readers in a list and closes them only after the entire file-reading loop. If opening or reading a later file throws an exception, control transfers to `catch`; execution never reaches the close loop. The `finally` block reports the remaining open-reader count but does not close the readers.

## Debugging procedure
1. Set a breakpoint on `readers.add(reader)` and another on the `for (TrackedReader reader : readers) reader.close()` loop.
2. Run the manual failure case.
3. Step through the first two successful file opens.
4. When the missing third file throws IOException, observe that execution jumps to `catch`; the close loop is skipped.
5. Inspect `openReaders`: it remains 2.
6. Repeat with try-with-resources. Each try scope closes its reader before the next file is opened; after the missing file error the count is 0.

## Logging
The program prints each file name immediately before opening it. The last printed file identifies the file that caused the failure.

## Suppressed exceptions
If the try body throws one exception and closing the resource also throws another, the body exception remains the primary exception and the close failure is attached as a suppressed exception. It can be inspected with `getSuppressed()`. This can occur with resources whose `close()` operation itself fails.

## Screenshots to capture
- Successful manual report and try-with-resources report.
- Manual failure showing `Open readers: 2`.
- Try-with-resources failure showing `Open readers: 0`.
- Debugger view showing exception jump over the manual close loop.
- The report text files showing the matching successful report contents.
