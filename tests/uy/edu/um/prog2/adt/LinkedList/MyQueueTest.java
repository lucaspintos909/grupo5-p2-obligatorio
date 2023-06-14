package uy.edu.um.prog2.adt.LinkedList;

import org.junit.jupiter.api.Test;
import uy.edu.um.prog2.adt.Queue.EmptyQueueException;
import uy.edu.um.prog2.adt.Queue.MyQueue;

import static org.junit.jupiter.api.Assertions.*;

class MyQueueTest {
    MyQueue<Integer> queuell = new LinkedList<>();

    @Test
    void enqueue() {
        queuell.enqueue(10);
        assertEquals(queuell.size(),1);

    }

    @Test
    void dequeue()throws EmptyQueueException {
        queuell.enqueue(10);
        queuell.dequeue();
        assertEquals(queuell.size(),0);
        try {
            queuell.dequeue();
        }catch (EmptyQueueException e){
            assertEquals(queuell.size(),0);

        }
    }

    @Test
    void contains() {
        queuell.enqueue(10);
        assertTrue(true, String.valueOf(queuell.contains(10)));
    }

    @Test
    void size() {
        queuell.enqueue(10);
        queuell.enqueue(9);
        assertEquals(queuell.size(),2);
    }

    @Test
    void get() {
        queuell.enqueue(7);
        System.out.println(queuell.get(0));
        assertEquals(queuell.get(0),7);
    }



    @Test
    void empty() {
        queuell.enqueue(10);
        queuell.enqueue(9);
        assertFalse(false, String.valueOf(queuell.Empty()));

    }

}