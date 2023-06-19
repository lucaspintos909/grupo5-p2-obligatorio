package entities;

public class User {
    private final String name;
    private Integer cantidadTweets = 1;
    private boolean verificado = false;

    public User(String name, boolean verificado) {
        this.name = name;
        this.verificado = verificado;
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

    public boolean isVerificado() {
        return verificado;
    }
}
