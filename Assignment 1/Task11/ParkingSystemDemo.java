
abstract class Vehicle {
    private final String registrationNumber;

    public Vehicle(String registrationNumber) {
        if (registrationNumber == null ||
            registrationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Invalid registration number."
            );
        }
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public abstract double ratePerHour();
}

class TwoWheeler extends Vehicle {
    public TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    public double ratePerHour() {
        return 10.0;
    }
}

class Car extends Vehicle {
    public Car(String regNo) {
        super(regNo);
    }

    @Override
    public double ratePerHour() {
        return 30.0;
    }
}

class ElectricCar extends Car {
    public ElectricCar(String regNo) {
        super(regNo);
    }

    public double chargingFee(int kWh) {
        if (kWh < 0) {
            throw new IllegalArgumentException(
                "Charging units cannot be negative."
            );
        }
        return kWh * 12.0;
    }
}

class ParkingUser {
    private final String name;
    private final boolean monthlyPass;

    public ParkingUser(String name, boolean monthlyPass) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "User name cannot be empty."
            );
        }
        this.name = name;
        this.monthlyPass = monthlyPass;
    }

    public String getName() {
        return name;
    }

    public boolean hasMonthlyPass() {
        return monthlyPass;
    }
}

class ParkingTicket {
    private final int ticketNumber;
    private final Vehicle vehicle;
    private final ParkingUser user;
    private final int floor;
    private final int slot;
    private final int durationMinutes;
    private final int chargingUnits;

