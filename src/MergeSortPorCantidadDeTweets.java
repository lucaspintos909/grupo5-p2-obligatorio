import entities.User;
import uy.edu.um.prog2.adt.Hash.Hash;

public class MergeSortPorCantidadDeTweets {
    public static void MergeSort(String[] listaUsuarios, Hash<String, User> hashUsers) {
        int largo = listaUsuarios.length;
        if (largo <= 1) return;
        int medio = largo / 2;
        String[] arrayDeLaIzquierda = new String[medio];
        String[] arrayDeLaDerecha = new String[largo - medio];

        int i = 0;//array de la izquierda
        int j = 0; // array de la derecha
        for (; i < largo; i++) {
            if (i < medio) {
                arrayDeLaIzquierda[i] = listaUsuarios[i];
            } else {
                arrayDeLaDerecha[j] = listaUsuarios[i];
                j++;
            }
        }
        MergeSort(arrayDeLaIzquierda, hashUsers);
        MergeSort(arrayDeLaDerecha, hashUsers);
        merge(arrayDeLaIzquierda, arrayDeLaDerecha, listaUsuarios, hashUsers);
    }

    private static void merge(String[] arrayDeLaIzquierda, String[] arrayDeLaDerecha, String[] listaDeUsuarios, Hash<String, User> hashUsers) {
        int tamanioIzquierda = listaDeUsuarios.length / 2;
        int tamanioDerecha = listaDeUsuarios.length - tamanioIzquierda;
        int i = 0;
        int l = 0;
        int r = 0;
        while (l < tamanioIzquierda && r < tamanioDerecha) {
            if (hashUsers.get(arrayDeLaIzquierda[l]).getValue().getCantidadTweets() < hashUsers.get(arrayDeLaDerecha[r]).getValue().getCantidadTweets()) {
                listaDeUsuarios[i] = arrayDeLaIzquierda[l];
                i++;
                l++;
            } else {
                listaDeUsuarios[i] = arrayDeLaDerecha[r];
                i++;
                r++;
            }
        }
        while (l < tamanioIzquierda) {
            listaDeUsuarios[i] = arrayDeLaIzquierda[l];
            i++;
            l++;
        }
        while (r < tamanioDerecha) {
            listaDeUsuarios[i] = arrayDeLaDerecha[r];
            i++;
            r++;
        }
    }
}
