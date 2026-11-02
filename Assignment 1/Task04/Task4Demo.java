import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task4Demo {
    public static void main(String[] args) {
        // 1. parameterised constructor
        ParkingTicket t1 = new ParkingTicket(1001L, "TN01AB1111", 0, 1,
                LocalDateTime.of(2026, 11, 2, 9, 15));
        System.out.println(t1);

        // 2. overloaded constructor taking a Vehicle (chains to the first)
        Vehicle car = new Car("TN02CD2222");
        ParkingTicket t2 = new ParkingTicket(1002L, car, 1, 0,
                LocalDateTime.of(2026, 11, 2, 9, 30));
        System.out.println(t2);

        // 3. overloaded constructor that uses the current time
        ParkingTicket t3 = new ParkingTicket(1003L, "TN03EF3333", 0, 0);
        System.out.println("entry time set: " + (t3.getEntryTime() != null));

        // 4. two references to the same object
        ParkingTicket alias = t1;
        System.out.println("same object: " + (alias == t1));
        t1.close(LocalDateTime.of(2026, 11, 2, 10, 16), 60.0);
        System.out.println("open through alias: " + alias.isOpen());
        System.out.println(alias);

        // 5. invalid objects are rejected during construction
        try {
            new ParkingTicket(0L, "TN04GH4444", 0, 0,
                    LocalDateTime.of(2026, 11, 2, 9, 0));
        } catch (IllegalArgumentException e) {
            System.out.println("rejected: " + e.getMessage());
        }

        // 6. constructors in the Vehicle classes and in MonthlyPass
        Vehicle bike = new TwoWheeler("TN05IJ5555");
        Vehicle ev = new ElectricCar("TN06KL6666");
        System.out.println(bike.getRegistrationNumber() + " "
                + bike.ratePerHour() + " | "
                + ev.getRegistrationNumber() + " " + ev.ratePerHour());

        MonthlyPass pass = new MonthlyPass("P-2026-001",
                LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 30));
        System.out.println(pass.isValidOn(LocalDate.of(2026, 11, 2)) + " "
                + pass.isValidOn(LocalDate.of(2026, 12, 1)));
        try {
            new MonthlyPass("P-2026-002",
                    LocalDate.of(2026, 11, 30), LocalDate.of(2026, 11, 1));
        } catch (IllegalArgumentException e) {
            System.out.println("rejected: " + e.getMessage());
        }
    }
}
