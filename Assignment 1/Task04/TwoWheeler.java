public class TwoWheeler extends Vehicle {
    public TwoWheeler(String registrationNumber) {
        super(registrationNumber);
    }

    @Override
    public double ratePerHour() {
        return 10.0;   // placeholder rate, not an assignment requirement
    }
}
