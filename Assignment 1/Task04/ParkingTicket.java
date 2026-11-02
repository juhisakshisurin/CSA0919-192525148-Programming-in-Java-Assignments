import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingTicket {
    private final long ticketNumber;
    private final String registrationNumber;
    private final int floor;
    private final int slot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;   // null while the ticket is open
    private double fee;               // set when the ticket is closed

    // Parameterised constructor: validates and initialises every field
    public ParkingTicket(long ticketNumber, String registrationNumber,
                         int floor, int slot, LocalDateTime entryTime) {
        if (ticketNumber <= 0) {
            throw new IllegalArgumentException(
                    "ticket number must be positive");
        }
        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "registration number is required");
        }
        if (floor < 0 || slot < 0) {
            throw new IllegalArgumentException(
                    "floor and slot must not be negative");
        }
        if (entryTime == null) {
            throw new IllegalArgumentException("entry time is required");
        }
        this.ticketNumber = ticketNumber;
        this.registrationNumber = registrationNumber;
        this.floor = floor;
        this.slot = slot;
        this.entryTime = entryTime;
    }

    // Overloaded constructor: takes a Vehicle, not a registration number
    public ParkingTicket(long ticketNumber, Vehicle vehicle,
                         int floor, int slot, LocalDateTime entryTime) {
        this(ticketNumber, registrationOf(vehicle), floor, slot, entryTime);
    }

    // Overloaded constructor: entry time is the current time (minutes)
    public ParkingTicket(long ticketNumber, String registrationNumber,
                         int floor, int slot) {
        this(ticketNumber, registrationNumber, floor, slot,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    private static String registrationOf(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicle is required");
        }
        return vehicle.getRegistrationNumber();
    }

    public boolean isOpen() {
        return exitTime == null;
    }

    public void close(LocalDateTime exitTime, double fee) {
        if (!isOpen()) {
            throw new IllegalStateException("ticket is already closed");
        }
        if (exitTime == null || exitTime.isBefore(entryTime)) {
            throw new IllegalArgumentException(
                    "exit time must not be before entry time");
        }
        if (fee < 0) {
            throw new IllegalArgumentException("fee must not be negative");
        }
        this.exitTime = exitTime;
        this.fee = fee;
    }

    public long getTicketNumber() {
        return ticketNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public int getFloor() {
        return floor;
    }

    public int getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getFee() {
        return fee;
    }

    @Override
    public String toString() {
        return "Ticket " + ticketNumber + " " + registrationNumber
                + " floor " + floor + " slot " + slot + " entry " + entryTime
                + (isOpen() ? " (open)"
                        : " exit " + exitTime + " fee " + fee);
    }
}
