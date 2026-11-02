import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ParkingService {
    private final ParkingFacility facility;
    private final Map<String, ParkingSession> activeSessions = new HashMap<>();

    public ParkingService(ParkingFacility facility) { this.facility = facility; }

    /** Receives a Vehicle reference, returns a ParkingTicket reference (or null if full). */
    public ParkingTicket enter(Vehicle vehicle, LocalDateTime entryTime) {
        if (activeSessions.containsKey(vehicle.getRegNo())) {
            throw new IllegalStateException("Vehicle already parked: " + vehicle.getRegNo());
        }
        int[] pos = facility.allocate(vehicle.getRegNo());
        if (pos == null) return null;
        ParkingTicket ticket = new ParkingTicket(vehicle, facility.getName(), pos[0], pos[1], entryTime);
        activeSessions.put(vehicle.getRegNo(), new ParkingSession(ticket, facility));
        return ticket;
    }

    /** Closes the ticket, frees the slot, removes the session and returns an ExitReceipt. */
    public ExitReceipt exit(String regNo, LocalDateTime exitTime) {
        ParkingSession session = activeSessions.remove(Vehicle.normalizeRegNo(regNo)); // removes one reference
        if (session == null) throw new IllegalStateException("No active session for " + regNo);
        ParkingTicket ticket = session.getTicket();
        facility.release(ticket.getFloor(), ticket.getSlot());
        ticket.close();
        long minutes = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
        long fee = Vehicle.billableHours(minutes) * Vehicle.rateFor(ticket.getVehicle().getType());
        return new ExitReceipt(ticket, exitTime, minutes, fee);
    }

    public int activeCount() { return activeSessions.size(); }
    public ParkingFacility getFacility() { return facility; }
}
