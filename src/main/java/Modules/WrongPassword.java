package Modules;

public class WrongPassword extends RuntimeException implements java.io.Serializable{
    public WrongPassword() {
        super("Wrong password");
    }
}
