import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    public void generatePatientReport(List<Patient> patients) {
        // Sort patients by name (using Merge Sort for demonstration)
        mergeSort(patients, 0, patients.size() - 1);
        System.out.println("--- Patient Report ---");
        for (Patient patient : patients) {
            System.out.println(patient.getPatientInfo());
        }
    }

    // Generates the billing report
    public void generateBillingReport(List<Billing> billings, boolean ascending) {
        // Sort billings by patient name
        mergeSortBilling(billings, 0, billings.size() - 1);
        System.out.println("--- Billing Report ---");
        for (Billing billing : billings) {
            System.out.println("Patient: " + billing.getPatientName() +
                    ", Total Bill: $" + billing.getBillingAmount() +
                    ", Paid: " + billing.getPaymentStatus());
        }
    }

    // Merge Sort for patients (based on name)
    private void mergeSort(List<Patient> patients, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(patients, left, mid);
            mergeSort(patients, mid + 1, right);
            merge(patients, left, mid, right);
        }
    }

    // Merge function for sorting patients
    private void merge(List<Patient> patients, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Patient> leftList = new ArrayList<>(patients.subList(left, mid + 1));
        List<Patient> rightList = new ArrayList<>(patients.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftList.get(i).getName().compareTo(rightList.get(j).getName()) <= 0) {
                patients.set(k++, leftList.get(i++));
            } else {
                patients.set(k++, rightList.get(j++));
            }
        }

        while (i < n1) {
            patients.set(k++, leftList.get(i++));
        }

        while (j < n2) {
            patients.set(k++, rightList.get(j++));
        }
    }

    // Merge Sort for billings (based on patient name)
    private void mergeSortBilling(List<Billing> billings, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortBilling(billings, left, mid);
            mergeSortBilling(billings, mid + 1, right);
            mergeBilling(billings, left, mid, right);
        }
    }

    // Merge function for sorting billings
    private void mergeBilling(List<Billing> billings, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Billing> leftList = new ArrayList<>(billings.subList(left, mid + 1));
        List<Billing> rightList = new ArrayList<>(billings.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftList.get(i).getPatientName().compareTo(rightList.get(j).getPatientName()) <= 0) {
                billings.set(k++, leftList.get(i++));
            } else {
                billings.set(k++, rightList.get(j++));
            }
        }

        while (i < n1) {
            billings.set(k++, leftList.get(i++));
        }

        while (j < n2) {
            billings.set(k++, rightList.get(j++));
        }
    }
}
