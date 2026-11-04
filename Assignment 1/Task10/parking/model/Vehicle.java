package parking.model;

public abstract class Vehicle {
    private final String registrationNumber;

    protected Vehicle(String registrationNumber) {
        if (registrationNumber == null ||
            registrationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Registration number cannot be empty."
            );
        }

        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}
