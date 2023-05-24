package Controllers;

import Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddTasksFormController implements Initializable {
    @FXML
    ChoiceBox<String> categorieAllTasks;
    @FXML
    DatePicker deadlineAllTasks;
    @FXML
    ChoiceBox<String> prioriteAllTasks;
    @FXML
    TextField nomAllTasks;
    @FXML
    TextField dureeAllTasks;
    @FXML
    Button NextAllTasks;
    @FXML
    TextField ProjetName;
    @FXML
    CheckBox Decompose;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        NextAllTasks.setOnAction(e -> {
            try {
                OnNextAllTasks();
            }catch (NotFilledForm notFilledForm) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("vous devez remplir touus les champs");
                alert.showAndWait();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (DeadLinePassed deadLinePassed) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("La deadline est dépassée");
                alert.showAndWait();
            } catch (WrongCreneauFormat wrongCreneauFormat) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Le format du créneau est incorrect");
                alert.showAndWait();
            } catch (WrongDureeFormat wrongDureeFormat) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de format");
                alert.setContentText("Le format de la durée est incorrect");
                alert.showAndWait();
            }

        });
    }
    public void OnNextAllTasks() throws IOException,NotFilledForm,WrongDureeFormat,WrongCreneauFormat,DeadLinePassed {
        String nom = nomAllTasks.getText();
        if (nom.isEmpty()) {
            throw new NotFilledForm();
        }
        try{
        int duree = Integer.parseInt(dureeAllTasks.getText());
        }catch (Exception e){
            throw new WrongDureeFormat();
        }
        int duree = Integer.parseInt(dureeAllTasks.getText());
        String categorieTacheStr = categorieAllTasks.getValue();
        if (categorieTacheStr.isEmpty()) {
            throw new NotFilledForm();
        }
        Categorie categorieTache = new Categorie(categorieTacheStr);
        String prioriteTacheStr = prioriteAllTasks.getValue();
        if (prioriteTacheStr.isEmpty()) {
            throw new NotFilledForm();
        }
        Priorite prioriteTache = Priorite.LOW;
        if(prioriteTacheStr.equals("MEDIUM")) {
            prioriteTache = Priorite.MEDIUM;
        }else if(prioriteTacheStr.equals("HIGH")) {
            prioriteTache = Priorite.HIGH;
        }

        Etat etatTache = Etat.Not_realised;
        Etat_realisation etatRealisationTache = Etat_realisation.EN_COURS;
        Tache tache;
        if(Decompose.isSelected()){
            tache = new TacheDecompose(nom, duree, new Creneau("08:00", "09:00"), prioriteTache, deadlineAllTasks.getValue(), categorieTache, categorieTache.getCouleur(), etatTache, etatRealisationTache, new ArrayList<TacheSimple>(),0);
        }else {
            tache = new TacheSimple(nom, duree, new Creneau("08:00", "09:00"), prioriteTache, deadlineAllTasks.getValue(), categorieTache, categorieTache.getCouleur(), etatTache, etatRealisationTache, 0);
        }
        String ProjetNameStr = ProjetName.getText();
        Projet projet = Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(ProjetNameStr);
        if(projet==null){
            projet=new Projet(ProjetNameStr,"test description",Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso());
            Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().ajouterProjet(projet);
        }
        if (Decompose.isSelected()){
            projet.ajouterTache((TacheDecompose) tache);
        }else{
            projet.ajouterTache((TacheSimple) tache);
        }

        Modal.getTasksToAdd().add(tache);
        Modal.setNumberOfTasksToAdd(Modal.getNumberOfTasksToAdd()-1);
        if(Modal.getNumberOfTasksToAdd()==0){
            System.out.println("tasks :"+Modal.getTasksToAdd().size());
            Modal.setSuggestions(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().planifierAuto(Modal.getTasksToAdd()));
            System.out.println("suggestions :"+Modal.getSuggestions().size());
            Modal.getInstance().getAppView().ShowSuggestionsPage();
            System.out.println("tasks :"+Modal.getTasksToAdd().size());
        }else{
            Modal.getInstance().getAppView().ShowAddTasksForm();
        }
    }
}
