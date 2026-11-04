# Task 12 – Re-evaluate and Adapt the Design

## Programming in Java – Assignment 1 (CO1)

| **Field** | **Details** |
|---|---|
| Student Name | Juhi Surin |
| Register Number | 192525148 |
| Course | CSA0919 – Programming in Java |
| Assignment | Assignment 1 |
| Task Number | Task 12 |
| Task Title | Re-evaluate and Adapt the Design |
| Marks | 8 |
| Bloom's Level | K5 – Evaluate |
| Java Version | Java 8 or later |

---

## 1. Objective

To evaluate and adapt the original Campus Smart-Parking System to support e-bikes, delivery vans, visitor one-day passes and a five-floor facility with unequal slot capacities.

## 2. Files Included

- `Task12DesignChallenge.java` – Complete Java implementation.
- `Task12_Report.pdf` – Impact analysis, design evaluation, adaptation and defence.
- `README.md` – Execution instructions and task summary.

## 3. New Requirements Implemented

- E-bikes are charged a flat fee of Rs. 25 per session.
- Delivery vans are charged at double the car rate during 9 AM–5 PM.
- Visitors can use a valid one-day pass for free parking on the covered date.
- Five floors support different slot capacities.
- Active tickets are stored in a HashMap using registration numbers as keys.

## 4. Concepts Demonstrated

- Abstract classes and inheritance.
- Interfaces and strategy-based pricing.
- Runtime polymorphism.
- Encapsulation.
- Jagged arrays.
- HashMap-based ticket management.
- LocalDate and LocalDateTime.
- Exception handling and validation.

## 5. Compile and Run

Open the terminal inside the `Task12` folder.

Compile:

```bash
javac Task12DesignChallenge.java
