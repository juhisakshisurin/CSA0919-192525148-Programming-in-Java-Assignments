
import java.time.Duration;
import java.time.LocalTime;

interface Chargeable {
    double chargingFee(int kWh);
}

abstract class Vehicle {

    private final String regNo;

    protected Vehicle(String regNo) {
        this.regNo = regNo;
    }

    public String getRegNo() {
        return regNo;
    }

    protected abstract double ratePerHour();

    /** Every started hour is charged in full. */
    public double calculateFee(int minutes) {

        if (minutes < 0) {
            throw new IllegalArgumentException(
                "Minutes cannot be negative");
        }

        // Ceiling division using integer arithmetic
        int hours = minutes / 60;

        if (minutes % 60 != 0) {
            hours++;
        }

        return hours * ratePerHour();
    }

    // Overloaded method: accepts entry and exit times
    public double calculateFee(LocalTime in, LocalTime out) {

        long minutes = Duration.between(in, out).toMinutes();

        if (minutes < 0) {
            throw new IllegalArgumentException(
                "Exit time cannot be earlier than entry time");
        }

        return calculateFee((int) minutes);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Vehicle
            && regNo.equals(((Vehicle) o).regNo);
    }

    @Override
    public int hashCode() {
        return regNo.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
            + "{regNo='" + regNo + "'}";
    }
}

class TwoWheeler extends Vehicle {

    TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 10;
    }
}

class Car extends Vehicle {

    Car(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 30;
    }
}

class ElectricCar extends Car implements Chargeable {

    ElectricCar(String regNo) {
        super(regNo);
    }

    @Override
    public double chargingFee(int kWh) {
        return kWh * 12;
    }
}

public class FeeDemo {

    public static void main(String[] args) {

        Vehicle[] parked = {
            new TwoWheeler("TN01A1"),
            new Car("TN02B2"),
            new ElectricCar("TN03C3")
        };

        for (Vehicle v : parked) {

            double fee = v.calculateFee(130);

            if (v instanceof Chargeable c) {
                fee += c.chargingFee(10);
            }

            System.out.println(
                v.getClass().getSimpleName() + " "
                + v.getRegNo() + " -> Rs " + fee
            );
        }

        System.out.println(
            new Car("TN02B2").equals(
                new ElectricCar("TN02B2")
            )
        );
    }
}
