package Controllers;

import Modules.Modal;
import Modules.Priorite;
import Modules.TacheSimple;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;

public class TaskInfoSimpleController implements Initializable {
    @FXML
    TextField NameField;
    @FXML
    TextField PrioriteField;
    @FXML
    TextField DeadlineField;
    @FXML
    TextField CategorieField;
    @FXML
    TextField CreneauField;
    @FXML
    TextField PeriodiqueField;
    @FXML
    Button SupprimeTacheButton;
    @FXML
    Button ModifTacheButton;
    @FXML
    Button ApplyModifButton;
    @FXML
    Button DoneButton;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        ApplyModifButton.setVisible(false);
        ApplyModifButton.setDisable(true);
        TacheSimple tache=Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTacheSimple(Modal.getSelectedTaskID());
        NameField.setText(tache.getNom());
        NameField.setEditable(false);
        PrioriteField.setText(tache.getPriorite().toString());
        PrioriteField.setEditable(false);
        DeadlineField.setText(tache.getDeadline().toString());
        DeadlineField.setEditable(false);
        CategorieField.setText(tache.getCategorie().getNom());
        CategorieField.setEditable(false);
        CreneauField.setText(tache.getCreneau().toString());
        CreneauField.setEditable(false);
        PeriodiqueField.setText(Integer.toString(tache.getPeriodicite()));
        PeriodiqueField.setEditable(false);
        SupprimeTacheButton.setOnAction(e -> {
            try {
                OnSupprimeTache();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        ModifTacheButton.setOnAction(e -> {
            try {
                OnModifTache();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        ApplyModifButton.setOnAction(e -> {
            try {
                OnConfirmModifTache();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        DoneButton.setOnAction(e -> {
            try {
                OnDone();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


    }
    public void OnDone() throws IOException {
        LocalDate date = Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTacheSimple(Modal.getSelectedTaskID()).getDate();
        Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(date).setCompletedTache(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTacheSimple(Modal.getSelectedTaskID()));
        Modal.getInstance().getAppView().ShowCalendarPage();
    }
    public void OnSupprimeTache() throws IOException {
        Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().supprimerTache(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTacheSimple(Modal.getSelectedTaskID()));
        Modal.getInstance().getAppView().ShowCalendarPage();
    }
    public void OnModifTache() throws IOException {
        NameField.setEditable(true);
        PrioriteField.setEditable(true);
        DeadlineField.setEditable(true);
        CategorieField.setEditable(true);
        CreneauField.setEditable(true);
        PeriodiqueField.setEditable(true);
        ModifTacheButton.setVisible(false);
        ModifTacheButton.setDisable(true);
        SupprimeTacheButton.setVisible(false);
        SupprimeTacheButton.setDisable(true);
        DoneButton.setVisible(false);
        DoneButton.setDisable(true);
        ApplyModifButton.setVisible(true);
        ApplyModifButton.setDisable(false);
    }
    public void OnConfirmModifTache() throws IOException {
        TacheSimple tache=Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTacheSimple(Modal.getSelectedTaskID());
        tache.setNom(NameField.getText());
        String prioTmp=PrioriteField.getText();
        if(prioTmp.equals("LOW")){
            tache.setPriorite(Priorite.LOW);
        }else if(prioTmp.equals("MEDIUM")){
            tache.setPriorite(Priorite.MEDIUM);
        }else {
            tache.setPriorite(Priorite.HIGH);
        }
        String dateTmp=DeadlineField.getText();
        LocalDate deadline = LocalDate.parse(dateTmp);
        tache.setDeadline(deadline);
        //!!!!!!!!!!!!!!!!!!!see categorie logic
        //tache.setCategorie(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getCategorie(CategorieField.getText()));
        //!!!!!!!!!!!!!!!!!!!see creneau logic
        //tache.setCreneau(Integer.parseInt(CreneauField.getText()));
        //!!!!!!!!!!!!!!!!!!!see periodicite logic
        //tache.setPeriodicite(Integer.parseInt(PeriodiqueField.getText()));
        Modal.getInstance().getAppView().ShowTaskInfoSimple();
    }
}
