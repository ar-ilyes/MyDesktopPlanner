package Controllers;

import Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AddTaskJourneeController implements Initializable {
    @FXML
    ChoiceBox<String> categorie;
    @FXML
    DatePicker deadline;
    @FXML
    CheckBox compose;
    @FXML
    ChoiceBox<String> priorite;
    @FXML
    TextField periodique;
    @FXML
    CheckBox blocked;
    @FXML
    TextField nom;
    @FXML
    TextField creneau;
    @FXML
    Button planifierJournee;
    @FXML
    Label CreneauLabel;
    @FXML
    TextField duree;
    @FXML
    CheckBox automatique;
    @FXML
    TextField ProjetName;
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        automatique.setOnAction(e -> {
           CreneauLabel.setVisible(!automatique.isSelected());
           creneau.setVisible(!automatique.isSelected());
        });

        planifierJournee.setOnAction(e -> {
            try {
            if (automatique.isSelected()) {
                if (compose.isSelected()) {
                    OnPlanifierJourneeComposeAuto();
                } else {
                    OnPlanifierJourneeAuto();
                }
            } else {
                OnPlanifierJourneeMan();//case of simple task not decomposed
            }
            }catch (NotFilledForm exp){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("vous devez remplir les champs");
                alert.showAndWait();
            }catch (DeadLinePassed exp) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("La deadline est dépassée");
                alert.showAndWait();
            }catch (WrongCreneauFormat exp) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Le format du créneau est incorrect");
                alert.showAndWait();
            }catch (WrongDureeFormat exp) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Le format de la durée est incorrect");
                alert.showAndWait();
            }
            try {
                Stage stage = (Stage) planifierJournee.getScene().getWindow();
                stage.close();
                Modal.getInstance().getAppView().ShowJournee();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

    }
    public void OnPlanifierJourneeComposeAuto() throws NotFilledForm,DeadLinePassed,WrongCreneauFormat,WrongDureeFormat{
        String nomTache = nom.getText();
        if (nomTache.isEmpty()){
            throw new NotFilledForm();
        }
        try {
            if (Integer.parseInt(duree.getText()) == 0) {
                throw new NotFilledForm();
            }

            int dureeTache = Integer.parseInt(duree.getText());
            if (dureeTache == 0) {
                throw new NotFilledForm();
            }
        }catch (NumberFormatException e){
            throw new WrongDureeFormat();
        }
        int dureeTache = Integer.parseInt(duree.getText());
        if (dureeTache == 0){
            throw new NotFilledForm();
        }
        String prioriteTacheStr = priorite.getValue();
        if (prioriteTacheStr.isEmpty()){
            throw new NotFilledForm();
        }
        Priorite prioriteTache = Priorite.LOW;
        if(prioriteTacheStr.equals("MEDIUM")) {
            prioriteTache = Priorite.LOW;
        }else if(prioriteTacheStr.equals("HIGH")) {
            prioriteTache = Priorite.LOW;
        }

        String categorieTacheStr = categorie.getValue();
        if (categorieTacheStr.isEmpty()){
            throw new NotFilledForm();
        }
        Categorie categorieTache = new Categorie(categorieTacheStr);
        Etat etatTache = Etat.Not_realised;
        Etat_realisation etatRealisationTache = Etat_realisation.EN_COURS;
        TacheDecompose tache = new TacheDecompose(nomTache,dureeTache,new Creneau("08:00","09:00"),prioriteTache,deadline.getValue(),categorieTache,categorieTache.getCouleur(),etatTache,etatRealisationTache,new ArrayList<TacheSimple>(),0);
        if(blocked.isSelected()) {
            tache.setBloqué(true);
        }
        String ProjetNameStr = ProjetName.getText();
        Projet projet = Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(ProjetNameStr);
        if(projet==null){
            projet=new Projet(ProjetNameStr,"test description",Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso());
            Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().ajouterProjet(projet);
        }
        projet.ajouterTache(tache);

        Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(Modal.getSelectedDay()).introduireTacheAuto(tache);

    }
    public void OnPlanifierJourneeAuto() throws NotFilledForm,DeadLinePassed,WrongCreneauFormat,WrongDureeFormat{
        String nomTache = nom.getText();
        if (nomTache.isEmpty()){
            throw new NotFilledForm();
        }
        //if duree is contains characters that are not numbers throw an error throw exception
        try {
            if (Integer.parseInt(duree.getText()) == 0) {
                throw new NotFilledForm();
            }

            int dureeTache = Integer.parseInt(duree.getText());
            if (dureeTache == 0) {
                throw new NotFilledForm();
            }
        }catch (NumberFormatException e){
            throw new WrongDureeFormat();
        }
        int dureeTache = Integer.parseInt(duree.getText());
        String categorieTacheStr = categorie.getValue();
        if (categorieTacheStr.isEmpty()){
            throw new NotFilledForm();
        }
        Categorie categorieTache = new Categorie(categorieTacheStr);
        String prioriteTacheStr = priorite.getValue();
        if (prioriteTacheStr.isEmpty()){
            throw new NotFilledForm();
        }
        Priorite prioriteTache = Priorite.LOW;
        if(prioriteTacheStr.equals("MEDIUM")) {
            prioriteTache = Priorite.LOW;
        }else if(prioriteTacheStr.equals("HIGH")) {
            prioriteTache = Priorite.LOW;
        }
        Etat etatTache = Etat.Not_realised;
        Etat_realisation etatRealisationTache = Etat_realisation.EN_COURS;
        int periodiciteTache = Integer.parseInt(periodique.getText());
        TacheSimple tache = new TacheSimple(nomTache,dureeTache,new Creneau("08:00","09:00"),prioriteTache,deadline.getValue(),categorieTache,categorieTache.getCouleur(),etatTache,etatRealisationTache,periodiciteTache);
        if(blocked.isSelected()) {
            tache.setBloqué(true);
        }
        String ProjetNameStr = ProjetName.getText();
        //check if there is a project with the same name in calendrier_perso
        Projet projet = Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(ProjetNameStr);
        if(projet==null){
            projet=new Projet(ProjetNameStr,"test description",Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso());
            Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().ajouterProjet(projet);

        }
        projet.ajouterTache(tache);
        Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(Modal.getSelectedDay()).introduireTacheAuto(tache);
    }
    public void OnPlanifierJourneeMan() throws NotFilledForm,DeadLinePassed,WrongCreneauFormat{
        String nomTache = nom.getText();
        if (nomTache.isEmpty()){
            throw new NotFilledForm();
        }
        int dureeTache = Integer.parseInt(duree.getText());
        if (dureeTache == 0){
            throw new NotFilledForm();
        }
        String categorieTacheStr = categorie.getValue();
        if (categorieTacheStr.isEmpty()){
            throw new NotFilledForm();
        }
        Categorie categorieTache = new Categorie(categorieTacheStr);
        String prioriteTacheStr = priorite.getValue();
        if (prioriteTacheStr.isEmpty()){
            throw new NotFilledForm();
        }
        Priorite prioriteTache = Priorite.LOW;
        String creneauTacheStr = creneau.getText();
        if (creneauTacheStr.isEmpty()){
            throw new NotFilledForm();
        }
        Creneau creneauTache = new Creneau("00:00","00:00");
        creneauTache.setDebut(creneauTacheStr.split("-")[0]);
        creneauTache.setFin(creneauTacheStr.split("-")[1]);
        if(prioriteTacheStr.equals("MEDIUM")) {
            prioriteTache = Priorite.LOW;
        }else if(prioriteTacheStr.equals("HIGH")) {
            prioriteTache = Priorite.LOW;
        }
        Etat etatTache = Etat.Not_realised;
        Etat_realisation etatRealisationTache = Etat_realisation.EN_COURS;
        int periodiciteTache = Integer.parseInt(periodique.getText());
        TacheSimple tache = new TacheSimple(nomTache,dureeTache,new Creneau("08:00","09:00"),prioriteTache,deadline.getValue(),categorieTache,categorieTache.getCouleur(),etatTache,etatRealisationTache,periodiciteTache);
        if(blocked.isSelected()) {
            tache.setBloqué(true);
        }
        String ProjetNameStr = ProjetName.getText();
        //check if there is a project with the same name in calendrier_perso
        Projet projet = Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(ProjetNameStr);
        if(projet==null){
            projet=new Projet(ProjetNameStr,"test description",Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso());
            Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().ajouterProjet(projet);

        }
        projet.ajouterTache(tache);
        Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(Modal.getSelectedDay()).introduireTacheManuelle(tache,creneauTache);
    }
}
