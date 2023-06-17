import entities.Piloto;
import uy.edu.um.prog2.adt.Hash.Hash;
import entities.Tweet;

import java.util.Scanner;

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

        Scanner sn=new Scanner(System.in);
        boolean salir=false;
        int opcion;

        while (!salir){

            System.out.println("1-Top 10 pilotos mencionados");
            System.out.println("2-Top 15 usuarios con más tweets");
            System.out.println("3-Cantidad de hashtags distintos para un día dado");
            System.out.println("4-Hashtag más usado para un día dado");
            System.out.println("5-Top 7 cuentas con más favoritos");
            System.out.println("6-Cantidad de tweets con una palabra o frase específicos");
            System.out.println("7-Salir");

            System.out.println("introduce un numero: ");
            opcion= sn.nextInt();

            if (opcion==7){
                break;
            }

            Sistema sistema = new Sistema();
            long startTime = System.nanoTime();
            long endTime = System.nanoTime();
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;

            switch (opcion){
                case 1:
                    String mes;
                    String año;

                    System.out.println("Introduzca el mes ");
                    mes= String.valueOf(sn.nextInt());
                    System.out.println("Introduzca el año ");
                    año= String.valueOf(sn.nextInt());
                    System.out.println("Top 10 pilotos mencionados...");
                    sistema.top10PilotosActivos(mes,año);

                    System.out.println("Top 10 pilotos mencionados. Duración: " + roundedDuration + " segundos.");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Las opciones son entre 1 y 7");

            }



        }
        System.out.println("Bye");

    }
}
