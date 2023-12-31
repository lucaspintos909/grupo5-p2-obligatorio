import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import entities.Hashtag;
import uy.edu.um.prog2.adt.LinkedList.LinkedList;
import uy.edu.um.prog2.adt.Hash.Hash;

import entities.Tweet;
import entities.Piloto;
import entities.User;

public class CSVReader {
    public static Piloto[] readDrivers() throws IOException {
        String path = "src/drivers.txt";
        String linea;
        BufferedReader br = new BufferedReader(new FileReader(path));
        int contador = 0;
        Piloto[] pilotos = new Piloto[20];
        while ((linea = br.readLine()) != null) {

            pilotos[contador] = new Piloto(linea);

            contador++;
        }
        return pilotos;
    }

    public static CSVReaderReturn readCSV() throws IOException {

        String path = "src/f1_dataset.csv";
        String linea;
        BufferedReader br = new BufferedReader(new FileReader(path));

        int tweets_contador = 0;

        Tweet[] tweets = new Tweet[650_000];
        Hash<String, User> users = new Hash<>(130_000);
        LinkedList<String> userNames = new LinkedList<>();
        br.readLine();
        while ((linea = br.readLine()) != null) {
            String[] tweetAIngresar;

            for (int posibilidad = 0; posibilidad < 280; posibilidad++) {
                String[] lineaSpliteada = linea.split(",");
                if ((lineaSpliteada[lineaSpliteada.length - 1].equals("True") || lineaSpliteada[lineaSpliteada.length - 1].equals("False"))) {
                    break;
                } else {
                    linea += br.readLine();
                    lineaSpliteada = linea.split(",");
                    if ((lineaSpliteada[lineaSpliteada.length - 1].equals("True") || lineaSpliteada[lineaSpliteada.length - 1].equals("False"))) {
                        break;
                    }
                }

            }
            tweetAIngresar = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if (tweetAIngresar.length != 14) {
                continue;
            }

            /* -------------------- Leyendo usuarios -------------------- */
            int cantidadFavoritos = 0;
            try {
                cantidadFavoritos = Integer.parseInt(tweetAIngresar[7].split("\\.")[0]);
            } catch (Exception ignored) {}

            User nuevoUsuario = new User(tweetAIngresar[1], Boolean.parseBoolean(tweetAIngresar[8]), cantidadFavoritos);
            User usuarioIngresado = users.putIfAbsent(tweetAIngresar[1], nuevoUsuario);
            User usuarioDelTweet;

            if (usuarioIngresado == null) {
                usuarioDelTweet = nuevoUsuario;
                userNames.add(tweetAIngresar[1]);
            } else {
                usuarioDelTweet = usuarioIngresado;
                usuarioDelTweet.sumCantidadTweets();
                if (usuarioDelTweet.getCantidadFavoritos() < cantidadFavoritos) {
                    usuarioDelTweet.setCantidadFavoritos(cantidadFavoritos);
                }
            }
            /* -------------------- ---------------- -------------------- */

            tweets[tweets_contador] = new Tweet(Long.parseLong(tweetAIngresar[0]),
                    tweetAIngresar[10].toLowerCase(),
                    tweetAIngresar[12],
                    Boolean.parseBoolean(tweetAIngresar[13]),
                    tweetAIngresar[9],
                    usuarioDelTweet,
                    stringToHashtagArray(tweetAIngresar[11]),
                    tweetAIngresar[7]
            );
            tweets_contador++;
        }

        return new CSVReaderReturn(tweets, users, userNames);
    }

    public static Hashtag[] stringToHashtagArray(String cadena) {
        cadena = cadena.replaceAll("(\\s+|'|\"|\\[|])", "");
        String[] hashtags = cadena.split(",");
        Hashtag[] hashtagsArray = new Hashtag[hashtags.length];

        for (int i = 0; i < hashtags.length; i++) {
            hashtagsArray[i] = new Hashtag(hashtags[i]);
        }
        return hashtagsArray;
    }
}