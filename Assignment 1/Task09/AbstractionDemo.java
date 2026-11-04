
interface Chargeable {
    double chargingFee(int kWh);

    default String chargingDescription() {
        return "EV charging is supported";
    }

    static boolean isValidEnergy(int kWh) {
        return kWh > 0;
    }
}

interface Payable {
    void pay(double amount);
}

abstract class Vehicle {
    private final String regNo;

    protected Vehicle(String regNo) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Registration number cannot be empty."
            );
        }
        this.regNo = regNo;
    }

    public String getRegNo() {
        return regNo;
    }

    protected abstract double ratePerHour();

    public double calculateFee(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException(
                "Parking duration cannot be negative."
            );
        }

        int hours = (minutes + 59) / 60;
        return hours * ratePerHour();
    }
}

class TwoWheeler extends Vehicle {
    public TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 10.0;
    }
}

class Car extends Vehicle {
    public Car(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 30.0;
    }
}

class ElectricCar extends Car implements Chargeable, Payable {
    public ElectricCar(String regNo) {
        super(regNo);
    }

    @Override
    public double chargingFee(int kWh) {
        if (!Chargeable.isValidEnergy(kWh)) {
            throw new IllegalArgumentException(
                "Charging energy must be positive."
            );
        }
        return kWh * 12.0;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Payment processed: Rs. " + amount);
    }
}

class Visitor implements Payable {
    @Override
    public void pay(double amount) {
        System.out.println("Visitor paid: Rs. " + amount);
    }
}

public class AbstractionDemo {
    public static void main(String[] args) {
        Vehicle[] vehicles = {
            new TwoWheeler("TN01A1"),
            new Car("TN02B2"),
            new ElectricCar("TN03C3")
        };

        for (Vehicle vehicle : vehicles) {
            double fee = vehicle.calculateFee(130);

            System.out.println(
                vehicle.getClass().getSimpleName() + " "
                + vehicle.getRegNo() + " -> Rs. " + fee
            );

            if (vehicle instanceof Chargeable) {
                Chargeable ev = (Chargeable) vehicle;
                double charging = ev.chargingFee(10);

                System.out.println(
                    "Charging Fee: Rs. " + charging
                );
                System.out.println(ev.chargingDescription());
            }
        }

        ElectricCar ev = new ElectricCar("TN03C3");
        ev.pay(210.0);

        Visitor visitor = new Visitor();
        visitor.pay(90.0);
    }
}
