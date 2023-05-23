package Modules;

public class NotFilledForm extends RuntimeException {
    public NotFilledForm() {
        super("you can't leave empty fields");
    }
}
