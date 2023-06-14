package uy.edu.um.prog2.adt.LinkedList;

public interface Mylist<T> {

    void add(T val);

    T get(int index);

    void remove(T value);

    int size();

    boolean contains(T value);
}