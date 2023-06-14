import entities.Piloto;
import uy.edu.um.prog2.adt.Hash.Hash;
import entities.Tweet;

public class Sistema {
    Tweet[] tweets;
    Piloto[] pilotos;

    public Sistema() {
        try {
            this.pilotos = CSVReader.readDrivers();
            System.out.println("Leyendo datos del csv...");
            this.tweets = CSVReader.readTweets();
            System.out.println("Datos leidos correctamente.");
        } catch (Exception e) {
            java.lang.System.out.println(e.getMessage());
        }
    }

    public void top10PilotosActivos(String mes, String anio) {
        Hash<String, Integer> contadorPilotos = new Hash<>(20);

        /* Inicializo los contadores para cada piloto */
        for (Piloto piloto : pilotos) {
            try {
                contadorPilotos.add(piloto.getNameLowerCase(), 0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        for (Tweet tweet : tweets) {
            if (tweet == null) {
                continue;
            }
            /* Para que no agarre las comas que pueda haber en el texto */
            boolean fechaTweetCorrecta = tweet.getDate().contains(anio + "-" + mes);
            String tweetText = tweet.getContent();

            for (Piloto piloto : pilotos) {
                /* Saco los datos necesarios */
                String nombre = piloto.getNameLowerCase().split(" ")[0];
                String apellido = piloto.getNameLowerCase().split(" ")[1];
                boolean esMencionado = tweetText.contains(piloto.getNameLowerCase()) || tweetText.contains(nombre) || tweetText.contains(apellido);

                /* Si la fecha coincide y el piloto es mencionado */
                if (fechaTweetCorrecta && esMencionado) {
                    try {
                        /* Agarro el contador actual del piloto, le sumo 1 y lo seteo devuelta */
                        Integer contadorActualPiloto = contadorPilotos.get(piloto.getNameLowerCase()).getValue();
                        contadorPilotos.get(piloto.getNameLowerCase()).setValue(contadorActualPiloto + 1);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                }
            }
        }

        int n = pilotos.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                int actual = contadorPilotos.get(pilotos[j].getNameLowerCase()).getValue();
                int siguiente = contadorPilotos.get(pilotos[j + 1].getNameLowerCase()).getValue();
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
        for (int i = 0; i < 10; i++) {
            System.out.println((i + 1) + " -> " + pilotos[i].getName() + ": " + contadorPilotos.get(pilotos[i].getNameLowerCase()).getValue());
        }
    }

    public static void main(String[] args) {
        Sistema sistema = new Sistema();

        sistema.top10PilotosActivos("11", "2021");

    }
}
