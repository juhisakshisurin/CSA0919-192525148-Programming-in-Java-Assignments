public interface Chargeable {
    // charging fee for the units (kWh) supplied to the vehicle
    double chargingFee(int kWh);
}
