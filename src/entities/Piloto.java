package entities;

public class Piloto {
    private final String name;
    private final String[] separatedName;

    public Piloto(String name) {
        this.name = name;
        this.separatedName = name.toLowerCase().split(" ");
    }

    public String[] getSeparatedName() {
        return separatedName;
    }

    public String getName() {
        return name;
    }
}
