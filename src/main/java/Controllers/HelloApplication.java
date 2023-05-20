package Controllers;

import Modules.Modal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Modal.getInstance().getAppView().ShowRegister();
        //Modal.getInstance().getAppView().ShowProjet();
    }
    public static void main(String[] args) {
        launch();
    }
}