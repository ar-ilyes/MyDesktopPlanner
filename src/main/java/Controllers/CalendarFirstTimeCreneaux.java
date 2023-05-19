package Controllers;

import Modules.Creneau;
import Modules.Journee;
import Modules.Modal;
import Modules.TacheSimple;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarFirstTimeCreneaux implements Initializable {

   @FXML
    Label DateOfTheDay;
    @FXML
    TextField CreneauxFirstTime;
    @FXML
    Button NextCreneauxFirstTime;
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        //put the date of the day to update
        int numberOfDaysSetted=Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getLesJournees().size();
        DateOfTheDay.setText(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeDebut().plusDays(numberOfDaysSetted).toString());
        NextCreneauxFirstTime.setOnAction(e -> {
            try {
                OnNextCreneauxFirstTime();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void OnNextCreneauxFirstTime() throws IOException {
        int numberOfDaysSetted = Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getLesJournees().size();
        ArrayList<Creneau> creneaux= new ArrayList<Creneau>();
        String creneauxTmp=CreneauxFirstTime.getText();
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
                creneaux.add(creneauToAdd);
            } else {
                throw new RuntimeException("Wrong format for creneaux");//!!!!!!!!!!!!!this must be a customized error
            }
        }
        Journee journee = new Journee(new HashMap<Integer, TacheSimple>(),creneaux,Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeDebut().plusDays(numberOfDaysSetted),Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso());
        Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().addDay(journee.getDate(),journee);
        //close the stage to open a new one :
        Stage stage= (Stage) NextCreneauxFirstTime.getScene().getWindow();
        stage.close();
        if (Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeDebut().plusDays(numberOfDaysSetted).isEqual(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeFin())) {
            Modal.getInstance().getAppView().ShowCalendarPage();//replace it later with showCalendar
        } else {
            Modal.getInstance().getAppView().ShowCalendarFirstTimeCreneaux();
        }
    }
}
