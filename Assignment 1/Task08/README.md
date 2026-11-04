# Assignment 1 – Task 8: Apply Polymorphism

## Student Details

| Field | Details |
|:---|:---|
| Name | Juhi Surin |
| Register Number | 192525148 |
| Course | CSA0919 – Programming in Java |
| Assignment | Assignment 1 |
| Task | Task 8 – Apply Polymorphism |

## Task Description

Demonstrate compile-time and runtime polymorphism in the Campus Smart-Parking System using method overloading, method overriding, upcasting and dynamic method dispatch.

Complete the given Vehicle hierarchy and FeeDemo program. Implement the Chargeable interface for electric vehicles and compare dynamic method dispatch with explicit instanceof checks and casting.

## Files Included

| File Name | Description |
|:---|:---|
| FeeDemo.java | Completed Java implementation demonstrating polymorphism, inheritance, method overriding, method overloading and the Chargeable interface. |
| Task08_Report.pdf | Detailed report covering compile-time polymorphism, runtime polymorphism, upcasting, dynamic method dispatch, method tracing and complexity analysis. |
| README.md | Task documentation and execution instructions. |
| Task08_Output.png | Screenshot showing the successfully executed Java program. |

## Concepts Covered

- Compile-Time Polymorphism
- Runtime Polymorphism
- Method Overloading
- Method Overriding
- Upcasting
- Dynamic Method Dispatch
- Abstract Classes
- Interfaces
- Chargeable Interface
- instanceof Operator
- Integer Ceiling Division
- Time Complexity Analysis

## Software Requirements

- Java Development Kit (JDK 17 or later)
- Visual Studio Code / IntelliJ IDEA / Eclipse
- Command Prompt or Terminal

## Compilation and Execution

**Step 1:** Open the Task08 folder in the terminal.

**Step 2:** Compile the Java program.

```bash
javac FeeDemo.java
```

**Step 3:** Execute the program.

```bash
java FeeDemo
```

## Expected Output

```text
TwoWheeler TN01A1 -> Rs 30.0
Car TN02B2 -> Rs 90.0
ElectricCar TN03C3 -> Rs 210.0
true
```

## Result

The Vehicle hierarchy was successfully completed and the FeeDemo program was implemented and executed. Compile-time polymorphism was demonstrated through method overloading, while runtime polymorphism was demonstrated through method overriding, upcasting and dynamic method dispatch.

The Chargeable interface was implemented for ElectricCar to calculate the additional charging fee. The alternative instanceof-based approach was also analysed and compared with dynamic method dispatch.

The output was verified against the expected output provided in the assignment.
