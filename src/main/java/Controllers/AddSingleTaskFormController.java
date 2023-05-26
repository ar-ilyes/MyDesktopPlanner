package Controllers;

import Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddSingleTaskFormController implements Initializable {
    @FXML
    Button planifierSingle;
    @FXML
    TextField dureeSingle;
    @FXML
    TextField nomSingle;
    @FXML
    TextField periodiqueSingle;
    @FXML
    ChoiceBox<String> prioriteSingle;
    @FXML
    CheckBox composeSingle;
    @FXML
    DatePicker deadlineSingle;
    @FXML
    ChoiceBox<String> categorieSingle;
    @FXML
    TextField ProjetName;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        planifierSingle.setOnAction(e -> {
            try {
                OnPlanifierSingle();
            } catch (NotFilledForm notFilledForm) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Le format du créneau est incorrect");
                alert.showAndWait();
                Stage stage = (Stage) planifierSingle.getScene().getWindow();
                stage.close();
                try {
                    Modal.getInstance().getAppView().ShowJournee();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (DeadLinePassed deadLinePassed) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("La deadline est dépassée");
                alert.showAndWait();
                Stage stage = (Stage) planifierSingle.getScene().getWindow();
                stage.close();
                try {
                    Modal.getInstance().getAppView().ShowJournee();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (WrongCreneauFormat wrongCreneauFormat) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Le format du créneau est incorrect");
                alert.showAndWait();
                Stage stage = (Stage) planifierSingle.getScene().getWindow();
                stage.close();
                try {
                    Modal.getInstance().getAppView().ShowJournee();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void OnPlanifierSingle() throws NotFilledForm, IOException, DeadLinePassed,WrongCreneauFormat {
        String nom = nomSingle.getText();
        if(nom.isEmpty()){
            throw new NotFilledForm();
        }
        String duree = dureeSingle.getText();
        if (duree.isEmpty()){
            throw new NotFilledForm();
        }
        String periodique = periodiqueSingle.getText();
        if (periodique.isEmpty()){
            throw new NotFilledForm();
        }
        String prioriteStr = prioriteSingle.getValue();
        if (prioriteStr.isEmpty()){
            throw new NotFilledForm();
        }

        LocalDate deadline = deadlineSingle.getValue();
        if (deadline == null){
            throw new NotFilledForm();
        }
        boolean compose = composeSingle.isSelected();
        String categorieTacheStr = categorieSingle.getValue();
        if (categorieTacheStr.isEmpty()){
            throw new NotFilledForm();
        }

        Etat etatTache = Etat.Not_realised;
        Etat_realisation etatRealisationTache = Etat_realisation.EN_COURS;
            Priorite priorite = Priorite.valueOf(prioriteStr);
            Categorie categorieTache = new Categorie(categorieTacheStr);
            Boolean bool=true;

            if(compose){
                TacheDecompose tache = new TacheDecompose(nom, Integer.parseInt(duree),new Creneau("00:00","00:00"),priorite,deadlineSingle.getValue(),categorieTache,categorieTache.getCouleur(),etatTache,etatRealisationTache,new ArrayList<TacheSimple>(),0);
                String ProjetNameStr = ProjetName.getText();
                Projet projet = Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(ProjetNameStr);
                if(projet==null){
                    projet=new Projet(ProjetNameStr,"test description",Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso());
                    Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().ajouterProjet(projet);
                }
                projet.ajouterTache(tache);
                bool=Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().planifierAuto(tache);
                ///!!!!!!!!!!!!!!!!we have to add replanification(Tachdecompose ) methode in calendrier_perso
                try {
                    Modal.getInstance().getAppView().ShowAllTasks();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                TacheSimple tacheSimple = new TacheSimple(nom, Integer.parseInt(duree),new Creneau("00:00","00:00"),priorite,deadlineSingle.getValue(),categorieTache,categorieTache.getCouleur(),etatTache,etatRealisationTache,Integer.parseInt(periodique));
                String ProjetNameStr = ProjetName.getText();
                Projet projet = Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(ProjetNameStr);
                if(projet==null){
                    projet=new Projet(ProjetNameStr,"test description",Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso());
                    Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().ajouterProjet(projet);

                }
                projet.ajouterTache(tacheSimple);
                bool=Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().planifierAuto(tacheSimple);
                if(!bool) {
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Replanification");
                    confirmationAlert.setHeaderText("Replanifications de toutes les taches");
                    confirmationAlert.setContentText("voulez vous replanifier tous les taches pour pouvoir planifier cette tache?");
                    ButtonType buttonTypeYes = new ButtonType("Yes");
                    ButtonType buttonTypeNo = new ButtonType("No");
                    confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == buttonTypeYes) {
                            Boolean bool2=Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().replanification(tacheSimple);
                            if(!bool2){
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Error");
                                alert.setHeaderText("Replanification impossible");
                                alert.setContentText("la replanification est impossible votre tache est en etat unscheduled");
                                // Show the alert and wait for user response
                                alert.showAndWait();
                                try {
                                    Modal.getInstance().getAppView().ShowAllTasks();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            try {
                                Modal.getInstance().getAppView().ShowAllTasks();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (response == buttonTypeNo) {
                            try {
                                Modal.getInstance().getAppView().ShowAllTasks();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }else{
                    try {
                        Modal.getInstance().getAppView().ShowAllTasks();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


    }
}

