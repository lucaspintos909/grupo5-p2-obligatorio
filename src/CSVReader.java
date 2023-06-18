import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

            pilotos[contador] = new Piloto(linea.toLowerCase());
            //System.out.println(linea);
            contador++;
        }
        return pilotos;
    }

    public static Tweet[] readTweets() throws IOException {

        String path = "/home/lpintos/proyectos/grupo5-p2-obligatorio/src/f1_dataset.csv";
        String linea;
        BufferedReader br = new BufferedReader(new FileReader(path));

        int segunda = 0;
        int tweets_contador = 0;

        Tweet[] tweets = new Tweet[650_000];

        while ((linea = br.readLine()) != null) {
            String[] tweetAIngresar;
            segunda++;
            if (segunda == 0) {
                /*tweets[0] = linea;*/
                tweets_contador++;
                continue;
            }
            for (int posibilidad = 0; posibilidad < 280; posibilidad++) {
                if ((linea.split(",")[linea.split(",").length - 1].equals("True") || linea.split(",")[linea.split(",").length - 1].equals("False"))) {
                    break;
                } else {
                    linea += br.readLine();
                    if ((linea.split(",")[linea.split(",").length - 1].equals("True") || linea.split(",")[linea.split(",").length - 1].equals("False"))) {
                        break;
                    }
                }

            }
            tweetAIngresar = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            if (tweetAIngresar.length != 14){
                continue;
            }
            tweets[tweets_contador] = new Tweet(Long.parseLong(tweetAIngresar[0]), tweetAIngresar[10].toLowerCase(), tweetAIngresar[12], Boolean.parseBoolean(tweetAIngresar[13]), tweetAIngresar[9]);
            tweets_contador++;
        }

        return tweets;
    }
}