package uy.edu.um.prog2.adt.Heap;

import adt.Heap.EmptyHeapException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {



    @Test
    void insert() {
        Heap<Integer> heap = new Heap<>(10, false);
        assertTrue(true, String.valueOf(heap.isEmpty()));
        heap.insert(5);
        heap.insert(2);
        assertFalse(false, String.valueOf(heap.isEmpty()));


    }
     @Test
    void deleteLast()throws EmptyHeapException {
         Heap<Integer> heap = new Heap<>(10, false);
         heap.insert(5);
         heap.deleteLast();
         assertTrue(true, String.valueOf(heap.isEmpty()));
     }

    @Test
    void deleteMin() throws EmptyHeapException {
        Heap<Integer> heap = new Heap<>(10, false);
        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);
        heap.insert(4);

        assertEquals(1, heap.deleteMin());
        assertEquals(2, heap.deleteMin());
        assertEquals(4, heap.deleteMin());
        assertEquals(5, heap.deleteMin());
        assertEquals(8, heap.deleteMin());



    }

    @Test
    void deleteMax() throws EmptyHeapException {
        Heap<Integer> heap = new Heap<>(10, true);
        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);
        heap.insert(4);

        assertEquals(8, heap.deleteMax());
        assertEquals(5, heap.deleteMax());
        assertEquals(4, heap.deleteMax());
        assertEquals(2, heap.deleteMax());


    }

    @Test
    void isEmpty() {
        Heap<Integer> heap = new Heap<>(10, true);
        assertTrue(heap.isEmpty());

        heap.insert(5);
        assertFalse(heap.isEmpty());
    }

    @Test
    void getMin() {
        Heap<Integer> heap = new Heap<>(10, false);
        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);
        heap.insert(4);

        assertEquals(1, heap.getMin());
    }

    @Test
    void getMax() {
        Heap<Integer> heap = new Heap<>(10, true);
        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);
        heap.insert(4);

        assertEquals(8, heap.getMax());
    }
    @Test
    void heapException(){
        Heap<Integer> heap = new Heap<>(5, false);
        try {
            heap.deleteMax();
            fail("el heap esta vacio ");
        } catch (EmptyHeapException e) {

        }
    }

}