package Controllers;

import Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;

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
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        NextAllTasks.setOnAction(e -> {
            try {
                OnNextAllTasks();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void OnNextAllTasks() throws IOException {
        String nom = nomAllTasks.getText();
        int duree = Integer.parseInt(dureeAllTasks.getText());
        String categorieTacheStr = categorieAllTasks.getValue();
        Categorie categorieTache = new Categorie(categorieTacheStr);
        String prioriteTacheStr = prioriteAllTasks.getValue();
        Priorite prioriteTache = Priorite.LOW;
        if(prioriteTacheStr.equals("MEDIUM")) {
            prioriteTache = Priorite.MEDIUM;
        }else if(prioriteTacheStr.equals("HIGH")) {
            prioriteTache = Priorite.HIGH;
        }

        Etat etatTache = Etat.Not_realised;
        Etat_realisation etatRealisationTache = Etat_realisation.EN_COURS;
        TacheSimple tache=new TacheSimple(nom,duree,new Creneau("08:00","09:00"),prioriteTache,deadlineAllTasks.getValue(),categorieTache,categorieTache.getCouleur(),etatTache,etatRealisationTache,0);
        String ProjetNameStr = ProjetName.getText();
        Projet projet = Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getProjet(ProjetNameStr);
        if(projet==null){
            projet=new Projet(ProjetNameStr,"test description",Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso());
            Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().ajouterProjet(projet);
        }
        projet.ajouterTache(tache);
        Modal.getTasksToAdd().add(tache);
        Modal.setNumberOfTasksToAdd(Modal.getNumberOfTasksToAdd()-1);
        if(Modal.getNumberOfTasksToAdd()==0){
            System.out.println("tasks :"+Modal.getTasksToAdd().size());
            Modal.setSuggestions(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().planifierAuto(Modal.getTasksToAdd()));
            System.out.println("suggestions :"+Modal.getSuggestions().size());
            Modal.getInstance().getAppView().ShowSuggestionsPage();
        }else{
            Modal.getInstance().getAppView().ShowAddTasksForm();
        }
    }
}
