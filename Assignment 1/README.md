# CSA0919 – Programming in Java
## Assignment 1: Campus Smart-Parking System

### Student Details

| Field | Details |
|---|---|
| **Name** | Juhi Surin |
| **Register Number** | 192525148 |
| **Course Code** | CSA0919 |
| **Course Name** | Programming in Java |
| **Assignment** | Assignment 1 |
| **Programming Language** | Java |

---

## 1. Introduction

This assignment focuses on designing, implementing, testing and evaluating an object-oriented Campus Smart-Parking System using Java.

The system is designed to manage a multi-level parking facility used by students, staff and visitors. It supports different vehicle categories, including two-wheelers, cars and electric vehicles.

The system handles parking-slot allocation, vehicle entry and exit, ticket generation, parking-fee calculation, monthly-pass validation and electric-vehicle charging fees.

The assignment is completed through 12 tasks, covering fundamental Java concepts and progressively developing a complete object-oriented design.

## 2. Objectives

- Understand problem analysis and identify suitable classes and objects.
- Select appropriate Java data types and data structures.
- Implement decision-making and looping logic.
- Design classes, objects and constructors.
- Understand static members and object life cycles.
- Implement inheritance and method overriding.
- Demonstrate compile-time and runtime polymorphism.
- Apply abstraction using abstract classes and interfaces.
- Enforce encapsulation using packages and access modifiers.
- Implement, test and evaluate an object-oriented application.
- Adapt the system to accommodate new requirements.

## 3. Task-wise Description

### Task 1 – Understand the Problem and Identify the Objects

Analysed the Campus Smart-Parking System and identified candidate classes, their responsibilities, inputs, outputs, business rules and assumptions.

Compared object-oriented design with the parallel-array approach using time and space complexity.

**Concepts covered:** Problem analysis, object identification, class responsibilities, business rules, assumptions and Big-O notation.

**Deliverable:** `Task1_Problem_Analysis.pdf`

### Task 2 – Choose the Data Representation

Selected appropriate Java data types and a jagged two-dimensional array for representing parking floors and slots.

Analysed input handling, type conversion, integer division, precision risks and alternative data representations.

**Concepts covered:** Primitive data types, arrays, jagged arrays, Scanner, type conversion, casting and complexity analysis.

**Deliverable:** `Task2_Data_Representation_Report.pdf`

### Task 3 – Design the Control Logic

Designed and implemented parking-slot allocation and release operations using nested loops and conditional statements.

Completed the `ParkingFacility` class and analysed an alternative approach using free-slot counters and stacks.

**Concepts covered:** If-else, nested loops, return statements, validation, allocation logic, release logic and pseudocode.

**Files:** `ParkingFacility.java`, `Task3_Control_Logic.pdf`

### Task 4 – Design Classes, Objects and Constructors

Designed the classes required for the parking system and demonstrated object creation using default, parameterised and overloaded constructors.

Explored constructor chaining, the `this` keyword, copy-constructor decisions and stack and heap memory.

**Concepts covered:** Classes, objects, constructors, constructor overloading, `this`, constructor chaining, references, stack and heap memory.

**Main file:** `Task4Demo.java`

### Task 5 – Apply Static Members and the Object Life Cycle

Demonstrated the difference between static and instance members using ticket numbering and parking occupancy.

Analysed the life cycle of parking-ticket objects, object passing and returning, and garbage-collection eligibility.

**Concepts covered:** Static variables, static methods, instance variables, static initialization, object references and garbage collection.

**Main file:** `Task5Demo.java`

### Task 6 – Design the Inheritance Hierarchy

Designed an inheritance hierarchy for the Campus Smart-Parking System, identifying superclasses, subclasses and their relationships.

Explained constructor execution order, constructor chaining and the use of the `super` keyword. Compared inheritance with composition.

**Concepts covered:** Inheritance, superclass, subclasses, single inheritance, hierarchical inheritance, multilevel inheritance, `super` and UML class diagrams.

