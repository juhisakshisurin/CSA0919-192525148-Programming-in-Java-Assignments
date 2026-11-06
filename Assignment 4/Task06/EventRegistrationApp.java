import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EventRegistrationApp extends JFrame {
    private static final int PAGE_SIZE = 50;
    private final RegistrationService service = new RegistrationService();

    private final JTextField studentId = new JTextField(14);
    private final JTextField studentName = new JTextField(14);
    private final JTextField email = new JTextField(14);
    private final JComboBox<String> eventChoice = new JComboBox<>(new String[]{
            "Tech Symposium", "Cultural Fest", "Sports Meet", "Workshop"
    });
    private final JComboBox<String> filterChoice = new JComboBox<>(
            new String[]{"All events", "Tech Symposium", "Cultural Fest",
                    "Sports Meet", "Workshop"});

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Student ID", "Student Name", "Email",
                    "Event", "Registered At"}, 0) {
        @Override public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(model);
    private final JLabel status = new JLabel("Ready");
    private final JButton previous = new JButton("Previous");
    private final JButton next = new JButton("Next");
    private int page = 0;
    private int totalRows = 0;
    private SwingWorker<?, ?> activeWorker;

    public EventRegistrationApp() {
        super("Campus Event Registration");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));
        setJMenuBar(createMenuBar());
        add(createRegistrationPanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        filterChoice.addActionListener(e -> {
            page = 0;
            loadPage();
        });
        previous.addActionListener(e -> {
            if (page > 0) { page--; loadPage(); }
        });
        next.addActionListener(e -> {
            if ((page + 1) * PAGE_SIZE < totalRows) { page++; loadPage(); }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) {
                if (activeWorker != null && !activeWorker.isDone()) {
                    int choice = JOptionPane.showConfirmDialog(
                            EventRegistrationApp.this,
                            "A database operation is in progress. Close anyway?",
                            "Confirm Exit", JOptionPane.YES_NO_OPTION);
                    if (choice != JOptionPane.YES_OPTION) return;
                    activeWorker.cancel(true);
                }
                dispose();
            }
        });

        setSize(1000, 620);
        setLocationRelativeTo(null);
        loadPage();
    }

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem export = new JMenuItem("Export CSV...");
        JMenuItem refresh = new JMenuItem("Refresh");
        JMenuItem exit = new JMenuItem("Exit");
        export.addActionListener(e -> exportCsv());
        refresh.addActionListener(e -> loadPage());
        exit.addActionListener(e -> dispatchEvent(
                new java.awt.event.WindowEvent(this,
                        java.awt.event.WindowEvent.WINDOW_CLOSING)));
        file.add(export);
        file.add(refresh);
        file.addSeparator();
        file.add(exit);
        bar.add(file);
        return bar;
    }

    private JPanel createRegistrationPanel() {
        JPanel outer = new JPanel(new BorderLayout(6, 6));
        JPanel form = new JPanel(new GridLayout(2, 4, 8, 5));
        form.add(new JLabel("Student ID"));
        form.add(new JLabel("Student Name"));
        form.add(new JLabel("Email"));
        form.add(new JLabel("Event"));
        form.add(studentId);
        form.add(studentName);
        form.add(email);
        form.add(eventChoice);

        JButton register = new JButton("Register");
        register.addActionListener(e -> registerStudent());
        JPanel action = new JPanel(new FlowLayout(FlowLayout.LEFT));
        action.add(register);
        outer.setBorder(BorderFactory.createTitledBorder("Student Registration"));
        outer.add(form, BorderLayout.CENTER);
        outer.add(action, BorderLayout.SOUTH);
        return outer;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controls.add(new JLabel("Filter by event:"));
        controls.add(filterChoice);
        controls.add(previous);
        controls.add(next);
        panel.add(controls, BorderLayout.NORTH);
        panel.add(status, BorderLayout.SOUTH);
        return panel;
    }

    private void registerStudent() {
        final String id = studentId.getText();
        final String name = studentName.getText();
        final String mail = email.getText();
        final String event = (String) eventChoice.getSelectedItem();

        runWorker("Registering...", () -> {
            service.register(id, name, mail, event);
            return "Registration successful.";
        }, message -> {
            JOptionPane.showMessageDialog(this, message,
                    "Registration", JOptionPane.INFORMATION_MESSAGE);
            studentId.setText("");
            studentName.setText("");
            email.setText("");
            page = 0;
            loadPage();
        });
    }

    private void loadPage() {
        final String filter = (String) filterChoice.getSelectedItem();
        runWorker("Loading registrations...", () -> {
            int count = service.getCount(filter);
            List<Registration> rows =
                    service.getPage(filter, PAGE_SIZE, page * PAGE_SIZE);
            return new PageData(count, rows);
        }, data -> {
            totalRows = data.count;
            model.setRowCount(0);
            for (Registration r : data.rows) {
                model.addRow(new Object[]{r.getId(), r.getStudentId(),
                        r.getStudentName(), r.getEmail(), r.getEventName(),
                        r.getRegisteredAt()});
            }
            previous.setEnabled(page > 0);
            next.setEnabled((page + 1) * PAGE_SIZE < totalRows);
            status.setText("Showing page " + (page + 1) + " | "
                    + data.rows.size() + " rows on this page | "
                    + totalRows + " total registrations");
        }, error -> showError(error));
    }

    private void exportCsv() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("registrations.csv"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        String filter = (String) filterChoice.getSelectedItem();
        runWorker("Exporting CSV...", () -> {
            service.export(filter, file);
            return "Export completed: " + file.getAbsolutePath();
        }, message -> JOptionPane.showMessageDialog(this, message,
                "Export", JOptionPane.INFORMATION_MESSAGE));
    }

    private <T> void runWorker(String message,
                               BackgroundTask<T> task,
                               java.util.function.Consumer<T> success) {
        runWorker(message, task, success, this::showError);
    }

    private <T> void runWorker(String message,
                               BackgroundTask<T> task,
                               java.util.function.Consumer<T> success,
                               java.util.function.Consumer<Throwable> failure) {
        status.setText(message);
        setBusy(true);
        activeWorker = new SwingWorker<T, Void>() {
            @Override protected T doInBackground() throws Exception {
                return task.run();
            }
            @Override protected void done() {
                setBusy(false);
                try {
                    T result = get();
                    success.accept(result);
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() == null ? ex : ex.getCause();
                    failure.accept(cause);
                }
            }
        };
        activeWorker.execute();
    }

    private void setBusy(boolean busy) {
        studentId.setEnabled(!busy);
        studentName.setEnabled(!busy);
        email.setEnabled(!busy);
        eventChoice.setEnabled(!busy);
        filterChoice.setEnabled(!busy);
        previous.setEnabled(!busy && page > 0);
        next.setEnabled(!busy && (page + 1) * PAGE_SIZE < totalRows);
        status.setText(busy ? "Please wait..." : status.getText());
    }

    private void showError(Throwable error) {
        status.setText("Operation failed.");
        JOptionPane.showMessageDialog(this,
                error.getMessage() == null ? error.toString() : error.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static class PageData {
        final int count;
        final List<Registration> rows;
        PageData(int count, List<Registration> rows) {
            this.count = count;
            this.rows = rows;
        }
    }

    @FunctionalInterface
    private interface BackgroundTask<T> {
        T run() throws Exception;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new EventRegistrationApp().setVisible(true));
    }
}
