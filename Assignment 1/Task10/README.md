# Task 10 – Enforce Encapsulation through Packages and Access Modifiers

## Programming in Java – Assignment 1 (CO1)

| **Field** | **Details** |
|---|---|
| Student Name | Juhi Surin |
| Register Number | 192525148 |
| Course | CSA0919 – Programming in Java |
| Assignment | Assignment 1 |
| Task Number | Task 10 |
| Task Title | Enforce Encapsulation through Packages and Access Modifiers |
| Marks | 3 |
| Bloom's Level | K4 – Analyse |
| Java Version | Java 8 or later |

---

## 1. Objective

To organise the Campus Smart-Parking System using Java packages and apply access modifiers to protect class data. The implementation demonstrates private fields, public getters, validating setters, wrapper classes, autoboxing and safe value comparison.

## 2. Package Structure

- `parking.model` – Vehicle, Car, ParkingSlot and ParkingTicket.
- `parking.service` – ParkingService.
- `parking.app` – Main application.

## 3. Files Included

- `parking/model/Vehicle.java`
- `parking/model/Car.java`
- `parking/model/ParkingSlot.java`
- `parking/model/ParkingTicket.java`
- `parking/service/ParkingService.java`
- `parking/app/Main.java`

## 4. Concepts Demonstrated

- Private fields protect internal object data.
- Protected constructor supports subclass construction.
- Public methods provide controlled access across packages.
- Setters validate duration and parking coordinates.
- Wrapper classes demonstrate autoboxing and unboxing.
- `equals()` compares wrapper values safely.
- The service class validates parking operations and prevents invalid slot releases.

## 5. Compile and Run

Open the terminal in the `Task10` folder.

Compile:

```bash
javac -d out parking/model/Vehicle.java parking/model/Car.java parking/model/ParkingSlot.java parking/model/ParkingTicket.java parking/service/ParkingService.java parking/app/Main.java
