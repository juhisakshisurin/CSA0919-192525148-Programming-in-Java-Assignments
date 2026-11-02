import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class ParkingTicket {
    /** One counter for the whole class, shared by every facility. Direct initialisation is enough. */
    private static final AtomicLong SEQUENCE = new AtomicLong(0);

    private final long ticketNo;             // each ticket keeps its own copy of its number
    private final Vehicle vehicle;
    private final String facilityName;
    private final int floor;
    private final int slot;
    private final LocalDateTime entryTime;
    private boolean closed;

    public ParkingTicket(Vehicle vehicle, String facilityName, int floor, int slot, LocalDateTime entryTime) {
        this.ticketNo = SEQUENCE.incrementAndGet();   // atomic read-modify-write
        this.vehicle = vehicle;
        this.facilityName = facilityName;
        this.floor = floor;
        this.slot = slot;
        this.entryTime = entryTime;
    }

    public long getTicketNo() { return ticketNo; }
    public Vehicle getVehicle() { return vehicle; }
    public String getFacilityName() { return facilityName; }
    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public boolean isClosed() { return closed; }
    public void close() { closed = true; }

    @Override public String toString() {
        return "Ticket#" + ticketNo + "(" + vehicle.getRegNo() + ", " + facilityName
                + " F" + floor + "-S" + slot + (closed ? ", CLOSED" : ", ACTIVE") + ")";
    }
}
