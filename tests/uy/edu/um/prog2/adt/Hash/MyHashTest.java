package uy.edu.um.prog2.adt.Hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyHashTest {

    @Test
    void size() throws FullHashException {
        Hash<Integer, String> hash = new Hash<>(10);
        assertEquals(0,hash.size());
        hash.add(1, "One");
        hash.add(2, "Two");
        hash.add(3, "Three");

        assertEquals(3, hash.size());
    }

    @Test
    void isEmpty() throws FullHashException {

        Hash<Integer, String> hash = new Hash<>(10);
        assertTrue(true, String.valueOf(hash.isEmpty()));
        hash.add(1, "One");
        hash.add(2, "Two");
        hash.add(3, "Three");
        assertFalse(false, String.valueOf(hash.isEmpty()));
    }

    @Test
    void remove() throws FullHashException {
        Hash<Integer, String> hash = new Hash<>(10);
        hash.add(1, "One");
        hash.add(2, "Two");
        hash.add(3, "Three");
        hash.remove(2);
        assertNull(hash.get(2));
    }

    @Test
    void get() throws FullHashException {
        Hash<Integer, String> hash = new Hash<>(10);
        hash.add(1, "One");
        hash.add(2, "Two");
        hash.add(3, "Three");

        assertEquals("One", hash.get(1).getValue());
        assertEquals("Two", hash.get(2).getValue());
        assertEquals("Three", hash.get(3).getValue());
    }

    @Test
    void add() throws FullHashException {
        Hash<Integer, String> hash = new Hash<>(10);
        hash.add(1, "One");
        hash.add(2, "Two");
        hash.add(3, "Three");
        assertEquals(3,hash.size());
    }
    @Test
    void addFullHashException() {
        Hash<Integer, String> hash = new Hash<>(3);
        try {
            hash.add(1, "One");
            hash.add(2, "Two");
            hash.add(3, "Three");
            hash.add(4, "Four");
            fail("El hash esta lleno.");
        } catch (FullHashException e) {
        }
    }
}
