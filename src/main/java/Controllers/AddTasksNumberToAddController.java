package Controllers;

import Modules.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
        Modal.setNumberOfTasksToAdd(numberOfTasks);
        Modal.getInstance().getAppView().ShowAddTasksForm();
    }
}
