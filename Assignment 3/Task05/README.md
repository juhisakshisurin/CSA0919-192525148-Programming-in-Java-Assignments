# Assignment 3 – Task 5

## Student Details
- **Name:** Juhi Surin
- **Register Number:** 192525148
- **Course:** CSA0919 Programming in Java

## Task
**Q5 – Race Conditions in a Cinema Seat-Booking System**  
6 Marks | Bloom's Level: K4 – Analyse

## Files
- `SeatBooking.java` – exact Complete the Code program with TODO 1 implemented as a synchronized method.
- `SeatBookingDemo.java` – unsynchronized race reproduction, synchronized method and block versions, 10 repeated race trials, and timing of 10,000 bookings.
- `AtomicSeatBooking.java` – alternative `AtomicBoolean.compareAndSet()` approach.
- `Task5_Report.pdf` – analysis report; personal details intentionally omitted.
- `test-cases.md` – test plan, interleaving explanation and evidence checklist.
- `run_output.txt` – sample output from local execution.

## Java version
Run `java -version` and record the version installed on your computer.

## Compile and run
Open PowerShell in the `Task05` folder.

### Exact assignment program
```powershell
javac SeatBooking.java
java SeatBooking
```
Expected output:
```text
Seats sold: 10
```

### Race and synchronization experiments
```powershell
javac SeatBookingDemo.java
java SeatBookingDemo
```

### AtomicBoolean alternative
```powershell
javac AtomicSeatBooking.java
java AtomicSeatBooking
```

## Measurement note
The timing values in `run_output.txt` are only sample measurements from the environment where the files were prepared. Run the program on your own computer several times and record your actual times. The unsynchronized test uses a latch to make the race reproducible: all 100 threads first observe the relevant seat as free, then are released to perform the booking. This is a controlled demonstration of the check-then-act race, not a production booking design.

## GitHub submission
Use repository `CSA09-192525148-Assignment3`. Add this `Task05` folder, include your own terminal screenshots and actual repeated timing measurements, and commit Task05 separately.
