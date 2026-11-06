import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handles fee-payment validation and database operations.
 * Update the database URL, username and password before running.
 */
public class FeeService {
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/college";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "YOUR_PASSWORD";

    /**
     * Validates the supplied details and stores the payment.
     * @return a confirmation message after a successful insert
     * @throws IllegalArgumentException when the input is invalid
     * @throws SQLException when the database operation fails
     */
    public String payFee(String regNoText, String amountText)
            throws SQLException {

        String regNo = regNoText == null ? "" : regNoText.trim();
        String amountValue = amountText == null ? "" : amountText.trim();

        if (regNo.isEmpty()) {
            throw new IllegalArgumentException("Register number is required.");
        }

        if (amountValue.isEmpty()) {
            throw new IllegalArgumentException("Amount is required.");
        }

        final double amount;
        try {
            amount = Double.parseDouble(amountValue);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Enter a valid numeric amount.");
        }

        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        String sql = "INSERT INTO fees (reg_no, amount) VALUES (?, ?)";

        try (Connection connection =
                     DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, regNo);
            statement.setDouble(2, amount);
            statement.executeUpdate();
        }

        return String.format("Payment successful for %s. Amount: %.2f",
                regNo, amount);
    }
}
