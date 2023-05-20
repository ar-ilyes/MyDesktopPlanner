package Controllers;

import Modules.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ProjetsController implements Initializable {
    @FXML
    Circle GoToCalendar;
    @FXML
    AnchorPane AllProjectsContainer;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        for (int i = 0; i< Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjets().size(); i++){
            Button ProjetContainer=new Button();
            ProjetContainer.setId("Projet"+Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjets().get(i).getID());
            ProjetContainer.setText(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjets().get(i).getNom());
            ProjetContainer.setPrefSize(570,48);
            ProjetContainer.setLayoutX(60.0);
            ProjetContainer.setLayoutY(14+i*80);
            ProjetContainer.setStyle("-fx-background-color: red;-fx-background-radius: 20px;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
            ProjetContainer.setOnAction(event -> {
                Modal.setSelectedProjetID(Integer.parseInt(ProjetContainer.getId().substring(6)));
                try {
                    Stage stage= (Stage) ProjetContainer.getScene().getWindow();
                    stage.close();
                    Modal.getInstance().getAppView().ShowProjetInfo();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            AllProjectsContainer.getChildren().add(ProjetContainer);
        }
    }
}
