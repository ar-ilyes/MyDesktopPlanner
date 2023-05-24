package Modules;

public class InvalidCreneauMin extends RuntimeException implements java.io.Serializable{
    public InvalidCreneauMin() {
        super("creneau min doit etre superieur a 30");
    }
}
