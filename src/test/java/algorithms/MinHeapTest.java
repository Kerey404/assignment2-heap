package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    public void testInsertAndExtractMin() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(5);
        heap.insert(20);
        heap.insert(1);

        assertEquals(4, heap.size());
        assertEquals(1, heap.peekMin());
        assertEquals(1, heap.extractMin());
        assertEquals(3, heap.size());
        assertEquals(5, heap.peekMin());
    }

    @Test
    public void testDecreaseKey() {
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(20);
        heap.insert(30);

        int idx = heap.indexOf(20);
        heap.decreaseKey(idx, 5);

        assertEquals(3, heap.size());
        assertEquals(5, heap.peekMin());
    }

    @Test
    public void testMerge() {
        MinHeap h1 = new MinHeap();
        MinHeap h2 = new MinHeap();

        h1.insert(10);
        h1.insert(30);
        h2.insert(5);
        h2.insert(15);

        h1.merge(h2);

        assertEquals(4, h1.size());
        assertEquals(5, h1.peekMin());
        assertEquals(5, h1.extractMin());
        assertEquals(10, h1.extractMin());
    }

    @Test
    public void testEmptyHeapThrows() {
        MinHeap heap = new MinHeap();
        assertThrows(Exception.class, heap::extractMin);
    }

    @Test
    public void testEnsureCapacity() {
        MinHeap heap = new MinHeap(2);
        heap.insert(3);
        heap.insert(1);
        heap.insert(2);
        assertEquals(3, heap.size());
        assertEquals(1, heap.peekMin());
    }
}
