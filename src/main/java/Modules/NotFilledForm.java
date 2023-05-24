package Modules;

public class NotFilledForm extends RuntimeException implements java.io.Serializable {
    public NotFilledForm() {
        super("you can't leave empty fields");
    }
}
