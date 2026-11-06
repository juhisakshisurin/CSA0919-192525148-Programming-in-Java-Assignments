# Assignment 4 – Task 2: Hospital Outpatient Appointment Desk

## Requirement summary
The receptionist enters a patient ID, selects a department, selects a doctor belonging to that department, chooses a time slot and books. The UI shows success/error feedback. Closing during an active booking requires confirmation.

## Task 2.1 – Event model

| Event source | Event object | Listener | Action |
|---|---|---|---|
| Department selector | `ItemEvent` (AWT `Choice`) / `ActionEvent` or `ItemEvent` (Swing `JComboBox`) | `ItemListener` / `ActionListener` | Refresh doctor list for selected department; clear stale doctor selection. |
| Doctor selector | `ItemEvent` / `ActionEvent` | `ItemListener` / `ActionListener` | Record doctor selection. |
| Time-slot selector | `ItemEvent` / `ActionEvent` | `ItemListener` / `ActionListener` | Record slot; update booking readiness. |
| Book button | `ActionEvent` | `ActionListener` | Validate, mark booking in progress, call booking service, show result. |
| Window close | `WindowEvent` | `WindowListener` / `WindowAdapter` | Ask for confirmation if booking is in progress. |
| Dialog choice | `ActionEvent` / dialog result | `ActionListener` or `JOptionPane` result | Continue closing or keep window open. |

## Task 2.2 – Component hierarchy

```text
JFrame AppointmentFrame [BorderLayout]
├── JPanel headerPanel [FlowLayout]
│   └── JLabel titleLabel
├── JPanel formPanel [GridBagLayout]
│   ├── JLabel patientIdLabel
│   ├── JTextField patientIdField
│   ├── JLabel departmentLabel
│   ├── JComboBox departmentCombo
│   ├── JLabel doctorLabel
│   ├── JComboBox doctorCombo
│   ├── JLabel timeSlotLabel
│   ├── JComboBox timeSlotCombo
│   ├── JButton bookButton
│   └── JButton clearButton (optional)
└── JPanel statusPanel [FlowLayout]
    └── JLabel statusLabel
```

A modal `JOptionPane` confirmation dialog appears when the user attempts to close during a booking.

## Task 2.3 – Handler pseudocode

### (a) Refresh doctors
```text
EVENT: Department selection changes
selectedDepartment <- departmentCombo.getSelectedItem()
doctorCombo.removeAllItems()

IF selectedDepartment is null THEN
    doctorCombo.setEnabled(FALSE)
    RETURN
END IF

doctorList <- doctorMap.get(selectedDepartment)
IF doctorList is empty THEN
    show "No doctors available"
    doctorCombo.setEnabled(FALSE)
ELSE
    FOR EACH doctor IN doctorList
        doctorCombo.addItem(doctor)
    END FOR
    doctorCombo.setEnabled(TRUE)
    doctorCombo.setSelectedIndex(0)
END IF
```

### (b) Validate and book
```text
EVENT: Book button pressed
IF bookingInProgress THEN RETURN

Read patientId, department, doctor and timeSlot

IF patientId is empty THEN show error; focus patientId; RETURN
IF any selection is missing THEN show error; RETURN
IF doctor does not belong to department THEN show error; RETURN

bookingInProgress <- TRUE
disable Book button
start background booking operation
    result <- AppointmentService.book(patientId, department, doctor, timeSlot)
    on UI event thread:
        bookingInProgress <- FALSE
        enable Book button
        show success confirmation OR clear failure message
```

For Swing, use `SwingWorker` or another background mechanism for slow booking/database work. Update Swing components on the Event Dispatch Thread.

## Task 2.4 – Event trace

1. `main()` schedules GUI creation with `SwingUtilities.invokeLater`.
2. `AppointmentFrame` constructor creates components and registers listeners.
3. `setVisible(true)` displays the frame.
4. Department selection triggers its listener and refreshes doctors.
5. Book button triggers `ActionListener`; fields are validated and booking starts.
6. Background booking completes; UI callback updates status and confirmation/error.
7. Window close generates a window event; handler checks `bookingInProgress`.
8. If active, show confirmation; cancel keeps window open, confirm follows safe-close policy.
9. `dispose()` releases the frame's native resources.

**Applet comparison:** Applets use container-managed `init`, `start`, `paint`, `stop`, `destroy` callbacks. A Swing desktop frame is created by application code and responds to registered listeners; it does not use the applet life cycle.

## Alternative concept and complexity

Let `d` = departments, `m` = total doctors, and `r` = doctors in the selected department.

- **Preloaded map:** building the map costs `O(d + m)` time and `O(d + m)` additional memory. A department lookup is expected `O(1)`; populating the combo box costs `O(r)`.
- **Database on each change:** each change incurs query execution and database/network round-trip latency, plus `O(r)` UI population. Exact query complexity depends on schema, indexes and query plan.
- **Responsiveness:** a database query on the Swing Event Dispatch Thread can freeze the UI; run it in a background worker.
- **Trade-off:** a map is faster for stable data; repeated database fetches provide fresher data and reduce client-side cached data.

## Verification checklist
- Department changes refresh doctors correctly.
- Empty patient ID and missing selections are rejected.
- Doctor must belong to selected department.
- Booking-in-progress prevents repeated submission.
- Success and failure feedback are displayed.
- Closing during booking asks for confirmation.
