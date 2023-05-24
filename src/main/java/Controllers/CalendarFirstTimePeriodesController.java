package Controllers;

import Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalendarFirstTimePeriodesController implements Initializable {
    @FXML
    TextField DebutPeriodeFirstTime;
    @FXML
    TextField FinPeriodeFirstTime;
    @FXML
    Button NextPeriodeFirstTime;
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        NextPeriodeFirstTime.setOnAction(e -> {
            try {
                OnNextPeriodeFirstTime();
            }catch (WrongPeriodeFormat wrongPeriodeFormat) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Le format de la période est incorrect");
                alert.showAndWait();
                Stage stage = (Stage) NextPeriodeFirstTime.getScene().getWindow();
                stage.close();
                try {
                    Modal.getInstance().getAppView().ShowCalendarFirstTimePeriodes();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
    });
    }
    public void OnNextPeriodeFirstTime() throws WrongPeriodeFormat{
        Stage stage= (Stage) NextPeriodeFirstTime.getScene().getWindow();
        stage.close();
        String debutPeriodeTmp=DebutPeriodeFirstTime.getText();
        //if debut periode isn't of the format dd/MM/yyyy throw an exception
        if(!debutPeriodeTmp.matches("\\d{2}/\\d{2}/\\d{4}")){
            throw new WrongPeriodeFormat();
        }
        String finPeriodeTmp=FinPeriodeFirstTime.getText();
        //if fin periode isn't of the format dd/MM/yyyy throw an exception
        if(!finPeriodeTmp.matches("\\d{2}/\\d{2}/\\d{4}")){
            throw new WrongPeriodeFormat();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate debutPeriode = LocalDate.parse(debutPeriodeTmp, formatter);
        LocalDate finPeriode = LocalDate.parse(finPeriodeTmp, formatter);
        Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().setPeriodeDebut(debutPeriode);
        Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().setPeriodeFin(finPeriode);
        Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().setUtilisateur(Modal.getMyPlannerApp().getCurrentUser());
        //close the stage to open a new one :
        Stage stage2= (Stage) NextPeriodeFirstTime.getScene().getWindow();
        stage2.close();
        try {
            Modal.getInstance().getAppView().ShowCalendarFirstTimeCreneaux();
        } catch (IOException e) {
            System.out.println("we couldn't open the creneaux window");
            e.printStackTrace();
        }
    }
}
