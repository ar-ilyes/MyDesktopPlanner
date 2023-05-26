package Modules;

public class WrongDureeFormat extends RuntimeException implements java.io.Serializable{
    public WrongDureeFormat() {
        super("format duree incorrect");
    }
}
