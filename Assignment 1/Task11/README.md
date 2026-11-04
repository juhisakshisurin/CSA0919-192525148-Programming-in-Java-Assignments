# Task 11 – Implement, Test and Defend the Design

## Programming in Java – Assignment 1 (CO1)

| **Field** | **Details** |
|---|---|
| Student Name | Juhi Surin |
| Register Number | 192525148 |
| Course | CSA0919 – Programming in Java |
| Assignment | Assignment 1 |
| Task Number | Task 11 |
| Task Title | Implement, Test and Defend the Design |
| Marks | 5 |
| Bloom's Level | K5 – Evaluate |
| Java Version | Java 8 or later |

---

## 1. Objective

To implement and test an object-oriented Campus Smart-Parking System using Java. The program demonstrates vehicle entry and exit, parking-slot allocation, ticket generation, parking-fee calculation, monthly-pass handling and validation of invalid operations.

## 2. Files Included

- `ParkingSystemDemo.java` – Complete Java implementation.
- `Task11_Report.pdf` – Written report, testing and complexity analysis.
- `README.md` – Execution instructions and task summary.

## 3. Concepts Demonstrated

- Abstract classes and inheritance.
- Method overriding and runtime polymorphism.
- Encapsulation through private fields and public methods.
- Jagged arrays for parking floors.
- Active parking-ticket management using an array.
- Fee calculation using integer arithmetic.
- Monthly-pass fee exemption.
- Input validation and exception handling.

## 4. Assumptions

- Two-wheelers are charged Rs. 10 per started hour.
- Cars are charged Rs. 30 per started hour.
- Electric cars are charged Rs. 30 per started hour plus Rs. 12 per kWh.
- Every started hour is charged in full.
- Valid monthly-pass holders pay no parking fee.
- Visitors pay the applicable parking fee.
- A registration number cannot have more than one active ticket.

## 5. Compile and Run

Open the terminal inside the `Task11` folder.

Compile:

```bash
javac ParkingSystemDemo.java
