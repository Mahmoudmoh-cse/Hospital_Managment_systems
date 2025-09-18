import java.util.ArrayList;
import java.util.List;

class CustomPriorityQueue {
    private List<Patient> heap;

    public CustomPriorityQueue() {
        this.heap = new ArrayList<>();
        //better than array as it has dynamic size
    }

    public void add(Patient patient) {
        heap.add(patient);
        heapifyUp(heap.size() - 1);
    }

    public Patient poll() {
        if (heap.isEmpty()) {
            return null;
        }

        Patient highestPriorityPatient = heap.get(0);

        int lastIndex = heap.size() - 1;
        heap.set(0, heap.get(lastIndex));
        heap.remove(lastIndex);


        heapifyDown(0);

        return highestPriorityPatient;
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void printQueue() {
        if (heap.isEmpty()) {
            System.out.println("The waiting list is empty.");
        } else {
            System.out.println("Current waiting list:");
            for (Patient patient : heap) {
                System.out.println(patient);
            }
        }
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).getPriority() > heap.get(parentIndex).getPriority()) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }


    private void heapifyDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;

        int largest = index;

        if (leftChildIndex < heap.size() && heap.get(leftChildIndex).getPriority() > heap.get(largest).getPriority()) {
            largest = leftChildIndex;
        }

        if (rightChildIndex < heap.size() && heap.get(rightChildIndex).getPriority() > heap.get(largest).getPriority()) {
            largest = rightChildIndex;
        }

        if (largest != index) {

            swap(index, largest);
            heapifyDown(largest);
        }
    }


    private void swap(int i, int j) {
        Patient temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}


public class WaitingList {
    private CustomPriorityQueue waitingQueue;

    public WaitingList() {
        this.waitingQueue = new CustomPriorityQueue();
    }

    public void addToWaitList(Patient patient) {
        waitingQueue.add(patient);
        System.out.println(patient.getName() + " (Priority: " + patient.getPriority() + ") added to the waiting list.");
    }

    public Patient removeFromWaitList() {
        Patient nextPatient = waitingQueue.poll();
        if (nextPatient != null) {
            System.out.println(nextPatient.getName() + " removed from the waiting list.");
            return nextPatient;
        } else {
            System.out.println("Waiting list is empty.");
            return null;
        }
    }

    public boolean isEmpty() {
        return waitingQueue.isEmpty();
    }
    public void printWaitingList() {
        waitingQueue.printQueue();
    }
}
