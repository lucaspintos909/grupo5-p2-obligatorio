package entities;

public class User {
    private final String name;
    private Integer cantidadTweets = 1;
    private boolean verificado = false;
    private int cantidadFavoritos;

    public User(String name, boolean verificado, int cantidadFavoritos) {
        this.name = name;
        this.verificado = verificado;
        this.cantidadFavoritos = cantidadFavoritos;
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


    public int getCantidadFavoritos() {
        return cantidadFavoritos;
    }

    public void setCantidadFavoritos(int cantidadFavoritos) {
        this.cantidadFavoritos = cantidadFavoritos;
    }
}
