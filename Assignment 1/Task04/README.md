# Task 4 – Design Classes, Objects and Constructors

## Programming in Java – Assignment 1 (CO1)

| **Field** | **Details** |
|---|---|
| Assignment Title | Design, Implement and Validate an Object-Oriented Model for a Campus Smart-Parking System |
| Student Name | Juhi Surin |
| Register Number | 192525148 |
| Course | CSA0919 – Programming in Java |
| Task Number | Task 4 |
| Task Title | Design Classes, Objects and Constructors |
| Bloom's Level | K4 – Analyse |
| Java Version | Java 27 |

---

## 1. Objective

To design the classes identified in Task 1 and decide how their objects are created: the fields and methods of each class, default, parameterised and overloaded constructors, the `this` keyword and constructor chaining, whether a copy constructor is needed, and how objects and references are stored in stack and heap memory. The chosen constructors are compared with a default constructor followed by setters and with static factory methods for creating `n` `ParkingTicket` objects.

## 2. Classes Designed

The classes are those of the Task 1 design; no new class was added.

| Class / Interface | Responsibility | Key fields (type) |
|---|---|---|
| `Vehicle` (abstract) | Data and behaviour common to all vehicles; abstract hourly rate | `registrationNumber` (`String`) |
| `TwoWheeler`, `Car`, `ElectricCar` | Vehicle categories; `ElectricCar` is a `Car` that implements `Chargeable` | none of their own |
| `Chargeable` (interface) | Charging capability of eligible vehicles; declares `chargingFee(int kWh)` | none (interfaces have no constructors) |
| `ParkingTicket` | One parking session, including entry and exit time | `ticketNumber` (`long`), `registrationNumber` (`String`), `floor`, `slot` (`int`), `entryTime`, `exitTime` (`LocalDateTime`), `fee` (`double`) |
| `ParkingFacility` | Owns the slots; allocation, release, ticket numbering, active tickets | `slots` (`String[][]`), `ticketCounter` (`long`), `activeTickets` (`List<ParkingTicket>`) |
| `ParkingUser`, `Student`, `Staff`, `Visitor` | User categories and free-parking eligibility | `userId` (`String`); `pass` (`MonthlyPass`) for students and staff |
| `MonthlyPass` | Pass validity | `passNumber` (`String`), `validFrom`, `validTo` (`LocalDate`) |
| `FeeCalculator` | Combines parking fee, pass exemption, charging fee and any daily maximum | `chargingRatePerKWh` (`double`), `dailyMaximum` (`Double`) |
| `ParkingApp` | Input, calling the model, output | none |

The full table with methods and justifications is in the PDF report.

## 3. Constructor Types Used

- **Default constructor:** supplied by the compiler only when a class declares no constructor. It suits classes without mandatory data, such as `ParkingApp`. It is unsuitable for `ParkingTicket`, `Vehicle` and `MonthlyPass`, so `new ParkingTicket()` does not compile.
- **Parameterised constructor:** supplies mandatory data at creation and validates it, so an invalid object is never created. The master constructor of `ParkingTicket` takes the ticket number, registration number, floor, slot and entry time.
- **Overloaded constructors:** `ParkingTicket` also has a constructor that takes a `Vehicle` and one that takes the current time as the entry time. A call with a bare `null` for the second argument is ambiguous and does not compile.

## 4. Use of `this` and Constructor Chaining

`this.floor = floor` assigns the parameter to the field of the current object. `this(...)` delegates to another constructor of the same class, so validation and assignment are written once in the master constructor:

```java
public ParkingTicket(long ticketNumber, Vehicle vehicle,
                     int floor, int slot, LocalDateTime entryTime) {
    this(ticketNumber, registrationOf(vehicle), floor, slot, entryTime);
}
```

`this(...)` is the first statement of every constructor in the source files. Up to Java 24 it had to be first; since Java 25 (JEP 513) statements that do not use the object under construction may precede it, and keeping it first is valid in every version. The arguments of `this(...)` are evaluated before the master constructor runs, which is why the helper `registrationOf` is `static`.

## 5. Copy-Constructor Decision

A copy constructor is **not required** in the final system. A plain copy of a `ParkingTicket` would duplicate the ticket number and violate the rule that ticket numbers are unique and never reused. `Vehicle`, `MonthlyPass` and `ParkingUser` are immutable, so no defensive copy is needed. If ticket re-issue is added later, the facility generates a **new** unique ticket number and a constructor `ParkingTicket(ParkingTicket original, long newTicketNumber)` keeps the registration number, floor, slot and entry time. The re-issue constructor rejects a null original, requires a positive new number that differs from the original's, rejects a closed original, and delegates with `this(...)` as its first statement. Uniqueness of the new number is guaranteed by `ParkingFacility`, which takes it from its counter. This constructor is illustrated in the report and is not part of the source files.

## 6. Stack and Heap Memory

