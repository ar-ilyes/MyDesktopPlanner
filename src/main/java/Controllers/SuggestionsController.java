package Controllers;

import Modules.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SuggestionsController implements Initializable {
    @FXML
    AnchorPane SuggestionsContainer;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        for(int i=0;i< Modal.getSuggestions().size();i++){
            AnchorPane SuggContainer=new AnchorPane();
            SuggContainer.setId("Sugg"+Modal.getSuggestions().get(i).getID());
            Label suggName=new Label();
            suggName.setText("SuggName"+Modal.getSuggestions().get(i).getID());
            TextField suggDate=new TextField();
            suggDate.setText("SuggDate"+Modal.getSuggestions().get(i).getID());
            suggDate.setText(Modal.getSuggestions().get(i).getDate().toString());
            suggName.setText(Modal.getSuggestions().get(i).getNom());
            SuggContainer.setPrefSize(570,52);
            SuggContainer.setLayoutX(60.0);
            SuggContainer.setLayoutY(82+i*80);
            suggName.setLayoutX(41.0);
            suggName.setLayoutY(10.0);
            suggDate.setLayoutX(388.0);
            suggDate.setLayoutY(13.0);
            SuggContainer.setStyle("-fx-background-color: RED; -fx-background-radius: 20PX;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
            SuggContainer.getChildren().add(suggName);
            SuggContainer.getChildren().add(suggDate);
            SuggestionsContainer.getChildren().add(SuggContainer);
        }

    }
}
