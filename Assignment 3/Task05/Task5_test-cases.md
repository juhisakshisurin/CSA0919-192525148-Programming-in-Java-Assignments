# Task 5 – Test Cases and Analysis

| Test | Implementation | Expected result |
|---|---|---|
| 1 | Unsynchronized with 100 concurrent users | Controlled race: multiple users can pass the free-seat check before any marks it booked; sold count can exceed 10 |
| 2 | Synchronized method | Exactly 10 successful bookings |
| 3 | Synchronized block | Exactly 10 successful bookings |
| 4 | Exact TODO program | `Seats sold: 10` |
| 5 | AtomicBoolean compareAndSet | First attempt on a seat returns true; second returns false |
| 6 | Performance | Record elapsed time for 10,000 booking attempts for method and block versions |

## Reproducing the race
For a seat, the unsafe operation is:
1. Thread A checks `booked[seat]` and reads `false`.
2. Thread B checks the same seat and also reads `false`.
3. Thread A writes `true` and increments `bookingsMade`.
4. Thread B writes `true` and increments `bookingsMade`.
5. Both report success for the same seat. The array still contains only one `true`, but the successful-booking counter has been incremented twice.

The exact critical section is the combined check and update:
```java
if (!booked[seat]) {
    booked[seat] = true;
    bookingsMade++;
}
```
It must execute atomically relative to competing threads.

## Synchronization strategies
- **Synchronized method:** locks the current `SeatBooking` object for the entire method.
- **Synchronized block:** locks the same object (`this`) only around the check-and-book critical section. Validation that does not access shared mutable state can occur outside the block.
- Both versions use the same intrinsic monitor lock when the block is `synchronized (this)`. If both methods are called on the same object, they contend for the same lock.
- **Static synchronized:** locks the `Class` object rather than an individual instance. It is useful if multiple `SeatBooking` instances represent shared global seat inventory and must coordinate on one class-wide lock. For independent screens with independent arrays, instance synchronization is more appropriate.

## Cinema with 20 screens
Use one booking-state object and lock per screen, so unrelated screens can accept bookings concurrently. A synchronized method is simple and correct. A synchronized block around only the critical section can reduce lock hold time if meaningful validation or non-shared work is performed outside the lock. Do not share one global lock across all 20 independent screens unless a cross-screen invariant requires it.

## Alternative: AtomicBoolean[]
Each seat has an AtomicBoolean. `compareAndSet(false, true)` atomically changes a free seat to booked. Only one concurrent caller can succeed. The array requires O(s) references and O(s) AtomicBoolean objects for s seats. Each booking attempt is O(1) expected time; under contention, losing callers may retry or return false.

## Other alternatives requested
- **ReentrantLock:** call `lock()`, perform check/update, and always `unlock()` in `finally`; O(1) extra lock object per protected resource. `tryLock()` can be used to avoid waiting indefinitely but needs a retry/failure policy.
- **ConcurrentHashMap:** `putIfAbsent(seatId, booking)` atomically inserts a booking only if no mapping exists. Expected O(1) per attempt, with O(s) map storage for s booked seats plus map overhead.

## Complexity
For s seats, the boolean array uses O(s) space. Each check-and-book attempt is O(1). For b booking attempts, total work is O(b), ignoring lock contention. Contention increases waiting time and reduces throughput; synchronized method holds the monitor across the entire method, while the block can hold it for just the critical section. Both require O(1) additional synchronization state per screen (monitor intrinsic to object); AtomicBoolean[] uses O(s) per-seat atomic objects/references.

## Evidence checklist
- [ ] Unsynchronized output over 10 runs.
- [ ] Synchronized method output: 10 seats sold.
- [ ] Synchronized block output: 10 seats sold.
- [ ] Timing results for 10,000 bookings (repeat measurements on your machine).
- [ ] Screenshot of the interleaving / race explanation or debugger view.
- [ ] AtomicBoolean alternative output.
