package Controllers;

import Modules.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTasksNumberToAddController implements Initializable {
    @FXML
    TextField NumberOfTasks;
    @FXML
    Button ConfirmNumber;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        ConfirmNumber.setOnAction(e -> {
            try {
                OnConfirmNumber();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void OnConfirmNumber() throws Exception {
        int numberOfTasks = Integer.parseInt(NumberOfTasks.getText());
        if(numberOfTasks==1){
            Stage stage = (Stage) ConfirmNumber.getScene().getWindow();
            stage.close();
            Modal.setNumberOfTasksToAdd(numberOfTasks);
            Modal.getInstance().getAppView().ShowAddSingleTaskForm();
        } else if (numberOfTasks>1) {
            Stage stage = (Stage) ConfirmNumber.getScene().getWindow();
            stage.close();
            Modal.setNumberOfTasksToAdd(numberOfTasks);
            Modal.getInstance().getAppView().ShowAddTasksForm();
        }else{
            //show error alert
            if(numberOfTasks==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong number of tasks");
            alert.setContentText("you can't add less than 1 task");
            // Show the alert and wait for user response
            alert.showAndWait();
            }else {//case where he enters characters and not numbers
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong number of tasks");
                alert.setContentText("you must enter a number");
                // Show the alert and wait for user response
                alert.showAndWait();
            }
        }

    }
}
