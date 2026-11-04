package parking.service;

import parking.model.Vehicle;
import parking.model.ParkingSlot;
import parking.model.ParkingTicket;

public class ParkingService {
    private final ParkingSlot[][] slots;
    private long ticketCounter = 1000;

    public ParkingService(int[] slotsPerFloor) {
        if (slotsPerFloor == null || slotsPerFloor.length == 0) {
            throw new IllegalArgumentException(
                "At least one floor is required."
            );
        }

        slots = new ParkingSlot[slotsPerFloor.length][];

        for (int floor = 0; floor < slotsPerFloor.length; floor++) {
            if (slotsPerFloor[floor] <= 0) {
                throw new IllegalArgumentException(
                    "Each floor must have at least one slot."
                );
            }

            slots[floor] = new ParkingSlot[slotsPerFloor[floor]];

            for (int slot = 0; slot < slotsPerFloor[floor]; slot++) {
                slots[floor][slot] = new ParkingSlot(floor, slot);
            }
        }
    }

    public ParkingTicket park(Vehicle vehicle, int durationMinutes) {
        if (vehicle == null) {
            throw new IllegalArgumentException(
                "Vehicle cannot be null."
            );
        }

        for (int floor = 0; floor < slots.length; floor++) {
            for (int slot = 0; slot < slots[floor].length; slot++) {
                if (slots[floor][slot].assignVehicle(vehicle)) {
                    ParkingTicket ticket =
                        new ParkingTicket(++ticketCounter, vehicle);

                    ticket.setDurationMinutes(durationMinutes);
                    ticket.setParkingLocation(floor, slot);

                    return ticket;
                }
            }
        }

        return null;
    }

    public boolean release(ParkingTicket ticket) {
        if (ticket == null || !ticket.hasParkingLocation()) {
            return false;
        }

        // Safe unboxing after checking for null
        int floor = ticket.getFloorNumber();
        int slot = ticket.getSlotNumber();

        if (floor < 0 || floor >= slots.length ||
            slot < 0 || slot >= slots[floor].length) {
            return false;
        }

        return slots[floor][slot].releaseVehicle(
            ticket.getVehicle().getRegistrationNumber()
        );
    }
}
