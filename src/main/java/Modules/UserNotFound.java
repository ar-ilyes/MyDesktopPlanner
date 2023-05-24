package Modules;

import java.io.Serializable;

public class UserNotFound extends RuntimeException implements Serializable {
    public UserNotFound() {
        super("User not found");
    }
}
