
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

// Pricing strategy interface
interface PricingRule {
    double calculateFee(LocalDateTime entry, LocalDateTime exit);
}

// Standard hourly pricing
class HourlyPricingRule implements PricingRule {
    private final double hourlyRate;

    public HourlyPricingRule(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateFee(LocalDateTime entry, LocalDateTime exit) {
        long minutes = Duration.between(entry, exit).toMinutes();

        if (minutes < 0) {
            throw new IllegalArgumentException("Invalid exit time.");
        }

        long hours = (minutes + 59) / 60;
        return hours * hourlyRate;
    }
}

// E-bike flat-fee pricing
class EbikeFlatPricingRule implements PricingRule {
    private final double flatFee;

    public EbikeFlatPricingRule(double flatFee) {
        this.flatFee = flatFee;
    }

    @Override
    public double calculateFee(LocalDateTime entry, LocalDateTime exit) {
        if (exit.isBefore(entry)) {
            throw new IllegalArgumentException("Invalid exit time.");
        }

        return flatFee;
    }
}

// Delivery van pricing: double rate during 9 AM to 5 PM
class DeliveryVanPricingRule implements PricingRule {
    private final double carRate;

    public DeliveryVanPricingRule(double carRate) {
        this.carRate = carRate;
    }

    @Override
    public double calculateFee(LocalDateTime entry, LocalDateTime exit) {
        long minutes = Duration.between(entry, exit).toMinutes();

        if (minutes < 0) {
            throw new IllegalArgumentException("Invalid exit time.");
        }

        long billableHours = (minutes + 59) / 60;
        double total = 0.0;

        for (int i = 0; i < billableHours; i++) {
            LocalDateTime blockStart = entry.plusHours(i);
            int hour = blockStart.getHour();

            if (hour >= 9 && hour < 17) {
                total += 2 * carRate;
            } else {
                total += carRate;
            }
        }

        return total;
    }
}

// Parking pass interface
interface ParkingPass {
    boolean isValid(LocalDate date);
}

// Visitor one-day pass
class OneDayPass implements ParkingPass {
    private final LocalDate validDate;

    public OneDayPass(LocalDate validDate) {
        if (validDate == null) {
            throw new IllegalArgumentException("Pass date is required.");
        }

        this.validDate = validDate;
    }

    @Override
    public boolean isValid(LocalDate date) {
        return validDate.equals(date);
    }
}

// Abstract vehicle class
abstract class Vehicle {
    private final String registrationNumber;
    private final PricingRule pricingRule;

    protected Vehicle(String registrationNumber, PricingRule pricingRule) {
        if (registrationNumber == null ||
            registrationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Registration number cannot be empty."
            );
        }

        if (pricingRule == null) {
            throw new IllegalArgumentException("Pricing rule is required.");
        }

        this.registrationNumber = registrationNumber;
        this.pricingRule = pricingRule;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public double calculateFee(LocalDateTime entry, LocalDateTime exit) {
        return pricingRule.calculateFee(entry, exit);
    }
}

// Existing vehicle categories
class Car extends Vehicle {
    public Car(String regNo) {
        super(regNo, new HourlyPricingRule(30.0));
    }
}

class TwoWheeler extends Vehicle {
    public TwoWheeler(String regNo) {
        super(regNo, new HourlyPricingRule(10.0));
    }
}

class ElectricCar extends Vehicle {
    public ElectricCar(String regNo) {
        super(regNo, new HourlyPricingRule(30.0));
    }
}

// New vehicle categories
class Ebike extends Vehicle {
    public Ebike(String regNo) {
        super(regNo, new EbikeFlatPricingRule(25.0));
    }
}

class DeliveryVan extends Vehicle {
    public DeliveryVan(String regNo) {
        super(regNo, new DeliveryVanPricingRule(30.0));
    }
}

// Parking ticket
class ParkingTicket {
    private final int ticketNumber;
    private final String registrationNumber;
    private final int floor;
    private final int slot;
    private final LocalDateTime entryTime;
    private final ParkingPass pass;

    public ParkingTicket(
        int ticketNumber,
        String registrationNumber,
        int floor,
        int slot,
        LocalDateTime entryTime,
        ParkingPass pass
    ) {
        this.ticketNumber = ticketNumber;
        this.registrationNumber = registrationNumber;
        this.floor = floor;
        this.slot = slot;
        this.entryTime = entryTime;
        this.pass = pass;
    }

    public int getTicketNumber() {
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

    public ParkingPass getPass() {
        return pass;
    }
}

// Five-floor parking facility
class ParkingFacility {
    private final String[][] slots;

    private final Map<String, ParkingTicket> activeTickets =
        new HashMap<String, ParkingTicket>();

    private int ticketCounter = 1000;

