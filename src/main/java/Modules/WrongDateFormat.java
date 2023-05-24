package Modules;

public class WrongDateFormat extends RuntimeException{
    public WrongDateFormat() {
        super("format date incorrect");
    }
}
