package Modules;

public class OutOfDateException extends Exception implements java.io.Serializable {
    public OutOfDateException(String message) {
        super(message);
    }
}