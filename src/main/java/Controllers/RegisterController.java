package Controllers;

import Modules.*;
import View.AppView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RegisterController implements Initializable {
    @FXML
    Button registerbtn;
    @FXML
    TextField thePseudo;
    @FXML
    PasswordField thePassword;
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        registerbtn.setOnAction(e -> {
            onLogin();
    });
    }
    public void onLogin(){
        //close the current stage first
        Stage stage= (Stage) registerbtn.getScene().getWindow();
        stage.close();
        String pseudo=thePseudo.getText();
        String motDePasse=thePassword.getText();
        LocalDate debutPeriode = LocalDate.of(2020, 1, 1);//just temporary
        LocalDate finPeriode = LocalDate.of(2020, 1, 7);//just temporary
        //the calendar of the user
        Calendrier calendrier = new Calendrier(debutPeriode,finPeriode,new Historique());
        Utilisateur user = new Utilisateur(pseudo,calendrier, 1, new ArrayList<Badge>(), motDePasse);
        Modal.getInstance().getMyPlannerApp().getUsers().put(pseudo,user);
        Modal.getInstance().getMyPlannerApp().setCurrentUser(user);
        try {
            Modal.getInstance().getAppView().ShowCalendarFirstTimePeriodes();
            System.out.println("User registered :"+Modal.getInstance().getMyPlannerApp().getCurrentUser().getPseudo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
