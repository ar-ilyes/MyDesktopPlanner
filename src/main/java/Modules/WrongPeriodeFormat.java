package Modules;

public class WrongPeriodeFormat extends RuntimeException implements java.io.Serializable{
    public WrongPeriodeFormat() {
        super("format periode incorrect");
    }
}
