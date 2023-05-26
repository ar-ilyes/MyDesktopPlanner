package Modules;

public class WrongPriorite extends RuntimeException implements java.io.Serializable{
    public WrongPriorite() {
        super("priorite doit etre LOW ou MEDIUM ou HIGH");
    }
}
