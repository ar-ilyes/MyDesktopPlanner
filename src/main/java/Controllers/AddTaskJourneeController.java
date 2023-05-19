package Controllers;

import Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        automatique.setOnAction(e -> {
           CreneauLabel.setVisible(!automatique.isSelected());
           creneau.setVisible(!automatique.isSelected());
        });

        planifierJournee.setOnAction(e -> {
            if(automatique.isSelected()) {
                if(compose.isSelected()) {
                    OnPlanifierJourneeComposeAuto();
                }else{
                    OnPlanifierJourneeAuto();
                }
            }else{
                OnPlanifierJourneeMan();//case of simple task not decomposed
            }
            try {
                Modal.getInstance().getAppView().ShowJournee();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

    }
    public void OnPlanifierJourneeComposeAuto(){
        String nomTache = nom.getText();
        int dureeTache = Integer.parseInt(duree.getText());
        String categorieTacheStr = categorie.getValue();
        Categorie categorieTache = new Categorie(categorieTacheStr);
        String prioriteTacheStr = priorite.getValue();
        Priorite prioriteTache = Priorite.LOW;
        if(prioriteTacheStr.equals("MEDIUM")) {
            prioriteTache = Priorite.LOW;
        }else if(prioriteTacheStr.equals("HIGH")) {
            prioriteTache = Priorite.LOW;
        }
        Etat etatTache = Etat.Not_realised;
        Etat_realisation etatRealisationTache = Etat_realisation.EN_COURS;
        TacheDecompose tache = new TacheDecompose(nomTache,dureeTache,new Creneau("08:00","09:00"),prioriteTache,deadline.getValue(),categorieTache,categorieTache.getCouleur(),etatTache,etatRealisationTache,new ArrayList<TacheSimple>(),0);
        if(blocked.isSelected()) {
            tache.setBloqué(true);
        }
        Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(Modal.getSelectedDay()).introduireTacheAuto(tache);

    }
    public void OnPlanifierJourneeAuto(){
        String nomTache = nom.getText();
        int dureeTache = Integer.parseInt(duree.getText());
        String categorieTacheStr = categorie.getValue();
        Categorie categorieTache = new Categorie(categorieTacheStr);
        String prioriteTacheStr = priorite.getValue();
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
        Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(Modal.getSelectedDay()).introduireTacheAuto(tache);
    }
    public void OnPlanifierJourneeMan(){
        //TODO
    }
}
