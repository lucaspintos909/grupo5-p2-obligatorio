package uy.edu.um.prog2.adt.Hash;

public interface MyHash<K,T> {
    int size();
    boolean isEmpty();
    void remove(K key);
    HashNode<K,T> get(K key);
    void add(K key, T value) throws FullHashException;
}
