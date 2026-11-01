# Task 3 – Design the Control Logic

## Programming in Java – Assignment 1 (CO1)

| **Field** | **Details** |
|---|---|
| Assignment Title | Design, Implement and Validate an Object-Oriented Model for a Campus Smart-Parking System |
| Student Name | Juhi Surin |
| Register Number | 192525148 |
| Course | CSA0919 – Programming in Java |
| Task Number | Task 3 |
| Task Title | Design the Control Logic |
| Bloom's Level | K4 – Analyse |
| Java Version | Java 27 |

---

## 1. Objective

To design the decision-making and looping logic for the two core parking operations, allocating a free parking slot and computing the parking fee, using conditional statements, loops, `break`, `continue` and `return`; to write pseudocode for both operations; to complete and execute the supplied `ParkingFacility` class; and to analyse an alternative design that uses free-slot counts and stacks.

## 2. Allocation and Release Logic

**Allocation.** `slots[floor][slot]` holds a vehicle number, or `null` if the slot is free. `allocate()` searches floor by floor and slot by slot, floors in increasing order and the slots of each floor in increasing order. At the first `null` element it stores the vehicle number and returns `{floor, slot}` immediately, so exactly one slot is filled and it is always the first free slot. If every slot has been examined and none is free, the method returns `null`. The caller must check for `null` before using the indexes.

**Release.** `release()` validates the floor first, then the slot against the length of that floor, then checks that the slot is not already empty. Each failed check returns `false`. Because the floor is validated before `slots[floor]` is read, no `ArrayIndexOutOfBoundsException` can occur. A valid occupied slot is set to `null` and `true` is returned.

The supplied `allocate()` uses `return` only; it does not use `break` or `continue`.

## 3. TODO Implementations

Only the two TODO sections of the supplied class were completed. The class name, field, constructor, method signatures, comments and `main()` method are as supplied.

**TODO 1 – `allocate()`**

```java
for (int floor = 0; floor < slots.length; floor++) {
    for (int slot = 0; slot < slots[floor].length; slot++) {
        if (slots[floor][slot] == null) {
            slots[floor][slot] = vehicleNo;
            return new int[]{floor, slot};
        }
    }
}

return null;
```

**TODO 2 – `release()`**

```java
if (floor < 0 || floor >= slots.length) {
    return false;
}
if (slot < 0 || slot >= slots[floor].length) {
    return false;
}
if (slots[floor][slot] == null) {
    return false;
}
slots[floor][slot] = null;
return true;
```

## 4. Pseudocode and Fee-Calculation Summary

The report contains pseudocode for both operations: **Algorithm 1 – Allocate Parking Slot** (nested FOR loops, IF test for NULL, RETURN of the floor and slot, RETURN NULL when full) and **Algorithm 2 – Calculate Parking Fee** (with a helper, SlabFee).

Fee-calculation summary:

- A student or staff member with a valid monthly pass pays no parking fee. A visitor, or a student or staff member without a valid pass, pays the full fee.
- Duration is converted to billable hours with `(durationMinutes + 59) / 60`, so every started hour is charged in full and zero minutes costs nothing.
- Fee slabs (first hour, each additional hour) and a daily maximum are named in the assignment only as examples. No hourly rates or daily maximum are prescribed, so none are treated as requirements. Slab amounts and a daily maximum are configuration values, and a daily maximum is applied only if one is defined.
- The vehicle type selects the rate with a `switch`; `if`–`else` handles the pass check, zero hours and the optional daily maximum.
- EV charging is calculated separately (units charged × charging rate) and is added to the parking fee only in the total.
- The Java code for the fee in the report is illustrative and is not part of `ParkingFacility.java`.

## 5. Alternative Concept and Complexity Summary

The alternative keeps a count of free slots per floor and a stack of free slot numbers for each floor. Allocation scans the floor counts for the first floor with a count above zero, pops a slot number from that floor's stack, marks the slot occupied and decreases the count; if all counts are zero it returns `null`. Release validates the position, rejects an already-empty slot (preventing duplicate pushes), pushes the slot number back and increases the count. This alternative is analysed only and is not implemented in `ParkingFacility.java`.

Let `F` be the number of floors and `S` the maximum number of slots on any floor.

| Criterion | Original scanning | Free count and stack |
|---|---|---|
| Data structures | `slots` array | `slots` array, `F` counts, `F` stacks |
| Allocation time | O(F × S) worst case | O(F) (linear scan of counts, O(1) pop) |
| Release time | O(1) | O(1) |
| Auxiliary space | O(1) | O(F × S) for stacks + O(F) for counts = O(F × S) |

The occupancy array itself needs O(F × S) space in both designs. Allocation in the stack design is not O(1) because the floor counts are still scanned linearly. A stack reuses the most recently released slot first, so it can differ from the lowest-free-slot rule of scanning. Scanning is the more suitable design for this assignment because of its simplicity and because the facility has at most 500 slots. No timing or memory measurements are reported.

## 6. Java Version

Java 27

## 7. Compile and Execution Commands

Save `ParkingFacility.java` in the `Task03` folder, open that folder in VS Code, open the terminal (Terminal > New Terminal) and run:

```bash
javac ParkingFacility.java
java ParkingFacility
```

## 8. Expected Output

The expected output given in the assignment is:

```text
[0, 0]
[0, 1]
[1, 0]
null
true false false
[0, 1]
```

Explanation: the first three vehicles take floor 0 slot 0, floor 0 slot 1 and floor 1 slot 0. The fourth finds no free slot, so `null` is returned. In the next line, the first `release(0, 1)` succeeds, the second fails because the slot is already empty, and `release(5, 0)` is rejected because floor 5 does not exist. The fourth vehicle is then allocated to the freed slot `[0, 1]`.

## 9. Result

The implementation is designed to demonstrate first-slot allocation, release of occupied slots, rejection of invalid floor and slot indexes, rejection of releasing an already empty slot, correct handling of a full facility, and reallocation after a release.

**Expected output** is the output given in the assignment (Section 8). **Verified execution** is recorded only after the program has been run; update the checklist below after running it.

- [ ] Program compiled with `javac ParkingFacility.java` on Java 27
- [ ] Program executed with `java ParkingFacility`
- [ ] Actual output matched the expected output line by line
- [ ] Screenshot saved as `output.png`

## 10. Files Included

- `README.md` – Task overview, implementation summary, execution instructions and result.
- `Task3_Control_Logic.pdf` – Complete written analysis, pseudocode, code explanation and alternative-concept complexity analysis.
- `ParkingFacility.java` – Completed Java source code.
- `output.png` – Screenshot of the actual compilation and execution (to be added after running the program).

