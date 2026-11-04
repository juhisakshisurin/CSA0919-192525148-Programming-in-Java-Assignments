package parking.app;

import parking.model.Car;
import parking.model.ParkingTicket;
import parking.service.ParkingService;

public class Main {
    public static void main(String[] args) {

        ParkingService service =
            new ParkingService(new int[]{2, 3});

        Car car = new Car("TN01AB1234");

        ParkingTicket ticket = service.park(car, 120);

        if (ticket != null) {
            System.out.println(
                "Ticket: " + ticket.getTicketNumber()
            );

            System.out.println(
                "Vehicle: " +
                ticket.getVehicle().getRegistrationNumber()
            );

            System.out.println(
                "Allocated Slot: Floor " +
                ticket.getFloorNumber() + ", Slot " +
                ticket.getSlotNumber()
            );

            System.out.println(
                "Duration: " +
                ticket.getDurationMinutes() + " minutes"
            );
        }

        // Test validation of negative duration
        try {
            ticket.setDurationMinutes(-30);
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Invalid duration rejected: " + e.getMessage()
            );
        }

        // Test slot release
        System.out.println(
            "Slot released: " + service.release(ticket)
        );

        System.out.println(
            "Slot released again: " + service.release(ticket)
        );

        // Demonstrate wrapper class value comparison
        Integer a = 200;
        Integer b = 200;

        System.out.println(
            "Wrapper comparison using equals: " + a.equals(b)
        );
    }
}
