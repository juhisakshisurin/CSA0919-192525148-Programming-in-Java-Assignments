package parking.model;

public class ParkingTicket {
    private final long ticketNumber;
    private final Vehicle vehicle;
    private int durationMinutes;

    // Wrapper classes used for parking coordinates
    private Integer floorNumber;
    private Integer slotNumber;

    public ParkingTicket(long ticketNumber, Vehicle vehicle) {
        if (ticketNumber <= 0 || vehicle == null) {
            throw new IllegalArgumentException(
                "Invalid ticket details."
            );
        }

        this.ticketNumber = ticketNumber;
        this.vehicle = vehicle;
        this.durationMinutes = 0;
    }

    public long getTicketNumber() {
        return ticketNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes < 0) {
            throw new IllegalArgumentException(
                "Parking duration cannot be negative."
            );
        }

        this.durationMinutes = durationMinutes;
    }

    public void setParkingLocation(int floor, int slot) {
        if (floor < 0 || slot < 0) {
            throw new IllegalArgumentException(
                "Invalid parking location."
            );
        }

        this.floorNumber = floor;
        this.slotNumber = slot;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public Integer getSlotNumber() {
        return slotNumber;
    }

    public boolean hasParkingLocation() {
        return floorNumber != null && slotNumber != null;
    }
}
