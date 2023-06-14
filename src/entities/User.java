package entities;

public class User {
    private final int id;
    private final String name;
    private int cantidadTweets = 0;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCantidadTweets() {
        return cantidadTweets;
    }

    public void sumCantidadTweets() {
        this.cantidadTweets += 1;
    }
}
