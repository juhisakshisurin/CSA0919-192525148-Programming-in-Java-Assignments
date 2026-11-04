# Assignment 1 – Task 7: Override and Specialise Behaviour

## Student Details

| Field | Details |
|:---|:---|
| Name | Juhi Surin |
| Register Number | 192525148 |
| Course | CSA0919 – Programming in Java |
| Assignment | Assignment 1 |
| Task | Task 7 – Override and Specialise Behaviour |

## Task Description

Identify and implement specialized behaviour in the Campus Smart-Parking System using method overriding. Demonstrate the use of the `@Override` annotation, appropriate applications of the `final` keyword and overriding of the `toString()`, `equals()` and `hashCode()` methods.

Additionally, compare method overriding with a switch-based fee calculation approach and analyse their time complexity, extensibility and maintainability.

## Files Included

| File Name | Description |
|:---|:---|
| Task07.java | Java source code demonstrating method overriding, inheritance and Object-class method overriding. |
| Task07_Report.pdf | Detailed report covering overriding, @Override, final, toString(), equals(), hashCode() and alternative concept analysis. |
| README.md | Documentation containing task details, execution instructions and result. |
| Task07_Output.png | Screenshot of the successfully executed Java program. |

## Concepts Covered

- Method Overriding
- `@Override` Annotation
- Runtime Polymorphism
- `final` Variables
- `final` Methods
- `final` Classes
- `toString()` Method
- `equals()` Method
- `hashCode()` Method
- Inheritance
- Switch-Based Fee Calculation
- Time Complexity Analysis

## Software Requirements

- Java Development Kit (JDK 8 or later)
- Visual Studio Code / IntelliJ IDEA / Eclipse
- Command Prompt or Terminal

## Compilation and Execution

**Step 1: Open the Task07 folder in the terminal.**

**Step 2: Compile the Java program.**

```bash
javac Task07.java
```

**Step 3: Execute the program.**

```bash
java Task07
```

## Expected Output

```text
TwoWheeler TN01A1 -> Rs 30.0
Car TN02B2 -> Rs 90.0
ElectricCar TN03C3 -> Rs 90.0
Car{regNo='TN02B2'}
true
true
```

## Result

The Java program was implemented and executed successfully. Method overriding was demonstrated through vehicle-specific parking fee calculations. The `@Override` annotation and `final` keyword were applied appropriately. The `toString()`, `equals()` and `hashCode()` methods were overridden to provide meaningful object representation and consistent equality based on registration numbers.

The alternative switch-based approach was also analysed and compared with method overriding in terms of complexity, extensibility and maintainability.
