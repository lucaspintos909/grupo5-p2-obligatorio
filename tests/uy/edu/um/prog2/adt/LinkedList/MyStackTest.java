package uy.edu.um.prog2.adt.LinkedList;

import org.junit.jupiter.api.Test;
import uy.edu.um.prog2.adt.Stack.EmptyStackException;
import uy.edu.um.prog2.adt.Stack.MyStack;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {

    MyStack<Integer> stackll= new LinkedList<>();
    @Test
    void push() {
        stackll.push(10);
        assertEquals(stackll.size(),1);
    }

    @Test
    void pop()throws EmptyStackException {
        stackll.push(10);
        stackll.pop();
        assertEquals(stackll.size(),0);
         try {
            stackll.pop();
        }catch (EmptyStackException e) {
             assertEquals(stackll.size(), 0);
         }
    }

    @Test
    void peek() {
        stackll.push(10);
        stackll.peek();
        assertEquals(stackll.size(),1);
}
    @Test
    void size() {
        stackll.push(10);
        stackll.push(9);
        assertEquals(stackll.size(),2);
    }

    @Test
    void get() {
        stackll.push(7);
        System.out.println(stackll.get(0));
        assertEquals(stackll.get(0),7);
    }

    @Test
    void empty() {
        stackll.push(10);
        assertFalse(false, String.valueOf(stackll.Empty()));
    }
}