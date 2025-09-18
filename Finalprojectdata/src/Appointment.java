import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Appointment {
    private int appointmentID;
    private int patientID;
    private LocalDate date; // Use LocalDate for proper date handling
    private String time;
    private String status; // Status can be "Scheduled", "Completed", or "Cancelled"

    // Constructor to initialize the appointment details
    public Appointment(int appointmentID, int patientID, String date, String time, String status) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        setDate(date); // Use the setter to validate and set the date
        this.time = time;
        this.status = status;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getPatientID() {
        return patientID;
    }

    public LocalDate getDate() {
        return date;
    }

    // Setter for Appointment Date with validation
    public void setDate(String date) {
        try {
            this.date = LocalDate.parse(date); // Parse the date string
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD.");
        }
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateAppointmentDetails(String newDate, String newTime, String newStatus) {
        if (newDate != null && !newDate.isEmpty()) {
            setDate(newDate); // Validate and set new date
        }
        if (newTime != null && !newTime.isEmpty()) {
            this.time = newTime;
        }
        if (newStatus != null && !newStatus.isEmpty()) {
            this.status = newStatus;
        }
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentID +
                ", Patient ID: " + patientID +
                ", Date: " + date +
                ", Time: " + time +
                ", Status: " + status;
    }

    public boolean isScheduled() {
        return "Scheduled".equalsIgnoreCase(this.status);
    }

    public boolean isCompleted() {
        return "Completed".equalsIgnoreCase(this.status);
    }

    public boolean isCancelled() {
        return "Cancelled".equalsIgnoreCase(this.status);
    }
}
