package uy.edu.um.prog2.adt.LinkedList;

import uy.edu.um.prog2.adt.Queue.EmptyQueueException;
import uy.edu.um.prog2.adt.Queue.MyQueue;
import uy.edu.um.prog2.adt.Stack.EmptyStackException;
import uy.edu.um.prog2.adt.Stack.MyStack;


public class LinkedList<T> implements MyStack<T>, MyQueue<T>, Mylist<T> {
    public Nodo<T> head;
    public Nodo<T> tail;

    @Override
    public void add(T value) {
        addLast(value);
    }

    public void addLast(T value) {
        /*
         * Para chequear que no se agregue un null
         */
        if (value != null) {

            Nodo<T> newNode = new Nodo<>(value);

            if (this.head == null) {
                this.head = newNode;
            } else {
                this.tail.next = newNode;
            }
            this.tail = newNode;
        }
    }

    public void addFirst(T value) {
        /*
         * Para chequear que no se agregue un null
         */
        if (value != null) {

            Nodo<T> newNode = new Nodo<>(value);

            if (this.head == null) {
                this.tail = newNode;
            } else {
                newNode.next = this.head;
            }
            this.head = newNode;
        }
    }

    @Override
    public void push(T value) {
        addLast(value);
    }

    @Override
    public T pop() throws EmptyStackException {
        if (this.tail == null) {
            throw new EmptyStackException();
        }
        return removeLast();
    }

    @Override
    public T peek() {
        if (this.tail != null) {
            return this.tail.variable;
        } else {
            return null;
        }
    }

    @Override
    public void enqueue(T value) {
        addFirst(value);
    }

    @Override
    public T dequeue() throws EmptyQueueException {
        if (head == null) {
            throw new EmptyQueueException();
        }

        return removeLast();
    }

    @Override
    public boolean contains(T value) {
        boolean contains = false;
        Nodo<T> aux = this.head;

        while (aux != null && !aux.variable.equals(value)) {
            aux = aux.next;
        }

        /*
         * Si no llega al final de la lista es que se encontro el valor
         */
        if (aux != null) {
            contains = true;
        }

        return contains;
    }


    @Override
    public int size() {
        int listSize = 0;
        Nodo<T> aux = this.head;

        if (aux == null) {
            return listSize;
        }

        while (aux != null) {
            aux = aux.next;
            listSize++;
        }

        return listSize;
    }


    @Override
    public T get(int index) {
        T returnValue = null;
        Nodo<T> temp = this.head;

        int aux = 0;
        while (temp != null && aux != index) {

            temp = temp.next;
            aux++;

        }

        if (aux == index) {
            returnValue = temp.variable;
        }

        /* Si llegó al final de la lista y no encontro el elemento se devuelve null */
        return returnValue;
    }

    public void remove(T value) {
        Nodo<T> anterior = null;
        Nodo<T> siguiente = this.head;

        /* Guardo el que busco y el anterior */
        while (siguiente != null && !siguiente.variable.equals(value)) {
            anterior = siguiente;
            siguiente = siguiente.next;
        }

        if (siguiente != null) {

            if (siguiente == this.head && siguiente != this.tail) {
                /*
                 * Sí es el primero y no el último
                 */
                Nodo<T> temp = this.head;
                this.head = this.tail.next;
                temp.next = null;

            } else if (siguiente == this.tail && siguiente != this.head) {
                /*
                 * Sí es el último y no el primero
                 */
                anterior.next = null;
                this.tail = anterior;

            } else if (siguiente == this.tail && siguiente == this.head) {
                /*
                 * Si es el único valor que hay en la lista
                 */
                this.head = null;
                this.tail = null;

            } else {
                /*
                 * Si esta en el diome
                 */
                anterior.next = siguiente.next;
                siguiente.next = null;
            }
        }
    }

    public T removeLast() {
        T valueToRemove = null;

        if (this.tail != null) {
            /*
             * Guardo el valor y después lo borro
             * */
            valueToRemove = this.tail.variable;
            remove(valueToRemove);
        }

        return valueToRemove;
    }

    public void display() {
        Nodo<T> temp = head;
        while (temp != null) {
            System.out.print(temp.variable + " -> ");
            temp = temp.next;

        }
        System.out.println("FIN");
    }

    @Override
    public boolean Empty() {
        if (head == null) {
            System.out.println("Esta Vacia ");
            return true;

        } else {
            System.out.println("No Esta Vacia, contiene " + size() + " elementos");
            return false;
        }
    }
}
