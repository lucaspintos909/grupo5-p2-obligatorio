package uy.edu.um.prog2.adt.Hash;

public class Hash<K, T> implements MyHash<K, T> {
    private final int hashSize;
    private int actualSize;
    private final HashNode<K, T>[] hash;

    private int defaultSize = 130_000;

    public Hash(int initSize) {
        this.hash = new HashNode[initSize];
        this.hashSize = initSize;
        this.initHash();
    }

    public Hash() {
        this.hash = new HashNode[defaultSize];
        this.hashSize = defaultSize;
        this.initHash();
    }

    private void initHash() {
        for (int i = 0; i < this.hashSize; i++) {
            this.hash[i] = null;
        }

        this.actualSize = 0;
    }

    private int hashCode(K key) {
        int position = key.hashCode() % this.hashSize;
        while (position < 0) {
            position = position + this.hashSize;
        }
        return position;
    }

    @Override
    public int size() {
        return this.actualSize;
    }

    @Override
    public boolean isEmpty() {
        return this.actualSize == 0;
    }

    @Override
    public void remove(K key) {
        int position = hashCode(key);
        while (hash[position] != null && !hash[position].getKey().equals(key)) {
            position = (position + 1) % hashSize;
        }
        if (hash[position] != null) {
            hash[position].setDeleted(true);
            actualSize--;
        }
    }


    /* public HashNode<K, T> get(K key) {
        int position = hashCode(key);
        while (hash[position] != null) {
            if (hash[position].getKey().equals(key)) {
                if (hash[position].isDeleted()) {
                    return null;
                } else {
                    return hash[position];
                }
            }
            position = (position + 1) % hashSize;
        }
        return null;
    } */
    @Override
    public HashNode<K, T> get(K key) {
        int position = hashCode(key);

        while (hash[position] != null) {
            if (hash[position].getKey().equals(key)) {
                return hash[position];
            }

            position++;
            if (position >= hash.length) {
                position = 0;
            }
        }

        return null;  // Retorna null si la clave no se encuentra.
    }

    public void put(K key, T value) {

        int index = hashCode(key);

        while (hash[index] != null && !key.equals(hash[index].getKey())) {
            index++;
            if (index >= hash.length) index = 0;
        }

        if (hash[index] == null) {
            actualSize++;
        }

        hash[index] = new HashNode<>(key, value);
    }

    public T putIfAbsent(K key, T value) {
        int index = hashCode(key);

        while (hash[index] != null && !key.equals(hash[index].getKey())) {
            index++;
            if (index >= hash.length) index = 0;
        }

        if (hash[index] == null) {
            hash[index] = new HashNode<>(key, value);
            actualSize++;
        } else if (key.equals(hash[index].getKey())) {
            // Si el valor ya existe, devolver el valor existente sin hacer ninguna modificación
            return hash[index].getValue();
        }
        return null;
    }


    @Override
    public void add(K key, T value) throws FullHashException {
        if (hashSize == actualSize) {
            throw new FullHashException("");
        } else {
            int position;
            for (position = this.hashCode(key); this.hash[position] != null && !this.hash[position].isDeleted(); position = (position + 1) % this.hashSize) {
                if (this.hash[position].getKey() == key) {
                    this.hash[position] = new HashNode<>(key, value);
                    return;
                }
            }
            this.hash[position] = new HashNode<>(key, value);
            this.actualSize++;
        }
    }
}