import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * AWT window: collects fee details and displays service results.
 * Database work runs on a worker thread, not on the AWT event thread.
 */
public class FeeWindow extends Frame {
    private final TextField regNoField = new TextField(18);
    private final TextField amountField = new TextField(18);
    private final Label statusLabel = new Label("Enter details and select Pay.");
    private final Button payButton = new Button("Pay");
    private final Button clearButton = new Button("Clear");
    private final Button receiptButton = new Button("Receipt");

    private final FeeService feeService = new FeeService();
    private String lastReceipt = null;

    public FeeWindow() {
        super("Student Fee Payment");

        Panel formPanel = new Panel(new GridLayout(2, 2, 8, 8));
        formPanel.add(new Label("Register Number:"));
        formPanel.add(regNoField);
        formPanel.add(new Label("Amount:"));
        formPanel.add(amountField);

        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.add(payButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(receiptButton);

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePay();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regNoField.setText("");
                amountField.setText("");
                statusLabel.setText("Fields cleared.");
            }
        });

        receiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastReceipt == null) {
                    statusLabel.setText("No successful payment receipt is available.");
                } else {
                    statusLabel.setText(lastReceipt);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(440, 190);
        setLocationRelativeTo(null);
    }

    private void handlePay() {
        final String regNo = regNoField.getText();
        final String amount = amountField.getText();

        payButton.setEnabled(false);
        statusLabel.setText("Processing payment...");

        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String confirmation =
                            feeService.payFee(regNo, amount);

                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            lastReceipt = confirmation;
                            statusLabel.setText(confirmation);
                            payButton.setEnabled(true);
                        }
                    });
                } catch (final IllegalArgumentException ex) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            statusLabel.setText(ex.getMessage());
                            payButton.setEnabled(true);
                        }
                    });
                } catch (final SQLException ex) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            statusLabel.setText("Database error: " + ex.getMessage());
                            payButton.setEnabled(true);
                        }
                    });
                }
            }
        }, "FeePaymentWorker");

        worker.start();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FeeWindow().setVisible(true);
            }
        });
    }
}
