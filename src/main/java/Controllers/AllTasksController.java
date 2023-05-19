package Controllers;

import Modules.Modal;
import Modules.TacheSimple;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AllTasksController implements Initializable {
    @FXML
    Button AddTasksAuto;
    @FXML
    AnchorPane AllTasksContainer;
    @FXML
    Circle GoToCalendar;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        ArrayList<TacheSimple> taches = new ArrayList<>(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTachesSimple().values());

        //put every task from all simple tasks in calendrier_perso in the container as a form of a button with the following style : style="-fx-background-color: #FF0000; -fx-background-radius: 20px;" and an id of the form Task+number of the task in the array list
        for (int i=0;i<taches.size();i++){
            Button taskContainer=new Button();
            taskContainer.setId("AllTasks"+taches.get(i).getID());
            taskContainer.setText(taches.get(i).getNom());
            taskContainer.setPrefSize(570,48);
            taskContainer.setLayoutX(60.0);
            taskContainer.setLayoutY(14+i*80);
            taskContainer.setStyle("-fx-background-color: red;-fx-background-radius: 20px;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
            AllTasksContainer.getChildren().add(taskContainer);
        }
        AddTasksAuto.setOnAction(e -> {
            try {
                Stage stage= (Stage) AddTasksAuto.getScene().getWindow();
                stage.close();
                Modal.getInstance().getAppView().ShowNumberTasksToAdd();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

    }
}
