import java.util.*;

public class PatientManagementSystem {
    private List<Patient> patients;
    private PatientBST bst;
    private List<Appointment> appointments;
    private List<Billing> billings;
    private Set<Integer> generatedPatientIDs;
    private Set<Integer> generatedAppointmentIDs;
    private List<Patient> waitingList;
    private ReportGenerator reportGenerator;



    // Instance of the ReportGenerator to generate billing report

    public PatientManagementSystem() {
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
        billings = new ArrayList<>();
        generatedPatientIDs = new HashSet<>();
        generatedAppointmentIDs = new HashSet<>();
        reportGenerator = new ReportGenerator(); // Initialize the report generator
    }

    // Centralized Unique ID Generator (Generalized)
    private int generateUniqueID(Set<Integer> usedIDs, int digits) {
        Random random = new Random();
        int id;
        int range = (int) Math.pow(10, digits); // Ensures the ID has the specified number of digits

        do {
            id = (int) (random.nextInt(range) + Math.pow(10, digits - 1)); // ID is 'digits' long
        } while (usedIDs.contains(id));
        usedIDs.add(id);
        return id;
    }

    // Get Patient ID
    public int getPatientId(Patient p) {
        return p.getPatientID();
    }

    // Add Patient with Unique ID and proper billing initialization
    public int addPatient(String name, int age, String contactInfo, double amount, String medicalHistory, boolean isUrgent) {
        // Generate a unique 6-digit ID for the new patient
        int generatedID = generateUniqueID(generatedPatientIDs, 6);

        // Create the patient with the generated ID and details, including urgency flag
        Patient patient = new Patient(generatedID, name, age, contactInfo, medicalHistory, amount, isUrgent);
        patients.add(patient);

        // Initialize the initial billing amount based on whether it's the first-time visit
        double initialBillingAmount = amount;
        Billing billing = new Billing(patient, initialBillingAmount);
        billings.add(billing);

        // Insert into Binary Search Tree (BST) or handle urgency by sorting patients
        if (bst == null) {
            bst = new PatientBST();
        }
        bst.insert(patient);

        // Print successful addition message
        System.out.println("Patient added successfully with ID: " + generatedID);
        return generatedID;  // Return the generated ID
    }

    // Schedule Appointment with Unique 4-digit ID and return it
    public int scheduleAppointment(int patientID, String date, String time) {
        Patient patient = getPatientByID(patientID);
        if (patient == null) {
            System.out.println("Invalid patient ID.");
            return -1;  // Return -1 if the patient ID is invalid
        }

        // Generate a unique 4-digit appointment ID
        int generatedAppointmentID = generateUniqueID(generatedAppointmentIDs, 4);

        // Create and add the appointment with the unique ID
        Appointment appointment = new Appointment(generatedAppointmentID, patientID, date, time, "Scheduled");
        appointments.add(appointment);

        // Print the successful appointment scheduling message
        System.out.println("Appointment scheduled successfully with ID: " + generatedAppointmentID);
        return generatedAppointmentID;  // Return the generated appointment ID
    }

    // Cancel Appointment
    public void cancelAppointment(int appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                appointment.setStatus("Cancelled");
                System.out.println("Appointment ID " + appointmentID + " has been cancelled.");
                return;
            }
        }
        System.out.println("Appointment ID " + appointmentID + " not found.");
    }

    // Get List of Patients
    public List<Patient> getPatientList() {
        return patients;
    }

    // Get List of Appointments
    public List<Appointment> getAppointmentList() {
        return appointments;
    }

    // Add Payment for Billing
    public void addPayment(int patientID, double amount) {
        for (Billing billing : billings) {
            if (billing.getPatientID() == patientID) {
                billing.addPayment("2024-12-01", amount); // Example date for payment
                System.out.println("Payment of " + amount + " added for Patient ID: " + patientID);
                return;
            }
        }
        System.out.println("Patient ID " + patientID + " not found in billing records.");
    }

    public double getPatientBill(int patientID) {
        for (Billing billing : billings) {
            if (billing.getPatientID() == patientID) {
                return billing.getTotalBilledAmount();
            }
        }
        System.out.println("Patient with ID " + patientID + " not found.");
        return -1.0; // Return -1.0 to indicate patient not found or no bill
    }

    // Generate Patient Report
    public String generatePatientReport() {
        StringBuilder report = new StringBuilder();
        report.append("Patient Report:\n");
        report.append("----------------------------\n");

        for (Patient patient : patients) {
            report.append("Patient ID: ").append(patient.getPatientID()).append("\n");
            report.append("Name: ").append(patient.getName()).append("\n");
            report.append("Age: ").append(patient.getAge()).append("\n");
            report.append("Contact: ").append(patient.getContact()).append("\n");
            report.append("Medical History: ").append(patient.getMedicalHistory()).append("\n");
            report.append("First-time Visitor: ").append(patient.getAmount()).append("\n");

            report.append("Appointments:\n");
            for (Appointment appointment : appointments) {
                if (appointment.getPatientID() == patient.getPatientID()) {
                    report.append("  - Appointment ID: ").append(appointment.getAppointmentID())
                            .append(", Date: ").append(appointment.getDate())
                            .append(", Time: ").append(appointment.getTime())
                            .append(", Status: ").append(appointment.getStatus()).append("\n");
                }
            }

            report.append("Billing:\n");
            for (Billing billing : billings) {
                if (billing.getPatientID() == patient.getPatientID()) {
                    report.append("  - Total Amount Due: ").append(billing.getTotalBilledAmount()).append("\n");
                    for (Billing.Payment payment : billing.getPayments()) {
                        report.append("    Payment on ").append(payment.getDate()).append(": ")
                                .append(payment.getAmount()).append("\n");
                    }
                }
            }

            report.append("----------------------------\n");
        }

        return report.toString();
    }
    // Get Patient by ID
    public Patient getPatientByID(int patientID) {
        return bst.search(patientID);
    }

    // Update Appointment Status
    public void updateAppointmentStatus(int appointmentID, String newStatus) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                appointment.setStatus(newStatus);
                System.out.println("Appointment ID " + appointmentID + " status updated to: " + newStatus);
                return;
            }
        }
        System.out.println("Appointment ID " + appointmentID + " not found.");
    }
    // Generate Billing Report through ReportGenerator
    public void generateBillingReport(boolean ascending) {
        reportGenerator.generateBillingReport(billings, ascending);
    }
}

