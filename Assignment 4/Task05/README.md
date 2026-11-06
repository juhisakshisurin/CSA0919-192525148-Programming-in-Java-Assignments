# Assignment 4 – Task 5: Same Screen, Different Event-Handling Styles

## Student details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task requirements covered
- Four event-handling implementations: frame implements `ActionListener`, named inner class, anonymous inner class, and lambda expression.
- Each version handles both the Convert button and Enter in the amount field.
- Each version displays the conversion in the result label and a dialog.
- Invalid amounts display `Invalid amount` in the label and an error dialog.
- Window closing is handled with `WindowAdapter`.
- `Task5_Report.pdf` contains the comparison, requirement-change analysis, and alternative method-reference/shared-Action discussion.

## Files
- `ConverterA_FrameListener.java` – frame itself implements `ActionListener`.
- `ConverterB_NamedInner.java` – named inner-class handler.
- `ConverterC_Anonymous.java` – anonymous inner-class handler.
- `Converter.java` – lambda version, matching the supplied complete-the-code class name.
- `Task5_Report.pdf` – report; student details intentionally omitted.

## Java version
Use JDK 8 or later. The code uses lambda expressions and `Double.isFinite`.

## Compile and run (Windows PowerShell)
Open a terminal in this folder.

Compile all four:
```powershell
javac ConverterA_FrameListener.java ConverterB_NamedInner.java ConverterC_Anonymous.java Converter.java
```

Run one version at a time:
```powershell
java ConverterA_FrameListener
```
```powershell
java ConverterB_NamedInner
```
```powershell
java ConverterC_Anonymous
```
```powershell
java Converter
```

## Test cases (repeat for each version)
1. Valid amount: enter `1000`, select `USD`, click Convert. Expected: `12.00 USD` in the result label and a conversion dialog.
2. Valid amount using keyboard: enter `1000`, select `EUR`, press Enter in the amount field. Expected: `11.00 EUR` and a dialog.
3. Invalid amount: enter `abc`, click Convert. Expected: `Invalid amount` in the result label and an error dialog.
4. Negative amount: enter `-50`. Expected: invalid amount feedback.
5. Zero amount: enter `0`. Expected: `0.00` in the selected currency.

## Screenshot evidence to capture
Capture genuine screenshots from your own execution:
- `valid_conversion.png` – valid input and conversion result (e.g. 1000 INR to USD).
- `invalid_amount.png` – invalid input and error dialog.
- Optional: `enter_key_conversion.png` – valid conversion triggered by pressing Enter.

Use Windows + Shift + S. Do not submit mock images as execution evidence.

## Note on the supplied assignment skeleton
The given skeleton has INR as the fixed source currency and a target dropdown containing USD and EUR. The implementations preserve that structure and use the rates supplied in the question: USD = 0.012 and EUR = 0.011.

## Result
All four versions implement the same conversion behavior using different event-handler styles. The button and Enter key share the same handler within each version.
