package Controllers;

import Modules.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class ProjetInfo implements Initializable {
    @FXML
    Circle GoToCalendar;
    @FXML
    Label projetName;
    @FXML
    AnchorPane TasksProjectContainer;
    @FXML
    Label RendementContent;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        projetName.setText(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(Modal.getSelectedProjetID()).getNom());
        RendementContent.setText(String.valueOf(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(Modal.getSelectedProjetID()).rendement()));
        for (int i = 0; i< Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(Modal.getSelectedProjetID()).getTacheSimples().size(); i++){
            Button TaskContainer=new Button();
            TaskContainer.setId("TaskProject"+Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(Modal.getSelectedProjetID()).getTacheSimples().get(i).getID());
            TaskContainer.setText(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(Modal.getSelectedProjetID()).getTacheSimples().get(i).getNom());
            TaskContainer.setPrefSize(570,48);
            TaskContainer.setLayoutX(60.0);
            TaskContainer.setLayoutY(14+i*80);
            TaskContainer.setStyle("-fx-background-color: red;-fx-background-radius: 20px;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
            TasksProjectContainer.getChildren().add(TaskContainer);
        }
        GoToCalendar.setOnMouseClicked(event -> {
            try {
                Modal.getInstance().getAppView().ShowCalendarPage();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