**Deliverables:** `Task06_Report.pdf`, `UML_Class_Diagram.png`

### Task 7 – Override and Specialise Behaviour

Implemented specialised vehicle behaviour using method overriding and runtime polymorphism.

Demonstrated the `@Override` annotation, appropriate applications of the `final` keyword and overriding methods inherited from the Object class.

**Concepts covered:** Method overriding, runtime polymorphism, `final`, `toString()`, `equals()` and `hashCode()`.

**Main file:** `Task07.java`

### Task 8 – Apply Polymorphism

Demonstrated compile-time and runtime polymorphism through method overloading, method overriding, upcasting and dynamic method dispatch.

Implemented the `Chargeable` interface for electric vehicles to calculate additional charging fees.

**Concepts covered:** Method overloading, method overriding, upcasting, dynamic method dispatch, interfaces and `instanceof`.

**Main file:** `FeeDemo.java`

### Task 9 – Introduce Abstraction

Applied abstraction to separate common vehicle behaviour from specialised implementations.

Used an abstract `Vehicle` class and multiple interfaces to represent optional capabilities and payment-related behaviour.

**Concepts covered:** Abstract classes, abstract methods, interfaces, multiple interface implementation, default methods and static interface methods.

**Main file:** `AbstractionDemo.java`

### Task 10 – Enforce Encapsulation through Packages and Access Modifiers

Organised the parking application into separate Java packages to improve modularity and maintainability.

Applied access modifiers, private fields, public getters, validating setters and wrapper classes to protect and manage object data.

**Concepts covered:** Encapsulation, packages, access modifiers, getters, setters, wrapper classes, autoboxing and unboxing.

**Package structure:**
- `parking.model`
- `parking.service`
- `parking.app`

**Main file:** `parking/app/Main.java`

### Task 11 – Implement, Test and Defend the Design

Implemented an object-oriented Campus Smart-Parking System integrating the concepts developed in the previous tasks.

The implementation supports vehicle entry and exit, ticket generation, slot allocation, fee calculation, monthly-pass validation and invalid-operation handling.

**Concepts covered:** Inheritance, abstraction, polymorphism, encapsulation, jagged arrays, ticket management, validation and exception handling.

**Main file:** `ParkingSystemDemo.java`

### Task 12 – Re-evaluate and Adapt the Design

Evaluated and enhanced the existing parking system to accommodate additional requirements and vehicle categories.

Extended the system to support e-bikes, delivery vans, visitor one-day passes and a five-floor facility with unequal parking capacities.

**Concepts covered:** Design evaluation, inheritance, interfaces, strategy-based pricing, HashMap, jagged arrays, LocalDate, LocalDateTime and exception handling.

**Main file:** `Task12DesignChallenge.java`

---

## 4. Technologies and Tools Used

| Technology / Tool | Purpose |
|---|---|
| Java | Programming language |
| JDK | Compilation and execution |
| Visual Studio Code | Source-code development |
| Command Prompt / Terminal | Compilation and execution |
| Mermaid Live Editor | UML class diagrams |
| PDF / Document Editor | Preparation of task reports |
| Git and GitHub | Version control and assignment submission |

## 5. Repository Structure

