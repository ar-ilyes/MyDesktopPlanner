package Controllers;

import Modules.Calendrier;
import Modules.Modal;
import Modules.Projet;
import Modules.TacheSimple;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HistoriqueInfoController implements Initializable{
        @FXML
        Circle GoToCalendar;
        @FXML
        Label histDate;
        @FXML
        Label JourPlusRentable;
        @FXML
        Label DureeCategorie;
        @FXML
        Label MoyenneRendement;
        @FXML
        AnchorPane BadgeContainer;
        @FXML
        AnchorPane TaskContainer;
        @FXML
        AnchorPane ProjetsContainer;
        @Override
        public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
            Calendrier histCal=Modal.getMyPlannerApp().getCurrentUser().getHistorique().getCalendriers().get(Modal.getNumHistSelected());
            histDate.setText(histCal.getPeriodeDebut().toString());
            JourPlusRentable.setText(histCal.getJourPlusRentable().toString());
            MoyenneRendement.setText(String.valueOf(histCal.getMoyenneDesRendements()));
            ArrayList<String> categories=new ArrayList<>();
            for (TacheSimple tache : histCal.getTachesSimple().values()) {
                if (!categories.contains(tache.getCategorie().getNom())) {
                    categories.add(tache.getCategorie().getNom());
                }
            }
            String CategoriesAff="";
            for (String catName : categories){
                CategoriesAff+=catName+" : "+histCal.getDureeCategorie(catName)+" ";
            }
            DureeCategorie.setText(CategoriesAff);

            int i=0;
            for (TacheSimple t : histCal.getTachesSimple().values()){
                Button tachecon=new Button();
                tachecon.setText(t.getNom());
                tachecon.setPrefSize(360,42);
                tachecon.setLayoutX(77);
                tachecon.setLayoutY(11+i*70);
                tachecon.setStyle("-fx-background-color: red;-fx-background-radius: 20px;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
                TaskContainer.getChildren().add(tachecon);
                i++;
            }

            i=0;
            for (Projet t : histCal.getProjets()){
                Button prjCon=new Button();
                prjCon.setText(t.getNom());
                prjCon.setPrefSize(360,42);
                prjCon.setLayoutX(77);
                prjCon.setLayoutY(11+i*70);
                prjCon.setStyle("-fx-background-color: red;-fx-background-radius: 20px;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
                ProjetsContainer.getChildren().add(prjCon);
                i++;
            }
            for (int j=0;j<histCal.getBadges().size();j++){
                Button badgeContainer=new Button();
                badgeContainer.setText(histCal.getBadges().get(j).getNom());
                badgeContainer.setPrefSize(100,42);
                badgeContainer.setLayoutX(14);
                badgeContainer.setLayoutY(11+j*70);
                badgeContainer.setStyle("-fx-background-color: yellow;-fx-background-radius: 20px;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
                BadgeContainer.getChildren().add(badgeContainer);
            }

            GoToCalendar.setOnMouseClicked(e -> {
                try {
                    Modal.getInstance().getAppView().ShowCalendarPage();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
}
