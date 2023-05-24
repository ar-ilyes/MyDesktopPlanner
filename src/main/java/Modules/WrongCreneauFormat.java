package Modules;

public class WrongCreneauFormat extends RuntimeException implements java.io.Serializable {
    public WrongCreneauFormat() {
        super("format creneau incorrect");
    }
}

