import entities.User;
import uy.edu.um.prog2.adt.Hash.Hash;

public class QuickSortUsuariosFavoritos {
    public static void quickSort(String[] array, Hash<String, User> contador) {
        quickSort(array, 0, array.length - 1, contador);
    }

    private static void quickSort(String[] array, int low, int high, Hash<String, User> contador) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, contador);
            quickSort(array, low, pivotIndex - 1, contador);
            quickSort(array, pivotIndex + 1, high, contador);
        }
    }

    private static int partition(String[] array, int low, int high, Hash<String, User> contador) {
        int pivot = contador.get(array[high]).getValue().getCantidadFavoritos();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (contador.get(array[j]).getValue().getCantidadFavoritos() > pivot) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);

        return i + 1;
    }

    private static void swap(String[] array, int i, int j) {
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