    public ParkingFacility(int[] slotsPerFloor) {
        if (slotsPerFloor == null || slotsPerFloor.length != 5) {
            throw new IllegalArgumentException(
                "The facility must have five floors."
            );
        }

        slots = new String[5][];

        for (int floor = 0; floor < 5; floor++) {
            if (slotsPerFloor[floor] <= 0) {
                throw new IllegalArgumentException(
                    "Every floor needs at least one slot."
                );
            }

            slots[floor] = new String[slotsPerFloor[floor]];
        }
    }

    public ParkingTicket park(
        Vehicle vehicle,
        ParkingPass pass,
        LocalDateTime entryTime
    ) {
        if (vehicle == null || entryTime == null) {
            throw new IllegalArgumentException(
                "Vehicle and entry time are required."
            );
        }

        String regNo = vehicle.getRegistrationNumber();

        if (activeTickets.containsKey(regNo)) {
            System.out.println("Rejected duplicate: " + regNo);
            return null;
        }

        for (int floor = 0; floor < slots.length; floor++) {
            for (int slot = 0; slot < slots[floor].length; slot++) {
                if (slots[floor][slot] == null) {
                    slots[floor][slot] = regNo;

                    int newTicketNumber = ++ticketCounter;

                    ParkingTicket ticket = new ParkingTicket(
                        newTicketNumber,
                        regNo,
                        floor,
                        slot,
                        entryTime,
                        pass
                    );

                    activeTickets.put(regNo, ticket);

                    System.out.println(
                        "Parked " + regNo +
                        " at Floor " + floor +
                        ", Slot " + slot +
                        " | Ticket " + ticket.getTicketNumber()
                    );

                    return ticket;
                }
            }
        }

        System.out.println("Parking facility is full.");
        return null;
    }

    public boolean exit(Vehicle vehicle, LocalDateTime exitTime) {
        if (vehicle == null || exitTime == null) {
            return false;
        }

        String regNo = vehicle.getRegistrationNumber();
        ParkingTicket ticket = activeTickets.get(regNo);

        if (ticket == null) {
            System.out.println("No active ticket for " + regNo);
            return false;
        }

        if (exitTime.isBefore(ticket.getEntryTime())) {
            System.out.println("Invalid exit time.");
            return false;
        }

        double fee;

        if (ticket.getPass() != null &&
            ticket.getPass().isValid(
                ticket.getEntryTime().toLocalDate()
            )) {
            fee = 0.0;
        } else {
            fee = vehicle.calculateFee(
                ticket.getEntryTime(), exitTime
            );
        }

        slots[ticket.getFloor()][ticket.getSlot()] = null;
        activeTickets.remove(regNo);

        System.out.println(
            "Exited " + regNo +
            " | Ticket " + ticket.getTicketNumber() +
            " | Fee: Rs. " + fee
        );

        return true;
    }

    public int getFloorCount() {
        return slots.length;
    }

    public int getSlotCount(int floor) {
        if (floor < 0 || floor >= slots.length) {
            return -1;
        }

        return slots[floor].length;
    }

    public int getActiveTicketCount() {
        return activeTickets.size();
    }
}

// Main class
public class Task12DesignChallenge {

    public static void main(String[] args) {

        // Five floors with unequal capacities
        ParkingFacility facility =
            new ParkingFacility(new int[]{2, 3, 4, 2, 5});

        LocalDate date = LocalDate.of(2026, 11, 4);

        System.out.println("=== NEW VEHICLE TESTS ===");

        // E-bike: flat fee
        Ebike ebike = new Ebike("TN10EB1001");

        facility.park(
            ebike, null, date.atTime(10, 0)
        );

        facility.exit(ebike, date.atTime(12, 0));

        // Delivery van: 8 AM to 10 AM
        DeliveryVan van = new DeliveryVan("TN10DV2002");

        facility.park(
            van, null, date.atTime(8, 0)
        );

        facility.exit(van, date.atTime(10, 0));

        System.out.println("\n=== ONE-DAY PASS TEST ===");

        ParkingPass pass = new OneDayPass(date);
        Car visitorCar = new Car("TN10CA3003");

        facility.park(
            visitorCar, pass, date.atTime(11, 0)
        );

        facility.exit(visitorCar, date.atTime(14, 0));

        System.out.println("\n=== STANDARD VEHICLE TEST ===");

        Car normalCar = new Car("TN10CA4004");

        facility.park(
            normalCar, null, date.atTime(10, 0)
        );

        facility.exit(normalCar, date.atTime(12, 0));

        System.out.println("\n=== FACILITY CONFIGURATION ===");

        System.out.println(
            "Number of floors: " + facility.getFloorCount()
        );

        for (int floor = 0; floor < facility.getFloorCount(); floor++) {
            System.out.println(
                "Floor " + floor + " slots: " +
                facility.getSlotCount(floor)
            );
        }

        System.out.println(
            "Active tickets remaining: " +
            facility.getActiveTicketCount()
        );
    }
}
