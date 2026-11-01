# Task 2 – Choose the Data Representation

## Programming in Java – Assignment 1 (CO1)

| **Field** | **Details** |
|---|---|
| Assignment Title | Campus Smart-Parking System |
| Student Name | Juhi Surin |
| Register Number | 192525148 |
| Course | CSA0919 – Programming in Java |
| Task Number | Task 2 |
| Java Version | Java 27 |

---

## 1. Objective

To select suitable Java data types and an array structure for the Campus Smart-Parking System, and to analyse type conversion, precision risks, input handling and alternative representations of floors and slots using time and space complexity.

## 2. Problem Description

The Campus Smart-Parking System manages a multi-level parking facility of 1 floor × 20 slots, 3 floors × 50 slots or 5 floors × 100 slots. Each floor has a fixed number of numbered slots. The system allocates a free slot, records the entry time, and on exit calculates the fee from the vehicle type and the parking duration.

Task 2 decides how this data is represented in Java: the data type of each field, the structure that holds floors and slots, the method of reading input, and the points where type conversion or casting can affect the fee.

## 3. Work Completed

- Chose a Java data type for each field and justified the choice.
- Compared one-dimensional, two-dimensional and jagged arrays for floors and slots, and selected a jagged array.
- Compared `Scanner` with command-line arguments, selected `Scanner`, and defined how each input is validated.
- Identified where type conversion is required and the risk of losing precision.
- Worked through an example in which integer division produces a wrong fee, and showed how the design avoids it.
- Compared the jagged array with a one-dimensional array and an `ArrayList<String[]>` for locating a slot and for space.

## 4. Data Types and Array Structure

### 4.1 Data Types

| Field | Java Type | Example |
|---|---|---|
| Slot number | `int` | `7` |
| Floor number | `int` | `1` |
| Vehicle registration number | `String` | `"TN01AB1111"` |
| Vehicle type | `enum VehicleType` | `ELECTRIC_CAR` |
| Entry time and exit time | `java.time.LocalDateTime` | `2026-11-02T09:15` |
| Parking duration | `int` (whole minutes) | `61` |
| Billable hours | `int` | `2` |
| Hourly rate | `double` | `30.0` |
| Parking fee | `double` | `60.0` |
| Ticket number | `long` | `1001` |
| Slot occupancy | `boolean`, derived from `slots[floor][slot] != null` | `true` |

`double` is acceptable for the fee because every amount is a whole number of hours multiplied by a whole-rupee rate, which `double` represents exactly. `BigDecimal` would be needed for paise, percentage tax or discounts.

### 4.2 Floors and Slots

A jagged array `String[floor][slot]` is used. The element `slots[floor][slot]` holds a vehicle registration number, or `null` if the slot is free. Each row has its own length, so floors can have different capacities without padding. Positions are validated with `floor < slots.length` and `slot < slots[floor].length` before access.

```java
int[] slotsPerFloor = {20, 50, 40};   // illustrative capacities
String[][] slots = new String[slotsPerFloor.length][];
for (int f = 0; f < slots.length; f++) {
    slots[f] = new String[slotsPerFloor[f]];   // null = free
}
slots[1][7] = "TN01AB1111";   // park on floor 1, slot 7
```

### 4.3 Input Method

`Scanner` with `nextLine()` and explicit parsing is used. Each value is validated immediately and requested again if it is invalid, which command-line arguments cannot do. Date-time input uses the pattern `uuuu-MM-dd HH:mm` with a strict resolver, so an impossible date such as `2026-02-30` is rejected.

## 5. Type Conversion, Casting and Precision

The billing rule is that every started hour is charged in full. This is the Task 1 assumption and it agrees with the assignment's explicit fee rule.

For 61 minutes at Rs 30 per hour, the correct billable duration is 2 hours and the correct fee is Rs 60.

