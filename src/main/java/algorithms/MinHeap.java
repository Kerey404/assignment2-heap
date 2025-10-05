package algorithms;

import metrics.PerformanceTracker;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MinHeap {
    private int[] heap;
    private int size;
    private final PerformanceTracker metrics;
    private static final int DEFAULT_CAPACITY = 16;

    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MinHeap(int capacity) {
        if (capacity <= 0) capacity = DEFAULT_CAPACITY;
        this.heap = new int[capacity];
        this.size = 0;
        this.metrics = new PerformanceTracker();
        metrics.allocations++;
    }

    public MinHeap(int[] input) {
        Objects.requireNonNull(input);
        this.heap = Arrays.copyOf(input, input.length);
        this.size = input.length;
        this.metrics = new PerformanceTracker();
        metrics.allocations++;
        buildHeapBottomUp();
    }

    public PerformanceTracker getMetrics() {
        return metrics;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(int value) {
        ensureCapacity();
        heap[size] = value;
        metrics.arrayAccesses++;
        size++;
        siftUp(size - 1);
    }

    public int peekMin() {
        if (size == 0) throw new NoSuchElementException("Heap is empty");
        metrics.arrayAccesses++;
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) throw new NoSuchElementException("Heap is empty");
        int min = heap[0];
        metrics.arrayAccesses++;
        heap[0] = heap[size - 1];
        metrics.arrayAccesses++;
        size--;
        siftDown(0);
        return min;
    }

    public void decreaseKey(int idx, int newValue) {
        if (idx < 0 || idx >= size)
            throw new IndexOutOfBoundsException("Index out of range");
        metrics.arrayAccesses++;
        int old = heap[idx];
        if (newValue > old)
            throw new IllegalArgumentException("newValue must be <= old value");
        heap[idx] = newValue;
        metrics.arrayAccesses++;
        siftUp(idx);
    }

    public void merge(MinHeap other) {
        if (other == null) return;
        if (other == this) throw new IllegalArgumentException("Cannot merge heap with itself");
        int newSize = this.size + other.size;
        int[] newArr = Arrays.copyOf(this.heap, Math.max(newSize, this.heap.length));
        System.arraycopy(other.heap, 0, newArr, this.size, other.size);
        metrics.allocations++;
        this.heap = newArr;
        this.size = newSize;
        buildHeapBottomUp();
    }

    private void ensureCapacity() {
        if (size >= heap.length) {
            int newCap = heap.length * 2;
            heap = Arrays.copyOf(heap, newCap);
            metrics.allocations++;
        }
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            metrics.comparisons++;
            metrics.arrayAccesses += 2;
            if (heap[i] < heap[parent]) {
                swap(i, parent);
                i = parent;
            } else break;
        }
    }

    private void siftDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < size) {
                metrics.comparisons++;
                metrics.arrayAccesses += 2;
                if (heap[left] < heap[smallest]) smallest = left;
            }
            if (right < size) {
                metrics.comparisons++;
                metrics.arrayAccesses += 2;
                if (heap[right] < heap[smallest]) smallest = right;
            }
            if (smallest == i) break;
            swap(i, smallest);
            i = smallest;
        }
    }

    private void swap(int a, int b) {
        int t = heap[a];
        heap[a] = heap[b];
        heap[b] = t;
        metrics.swaps++;
        metrics.arrayAccesses += 4;
    }

    private void buildHeapBottomUp() {
        for (int i = parent(size - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    private int parent(int i) {
        if (i <= 0) return -1;
        return (i - 1) / 2;
    }

    public int indexOf(int value) {
        for (int i = 0; i < size; i++) {
            metrics.arrayAccesses++;
            if (heap[i] == value) return i;
        }
        return -1;
    }
}
