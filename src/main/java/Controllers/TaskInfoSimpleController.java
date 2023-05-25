package Controllers;

import Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
    @FXML
    Circle returnToCalendar;
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
        CreneauField.setText(tache.getCreneau().getDebut()+" - "+tache.getCreneau().getFin());
        CreneauField.setEditable(false);
        PeriodiqueField.setText(Integer.toString(tache.getDuree()));//it displays now duree instead of periodicite
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
            } catch (NotFilledForm notFilledForm) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
                try {
                    Modal.getInstance().getAppView().ShowJournee();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (WrongDateFormat ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Veuillez entrer une date valide");
                alert.showAndWait();
                try {
                    Modal.getInstance().getAppView().ShowJournee();
                } catch (IOException exp) {
                    throw new RuntimeException(exp);
                }
            } catch (WrongPriorite ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Veuillez entrer une priorité valide");
                alert.showAndWait();
                try {
                    Modal.getInstance().getAppView().ShowJournee();
                } catch (IOException exp) {
                    throw new RuntimeException(exp);
                }
            }catch (IOException ex){
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
        returnToCalendar.setOnMouseClicked(e -> {
            try {
                Modal.getInstance().getAppView().ShowJournee();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }
    public void OnDone() throws IOException {
        LocalDate date = Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTacheSimple(Modal.getSelectedTaskID()).getDate();
        if(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(date).getNbTachesRealisees()==Modal.getMyPlannerApp().getCurrentUser().getNbr_min_tache()-1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Félicitations");
            alert.setHeaderText("Vous avez atteint votre objectif de tâches pour aujourd'hui");
            alert.setContentText("Vous pouvez vous reposer");
            alert.showAndWait();
        }
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
        DeadlineField.setEditable(false);
        CategorieField.setEditable(true);
        CreneauField.setEditable(false);
        PeriodiqueField.setEditable(false);
        ModifTacheButton.setVisible(false);
        ModifTacheButton.setDisable(true);
        SupprimeTacheButton.setVisible(false);
        SupprimeTacheButton.setDisable(true);
        DoneButton.setVisible(false);
        DoneButton.setDisable(true);
        ApplyModifButton.setVisible(true);
        ApplyModifButton.setDisable(false);
    }
    public void OnConfirmModifTache() throws IOException , NotFilledForm, WrongDateFormat, WrongPriorite {
        TacheSimple tache=Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTacheSimple(Modal.getSelectedTaskID());
        if (NameField.getText().isEmpty()){
            throw new NotFilledForm();
        }
        tache.setNom(NameField.getText());
        String prioTmp=PrioriteField.getText();
        if (prioTmp.isEmpty()){
            throw new NotFilledForm();
        }
        if(prioTmp.equals("LOW")){
            tache.setPriorite(Priorite.LOW);
        }else if(prioTmp.equals("MEDIUM")){
            tache.setPriorite(Priorite.MEDIUM);
        }else if(prioTmp.equals("HIGH")) {
            tache.setPriorite(Priorite.HIGH);
        }else {
            throw new WrongPriorite();
        }
        String dateTmp=DeadlineField.getText();
        if (dateTmp.isEmpty()){
            throw new NotFilledForm();
        }
        try {
            LocalDate deadline = LocalDate.parse(dateTmp);
        }catch ( DateTimeParseException e){
            throw new WrongDateFormat();
        }
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
