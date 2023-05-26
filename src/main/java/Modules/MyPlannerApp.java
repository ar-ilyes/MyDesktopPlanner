package Modules;

import java.util.HashMap;

public class MyPlannerApp implements java.io.Serializable {
    private HashMap<String, Utilisateur> users;
    private Utilisateur currentUser;

    // Constructor
    public MyPlannerApp() {
        this.users = new HashMap<String, Utilisateur>();
        this.currentUser = null;
    }

    // Getters
    public HashMap<String, Utilisateur> getUsers() {
        return users;
    }

    public Utilisateur getCurrentUser() {
        return currentUser;
    }

    // Setters
    public void setUsers(HashMap<String, Utilisateur> users) {
        this.users = users;
    }

    public void setCurrentUser(Utilisateur currentUser) {
        this.currentUser = currentUser;
    }
}
