public class ParkingSession {
    private final ParkingTicket ticket;
    private final ParkingFacility facility;

    public ParkingSession(ParkingTicket ticket, ParkingFacility facility) {
        this.ticket = ticket;
        this.facility = facility;
    }

    public ParkingTicket getTicket() { return ticket; }
    public ParkingFacility getFacility() { return facility; }
}