    public ParkingTicket(
        int ticketNumber,
        Vehicle vehicle,
        ParkingUser user,
        int floor,
        int slot,
        int durationMinutes,
        int chargingUnits
    ) {
        this.ticketNumber = ticketNumber;
        this.vehicle = vehicle;
        this.user = user;
        this.floor = floor;
        this.slot = slot;
        this.durationMinutes = durationMinutes;
        this.chargingUnits = chargingUnits;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingUser getUser() {
        return user;
    }

    public int getFloor() {
        return floor;
    }

    public int getSlot() {
        return slot;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getChargingUnits() {
        return chargingUnits;
    }
}

class ParkingSlot {
    private final int floor;
    private final int slotNumber;
    private String occupiedBy;

    public ParkingSlot(int floor, int slotNumber) {
        this.floor = floor;
        this.slotNumber = slotNumber;
    }

    public boolean isFree() {
        return occupiedBy == null;
    }

    public boolean occupy(String registrationNumber) {
        if (!isFree()) {
            return false;
        }
        occupiedBy = registrationNumber;
        return true;
    }

    public boolean release(String registrationNumber) {
        if (isFree() || !occupiedBy.equals(registrationNumber)) {
            return false;
        }
        occupiedBy = null;
        return true;
    }

    public int getFloor() {
        return floor;
    }

    public int getSlotNumber() {
        return slotNumber;
    }
}

class ParkingFacility {
    private final ParkingSlot[][] slots;
    private final ParkingTicket[] activeTickets;
    private int ticketCounter = 1000;
    private int activeCount;
    private double totalCollection;

    public ParkingFacility(int[] slotsPerFloor) {
        if (slotsPerFloor == null || slotsPerFloor.length == 0) {
            throw new IllegalArgumentException(
                "At least one floor is required."
            );
        }

        slots = new ParkingSlot[slotsPerFloor.length][];
        int capacity = 0;

        for (int f = 0; f < slotsPerFloor.length; f++) {
            if (slotsPerFloor[f] <= 0) {
                throw new IllegalArgumentException(
                    "Each floor must have at least one slot."
                );
            }

            slots[f] = new ParkingSlot[slotsPerFloor[f]];
            capacity += slotsPerFloor[f];

            for (int s = 0; s < slotsPerFloor[f]; s++) {
                slots[f][s] = new ParkingSlot(f, s);
            }
        }

        activeTickets = new ParkingTicket[capacity];
    }

    public ParkingTicket park(
        Vehicle vehicle,
        ParkingUser user,
        int durationMinutes,
        int chargingUnits
    ) {
        if (vehicle == null || user == null) {
            throw new IllegalArgumentException(
                "Vehicle and user are required."
            );
        }

        if (durationMinutes < 0 || chargingUnits < 0) {
            throw new IllegalArgumentException(
                "Duration and charging units cannot be negative."
            );
        }

        if (!(vehicle instanceof ElectricCar) && chargingUnits > 0) {
            throw new IllegalArgumentException(
                "Charging units are only valid for electric cars."
            );
        }

        if (findTicket(vehicle.getRegistrationNumber()) != null) {
            System.out.println(
                "Rejected: Duplicate registration number."
            );
            return null;
        }

        if (activeCount == activeTickets.length) {
            System.out.println("Rejected: Parking facility is full.");
            return null;
        }

        for (int f = 0; f < slots.length; f++) {
            for (int s = 0; s < slots[f].length; s++) {
                if (slots[f][s].occupy(
                    vehicle.getRegistrationNumber()
                )) {
                    ParkingTicket ticket = new ParkingTicket(
                        ++ticketCounter,
                        vehicle,
                        user,
                        f,
                        s,
                        durationMinutes,
                        chargingUnits
                    );

                    activeTickets[activeCount++] = ticket;
                    return ticket;
                }
            }
        }

        return null;
    }

    private ParkingTicket findTicket(String registrationNumber) {
        for (int i = 0; i < activeCount; i++) {
            if (activeTickets[i].getVehicle()
                .getRegistrationNumber().equals(registrationNumber)) {
                return activeTickets[i];
            }
        }
        return null;
    }

    public double calculateFee(ParkingTicket ticket) {
        if (ticket.getUser().hasMonthlyPass()) {
            return 0.0;
        }

        int minutes = ticket.getDurationMinutes();
        int hours = (minutes + 59) / 60;

        double fee = hours * ticket.getVehicle().ratePerHour();

        if (ticket.getVehicle() instanceof ElectricCar) {
            ElectricCar ev = (ElectricCar) ticket.getVehicle();
            fee += ev.chargingFee(ticket.getChargingUnits());
        }

        return fee;
    }

    public boolean exit(String registrationNumber) {
        ParkingTicket ticket = findTicket(registrationNumber);

        if (ticket == null) {
            return false;
        }

        double fee = calculateFee(ticket);

        int floor = ticket.getFloor();
        int slot = ticket.getSlot();

        if (!slots[floor][slot].release(registrationNumber)) {
            return false;
        }

        totalCollection += fee;

        System.out.println(
            "Exit: " + registrationNumber +
            " | Ticket: " + ticket.getTicketNumber() +
            " | Fee: Rs. " + fee
        );

        for (int i = 0; i < activeCount; i++) {
            if (activeTickets[i] == ticket) {
                for (int j = i; j < activeCount - 1; j++) {
                    activeTickets[j] = activeTickets[j + 1];
                }
                activeTickets[--activeCount] = null;
                break;
            }
        }

        return true;
    }

    public boolean releaseSlot(int floor, int slot) {
        if (floor < 0 || floor >= slots.length ||
            slot < 0 || slot >= slots[floor].length) {
            return false;
        }

        return slots[floor][slot].isFree();
    }

    public double getTotalCollection() {
        return totalCollection;
    }

    public int getActiveCount() {
        return activeCount;
    }
}

public class ParkingSystemDemo {

    public static Vehicle createVehicle(
        String type,
        String registrationNumber
    ) {
        if (type == null) {
            throw new IllegalArgumentException(
                "Unknown vehicle type."
            );
        }

        switch (type.toLowerCase()) {
            case "two-wheeler":
                return new TwoWheeler(registrationNumber);
            case "car":
                return new Car(registrationNumber);
            case "electric-car":
                return new ElectricCar(registrationNumber);
            default:
                throw new IllegalArgumentException(
                    "Unknown vehicle type: " + type
                );
        }
    }

    public static void main(String[] args) {

        ParkingUser visitor =
            new ParkingUser("Visitor", false);
        ParkingUser staff =
            new ParkingUser("Staff", true);

        System.out.println("=== DAILY COLLECTION TEST ===");

        ParkingFacility facility =
            new ParkingFacility(new int[]{2, 2});

        ParkingTicket t1 = facility.park(
            createVehicle("two-wheeler", "TN01A1111"),
            visitor, 60, 0
        );

        ParkingTicket t2 = facility.park(
            createVehicle("car", "TN02B2222"),
            visitor, 120, 0
        );

        ParkingTicket t3 = facility.park(
            createVehicle("electric-car", "TN03C3333"),
            visitor, 60, 5
        );

        ParkingTicket t4 = facility.park(
            createVehicle("car", "TN04D4444"),
            staff, 120, 0
        );

        System.out.println("Duplicate registration test:");
        facility.park(
            createVehicle("car", "TN02B2222"),
            visitor, 60, 0
        );

        facility.exit("TN01A1111");
        facility.exit("TN02B2222");
        facility.exit("TN03C3333");
        facility.exit("TN04D4444");

        System.out.println(
            "Total daily collection: Rs. " +
            facility.getTotalCollection()
        );

        System.out.println("\n=== BOUNDARY TESTS ===");

        ParkingFacility small =
            new ParkingFacility(new int[]{1});

        ParkingTicket zero = small.park(
            createVehicle("car", "TN05E5555"),
            visitor, 0, 0
        );

        System.out.println(
            "Zero-minute parking fee: Rs. " +
            small.calculateFee(zero)
        );

        small.exit("TN05E5555");

        ParkingTicket boundary = small.park(
            createVehicle("car", "TN06F6666"),
            visitor, 60, 0
        );

        System.out.println(
            "Exactly 60-minute fee: Rs. " +
            small.calculateFee(boundary)
        );

        small.exit("TN06F6666");

        System.out.println(
            "Invalid floor/slot test: " +
            small.releaseSlot(5, 0)
        );

        System.out.println(
            "Invalid slot test: " +
            small.releaseSlot(0, 5)
        );

        System.out.println(
            "Last slot allocation test: " +
            (boundary != null)
        );

        System.out.println(
            "Already released slot test: " +
            small.releaseSlot(0, 0)
        );

        System.out.println("\n=== INVALID VEHICLE TEST ===");

        try {
            createVehicle("bus", "TN07G7777");
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Unknown vehicle rejected: " + e.getMessage()
            );
        }
    }
}
