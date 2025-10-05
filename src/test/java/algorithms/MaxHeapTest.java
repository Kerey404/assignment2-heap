package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MaxHeapTest {

    @Test
    public void testInsertAndExtract() {
        MaxHeap heap = new MaxHeap();
        heap.insert(5);
        heap.insert(10);
        heap.insert(3);
        assertEquals(10, heap.extractMax());
        assertEquals(5, heap.extractMax());
        assertEquals(3, heap.extractMax());
    }

    @Test
    public void testIncreaseKey() {
        MaxHeap heap = new MaxHeap();
        heap.insert(10);
        heap.insert(20);
        int idx = heap.indexOf(10);
        heap.increaseKey(idx, 25);
        assertEquals(25, heap.extractMax());
    }

    @Test
    public void testMerge() {
        MaxHeap h1 = new MaxHeap();
        MaxHeap h2 = new MaxHeap();
        h1.insert(1);
        h1.insert(2);
        h2.insert(3);
        h2.insert(4);
        h1.merge(h2);
        assertEquals(4, h1.extractMax());
    }

    // 🔹 Новые тесты, добавь их ниже:
    @Test
    public void testMergeWithEmpty() {
        MaxHeap a = new MaxHeap();
        a.insert(5); a.insert(3);
        MaxHeap empty = new MaxHeap();
        a.merge(empty);
        assertEquals(2, a.size());
    }

    @Test
    public void testSelfMergeThrows() {
        MaxHeap a = new MaxHeap();
        a.insert(1);
        assertThrows(IllegalArgumentException.class, () -> a.merge(a));
    }

    @Test
    public void testEnsureCapacityAndLargeInsert() {
        MaxHeap h = new MaxHeap(2);
        for (int i = 0; i < 10000; i++) h.insert(i);
        assertEquals(10000, h.size());
    }
}
