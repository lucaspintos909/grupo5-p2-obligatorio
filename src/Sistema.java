import entities.Piloto;
import uy.edu.um.prog2.adt.Hash.Hash;
import uy.edu.um.prog2.adt.LinkedList.LinkedList;
import entities.Tweet;
import entities.User;


import java.util.Scanner;

public class Sistema {
    Tweet[] tweets;
    Piloto[] pilotos;
    Hash<String, User> users;
    LinkedList<String> userNames;


    public Sistema() {
        try {
            this.pilotos = CSVReader.readDrivers();

            long startTime = System.nanoTime();
            System.out.println("Leyendo tweets del csv...");

            CSVReaderReturn csvReturn = CSVReader.readCSV();

            this.tweets = csvReturn.tweets;
            this.users = csvReturn.users;
            this.userNames = csvReturn.userNames;

            int contador = 0;
            for (Tweet tweet : csvReturn.tweets) {
                if (tweet != null) contador++;
            }

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
                    boolean esMencionado = tweetText.contains(nombre.toLowerCase()) || tweetText.contains(apellido.toLowerCase());

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

        QuickSort.quickSort(pilotos, contadorPilotos);

        for (int i = 0; i < 10; i++) {
            System.out.println("Top " + (i + 1) + " -> " + pilotos[i].getName() + " - Cantidad de menciones: " + contadorPilotos.get(pilotos[i].getName()).getValue());
        }
    }

    public void Cantidad_de_tweets(String palabra) {

        Integer contador_palbras = 0;
        for (Tweet tweet : tweets) {
            if (tweet == null) {
                continue;
            }
            String tweetText = tweet.getContent();
            boolean esMencionado = tweetText.contains(palabra);
            if (esMencionado) {
                contador_palbras++;
            }

        }
        System.out.println(contador_palbras + " es el numero de twwets que contienen: " + palabra);
    }
    public void Cantidad_de_hashtags_distintos_para_un_día_dado(String anio,String mes,String dia){

        Hash<String[], Integer> contadorHashtag = new Hash<>(10000);
        for (Tweet tweet : tweets) {
            if (tweet == null) {
                continue;
            }
            try {
                contadorHashtag.add(tweet.getHashtags(), 0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            /* Para que no agarre las comas que pueda haber en el texto */
            String[] fecha = tweet.getDate().split("-");
            boolean fechaTweetCorrecta = fecha[0].contains(anio) && fecha[1].contains(mes)&&fecha[2].contains(dia);
            /*System.out.println(tweet.getDate());*/
            String[]hashtag= tweet.getHashtags();

            if (fechaTweetCorrecta) {
                String[] hashtagdia = tweet.getHashtags();


            }

        }


    }


    public void top15PilotosConMasTweets() {

    }

    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        String opcion;


        long startTime = System.nanoTime();
        Sistema sistema = new Sistema();
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
        System.out.println("Duración en leer el CSV " + roundedDuration + " Segundos");
        System.out.println();

        while (!salir) {
            System.out.println();
            System.out.println("1-Top 10 pilotos mencionados");
            System.out.println("2-Top 15 usuarios con más tweets");
            System.out.println("3-Cantidad de hashtags distintos para un día dado");
            System.out.println("4-Hashtag más usado para un día dado");
            System.out.println("5-Top 7 cuentas con más favoritos");
            System.out.println("6-Cantidad de tweets con una palabra o frase específicos");
            System.out.println("7-Salir");
            System.out.println();

            System.out.print("Ingrese una opción: ");
            opcion = sn.next();

            switch (opcion) {
                case "1":
                    String mes;
                    String anio;

                    System.out.print("Introduzca el mes: ");
                    mes = sn.next();
                    System.out.print("Introduzca el año: ");
                    anio = sn.next();
                    System.out.println("Top 10 pilotos mencionados...");
                    System.out.println();
                    long startTime_1 = System.nanoTime();

                    sistema.top10PilotosActivos(mes, anio);

                    long endTime_1 = System.nanoTime();
                    double durationInSeconds_1 = (endTime_1 - startTime_1) / 1_000_000_000.0;
                    double roundedDuration_1 = Math.round(durationInSeconds_1 * 10.0) / 10.0;
                    System.out.println();
                    System.out.println("Top 10 pilotos mencionados. Duración: " + roundedDuration_1 + " segundos.");
                    break;
                case "2":
                    break;
                case "3":
                    String dia_1;
                    String mes_1;
                    String anio_1;
                    System.out.println("Introduzca el dia: ");
                    dia_1=sn.next();
                    System.out.print("Introduzca el mes: ");
                    mes_1 = sn.next();
                    System.out.print("Introduzca el año: ");
                    anio_1 = sn.next();
                    long startTime_3=System.nanoTime();
                    sistema.Cantidad_de_hashtags_distintos_para_un_día_dado(anio_1,mes_1,dia_1);
                    long endTime_3=System.nanoTime();
                    double durationInSeconds_3 = (endTime_3 - startTime_3) / 1_000_000_000.0;
                    double roundedDuration_3 = Math.round(durationInSeconds_3 * 10.0) / 10.0;
                    System.out.println();
                    System.out.println("Cantidad de hashtags distintos para un día dado. Duración: " + roundedDuration_3 + " segundos.");

                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    String palabra;
                    System.out.println("introduzca la palbra o frase");
                    palabra = sn.next();
                    long startTime_6 = System.nanoTime();
                    sistema.Cantidad_de_tweets(palabra);
                    long endTime_6 = System.nanoTime();
                    double durationInSeconds_6 = (endTime_6 - startTime_6) / 1_000_000_000.0;
                    double roundedDuration_6 = Math.round(durationInSeconds_6 * 10.0) / 10.0;
                    System.out.println();
                    System.out.println("Cantidad de tweets con una palabra o frase específicos. Duración: " + roundedDuration_6 + " segundos");
                    break;
                case "7":
                    salir = true;
                    break;
                default:
                    System.out.println("Las opciones son entre 1 y 7");

            }


        }
        System.out.println("Bye");

    }
}
