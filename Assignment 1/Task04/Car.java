public class Car extends Vehicle {
    public Car(String registrationNumber) {
        super(registrationNumber);
    }

    @Override
    public double ratePerHour() {
        return 30.0;   // placeholder rate, not an assignment requirement
    }
}
