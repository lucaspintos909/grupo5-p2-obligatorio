package uy.edu.um.prog2.adt.BinarySearchTree;

import uy.edu.um.prog2.adt.LinkedList.LinkedList;

public interface MyBinarySearchTree <T extends Comparable<T>,V>{
    void add(T key,V value);
    void remove(T valor);
    boolean contains(T valor);
    TreeNode<T,V> find(T valor);
    LinkedList<TreeNode<T,V>> preOrder();
    LinkedList<TreeNode<T,V>> inOrder();
    LinkedList<TreeNode<T,V>> postOrder();

}
