package Controllers;

import Modules.Creneau;
import Modules.Modal;
import Modules.WrongCreneauFormat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCreneaux implements Initializable {
    @FXML
    Button AddNewCreneau;
    @FXML
    TextField NewCreneaux;
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        AddNewCreneau.setOnAction(e -> {
            try {
                OnAddNewCreneau();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (WrongCreneauFormat wrongCreneauFormat) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Le format du créneau est incorrect");
                alert.showAndWait();
                Stage stage = (Stage) AddNewCreneau.getScene().getWindow();
                stage.close();
                try {
                    Modal.getInstance().getAppView().ShowJournee();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void OnAddNewCreneau() throws IOException,WrongCreneauFormat {
        String creneauxTmp = NewCreneaux.getText();
        //remove spaces from creneauxTmp
        creneauxTmp=creneauxTmp.replaceAll("\\s+","");
        //split creneauxTmp by "|" and put it in creneauxArray
        String[] creneauxArray = creneauxTmp.split("\\|");
        //for each creneau in creneauxArray split it by "-" and with the 2 strings create a creneau and put it in creneaux else throw an error
        for (String creneau : creneauxArray) {
            String[] creneauArray = creneau.split("-");
            if (creneauArray.length == 2) {
                Creneau creneauToAdd = new Creneau(creneauArray[0], creneauArray[1]);
                creneauToAdd.setDebut(creneauArray[0]);//this one is to verify the format of the hour because it's not verified in the constructor
                creneauToAdd.setFin(creneauArray[1]);//this one is to verify the format of the hour because it's not verified in the constructor
                Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(Modal.getSelectedDay()).introduireCreneau(creneauToAdd);
            } else {
                throw new WrongCreneauFormat();
            }
        }
        Stage stage = (Stage) AddNewCreneau.getScene().getWindow();
        stage.close();
        Modal.getInstance().getAppView().ShowJournee();
    }
}
