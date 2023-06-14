package entities;

public class Piloto {
    private final String name;
    private int cantidadTweets = 0;

    public Piloto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNameLowerCase() {
        return name.toLowerCase();
    }

    public int getCantidadTweets() {
        return cantidadTweets;
    }

    public void sumCantidadTweets() {
        this.cantidadTweets += 1;
    }
}
