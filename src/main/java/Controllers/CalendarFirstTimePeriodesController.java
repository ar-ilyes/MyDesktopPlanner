package Controllers;

import Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        OnNextPeriodeFirstTime();
    });
    }
    public void OnNextPeriodeFirstTime(){
        Stage stage= (Stage) NextPeriodeFirstTime.getScene().getWindow();
        stage.close();
        String debutPeriodeTmp=DebutPeriodeFirstTime.getText();
        String finPeriodeTmp=FinPeriodeFirstTime.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate debutPeriode = LocalDate.parse(debutPeriodeTmp, formatter);
        LocalDate finPeriode = LocalDate.parse(finPeriodeTmp, formatter);
        Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().setPeriodeDebut(debutPeriode);
        Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().setPeriodeFin(finPeriode);

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
