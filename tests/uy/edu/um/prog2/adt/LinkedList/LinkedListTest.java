package uy.edu.um.prog2.adt.LinkedList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    Mylist<Integer> listll = new LinkedList<>();

    @Test
    void add() {
        listll.add(10);
        assertEquals(listll.size(), 1);
    }

    @Test
    void get() {
    }

    @Test
    void remove() {
        listll.add(10);
        listll.remove(10);
        assertEquals(listll.size(), 0);
    }

    @Test
    void size() {
        listll.add(10);
        listll.add(9);
        assertEquals(listll.size(), 2);
    }

    @Test
    void contains() {
        listll.add(10);
        assertTrue(true, String.valueOf(listll.contains(10)));
    }
}