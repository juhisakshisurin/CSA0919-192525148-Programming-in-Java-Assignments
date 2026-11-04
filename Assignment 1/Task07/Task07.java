abstract class Vehicle {

    private final String regNo;

    public Vehicle(String regNo) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Registration number cannot be empty");
        }
        this.regNo = regNo;
    }

    public final String getRegNo() {
        return regNo;
    }

    // Calculates the number of started hours
    protected final int billableHours(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException(
                "Parking duration cannot be negative");
        }

        return minutes / 60 +
               (minutes % 60 == 0 ? 0 : 1);
    }

    // Subclasses must provide their own fee calculation
    public abstract double calculateFee(int minutes);

    // Overriding toString()
    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{regNo='" + regNo + "'}";
    }

    // Overriding equals()
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Vehicle))
            return false;

        Vehicle other = (Vehicle) obj;

        return regNo.equals(other.regNo);
    }

    // Overriding hashCode()
    @Override
    public int hashCode() {
        return regNo.hashCode();
    }
}

class TwoWheeler extends Vehicle {

    public TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    public double calculateFee(int minutes) {
        return billableHours(minutes) * 10;
    }
}

class Car extends Vehicle {

    public Car(String regNo) {
        super(regNo);
    }

    @Override
    public double calculateFee(int minutes) {
        return billableHours(minutes) * 30;
    }
}

class ElectricCar extends Car {

    public ElectricCar(String regNo) {
        super(regNo);
    }
}

public class Task07 {

    public static void main(String[] args) {

        Vehicle[] vehicles = {
            new TwoWheeler("TN01A1"),
            new Car("TN02B2"),
            new ElectricCar("TN03C3")
        };

        // Demonstrating method overriding
        for (Vehicle v : vehicles) {
            System.out.println(
                v.getClass().getSimpleName() + " "
                + v.getRegNo() + " -> Rs "
                + v.calculateFee(130)
            );
        }

        // Demonstrating toString()
        System.out.println(vehicles[1]);

        // Demonstrating equals()
        Car c1 = new Car("TN02B2");
        ElectricCar c2 = new ElectricCar("TN02B2");

        System.out.println(c1.equals(c2));

        // Demonstrating hashCode() consistency
        System.out.println(
            c1.hashCode() == c2.hashCode()
        );
    }
}
