import entities.Piloto;
import uy.edu.um.prog2.adt.Hash.Hash;
import entities.Tweet;

public class Sistema {
    Tweet[] tweets;
    Piloto[] pilotos;

    public Sistema() {
        try {
            this.pilotos = CSVReader.readDrivers();

            long startTime = System.nanoTime();
            System.out.println("Leyendo tweets del csv...");

            this.tweets = CSVReader.readTweets();

            long endTime = System.nanoTime();
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
            System.out.println("Datos leidos correctamente. Duración: " + roundedDuration + " segundos.");


        } catch (Exception e) {
            java.lang.System.out.println(e.getMessage());
        }
    }

    public void top10PilotosActivos(String mes, String anio) {
        Hash<String, Integer> contadorPilotos = new Hash<>(20);

        /* Inicializo los contadores para cada piloto */
        for (Piloto piloto : pilotos) {
            try {
                contadorPilotos.add(piloto.getName(), 0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        /* ------------------------------ Contador ------------------------------ */
        for (Tweet tweet : tweets) {
            if (tweet == null) {
                continue;
            }
            /* Para que no agarre las comas que pueda haber en el texto */
            String[] fecha = tweet.getDate().split("-");
            boolean fechaTweetCorrecta = fecha[0].contains(anio) && fecha[1].contains(mes);
            /*System.out.println(tweet.getDate());*/
            String tweetText = tweet.getContent();

            if (fechaTweetCorrecta) {
                for (Piloto piloto : pilotos) {
                    /* Saco los datos necesarios */
                    String[] nombreCompletoPiloto = piloto.getName().split(" ");
                    String nombre = nombreCompletoPiloto[0];
                    String apellido = nombreCompletoPiloto[1];
                    if (nombreCompletoPiloto.length == 3) {
                        apellido += " " + nombreCompletoPiloto[2];
                    }
                    boolean esMencionado = tweetText.contains(nombre) || tweetText.contains(apellido);

                    /* Si la fecha coincide y el piloto es mencionado */
                    if (esMencionado) {
                        try {
                            /* Agarro el contador actual del piloto, le sumo 1 y lo seteo devuelta */
                            Integer contadorActualPiloto = contadorPilotos.get(piloto.getName()).getValue();
                            contadorPilotos.get(piloto.getName()).setValue(contadorActualPiloto + 1);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
        /* ---------------------------- Fin contador ---------------------------- */

        int n = pilotos.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                int actual = contadorPilotos.get(pilotos[j].getName()).getValue();
                int siguiente = contadorPilotos.get(pilotos[j + 1].getName()).getValue();
                if (actual < siguiente) {
                    // Swap pilotos[j] and pilotos[j + 1]
                    Piloto temp = pilotos[j];
                    pilotos[j] = pilotos[j + 1];
                    pilotos[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no two elements were swapped in the inner loop, the copiaPilotos is already sorted
            if (!swapped) {
                break;
            }
        }
        /*QuickSort.quickSort(pilotos, contadorPilotos);*/
        for (int i = 0; i < 10; i++) {
            System.out.println((i + 1) + " -> " + pilotos[i].getName() + ": " + contadorPilotos.get(pilotos[i].getName()).getValue());
        }
    }

    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        long startTime = System.nanoTime();
        System.out.println("top10PilotosActivos...");

        sistema.top10PilotosActivos("11", "2021");

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
        System.out.println("top10PilotosActivos. Duración: " + roundedDuration + " segundos.");

    }
}