```text
CSA0919-192525148-Programming-in-Java-Assignments/
│
└── Assignment 1/
    │
    ├── README.md
    │
    ├── Task01/
    │   ├── README.md
    │   └── Task1_Problem_Analysis.pdf
    │
    ├── Task02/
    │   ├── README.md
    │   └── Task2_Data_Representation_Report.pdf
    │
    ├── Task03/
    │   ├── README.md
    │   ├── ParkingFacility.java
    │   ├── Task3_Control_Logic.pdf
    │   └── Java Task03 output.png
    │
    ├── Task04/
    │   ├── README.md
    │   ├── Car.java
    │   ├── Chargeable.java
    │   ├── ElectricCar.java
    │   ├── MonthlyPass.java
    │   ├── ParkingTicket.java
    │   └── Task4Demo.java
    │
    ├── Task05/
    │   ├── README.md
    │   ├── Vehicle.java
    │   ├── ParkingTicket.java
    │   ├── ParkingSession.java
    │   ├── ExitReceipt.java
    │   ├── ParkingFacility.java
    │   ├── ParkingService.java
    │   ├── Task5Demo.java
    │   └── output.txt
    │
    ├── Task06/
    │   ├── Task06_Report.pdf
    │   └── UML_Class_Diagram.png
    │
    ├── Task07/
    │   ├── README.md
    │   ├── Task07.java
    │   ├── Task07_Report.pdf
    │   └── Task07_Output.png
    │
    ├── Task08/
    │   ├── README.md
    │   ├── FeeDemo.java
    │   ├── Task08_Report.pdf
    │   └── Task08_Output.png
    │
    ├── Task09/
    │   ├── README.md
    │   ├── AbstractionDemo.java
    │   ├── Task09_Report.pdf
    │   └── output.txt
    │
    ├── Task10/
    │   ├── README.md
    │   ├── Task10_Report_Fresh.pdf
    │   ├── output.txt
    │   └── parking/
    │       ├── model/
    │       ├── service/
    │       └── app/
    │
    ├── Task11/
    │   ├── README.md
    │   ├── ParkingSystemDemo.java
    │   ├── Task11_Report.pdf
    │   └── output.txt
    │
    └── Task12/
        ├── README.md
        ├── Task12DesignChallenge.java
        ├── Task12_Report.pdf
        └── output.txt
```

*Note: The structure above summarises the main files. Refer to each task folder for its complete source files, reports and supporting materials.*

## 6. Compilation and Execution

Java programs can be compiled and executed using the JDK through the terminal or Visual Studio Code.

### General execution procedure

1. Open the required task folder.
2. Open a terminal in that folder.
3. Compile the Java source file or files.
4. Execute the class containing the `main()` method.
5. Verify the program output.

### Example commands

For a single Java file:

```bash
javac FileName.java
java ClassName
```

For multiple Java files in a folder without package declarations:

```bash
javac *.java
java MainClassName
```

For Task 10, which uses packages:

```bash
cd Task10

javac -d out parking/model/Vehicle.java parking/model/Car.java parking/model/ParkingSlot.java parking/model/ParkingTicket.java parking/service/ParkingService.java parking/app/Main.java

java -cp out parking.app.Main
```

The main class and compilation command may differ depending on the task.

## 7. Learning Outcomes

Through this assignment, the following learning outcomes were addressed:

- Understanding and analysing real-world programming problems.
- Identifying suitable classes, objects, attributes and methods.
- Selecting efficient data structures for storing and managing information.
- Applying Java decision-making and looping constructs.
- Understanding object creation, constructors and memory management.
- Implementing inheritance, polymorphism and abstraction.
- Applying encapsulation and modular programming.
- Developing and testing Java applications.
- Analysing time and space complexity.
- Evaluating existing designs and adapting them to changing requirements.

## 8. Conclusion

The Campus Smart-Parking System assignment provides practical experience in applying Java programming and object-oriented programming principles to a real-world problem.

Across the 12 tasks, the system progresses from problem analysis and data representation to class design, inheritance, polymorphism, abstraction, encapsulation and complete application development.

The final tasks demonstrate how an existing software design can be tested, evaluated and extended to support additional requirements without unnecessarily changing its original structure.

Overall, this assignment strengthened the understanding of Java fundamentals, object-oriented design, software development practices, program validation and design evaluation.

---

**Course:** CSA0919 – Programming in Java  
**Assignment:** 1 – Campus Smart-Parking System  
**Student:** Juhi Surin  
**Register Number:** 192525148
