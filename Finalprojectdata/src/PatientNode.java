import java.util.List;
public class PatientNode {
        Patient patient;
        PatientNode left, right;

        public PatientNode(Patient patient) {
            this.patient = patient;
            left = right = null;
        }

    public class PatientBST {
        private PatientNode root;

        public PatientBST() {
            root = null;
        }

        public void insert(Patient patient) {
            root = insertRec(root, patient);
        }

        private PatientNode insertRec(PatientNode root, Patient data) {
            if (root == null) {
                root = new PatientNode(data);
                return root;
            }
            if (data.getPatientID() < root.patient.getPatientID())
                root.left = insertRec(root.left, data);
            else if (data.getPatientID() > root.patient.getPatientID())
                root.right = insertRec(root.right, data);
            return root;
        }

        public Patient search(int patientID) {
            return searchRec(root, patientID);
        }

        private Patient searchRec(PatientNode root, int patientID) {
            if (root == null || root.patient.getPatientID() == patientID)
                return (root != null) ? root.patient : null;
            if (patientID < root.patient.getPatientID())
                return searchRec(root.left, patientID);
            return searchRec(root.right, patientID);
        }

        public void inOrderTraversal(List<Patient> patientList) {
            inOrderRec(root, patientList);
        }

        private void inOrderRec(PatientNode root, List<Patient> patientList) {
            if (root != null) {
                inOrderRec(root.left, patientList);
                patientList.add(root.patient);
                inOrderRec(root.right, patientList);
            }
        }
    }
}
