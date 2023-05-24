package Modules;

public class WrongDateFormat extends RuntimeException implements java.io.Serializable{
    public WrongDateFormat() {
        super("format date incorrect");
    }
}
