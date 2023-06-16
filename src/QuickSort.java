import entities.Piloto;
import uy.edu.um.prog2.adt.Hash.Hash;

public class QuickSort {
    public static void quickSort(Piloto[] array, Hash<String, Integer> contador) {
        quickSort(array, 0, array.length - 1, contador);
    }

    private static void quickSort(Piloto[] array, int low, int high,  Hash<String, Integer> contador) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, contador);
            quickSort(array, low, pivotIndex - 1, contador);
            quickSort(array, pivotIndex + 1, high, contador);
        }
    }

    private static int partition(Piloto[] array, int low, int high, Hash<String, Integer> contador) {
        Integer pivot = contador.get(array[high].getName()).getValue();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (contador.get(array[j].getName()).getValue().compareTo(pivot) > 0) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);

        return i + 1;
    }

    private static void swap(Piloto[] array, int i, int j) {
        Piloto temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

/*    public static void main(String[] args) {
        Integer[] numbers = {5, 2, 10, 1, 6};

        System.out.println("Array before sorting:");
        for (int number : numbers) {
            System.out.print(number + " ");
        }

        quickSort(numbers);

        System.out.println("\nArray after sorting:");
        for (int number : numbers) {
            System.out.print(number + " ");
        }
    }*/
}
