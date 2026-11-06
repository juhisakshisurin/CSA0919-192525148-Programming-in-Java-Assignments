import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ATMWithdrawal {
    private static final double ACCOUNT_BALANCE = 10000.00;
    private static final String CARD_READER_FILE = "card-reader.txt";

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("--uncaught")) {
            demonstrateUncaughtException();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== BANK ATM WITHDRAWAL ===");

        try {
            try (FileReader reader = new FileReader(CARD_READER_FILE)) {
                reader.read();
            }

            System.out.print("Enter account number: ");
            if (!scanner.hasNextInt()) {
                throw new InputMismatchException("Account number must contain digits only");
            }
            int accountNumber = scanner.nextInt();

            System.out.print("Enter withdrawal amount: ");
            if (!scanner.hasNextDouble()) {
                throw new InputMismatchException("Withdrawal amount must be numeric");
            }
            double amount = scanner.nextDouble();

            if (accountNumber <= 0) {
                throw new IllegalArgumentException("Account number must be positive");
            }
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be positive");
            }
            if (amount > ACCOUNT_BALANCE) {
                throw new IllegalStateException("Insufficient balance");
            }

            System.out.printf("Withdrawal successful for account %d.%n", accountNumber);
            System.out.printf("Cash dispensed: Rs. %.2f%n", amount);
            System.out.printf("Remaining balance: Rs. %.2f%n", ACCOUNT_BALANCE - amount);

        } catch (IOException e) {
            System.out.println("Card reader error: " + e.getMessage());
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        } finally {
            System.out.println("ATM session ended. Finally block executed.");
            scanner.close();
        }
    }

    private static void demonstrateUncaughtException() {
        throw new ArithmeticException("Demonstration of an uncaught exception");
    }
}
