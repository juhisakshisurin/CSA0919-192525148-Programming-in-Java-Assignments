# Task 5 – Apply static Members and the Object Life Cycle

| | |
|---|---|
| **Course** | CSA0919 – Programming in Java |
| **Assignment** | Assignment 1 – Campus Smart-Parking System |
| **Task** | Task 5 – Bloom's Level K4 – Analyse |
| **Student** | Juhi Surin |
| **Register Number** | 192525148 |
| **Java version** | Source uses syntax valid in Java 21 and later (including Java 27). Preparation run was on OpenJDK 21.0.12. |

## 1. Objective
Identify class-level (static) and object-level (instance) data and behaviour in the parking system, trace the life cycle of a `ParkingTicket`, and analyse how objects are passed to and returned from methods.

## 2. Static variables used
| Variable | Class | Purpose |
|---|---|---|
| `private static final AtomicLong SEQUENCE` | `ParkingTicket` | One ticket sequence shared by North Gate and South Gate; `incrementAndGet()` is atomic. |
| `private static final Map<String,Integer> RATE_PER_HOUR` | `Vehicle` | Shared constant rate table. |

Each ticket copies the generated value into its own `final long ticketNo`.

**Occupancy uses two counters in `ParkingFacility`:**

| Field | Kind | Meaning | Getter |
|---|---|---|---|
| `private int occupancy` | instance | Vehicles parked in **this** facility (North and South count independently) | `getOccupancy()` |
| `private static int totalOccupancy` | static | Vehicles parked across **all** facilities (one shared value) | `static getTotalOccupancy()` |

`allocate()` increments both counters only when a free slot is found; `release()` decrements both only for a valid, occupied slot. A failed allocation (full facility) or an invalid/repeated release changes neither counter.

## 3. Static methods and initialization block
- `Vehicle.normalizeRegNo(String)` – strips spaces/hyphens, upper-cases.
- `Vehicle.billableHours(long)` – integer ceiling `(minutes + 59) / 60`.
- `Vehicle.rateFor(String)` – rate lookup.
- `static { ... }` in `Vehicle` – fills the rate table once at class initialisation. A static block is not needed for the `AtomicLong` counter because direct initialisation is sufficient.
- A static method cannot use instance variables directly because it has no `this`; it needs an object reference.

## 4. Static versus instance members
| Aspect | Static | Instance |
|---|---|---|
| Ownership | Class | One object |
| Copies | One per class | One per object |
| Access | `ClassName.member` | `objectRef.member` |
| Lifetime | Class initialisation until unload | Object creation until collected |
| Parking examples | Ticket sequence, total occupancy (all facilities), rate table | Per-facility occupancy, registration number, ticket number |

## 5. ParkingTicket object life cycle
1. `ParkingService.enter()` allocates a slot and creates the ticket.
2. Constructor takes the number from `SEQUENCE.incrementAndGet()`.
3. Ticket is linked to its vehicle; a `ParkingSession` is stored in `activeSessions`.
4. Active period – ticket reachable via the session map and the caller's variable.
5. `exit()` removes the session, releases the slot, calls `ticket.close()` and returns an `ExitReceipt`.
6. Removing the session removes only one reference.
7. The ticket becomes **eligible** for garbage collection only when no strong reference remains (for example the caller's variable or an `ExitReceipt` that holds it). The JVM decides when memory is reclaimed; Java does not destroy the object immediately at exit, and `System.gc()` is only a request.

Diagrams are in the PDF (Figures 1 and 2).

## 6. Object passing and returning
Java passes the reference **by value**. `updateOwner(Vehicle)` changes the shared object's state, while reassigning its local parameter does not affect the caller's variable. `enter(...)` returns a `ParkingTicket`; `exit(...)` returns an `ExitReceipt`.

## 7. North Gate versus South Gate counter
| Design | North, South, North | Result |
|---|---|---|
| Static counter | 1, 2, 3 | Globally unique within one run |
| Instance counter | 1, 1, 2 | Duplicate ticket number across facilities |
| Gate prefix + local counter | N-1, S-1, N-2 | Unique as a pair |

Recommended: a single static sequence. An in-memory counter restarts after an application restart; persistent uniqueness needs durable storage.

## 8. Space complexity (k facilities, n tickets)
| Design | Counter storage | Ticket object storage |
|---|---|---|
| Static counter | O(1) | O(n) |
| Instance counter | O(k) | O(n) |

Both designs need O(n) for tickets; O(1) refers to the ticket counter only, not the whole system.

Occupancy counters are analysed separately: the per-facility `occupancy` fields need O(k) storage across k facilities, and the single static `totalOccupancy` needs O(1). These figures are independent of the ticket-counter choice.

## 9. Java source files
`Vehicle.java`, `ParkingTicket.java`, `ParkingSession.java`, `ExitReceipt.java`, `ParkingFacility.java`, `ParkingService.java`, `Task5Demo.java` (runnable). Default package. Task 1–4 class names are kept; vehicle sub-classes (Task 6+) are not introduced.

## 10. Compile and run
```bash
cd Task05
javac *.java
java Task5Demo
```
`output.txt` holds the output captured when the demo was run during preparation (key lines below). Add your own `output.png` screenshot after running it on your machine; none is included yet.

```text
North occupancy = 2, South occupancy = 1, Total occupancy = 3
North occupancy = 1, South occupancy = 1, Total occupancy = 2
release(5,0)=false, release(0,0)=false, allocate on full lot=null
North occupancy = 1, South occupancy = 1, Tiny occupancy = 0, Total occupancy = 2 (before: 2)
```

## 11. Result
Static and instance members were identified and demonstrated (static ticket sequence, static total occupancy, instance per-facility occupancy, static methods, static block). The demo shows North 2 / South 1 / Total 3, then North 1 / South 1 / Total 2 after one North Gate exit, and an invalid release or full-lot allocation leaving the total unchanged. The ticket life cycle and garbage-collection eligibility were explained, object passing and returning were demonstrated, the static, instance and gate-prefixed counters were compared for North and South Gate, and counter memory was analysed as O(1) versus O(k) with O(n) for n tickets.

## Folder contents
```text
Assignment 1/
└── Task05/
    ├── README.md
    ├── Task5_Static_Members_Object_Lifecycle.pdf
    ├── Task5_Static_Members_Object_Lifecycle.docx
    ├── *.java
    ├── output.txt
    └── output.png   (add after running on your machine)
```