Local variables, primitive and reference, live in the frame of the running method on the thread's stack. Objects created with `new`, with all their fields, live on the heap. A reference variable such as `ticket` holds a reference to the object, not the object itself. After `ParkingTicket sameTicket = ticket;` two references refer to one object, and a change through one is visible through the other. An object becomes eligible for garbage collection when it can no longer be reached through any reference; when it is actually collected is decided by the JVM. The report shows a conceptual diagram; the model is conceptual, and no exact memory sizes are claimed.

## 7. Alternative Constructor Approaches

| Approach | How a `ParkingTicket` is created | Guarantee of a valid object |
|---|---|---|
| Overloaded constructors with chaining (chosen) | `new ParkingTicket(...)` with all mandatory data; validation in the master constructor | Strong: no object exists unless construction succeeded; identity fields are `final` |
| Default constructor and setters | `new ...()` followed by one setter per field | None: the object is incomplete until the last setter; a forgotten setter leaves defaults, and floor 0 and slot 0 look valid |
| Static factory methods | `issueAt(...)` or `issueNow(...)` validates, then calls a private constructor | Strong if the constructor is private and all creation uses the factory |

## 8. Time and Space Complexity Comparison

Let `n` be the number of `ParkingTicket` objects created. Assumptions: a fixed number of fields, constant-time validation, a fixed number of setter calls, and a constructor chain of at most two constructors.

| Criterion | Constructors with chaining | Default constructor and setters | Static factory methods |
|---|---|---|---|
| Time per object | O(1) | O(1) | O(1) |
| Total time for `n` objects | O(n) | O(n) | O(n) |
| Space per object | O(1) | O(1) | O(1) |
| Total space for `n` objects | O(n) | O(n) | O(n) |

Constructor chaining adds only constant overhead per object. A static factory does not reduce time or space. The approaches differ in the guarantee of a valid object, not in complexity. No benchmarks or exact memory sizes are reported.

**Recommendation:** overloaded constructors with chaining and one validating master constructor, because they guarantee object validity, force mandatory data, keep identity fields immutable and keep validation in one place.

## 9. Java Source Files Included

| File | Purpose |
|---|---|
| `Vehicle.java` | Abstract class; registration number and validating constructor |
| `Chargeable.java` | Interface that declares `double chargingFee(int kWh)` |
| `TwoWheeler.java`, `Car.java` | Vehicle categories; constructors call `super(registrationNumber)`; rates are placeholders, not assignment requirements |
| `ElectricCar.java` | Extends `Car` and implements `Chargeable`; `chargingFee(int kWh)` rejects a negative `kWh` and returns `kWh * 12.0`, an illustrative rate and not an assignment requirement |
| `MonthlyPass.java` | Validity period with a validating constructor |
| `ParkingTicket.java` | Parameterised constructor, two overloads and chaining with `this(...)` |
| `Task4Demo.java` | Demonstration of object creation, shared references and rejected invalid objects |

The Task 4 source consists of eight Java files. `Task4Demo` does not call `chargingFee`, so its expected output is unaffected. `ParkingFacility` is the Task 3 class and is not repeated here. No package declaration is used.

Compile and run from a terminal in the `Task04` folder:

```bash
javac *.java
java Task4Demo
```

Expected output (the program prints the same ten lines on every run):

```text
Ticket 1001 TN01AB1111 floor 0 slot 1 entry 2026-11-02T09:15 (open)
Ticket 1002 TN02CD2222 floor 1 slot 0 entry 2026-11-02T09:30 (open)
entry time set: true
same object: true
open through alias: false
Ticket 1001 TN01AB1111 floor 0 slot 1 entry 2026-11-02T09:15 exit 2026-11-02T10:16 fee 60.0
rejected: ticket number must be positive
TN05IJ5555 10.0 | TN06KL6666 30.0
true false
rejected: valid-to date must not be before valid-from date
```

## 10. Result and Execution Status

The classes of Task 1 were designed with fields, data types and methods. Default, parameterised and overloaded constructors were evaluated, and `this` and constructor chaining were demonstrated. A copy constructor was evaluated and judged unnecessary because copying a ticket would duplicate its unique number. Stack and heap memory and object references were explained. Constructors with chaining, default constructor with setters, and static factory methods were compared, with O(1) time per object, O(n) total time and O(n) total space for `n` tickets in each approach.

**Execution status:** the expected output above is derived from the code. The record below is completed only after the program has been compiled and run on your own computer. No output screenshot is included until it has been run.

- [ ] Java 27 installed
- [ ] All eight files compiled with `javac *.java`
- [ ] Program executed with `java Task4Demo`
- [ ] Actual output matched the expected output
- [ ] Screenshot saved as `output.png`

## 11. Files in This Folder

- `README.md` – this file
- `Task4_Classes_Constructors.pdf` – complete written report
- `Vehicle.java`, `Chargeable.java`, `TwoWheeler.java`, `Car.java`, `ElectricCar.java`, `MonthlyPass.java`, `ParkingTicket.java`, `Task4Demo.java` – the eight Java source files
- `output.png` – execution screenshot (to be added only after the program has been run)
