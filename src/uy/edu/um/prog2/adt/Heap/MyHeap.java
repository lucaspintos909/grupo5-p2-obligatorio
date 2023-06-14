package uy.edu.um.prog2.adt.Heap;

public interface MyHeap<T extends Comparable<T>> {
    void insert(T value);
    T deleteMin() throws EmptyHeapException;
    T deleteMax() throws EmptyHeapException;
    int size();
    boolean isEmpty();
    T getMin();
    T getMax();

}
