import entities.Hashtag;
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
        long startTime = System.nanoTime();
        System.out.println("Leyendo tweets del csv...");

        try {
            this.pilotos = CSVReader.readDrivers();

            CSVReaderReturn csvReturn = CSVReader.readCSV();

            this.tweets = csvReturn.tweets;
            this.users = csvReturn.users;

            long endTime = System.nanoTime();
            double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
            double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;
            System.out.println("Datos leídos correctamente. Duración: " + roundedDuration + " segundos.");

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

        } catch (Exception e) {
            java.lang.System.out.println(e.getMessage());
        }
    }

    public void top10PilotosActivos(String mes, String anio) {
        long startTime = System.nanoTime();

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

        /* --------- ORDENAMIENTO ---------*/
        QuickSortPilotosMasMencionados.quickSort(pilotos, contadorPilotos);
        /* ------------------------------- */

        System.out.println("| Fecha: " + mes + "/" + anio);
        System.out.println("| ");

        for (int i = 0; i < 10; i++) {
            System.out.println("| Top " + (i + 1) + " -> " + pilotos[i].getName() + " - Cantidad de menciones: " + contadorPilotos.get(pilotos[i].getName()).getValue());
        }

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;

        System.out.println("| ");
        System.out.println("| Duración: " + roundedDuration + " segundos.");
    }

    public void cantidadDeTweets(String palabra) {
        long startTime = System.nanoTime();

        int contadorPalabras = 0;
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

        System.out.println("| La palabra \"" + palabra + "\" es mencionada en " + contadorPalabras + " tweets.");

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;

        System.out.println("| ");
        System.out.println("| Duración: " + roundedDuration + " segundos.");
    }

    public void cantidadDeHashtagsDistintosParaUnDiaDado(String fecha) {
        long startTime = System.nanoTime();

        String[] fechaSeparada = fecha.split("-");

        if (fechaSeparada.length != 3) {
            System.out.println("| Fecha ingresada con el formato incorrecto. Intente nuevamente.");
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
                    for (Hashtag hashtag : tweet.getHashtags()) {
                        contadorHashtag.putIfAbsent(hashtag.getText(), 0);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("| Hay " + contadorHashtag.size() + " hashtags diferentes para el día " + fecha);

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;

        System.out.println("| ");
        System.out.println("| Duración: " + roundedDuration + " segundos.");
    }

    public void hashtagMasUsadoParaUnDiaDado(String fecha) {
        long startTime = System.nanoTime();

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
                    for (Hashtag hashtag : tweet.getHashtags()) {
                        if (!hashtag.getText().equalsIgnoreCase("f1")) {
                            Integer contadorActual = contadorHashtag.putIfAbsent(hashtag.getText(), 0);
                            if (contadorActual != null) {
                                if (contadorActual >= contadorDeOcurrencias) {
                                    hashtagMasUsado = hashtag.getText();
                                    contadorDeOcurrencias = contadorActual;
                                }
                                contadorHashtag.put(hashtag.getText(), contadorActual + 1);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (contadorDeOcurrencias != 0) {
            System.out.println("| El hashtag mas usado para el día " + fecha + " es: #" + hashtagMasUsado + ", cantidad: " + contadorDeOcurrencias);
        }else{
            System.out.println("| No hay hashtags para el dia dado.");
        }

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;

        System.out.println("| ");
        System.out.println("| Duración: " + roundedDuration + " segundos.");
    }

    public void top15UsuariosConMasTweets() {
        long startTime = System.nanoTime();

        MergeSortPorCantidadDeTweets.MergeSort(userNames, users);

        for (int j = 0; j < 15; j++) {
            String nombre = userNames[userNames.length - 1 - j];
            User usuario = users.get(nombre).getValue();
            System.out.println("| Top " + (j + 1) + " -> " + nombre + ". Cantidad de tweets: " + usuario.getCantidadTweets() + ". Verificado: " + usuario.isVerificado());
        }

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;

        System.out.println("| ");
        System.out.println("| Duración: " + roundedDuration + " segundos.");
    }

    public void top7CuentasConMasFavoritos() {
        long startTime = System.nanoTime();

        /*
         * Esto lo hago porque para el mismo usuario, en diferentes tweets puede llegar a tener más favoritos.
         * Entonces lo que hago es dejar la cantidad más grande
         */
        /*for (Tweet tweet : tweets) {
            if(tweet == null){
                continue;
            }
            User usuarioTweet = users.get(tweet.getUser().getName()).getValue();
            int cantidadFavoritos = tweet.getUser().getCantidadFavoritos();

            if (usuarioTweet.getCantidadFavoritos() < cantidadFavoritos) {
                usuarioTweet.setCantidadFavoritos(cantidadFavoritos);
            }
        }*/

        QuickSortUsuariosFavoritos.quickSort(userNames, users);

        for (int i = 0; i < 7; i++) {
            User usuario = users.get(userNames[i]).getValue();
            System.out.println("| TOP " + (i + 1) + " -> " + usuario.getName() + ", cantidad de favoritos: " + usuario.getCantidadFavoritos());
        }

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double roundedDuration = Math.round(durationInSeconds * 10.0) / 10.0;

        System.out.println("| ");
        System.out.println("| Duración: " + roundedDuration + " segundos.");
    }

    public static void main(String[] args) {
        Sistema sistema = new Sistema();

        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        String opcion;

        while (!salir) {
            System.out.println("\n");
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("| MENU PRINCIPAL                                                      |");
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("| 1) Top 10 pilotos mencionados.                                      |");
            System.out.println("| 2) Top 15 usuarios con más tweets.                                  |");
            System.out.println("| 3) Cantidad de hashtags distintos para un día dado.                 |");
            System.out.println("| 4) Hashtag más usado para un día dado.                              |");
            System.out.println("| 5) Top 7 cuentas con más favoritos.                                 |");
            System.out.println("| 6) Cantidad de tweets con una palabra o frase específicos.          |");
            System.out.println("| 7) Salir                                                            |");
            System.out.println("-----------------------------------------------------------------------");

            System.out.print("| Ingrese una opción: ");
            opcion = sn.next();
            System.out.println();

            switch (opcion) {
                case "1":
                    String mes;
                    String anio;
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println("| 1) Top 10 pilotos mencionados.                                      |");
                    System.out.println("-----------------------------------------------------------------------");

                    System.out.print("| Introduzca el mes: ");
                    mes = sn.next();

                    System.out.print("| Introduzca el año: ");
                    anio = sn.next();

                    sistema.top10PilotosActivos(mes, anio);

                    System.out.println("-----------------------------------------------------------------------");


                    break;
                case "2":
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println("| 2) Top 15 usuarios con más tweets.                                  |");
                    System.out.println("-----------------------------------------------------------------------");

                    sistema.top15UsuariosConMasTweets();

                    System.out.println("-----------------------------------------------------------------------");
                    break;
                case "3":
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println("| 3) Cantidad de hashtags distintos para un día dado.                 |");
                    System.out.println("-----------------------------------------------------------------------");

                    String fecha;
                    System.out.print("| Ingrese la fecha en formato 'YYYY-MM-DD': ");
                    fecha = sn.next();

                    System.out.println("| ");
                    sistema.cantidadDeHashtagsDistintosParaUnDiaDado(fecha);
                    System.out.println("-----------------------------------------------------------------------");
                    break;
                case "4":
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println("| 4) Hashtag más usado para un día dado.                              |");
                    System.out.println("-----------------------------------------------------------------------");

                    String fecha2;
                    System.out.print("Ingrese la fecha en formato 'YYYY-MM-DD': ");
                    fecha2 = sn.next();
                    System.out.println("| ");

                    sistema.hashtagMasUsadoParaUnDiaDado(fecha2);

                    System.out.println("-----------------------------------------------------------------------");
                    break;
                case "5":
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println("| 5) Top 7 cuentas con más favoritos.                                 |");
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println("| ");
                    sistema.top7CuentasConMasFavoritos();
                    break;
                case "6":
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println("| 6) Cantidad de tweets con una palabra o frase específicos.          |");
                    System.out.println("-----------------------------------------------------------------------");

                    String palabra;
                    System.out.print("| Introduzca la palabra o frase: ");
                    palabra = sn.next();
                    System.out.println("| ");

                    sistema.cantidadDeTweets(palabra);

                    System.out.println("-----------------------------------------------------------------------");
                    break;
                case "7":
                    salir = true;
                    break;
                default:
                    System.out.println("| ERROR: Las opciones son entre 1 y 7");

            }


        }

        System.out.println("Bye!");
    }
}
