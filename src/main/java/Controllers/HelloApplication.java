package Controllers;

import Modules.Modal;
import Modules.Utilisateur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Modal.getInstance().getAppView().ShowLogin();
        // print users and there passwords
        System.out.println("users number"+ Modal.getMyPlannerApp().getUsers().size());
        for (Utilisateur user : Modal.getMyPlannerApp().getUsers().values()) {
            System.out.println(user.getPseudo() +  " " + user.getMotDePasse());
        }
    }
    public static void main(String[] args) {
        launch();
    }
}