package Controllers;

import Modules.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class JourneeController implements Initializable {
    @FXML
    Label JourneeDate;
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
    JourneeDate.setText(Modal.getSelectedDay().toString());
    }
}
