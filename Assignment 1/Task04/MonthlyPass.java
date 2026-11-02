import java.time.LocalDate;

public class MonthlyPass {
    private final String passNumber;
    private final LocalDate validFrom;
    private final LocalDate validTo;

    public MonthlyPass(String passNumber, LocalDate validFrom,
                       LocalDate validTo) {
        if (passNumber == null || passNumber.isBlank()) {
            throw new IllegalArgumentException("pass number is required");
        }
        if (validFrom == null || validTo == null) {
            throw new IllegalArgumentException("validity dates are required");
        }
        if (validTo.isBefore(validFrom)) {
            throw new IllegalArgumentException(
                    "valid-to date must not be before valid-from date");
        }
        this.passNumber = passNumber;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public boolean isValidOn(LocalDate date) {
        return date != null
                && !date.isBefore(validFrom)
                && !date.isAfter(validTo);
    }

    public String getPassNumber() {
        return passNumber;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }
}
