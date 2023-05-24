package Modules;

public class InvalidCreneauMin extends RuntimeException{
    public InvalidCreneauMin() {
        super("creneau min doit etre superieur a 30");
    }
}
