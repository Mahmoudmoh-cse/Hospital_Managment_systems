import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

// Assuming Patient, Appointment, and PatientManagementSystem classes are defined elsewhere in your project
public class PatientManagementGUI {

    private JFrame frame;
    private JPanel mainPanel, patientPanel, appointmentPanel, waitingListPanel, formPanel, buttonPanel;
    private JTable patientTable, appointmentTable, waitingListTable;
    private DefaultTableModel patientTableModel, appointmentTableModel, waitingListTableModel;
    private JTextField nameField, ageField, contactField, searchField, amountField;
    private JComboBox<String> medicalHistoryComboBox , urgencyComboBox ;
    private JButton addPatientButton, scheduleAppointmentButton, cancelAppointmentButton, generateReportButton, searchButton, billingButton, addToWaitingListButton, removeFromWaitingListButton, completeButton;
    private PatientManagementSystem pms;
    private int maxCapacity;// Maximum number of patients allowed (excluding waiting list)

    private Map<Integer, Object[]> patientInfoMap = new HashMap<>();
    public PatientManagementGUI() {
        askForCapacity();
        initializeGUI();
    }

    private void askForCapacity() {
        // Create the main panel with BoxLayout for better structure
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));  // Padding around the panel

        // Title Label
        JLabel titleLabel = new JLabel("Set Maximum Capacity for the System:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Instruction Label
        JLabel instructionLabel = new JLabel("Please enter a positive integer value.");
        instructionLabel.setForeground(Color.RED);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Text Field for Input
        JTextField capacityField = new JTextField(10);
        capacityField.setFont(new Font("Arial", Font.PLAIN, 16));
        capacityField.setMaximumSize(new Dimension(200, 30));
        capacityField.setAlignmentX(Component.CENTER_ALIGNMENT);
        capacityField.setToolTipText("Enter a positive integer");
        capacityField.setForeground(Color.blue);


        // Error Message Label (Initially Hidden)
        JLabel errorMessage = new JLabel("Invalid input. Please enter a positive integer.");
        errorMessage.setForeground(Color.RED);
        errorMessage.setFont(new Font("Arial", Font.ITALIC, 12));
        errorMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorMessage.setVisible(false);

        // Custom Panel for Buttons (OK and Cancel)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // OK Button
        JButton okButton = new JButton("OK");
        okButton.setBackground(Color.GREEN);
        okButton.setFont(new Font("Arial", Font.PLAIN, 14));
        okButton.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(okButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.RED);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(cancelButton);

        // Add Components to Main Panel
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10)); // Spacer
        panel.add(instructionLabel);
        panel.add(Box.createVerticalStrut(10)); // Spacer
        panel.add(capacityField);
        panel.add(Box.createVerticalStrut(10)); // Spacer
        panel.add(errorMessage);
        panel.add(Box.createVerticalStrut(20)); // Spacer
        panel.add(buttonPanel);

        // Create the Dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Set Capacity");
        dialog.setModal(true);  // Ensure the dialog blocks other windows
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null); // Center the dialog

        // Action Listeners for Buttons
        okButton.addActionListener(e -> {
            String inputText = capacityField.getText().trim();

            try {
                if (inputText.isEmpty()) {
                    throw new NumberFormatException();
                }

                maxCapacity = Integer.parseInt(inputText);

                if (maxCapacity <= 0) {
                    throw new NumberFormatException();
                }

                dialog.dispose();  // Close the dialog once input is valid

            } catch (NumberFormatException ex) {
                // Show error message if invalid input
                errorMessage.setVisible(true);
            }
        });

        cancelButton.addActionListener(e -> {
            dialog.dispose();  // Close the dialog when cancel button is clicked
        });

        dialog.setVisible(true);  // Display the dialog
    }



    private void initializeGUI() {
        pms = new PatientManagementSystem(); // Initialize PatientManagementSystem instance.
        frame = new JFrame("Hospital Patient Management System");

        // Main Panel setup
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.lightGray);

        // Form Panel setup for input fields
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        ageField = new JTextField();
        contactField = new JTextField();
        searchField = new JTextField();
        amountField = new JTextField();

        String[] medicalHistoryOptions = {"No Significant Medical History", "Hypertension", "Diabetes", "Asthma", "Allergies", "Heart Disease", "Other"};
        medicalHistoryComboBox = new JComboBox<>(medicalHistoryOptions);
        urgencyComboBox = new JComboBox<>(new String[]{"Normal", "Urgent"});

        // Adding new buttons for Waiting List functionality
        addPatientButton = new JButton("Add Patient");
        scheduleAppointmentButton = new JButton("Schedule Appointment");
        cancelAppointmentButton = new JButton("Cancel Appointment");
        generateReportButton = new JButton("Generate Report");
        searchButton = new JButton("Search");
        billingButton = new JButton("Billing");
        addToWaitingListButton = new JButton("Add to Waiting List");
        removeFromWaitingListButton = new JButton("Remove from Waiting List");
        completeButton = new JButton("Complete Appointment");

        // Styling buttons with colors
        styleButton(addPatientButton, Color.GREEN);
        styleButton(scheduleAppointmentButton, Color.BLUE);
        styleButton(cancelAppointmentButton, Color.RED);
        styleButton(generateReportButton, Color.BLUE);
        styleButton(searchButton, Color.BLACK);
        styleButton(billingButton, Color.BLUE);
        styleButton(addToWaitingListButton, Color.BLACK);
        styleButton(removeFromWaitingListButton, Color.BLUE);
        styleButton(completeButton, Color.green);

        // Form fields and buttons
        formPanel.add(new JLabel("Search Patient:"));
        formPanel.add(searchField);
        formPanel.add(searchButton);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);
        formPanel.add(new JLabel("Medical History:"));
        formPanel.add(medicalHistoryComboBox);
        formPanel.add(new JLabel("Amount:"));
        formPanel.add(amountField);
        formPanel.add(new JLabel("Urgency:"));
        formPanel.add(urgencyComboBox);  // Add the Urgency ComboBox before the Add Patient button
        formPanel.add(addPatientButton);  // Add the Add Patient button after the Urgency ComboBox
        // Patient Table Panel setup
        patientPanel = new JPanel(new BorderLayout());
        patientTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Contact", "Medical History", "Amount", "Urgency"}, 0);

        patientTable = new JTable(patientTableModel);
        JScrollPane patientScrollPane = new JScrollPane(patientTable);
        patientPanel.add(patientScrollPane, BorderLayout.CENTER);
        patientPanel.setBorder(BorderFactory.createTitledBorder("Patients"));
        styleTable(patientTable);

        // Appointment Table Panel setup
        appointmentPanel = new JPanel(new BorderLayout());
        appointmentTableModel = new DefaultTableModel(new Object[]{"Appointment ID", "Patient ID", "Date", "Time", "Status"}, 0);
        appointmentTable = new JTable(appointmentTableModel);
        JScrollPane appointmentScrollPane = new JScrollPane(appointmentTable);
        appointmentPanel.add(appointmentScrollPane, BorderLayout.CENTER);
        appointmentPanel.setBorder(BorderFactory.createTitledBorder("Appointments"));
        styleTable(appointmentTable);

        // Waiting List Table Panel setup
        waitingListPanel = new JPanel(new BorderLayout());
        waitingListTableModel = new DefaultTableModel(new Object[]{"Patient ID", "Name", "Urgency"}, 0);

        waitingListTable = new JTable(waitingListTableModel);
        JScrollPane waitingListScrollPane = new JScrollPane(waitingListTable);
        waitingListPanel.add(waitingListScrollPane, BorderLayout.CENTER);
        waitingListPanel.setBorder(BorderFactory.createTitledBorder("Waiting List"));
        styleTable(waitingListTable);

        // Button Panel with action buttons for appointments and waiting list
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(scheduleAppointmentButton);
        buttonPanel.add(cancelAppointmentButton);
        buttonPanel.add(generateReportButton);
        buttonPanel.add(billingButton);
        buttonPanel.add(addToWaitingListButton);
        buttonPanel.add(removeFromWaitingListButton);
        buttonPanel.add(completeButton);

        // Layout for tables
        JPanel tablesPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        tablesPanel.add(patientPanel);
        tablesPanel.add(appointmentPanel);
        tablesPanel.add(waitingListPanel);

        // Adding components to the main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(tablesPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        // Adding the main panel to the frame
        frame.add(mainPanel);
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Button Action listeners
        addPatientButton.addActionListener(this::addPatient);
        scheduleAppointmentButton.addActionListener(this::scheduleAppointment);
        cancelAppointmentButton.addActionListener(this::cancelAppointment);
        generateReportButton.addActionListener(this::generateReport);
        searchButton.addActionListener(this::searchPatient);
        billingButton.addActionListener(this::showBilling);
        addToWaitingListButton.addActionListener(this::addToWaitingList);
        removeFromWaitingListButton.addActionListener(this::removeFromWaitingList);
        completeButton.addActionListener(this::completeAppointment);
    }


    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
    }

    private void addPatient(ActionEvent e) {
        // Check if the current number of patients has reached the maximum capacity
        if (patientTableModel.getRowCount() >= maxCapacity) {
            // Show a message that capacity is full
            JOptionPane.showMessageDialog(frame, "Cannot add more patients. The system has reached its maximum capacity of " + maxCapacity + " patients.");

            // Add the patient to the waiting list instead
            String name = nameField.getText();
            String ageText = ageField.getText();
            String contact = contactField.getText();
            String medicalHistory = (String) medicalHistoryComboBox.getSelectedItem();
            String amountText = amountField.getText();
            String urgency = (String) urgencyComboBox.getSelectedItem();

            if (name.isEmpty() || ageText.isEmpty() || contact.isEmpty() || amountText.isEmpty() || urgency.isEmpty() || medicalHistory.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill out all required fields.");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                double amount = Double.parseDouble(amountText);

                boolean isUrgent = urgency.equalsIgnoreCase("Urgent"); // Convert urgency string to boolean

                int patientID = pms.addPatient(name, age, contact, amount, medicalHistory, isUrgent);

                // Store patient information in the map
                patientInfoMap.put(patientID, new Object[]{patientID, name, age, contact, medicalHistory, amount, urgency});

                // Add the new patient to the waiting list
                if (isUrgent) {
                    waitingListTableModel.insertRow(0, new Object[]{patientID, name, "Waiting"});
                } else {
                    waitingListTableModel.addRow(new Object[]{patientID, name, "Waiting"});
                }

                // Clear the input fields
                clearPatientFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid age or amount. Please enter valid numbers.");
            }
        } else {
            // Proceed to add the patient to the table if there is space
            String name = nameField.getText();
            String ageText = ageField.getText();
            String contact = contactField.getText();
            String medicalHistory = (String) medicalHistoryComboBox.getSelectedItem();
            String amountText = amountField.getText();
            String urgency = (String) urgencyComboBox.getSelectedItem();

            if (name.isEmpty() || ageText.isEmpty() || contact.isEmpty() || amountText.isEmpty() || urgency.isEmpty() || medicalHistory.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill out all required fields.");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                double amount = Double.parseDouble(amountText);

                boolean isUrgent = urgency.equalsIgnoreCase("Urgent"); // Convert urgency string to boolean

                int patientID = pms.addPatient(name, age, contact, amount, medicalHistory, isUrgent);

                // Store patient information in the map
                patientInfoMap.put(patientID, new Object[]{patientID, name, age, contact, medicalHistory, amount, urgency});

                // Add the new patient to the table in the correct position based on urgency
                if (isUrgent) {
                    patientTableModel.insertRow(0, new Object[]{patientID, name, age, contact, medicalHistory, amount, urgency});
                } else {
                    patientTableModel.addRow(new Object[]{patientID, name, age, contact, medicalHistory, amount, urgency});
                }

                // Clear the input fields
                clearPatientFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid age or amount. Please enter valid numbers.");
            }
        }
    }


    private void clearPatientFields() {
        nameField.setText("");
        ageField.setText("");
        contactField.setText("");
        medicalHistoryComboBox.setSelectedIndex(0);
        amountField.setText("");
    }

    private void searchPatient(ActionEvent e) {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Enter a name to search.");
            return;
        }

        for (int i = 0; i < patientTableModel.getRowCount(); i++) {
            String name = patientTableModel.getValueAt(i, 1).toString().toLowerCase();
            if (name.contains(searchText)) {
                patientTable.setRowSelectionInterval(i, i);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "No patient found with the given name.");
    }



    private void scheduleAppointment(ActionEvent e) {
        int patientRow = patientTable.getSelectedRow();
        if (patientRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a patient.");
            return;
        }

        int patientID = (Integer) patientTable.getValueAt(patientRow, 0);
        String date = JOptionPane.showInputDialog(frame, "Enter Appointment Date (yyyy-mm-dd):");
        String time = JOptionPane.showInputDialog(frame, "Enter Appointment Time (hh:mm):");

        if (date == null || date.trim().isEmpty() || time == null || time.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Date and time cannot be empty.");
            return;
        }

        try {
            int generatedAppointmentID = pms.scheduleAppointment(patientID, date, time);
            Appointment appointment = new Appointment(generatedAppointmentID, patientID, date, time, "Scheduled");
            appointmentTableModel.addRow(new Object[]{generatedAppointmentID, patientID, date, time, "Scheduled"});
            JOptionPane.showMessageDialog(frame, "Appointment scheduled successfully.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error scheduling the appointment: " + ex.getMessage());
        }
    }

    private void cancelAppointment(ActionEvent e) {
        int row = appointmentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an appointment to cancel.");
            return;
        }

        int appointmentID = (Integer) appointmentTable.getValueAt(row, 0);
        pms.cancelAppointment(appointmentID);
        appointmentTableModel.setValueAt("Cancelled", row, 4);
    }

    // Method to add a patient to the waiting list and remove from the patient table
    private void addToWaitingList(ActionEvent e) {
        int selectedRow = patientTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a patient to add to the waiting list.");
            return;
        }

        // Retrieve patient data from the patient table
        Object patientIDObj = patientTable.getValueAt(selectedRow, 0);  // Assuming Patient ID is in column 0
        Object patientNameObj = patientTable.getValueAt(selectedRow, 1);  // Assuming Name is in column 1
        Object urgencyObj = patientTable.getValueAt(selectedRow, 6);  // Assuming Urgency is in column 6

        // Validate patient data
        if (patientIDObj == null || patientNameObj == null || urgencyObj == null) {
            JOptionPane.showMessageDialog(frame, "One or more fields are empty. Please check the patient data.");
            return;
        }

        int patientID = Integer.parseInt(patientIDObj.toString());
        String patientName = patientNameObj.toString().trim();
        String urgency = urgencyObj.toString().trim();

        // Create patient row to add to the waiting list
        Object[] waitingListRow = new Object[]{patientID, patientName, urgency, "No"};

        // Add to waiting list (based on urgency)
        if (urgency.equalsIgnoreCase("Urgent")) {
            waitingListTableModel.insertRow(0, waitingListRow);  // Insert urgent patients at the top
        } else {
            waitingListTableModel.addRow(waitingListRow);  // Insert non-urgent patients at the bottom
        }

        // Remove from patient table after adding to the waiting list
        patientTableModel.removeRow(selectedRow);

        // Optionally, refresh the tables
        waitingListTable.repaint();
        patientTable.repaint();

        JOptionPane.showMessageDialog(frame, "Patient added to the waiting list and removed from the patient table.");
    }

    // Method to remove a patient from the waiting list and keep the patient info unchanged in the patient table
    // Method to remove a patient from the waiting list and keep the patient info unchanged in the patient table
    private void removeFromWaitingList(ActionEvent e) {
        int selectedRow = waitingListTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a patient to remove from the waiting list.");
            return;
        }

        // Retrieve patient ID from the waiting list
        Object patientIDObj = waitingListTable.getValueAt(selectedRow, 0);
        if (patientIDObj == null) {
            JOptionPane.showMessageDialog(frame, "Invalid patient selected.");
            return;
        }

        int patientID = Integer.parseInt(patientIDObj.toString());

        // Retrieve full patient details from the map
        Object[] patientInfo = patientInfoMap.get(patientID);
        if (patientInfo == null) {
            JOptionPane.showMessageDialog(frame, "Patient information not found.");
            return;
        }

        // Add the patient back to the patient table with full details
        boolean isUrgent = patientInfo[6].toString().equalsIgnoreCase("Urgent");
        if (isUrgent) {
            patientTableModel.insertRow(0, patientInfo);
        } else {
            patientTableModel.addRow(patientInfo);
        }

        // Remove the patient from the waiting list
        waitingListTableModel.removeRow(selectedRow);

        // Refresh the tables
        waitingListTable.repaint();
        patientTable.repaint();

        JOptionPane.showMessageDialog(frame, "Patient removed from the waiting list and added back to the patient table.");
    }



    private void generateReport(ActionEvent e) {
        String report = pms.generatePatientReport();
        JOptionPane.showMessageDialog(frame, report);
    }

    private void styleTable(JTable table) {
        // Set general font and row height
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);

        // Set selection mode for single row selection
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Table color customization
        table.setBackground(new Color(240, 240, 240)); // Light Gray background
        table.setForeground(Color.BLACK); // Text color

        // Customize table header
        table.getTableHeader().setBackground(new Color(60, 60, 60)); // Dark header
        table.getTableHeader().setForeground(Color.WHITE); // White text on header

        // Set row selection color (when selecting a row)
        table.setSelectionBackground(new Color(65, 105, 225)); // Selected row's background (blue)
        table.setSelectionForeground(Color.WHITE); // White text on selected row

        // Set the alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Alternating row colors
                if (row % 2 == 0) {
                    c.setBackground(new Color(255, 255, 255)); // White for even rows
                } else {
                    c.setBackground(new Color(243, 243, 243)); // Light lavender for odd rows
                }

                if (isSelected) {
                    c.setBackground(new Color(65, 105, 225)); // Highlighted color (blue) for selected row
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        });

        // Custom column header renderer for more style (if needed)
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16)); // Set bold header text

        // Apply color alignment to center (optional)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void completeAppointment(ActionEvent e) {
        int row = appointmentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an appointment to mark as completed.");
            return;
        }

        int appointmentID = (Integer) appointmentTable.getValueAt(row, 0);
        int patientID = (Integer) appointmentTable.getValueAt(row, 1);  // Get Patient ID
        String status = "Completed";

        // Update the appointment status in Patient Management System (PMS)
        pms.updateAppointmentStatus(appointmentID, status);

        // Update the appointment status in the appointment table
        appointmentTableModel.setValueAt(status, row, 4);
        JOptionPane.showMessageDialog(frame, "Appointment marked as completed.");

        // Now remove from the waiting list table (if the patient is present there)
        removeFromWaitingList(patientID);
    }
    private void showBilling(ActionEvent e) {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a patient to view billing.");
            return;
        }

        int patientID = (Integer) patientTable.getValueAt(selectedRow, 0);
        double bill = pms.getPatientBill(patientID);
        JOptionPane.showMessageDialog(frame, "Total Bill for Patient ID " + patientID + ": $" + bill);
    }


    private void removeFromWaitingList(int patientID) {
        for (int i = 0; i < waitingListTableModel.getRowCount(); i++) {
            int waitingPatientID = (Integer) waitingListTableModel.getValueAt(i, 0);  // Get Patient ID from the waiting list
            if (waitingPatientID == patientID) {
                // Remove this patient from the waiting list
                waitingListTableModel.removeRow(i);
                return;  // Exit once we remove the patient from the list
            }
        }
    }
    private void moveFromWaitingListToPatientTable(ActionEvent e) {
        int selectedRow = waitingListTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a patient to move to the patient table.");
            return;
        }

        // Retrieve patient data from the waiting list table
        Object patientIDObj = waitingListTable.getValueAt(selectedRow, 0);  // Assuming Patient ID is in column 0
        Object patientNameObj = waitingListTable.getValueAt(selectedRow, 1);  // Assuming Name is in column 1
        Object urgencyObj = waitingListTable.getValueAt(selectedRow, 2);  // Assuming Urgency is in column 2

        if (patientIDObj == null || patientNameObj == null || urgencyObj == null) {
            JOptionPane.showMessageDialog(frame, "One or more fields are empty. Please check the patient data.");
            return;
        }

        // Attempt to cast the retrieved values to the expected types
        int patientID = -1;  // Default value in case of error
        String patientName = "";
        String urgency = "";

        try {
            // Cast Patient ID to Integer
            patientID = Integer.parseInt(patientIDObj.toString());  // Safe conversion to int

            // Cast Patient Name and Urgency to String
            patientName = patientNameObj.toString().trim();  // Trim whitespace for patient name
            urgency = urgencyObj.toString().trim();  // Trim whitespace for urgency
        } catch (NumberFormatException ex) {
            // Handle casting errors for numeric fields
            JOptionPane.showMessageDialog(frame, "Error processing Patient ID: " + ex.getMessage());
            return;
        } catch (Exception ex) {
            // Handle any other unexpected errors
            JOptionPane.showMessageDialog(frame, "Error processing patient data: " + ex.getMessage());
            return;
        }

        // Remove the patient from the waiting list table
        waitingListTableModel.removeRow(selectedRow);

        // Add the patient back to the patient table
        Object[] patientRow = new Object[]{patientID, patientName, "Unknown", "Unknown", "Unknown", "Unknown", urgency};  // Assuming these columns exist in the patient table

        if (urgency.equalsIgnoreCase("Urgent")) {
            // If the patient is urgent, insert at the top of the patient table
            patientTableModel.insertRow(0, patientRow);
        } else {
            // If the patient is not urgent, add them at the bottom of the patient table
            patientTableModel.addRow(patientRow);
        }

        // Optionally, update the table if needed
        waitingListTable.repaint();  // Refresh the waiting list table view
        patientTable.repaint();  // Refresh the patient table view

        JOptionPane.showMessageDialog(frame, "Patient moved to the patient table.");
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientManagementGUI());
    }
}