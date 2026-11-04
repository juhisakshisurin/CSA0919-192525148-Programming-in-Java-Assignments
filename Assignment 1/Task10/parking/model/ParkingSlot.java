package parking.model;

public class ParkingSlot {
    private final int floorNumber;
    private final int slotNumber;
    private String occupiedBy;

    public ParkingSlot(int floorNumber, int slotNumber) {
        if (floorNumber < 0 || slotNumber < 0) {
            throw new IllegalArgumentException(
                "Invalid floor or slot number."
            );
        }

        this.floorNumber = floorNumber;
        this.slotNumber = slotNumber;
        this.occupiedBy = null;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public boolean isFree() {
        return occupiedBy == null;
    }

    public boolean assignVehicle(Vehicle vehicle) {
        if (vehicle == null || !isFree()) {
            return false;
        }

        occupiedBy = vehicle.getRegistrationNumber();
        return true;
    }

    public boolean releaseVehicle(String registrationNumber) {
        if (isFree() || registrationNumber == null) {
            return false;
        }

        if (!occupiedBy.equals(registrationNumber)) {
            return false;
        }

        occupiedBy = null;
        return true;
    }
}
