import java.util.ArrayList;
import java.util.NoSuchElementException;

public class PowerOfTwoMaxHeap {
    private ArrayList<Integer> heap;
    private int power; // Represents the 'k' in 2^k children

    public PowerOfTwoMaxHeap(int power) {
        if (power < 0) {
            throw new IllegalArgumentException("Power must be non-negative.");
        }
        this.heap = new ArrayList<>();
        this.power = power;
    }

    // Helper method to get the parent index
    private int getParentIndex(int index) {
        return (index - 1) / (1 << power); // (1 << power) is 2^power
    }

    // Helper method to get the child index
    private int getChildIndex(int parentIndex, int childNumber) {
        return (parentIndex * (1 << power)) + 1 + childNumber;
    }

    // Method to insert an element into the heap
    public void insert(int value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }

    // Method to pop the maximum element from the heap
    public int popMax() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        int maxValue = heap.get(0);
        int lastValue = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, lastValue);
            heapifyDown(0);
        }
        return maxValue;
    }

    // Helper method to maintain heap property on insertion
    private void heapifyUp(int index) {
        int parentIndex = getParentIndex(index);
        while (index > 0 && heap.get(index) > heap.get(parentIndex)) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = getParentIndex(index);
        }
    }

    // Helper method to maintain heap property on removal
    private void heapifyDown(int index) {
        int maxIndex = index;
        while (true) {
            int leftmostChildIndex = getChildIndex(index, 0);
            for (int i = 0; i < (1 << power); i++) {
                int currentChildIndex = leftmostChildIndex + i;
                if (currentChildIndex < heap.size() && heap.get(currentChildIndex) > heap.get(maxIndex)) {
                    maxIndex = currentChildIndex;
                }
            }
            if (maxIndex == index) break;
            swap(index, maxIndex);
            index = maxIndex;
        }
    }

    // Helper method to swap elements in the heap
    private void swap(int index1, int index2) {
        int temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    // Method to check if the heap is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Method to get the size of the heap
    public int size() {
        return heap.size();
    }

    // Method to get the maximum element in the heap
    public int peekMax() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return heap.get(0);
    }

    public static void main(String[] args) {
        PowerOfTwoMaxHeap heap1 = new PowerOfTwoMaxHeap(1); // Binary heap
        PowerOfTwoMaxHeap heap2 = new PowerOfTwoMaxHeap(2); // Each parent has 4 children

        heap1.insert(10);
        heap1.insert(20);
        heap1.insert(5);

        System.out.println("Max value (heap1): " + heap1.popMax()); // Should print 20
        System.out.println("Max value (heap1): " + heap1.popMax()); // Should print 10
        System.out.println("Max value (heap1): " + heap1.popMax()); // Should print 5

        heap2.insert(10);
        heap2.insert(20);
        heap2.insert(5);

        System.out.println("Max value (heap2): " + heap2.popMax()); // Should print 20
        System.out.println("Max value (heap2): " + heap2.popMax()); // Should print 10
        System.out.println("Max value (heap2): " + heap2.popMax()); // Should print 5
    }
}
