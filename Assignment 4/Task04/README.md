# Assignment 4 – Task 4: Event-Handling Forensics

## Student details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Files
- `DrawPad.java` – corrected AWT drawing pad implementation.
- `BrokenDrawPad.java` – intentionally incorrect version used only to demonstrate the `@Override` compiler error.
- `Task4_Report.pdf` – report and analysis. Student details are intentionally omitted from the report.

## Requirements
- JDK installed and `javac` available in the terminal.
- Windows, Linux, or macOS with a graphical desktop environment (AWT window).

Check Java:
```bash
java -version
javac -version
```

## Compile and run the corrected program
Open a terminal in this folder and run:
```bash
javac DrawPad.java
java DrawPad
```

## Tests to perform
1. Click inside the drawing window several times. A 6 × 6 dot should appear at each click and the status label should increment.
2. Minimise the window, then restore it. Previously drawn dots should remain visible and the click count should remain unchanged.
3. Resize or cover and uncover the window. The stored dots should be drawn again during repainting.
4. Close the window using its close button.

## Task 4.4 – demonstrate the compiler error
Compile the intentionally faulty file separately:
```bash
javac BrokenDrawPad.java
```
It should fail because `mouseclicked` is not the method name declared by `MouseAdapter`. The expected diagnostic includes:
`method does not override or implement a method from a supertype`

Do not rename `BrokenDrawPad.java` or remove `@Override` when collecting this evidence. The faulty file is only for the compiler-error demonstration; use `DrawPad.java` for the working application.

## Screenshots to capture
Capture genuine screenshots from your own execution and add them to this folder:
- `output_before_minimize.png` – drawing pad with several dots and the updated count.
- `output_after_restore.png` – same window after minimising and restoring, showing that the dots remain.
- `override_compile_error.png` – terminal showing the `javac BrokenDrawPad.java` error.

Use **Windows + Shift + S** on Windows to capture the application or terminal. Do not use mock screenshots as execution evidence.

## Result
The corrected program registers mouse clicks, stores each click as a `Point`, updates the click-count label, and redraws the stored dots from `paint(Graphics)`. This preserves the drawing across repaint operations.
