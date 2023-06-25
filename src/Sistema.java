import entities.Piloto;
import uy.edu.um.prog2.adt.Hash.Hash;
import entities.Tweet;
import entities.User;
import uy.edu.um.prog2.adt.LinkedList.Nodo;

import java.util.Scanner;

public class Sistema {
    Tweet[] tweets;
    Piloto[] pilotos;
    Hash<String, User> users;
    String[] userNames;


    public Sistema() {
        try {
            this.pilotos = CSVReader.readDrivers();

            long startTime = System.nanoTime();
            System.out.println("Leyendo tweets del csv...");

            CSVReaderReturn csvReturn = CSVReader.readCSV();

            this.tweets = csvReturn.tweets;
            this.users = csvReturn.users;

            /* ------ Creando array de nombres de usuario ------ */
            String[] userNameArray = new String[csvReturn.userNames.size];
            Nodo<String> aux = csvReturn.userNames.head;
            int i = 0;
            while (aux != null) {
                userNameArray[i] = aux.variable;
                aux = aux.next;
                i++;
            }
            this.userNames = userNameArray;
            /* ------------------------------------------------- */

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

    public void cantidadDeTweets(String palabra) {
        Integer contadorPalabras = 0;
        for (Tweet tweet : tweets) {
            if (tweet == null) {
                continue;
            }
            String tweetText = tweet.getContent();
            boolean esMencionado = tweetText.contains(palabra.toLowerCase());
            if (esMencionado) {
                contadorPalabras++;
            }

        }
        System.out.println(contadorPalabras + " es el numero de tweets que contienen: " + palabra);
    }

    public void cantidadDeHashtagsDistintosParaUnDiaDado(String fecha) {
        String[] fechaSeparada = fecha.split("-");

        if (fechaSeparada.length != 3) {
            System.out.println("Fecha ingresada con el formato incorrecto.");
            return;
        }

        String dia = fechaSeparada[2];
        String mes = fechaSeparada[1];
        String anio = fechaSeparada[0];

        Hash<String, Integer> contadorHashtag = new Hash<>(15000);

        for (Tweet tweet : tweets) {
            if (tweet == null) {
                continue;
            }

            String[] fechaTweet = tweet.getDate().split("-");
            boolean fechaTweetCorrecta = fechaTweet[0].contains(anio) && fechaTweet[1].contains(mes) && fechaTweet[2].split(" ")[0].contains(dia);

            if (fechaTweetCorrecta) {
                try {
                    for (String hashtag : tweet.getHashtags()) {
                        contadorHashtag.putIfAbsent(hashtag, 0);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("Cantidad de hashtags diferentes para el día " + fecha + ": " + contadorHashtag.size());
    }

    public void hashtagMasUsadoParaUnDiaDado(String fecha) {
        String[] fechaSeparada = fecha.split("-");

        if (fechaSeparada.length != 3) {
            System.out.println("Fecha ingresada con el formato incorrecto.");
            return;
        }

        String dia = fechaSeparada[2];
        String mes = fechaSeparada[1];
        String anio = fechaSeparada[0];

        Hash<String, Integer> contadorHashtag = new Hash<>(15000);

        String hashtagMasUsado = "";
        int contadorDeOcurrencias = 0;

        for (Tweet tweet : tweets) {
            if (tweet == null) {
                continue;
            }

            String[] fechaTweet = tweet.getDate().split("-");
            boolean fechaTweetCorrecta = fechaTweet[0].contains(anio) && fechaTweet[1].contains(mes) && fechaTweet[2].split(" ")[0].contains(dia);

            if (fechaTweetCorrecta) {
                try {
                    for (String hashtag : tweet.getHashtags()) {
                        if (!hashtag.toLowerCase().equals("f1")) {
                            Integer contadorActual = contadorHashtag.putIfAbsent(hashtag, 0);
                            if (contadorActual != null) {
                                if (contadorActual >= contadorDeOcurrencias) {
                                    hashtagMasUsado = hashtag;
                                    contadorDeOcurrencias = contadorActual;
                                }
                                contadorHashtag.put(hashtag, contadorActual + 1);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("El hashtag mas usado para el día " + fecha + " es: " + hashtagMasUsado);
    }

    public void top15PilotosConMasTweets() {
        MergeSort.MergeSort(userNames, users);

        for (int j = 0; j < 15; j++) {
            String nombre = userNames[userNames.length - 1 - j];
            User usuario = users.get(nombre).getValue();
            System.out.println("Top " + (j + 1) + " -> " + nombre + ". Cantidad de tweets: " + usuario.getCantidadTweets() + ". Verificado: " + usuario.isVerificado());
        }
    }

    public void top7CuentasConMasFavoritos() {

        QuickSortUsuariosFavoritos.quickSort(userNames, users);

        for (int i = 0; i < 7; i++) {
            User usuario = users.get(userNames[i]).getValue();
            System.out.println("TOP " + (i + 1) + " -> " + usuario.getName() + ", cantidad de favoritos: " + usuario.getCantidadFavoritos());
        }
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
                    System.out.println("Top 15 con mas tweets...");
                    System.out.println();
                    long startTime_2 = System.nanoTime();

                    sistema.top15PilotosConMasTweets();

                    long endTime_2 = System.nanoTime();
                    double durationInSeconds_2 = (endTime_2 - startTime_2) / 1_000_000_000.0;
                    double roundedDuration_2 = Math.round(durationInSeconds_2 * 10.0) / 10.0;
                    System.out.println();
                    System.out.println("Top 15 con mas tweets. Duración: " + roundedDuration_2 + " segundos.");
                    break;
                case "3":
                    String fecha;
                    System.out.print("Ingrese la fecha en formato 'YYYY-MM-DD': ");
                    fecha = sn.next();
                    long startTime_3 = System.nanoTime();
                    sistema.cantidadDeHashtagsDistintosParaUnDiaDado(fecha);
                    long endTime_3 = System.nanoTime();
                    double durationInSeconds_3 = (endTime_3 - startTime_3) / 1_000_000_000.0;
                    double roundedDuration_3 = Math.round(durationInSeconds_3 * 10.0) / 10.0;
                    System.out.println();
                    System.out.println("Cantidad de hashtags distintos para un día dado. Duración: " + roundedDuration_3 + " segundos.");
                    break;
                case "4":
                    String fecha2;
                    System.out.print("Ingrese la fecha en formato 'YYYY-MM-DD': ");
                    fecha2 = sn.next();
                    long startTime_4 = System.nanoTime();
                    sistema.hashtagMasUsadoParaUnDiaDado(fecha2);
                    long endTime_4 = System.nanoTime();
                    double durationInSeconds_4 = (endTime_4 - startTime_4) / 1_000_000_000.0;
                    double roundedDuration_4 = Math.round(durationInSeconds_4 * 10.0) / 10.0;
                    System.out.println();
                    System.out.println("El hashtag mas usado para el día dado. Duración: " + roundedDuration_4 + " segundos.");
                    break;
                case "5":
                    sistema.top7CuentasConMasFavoritos();
                    break;
                case "6":
                    String palabra;
                    System.out.print("Introduzca la palabra o frase: ");
                    palabra = sn.next();
                    long startTime_6 = System.nanoTime();
                    sistema.cantidadDeTweets(palabra);
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
