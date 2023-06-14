package uy.edu.um.prog2.adt.BinarySearchTree;

import uy.edu.um.prog2.adt.LinkedList.LinkedList;

public class BinarySearchTree<T extends Comparable<T>, V> implements MyBinarySearchTree<T, V> {

    private TreeNode<T, V> root;

    @Override
    public void add(T key, V valor) {
        root = insert(key, valor, root);
    }

    private TreeNode<T, V> insert(T key, V valor, TreeNode<T, V> root) {
        TreeNode<T, V> result = null;
        if (root == null) {
            result = new TreeNode<>(key, valor);
        } else if (key.compareTo(root.getKey()) > 0) {
            root.setRight(insert(key, valor, root.getRight()));
            result = root;
        } else if (key.compareTo(root.getKey()) < 0) {
            root.setLeft(insert(key, valor, root.getLeft()));
            result = root;
        }
        return result;
    }

    @Override
    public void remove(T key) {
        root = delete(key, root);
    }

    private TreeNode<T, V> delete(T key, TreeNode<T, V> root) {
        TreeNode<T, V> resultado = root;

        if (root != null) {
            /*
             *Para evitar errores si se pasa un null como nodo
             */
            if (key.compareTo(root.getKey()) < 0) {
                root.setLeft(delete(key, root.getLeft()));
            } else if (key.compareTo(root.getKey()) > 0) {
                root.setRight(delete(key, root.getRight()));
            } else {
                if (root.getRight() == null && root.getLeft() == null) {
                    resultado = null;
                } else if (root.getLeft() != null) {
                    TreeNode<T, V> aux = getMax(root.getLeft());
                    root.setValue(aux.getValue());
                    root.setKey(aux.getKey());
                    root.setLeft(delete(aux.getKey(), root.getLeft()));
                } else {
                    TreeNode<T, V> aux = getMin(root.getRight());
                    root.setValue(aux.getValue());
                    root.setKey(aux.getKey());
                    root.setRight(delete(aux.getKey(), root.getRight()));
                }
            }
        }

        return resultado;
    }

    private TreeNode<T, V> getMin(TreeNode<T, V> root) {
        TreeNode<T, V> resultado;

        if (root.getLeft() == null) {
            resultado = root;
        } else {
            resultado = getMin(root.getLeft());
        }
        return resultado;
    }

    private TreeNode<T, V> getMax(TreeNode<T, V> root) {
        TreeNode<T, V> resultado;

        if (root.getRight() == null) {
            resultado = root;
        } else {
            resultado = getMax(root.getRight());
        }
        return resultado;
    }


    @Override
    public boolean contains(T key) {
        return search(this.root, key);
    }

    private boolean search(TreeNode<T, V> root, T key) {
        if (root == null) {
            return false;
        } else if (root.getKey().compareTo(key) == 0) {
            return true;
        } else if (root.getKey().compareTo(key) > 0) {
            return search(root.getLeft(), key);
        }
        return search(root.getRight(), key);
    }

    @Override
    public TreeNode<T, V> find(T key) {
        return findA(key, root);
    }

    private TreeNode<T, V> findA(T key, TreeNode<T, V> root) {
        if (root == null) {
            return null;
        } else if (root.getKey().compareTo(key) == 0) {
            return root;
        } else if (root.getKey().compareTo(key) > 0) {
            return findA(key, root.getLeft());
        }
        return findA(key, root.getRight());
    }


    LinkedList<TreeNode<T, V>> listaPreOrder = new LinkedList<>();
    @Override
    public LinkedList<TreeNode<T, V>> preOrder() {
        return preOrder(listaPreOrder, root);
    }

    private LinkedList<TreeNode<T, V>> preOrder(LinkedList<TreeNode<T, V>> lista, TreeNode<T, V> root) {
        if (root != null) {
            lista.add(root);
            preOrder(lista, root.getLeft());
            preOrder(lista, root.getRight());
        }
        return lista;
    }

    LinkedList<TreeNode<T, V>> listaInOrder = new LinkedList<>();
    @Override
    public LinkedList<TreeNode<T, V>> inOrder() {
        return inOrder(listaInOrder, root);
    }

    private LinkedList<TreeNode<T, V>> inOrder(LinkedList<TreeNode<T, V>> lista, TreeNode<T, V> root) {
        if (root.getLeft() != null) {
            inOrder(lista, root.getLeft());
        }
        lista.add(root);
        if (root.getRight() != null) {
            inOrder(lista, root.getRight());
        }
        return lista;
    }
    LinkedList<TreeNode<T, V>> listaPostOrder = new LinkedList<>();
    @Override
    public LinkedList<TreeNode<T, V>> postOrder() {
        return postOrder(listaPostOrder, root);
    }

    private LinkedList<TreeNode<T, V>> postOrder(LinkedList<TreeNode<T, V>> lista, TreeNode<T, V> root) {

        if (root.getLeft() != null) {
            postOrder(lista, root.getLeft());
            }

        if (root.getRight() != null) {
            postOrder(lista, root.getRight());
        }

        lista.add(root);
        return lista;
    }
    public TreeNode<T, V> getRoot() {
        return root;
    }

}

