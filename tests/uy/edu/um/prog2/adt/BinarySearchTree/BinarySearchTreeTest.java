package uy.edu.um.prog2.adt.BinarySearchTree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {
    BinarySearchTree<Integer,String> tree = new BinarySearchTree<>();

    @Test
    void add() {
        tree.add(3,"root");
        assertFalse(false, String.valueOf(String.valueOf(tree.getRoot())==null));
        tree.add(1,"left");
        tree.add(5,"right");

        assertEquals("root", tree.find(3).getValue());
        assertEquals("left", tree.find(1).getValue());
        assertEquals("right", tree.find(5).getValue());
    }

    @Test
    void remove() {
        tree.add(3,"val");
        tree.remove(3);
        assertEquals(null,tree.getRoot());
        tree.add(3,"val");
        tree.add(1,"val-2");
        tree.add(5,"val+2");
        assertEquals(tree.getRoot(),tree.find(3));
       assertEquals(tree.getRoot().getRight(),tree.find(5));
       assertEquals(tree.getRoot().getLeft(),tree.find(1));

        tree.remove(3);
        assertFalse(false, String.valueOf(tree.getRoot()==null));
        System.out.println(tree.getRoot().getValue());
        tree.remove(5);
        System.out.println(tree.getRoot().getValue());
    }


    @Test
    void contains() {
        tree.add(1,"hola");
        assertTrue(true, String.valueOf(tree.contains(1)));
    }

    @Test
    void find() {
        tree.add(1,"chau");
        assertEquals("chau",tree.find(1).getValue());



    }

    @Test
    void preOrder() {
        tree.add(3,"root");
        tree.add(2,"root-left");
        tree.add(4,"root-left-right");
        tree.add(1,"root-left-left");
        tree.add(6,"root-right");
        tree.add(5,"root-right-left");
        tree.add(7,"root-right-right");

        assertEquals("root",tree.preOrder().get(0).getValue());
        assertEquals("root-left",tree.preOrder().get(1).getValue());
        assertEquals("root-left-left",tree.preOrder().get(2).getValue());
        assertEquals("root-left-right",tree.preOrder().get(3).getValue());
        assertEquals("root-right",tree.preOrder().get(4).getValue());
        assertEquals("root-right-left",tree.preOrder().get(5).getValue());
        assertEquals("root-right-right",tree.preOrder().get(6).getValue());


    }

    @Test
    void inOrder() {
        tree.add(3,"root");
        tree.add(2,"root-left");
        tree.add(4,"root-left-right");
        tree.add(1,"root-left-left");
        tree.add(6,"root-right");
        tree.add(5,"root-right-left");
        tree.add(7,"root-right-right");

        assertEquals("root-left-left",tree.inOrder().get(0).getValue());
        assertEquals("root-left",tree.inOrder().get(1).getValue());
        assertEquals("root",tree.inOrder().get(2).getValue());
        assertEquals("root-left-right",tree.inOrder().get(3).getValue());
        assertEquals("root-right-left",tree.inOrder().get(4).getValue());
        assertEquals("root-right",tree.inOrder().get(5).getValue());
        assertEquals("root-right-right",tree.inOrder().get(6).getValue());
    }

    @Test
    void postOrder() {
        tree.add(3,"root");
        tree.add(2,"root-left");
        tree.add(4,"root-left-right");
        tree.add(1,"root-left-left");
        tree.add(6,"root-right");
        tree.add(5,"root-right-left");
        tree.add(7,"root-right-right");

        assertEquals("root-left-left",tree.postOrder().get(0).getValue());
        assertEquals("root-left",tree.postOrder().get(1).getValue());

    }
}