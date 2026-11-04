# Assignment 1 – Task 9: Introduce Abstraction

## Student Details

| **Field** | **Details** |
|---|---|
| Name | Juhi Surin |
| Register Number | 192525148 |
| Course | CSA0919 Programming in Java |
| Assignment | Assignment 1 |
| Task | Task 9 – Introduce Abstraction |

## Task Description

Use abstract classes and interfaces to separate what the Campus Smart-Parking System does from how it is implemented.

The task demonstrates:
- Abstract classes and abstract methods.
- Interfaces and their implementation.
- Differences between abstract classes and interfaces.
- Multiple behaviours through interfaces.
- Default and static methods in interfaces.
- Alternative design and time and space complexity analysis.

## Files Included

| **File Name** | **Description** |
|---|---|
| `Task09_Report.pdf` | Report explaining abstraction, design decisions and complexity analysis. |
| `AbstractionDemo.java` | Java implementation demonstrating abstract classes and interfaces. |
| `README.md` | Task description, student details and execution instructions. |
| `output.txt` | Program execution output. |

## Concepts Used

1. Abstract class – `Vehicle`
2. Abstract method – `ratePerHour()`
3. Interface – `Chargeable`
4. Interface – `Payable`
5. Interface default method – `chargingDescription()`
6. Interface static method – `isValidEnergy()`
7. Multiple interface implementation – `ElectricCar`
8. Method overriding
9. Runtime type checking using `instanceof`

## Software Requirements

- Java Development Kit (JDK 8 or later)
- Command Prompt / Terminal
- Any Java-compatible IDE (optional)

## Steps to Compile and Execute

1. Open the terminal in the `Task09` folder.
2. Compile the Java source file:

   ```bash
   javac AbstractionDemo.java
   ```

3. Run the program:

   ```bash
   java AbstractionDemo
   ```

4. Verify the displayed output with the expected output.

## Expected Result

The program successfully demonstrates abstraction using an abstract `Vehicle` class and the `Chargeable` and `Payable` interfaces. Different vehicle classes provide their own parking-rate implementations, while optional capabilities are implemented through interfaces.

The program displays parking fees, EV charging fees and payment messages.

## Conclusion

The task demonstrates how abstract classes and interfaces separate common structure from implementation-specific behaviour. The design improves code reusability, flexibility and maintainability in the Campus Smart-Parking System.
