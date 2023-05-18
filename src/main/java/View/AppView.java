package View;

import Controllers.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppView {
    public void ShowJournee() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("JOURNEE-PAGE.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}
