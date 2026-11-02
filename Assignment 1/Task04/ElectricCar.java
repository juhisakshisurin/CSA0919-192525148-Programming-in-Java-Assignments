public class ElectricCar extends Car implements Chargeable {
    public ElectricCar(String registrationNumber) {
        super(registrationNumber);
    }

    @Override
    public double chargingFee(int kWh) {
        if (kWh < 0) {
            throw new IllegalArgumentException("kWh must not be negative");
        }
        return kWh * 12.0;   // illustrative rate, not an assignment requirement
    }
}
