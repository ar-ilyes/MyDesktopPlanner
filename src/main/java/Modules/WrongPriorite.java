package Modules;

public class WrongPriorite extends RuntimeException{
    public WrongPriorite() {
        super("priorite doit etre LOW ou MEDIUM ou HIGH");
    }
}
