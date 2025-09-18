import java.util.ArrayList;
import java.util.List;

public class Patient implements Comparable<Patient> {

    private int patientID;
    private int priority;
    private String name;
    private int age;
    private String contactInfo;
    private String medicalHistory;
    private List<String> visitRecords;
    private double amount;
    private boolean isUrgent;


    public Patient(int patientID, String name, int age, String contactInfo, String medicalHistory, double amount,boolean isUrgent) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.contactInfo = contactInfo;
        this.medicalHistory = medicalHistory;
        this.visitRecords = new ArrayList<>();
        this.amount = amount;// Initialize the first-time visitor flag
        this.isUrgent=isUrgent;
    }
    public boolean isUrgent() {
        return isUrgent;
    }


    // Setter for the urgency flag
    public void setUrgent(boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPriority(int priority){
        this.priority=priority;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getContactInfo() {
        return contactInfo;  // Getter for contact info
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public double getAmount() {
        return amount;  // Getter for the first-time visitor status
    }

    public void updateContactInfo(String newContact) {
        this.contactInfo = newContact;  // This updates the patient's contact info with the new value
    }

    public void addVisitRecord(String visit) {
        visitRecords.add(visit);
    }

    public String getPatientInfo() {
        return "ID: " + patientID + ", Name: " + name + ", Age: " + age + ", Contact: " + contactInfo;
    }

    @Override
    public int compareTo(Patient other) {
        return Integer.compare(this.patientID, other.patientID);
    }
    public int getPriority() {
        return priority;
    }

    // Get contact information as a char array
    public char[] getContact() {
        return contactInfo.toCharArray();  // Convert the contact info string to a character array and return it
    }
}