| Expression | Hours | Fee | Verdict |
|---|---|---|---|
| `61 / 60` | 1 | Rs 30 | Wrong – integer division truncates |
| `Math.ceil(61 / 60)` | 1.0 | Rs 30 | Wrong – the division is done on ints first |
| `Math.ceil(61 / 60.0)` | 2.0 | Rs 60 | Correct (floating point) |
| `(61 + 59) / 60` | 2 | Rs 60 | Correct (integer arithmetic) |

```java
int billableHours = (durationMinutes + 59) / 60;   // valid for durationMinutes >= 0
double fee = billableHours * ratePerHour;          // 2 * 30.0 = 60.0
```

**Assumptions and risks:**

- The duration is not negative; an exit time before the entry time is rejected.
- A stay of zero minutes gives zero billable hours and a fee of Rs 0.
- `durationMinutes + 59` overflows an `int` above `Integer.MAX_VALUE - 59`, so the range is validated or an overflow-free form is used.
- The `long` result of `Duration.toMinutes()` is narrowed with `Math.toIntExact`, which throws an exception instead of silently wrapping like a plain `(int)` cast.

## 6. Alternative Concept and Complexity Analysis

The alternative representations are a one-dimensional array with `index = floor × slotsPerFloor + slot`, and an `ArrayList<String[]>`.

- The one-dimensional formula assumes the same number of slots on every floor. Unequal floors need cumulative offsets or a mapping table.
- `ArrayList<String[]>` supports rows of different lengths but needs two index checks: the floor against `rows.size()` and the slot against `rows.get(floor).length`.

### 6.1 Code Snippets

```java
// one-dimensional array, uniform floors
String[] flat = new String[floors * slotsPerFloor];
flat[floor * slotsPerFloor + slot] = regNo;

// ArrayList of rows (requires import java.util.ArrayList)
ArrayList<String[]> rows = new ArrayList<>();
rows.add(new String[20]);   // one row per floor
```

These are illustrative excerpts, not standalone Java programs.

### 6.2 Complexity Comparison

Let `F` represent the number of floors and `N` the total number of slots. Every floor is assumed to have at least one slot, so `F ≤ N`.

| Criterion | Jagged Array | One-Dimensional Array | `ArrayList<String[]>` |
|---|---|---|---|
| Unequal floor capacities | Handled directly | Needs offsets or a mapping table | Handled directly |
| Access by known floor and slot | O(1) | O(1) | O(1) |
| Locate a free slot by scanning | O(N) worst case, O(1) best case | O(N) worst case, O(1) best case | O(N) worst case, O(1) best case |
| Space | O(N + F) | O(N), or O(N + F) with offsets | O(N + F) |

Direct access and free-slot search have the same asymptotic complexity in all three representations, so none is claimed to be asymptotically faster. The differences are in constant factors, in the handling of unequal floors and in how easily floors can be added. This is an analytical comparison; no timing or memory measurements are reported.

## 7. Files Included

- `Task2_Data_Representation_Report.pdf` – PDF contains the complete Task 2 report: data types, floor-and-slot representation, input handling, type-conversion analysis, alternative representations and complexity comparison.
- `README.md` – Documents the scope, decisions, illustrative code snippets and result of Task 2.

## 8. Result

The data types, the jagged-array structure for floors and slots, and the `Scanner` input method were selected and justified. The analysis showed that integer division would bill 61 minutes as Rs 30 instead of Rs 60, and that ceiling division, `(61 + 59) / 60 = 2`, avoids the error for non-negative durations. The comparison showed that direct access is O(1) and a free-slot search is O(N) in the worst case for all three representations, with space growing linearly with the number of slots.

## 9. Execution Requirement

Task 2 is primarily a data-representation and analysis task. The assignment does not specify a separate executable Java program for this task. The code snippets illustrate the chosen and alternative representations and are not complete programs.

Java implementation and execution evidence will be provided in the task folders for tasks that explicitly require programming.

