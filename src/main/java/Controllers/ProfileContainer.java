package Controllers;

import Modules.Categorie;
import Modules.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class ProfileContainer implements Initializable {
    @FXML
    Circle GoToCalendar;
    @FXML
    Label UserName;
    @FXML
    AnchorPane RendementContainer;
    @FXML
    AnchorPane HistoriqueContainer;
    @FXML
    AnchorPane BadgeContainer;
    @FXML
    Label JourPlusRentable;
    @FXML
    Label DureeCategorie;
    @FXML
    Label MoyenneRendement;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        GoToCalendar.setOnMouseClicked(e -> {
            try {
                OnGoToCalendar();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        for (int i=0;i<Modal.getMyPlannerApp().getCurrentUser().getHistorique().getCalendriers().size();i++){
            Button HistContainer=new Button();
            HistContainer.setId("Historique"+Modal.getMyPlannerApp().getCurrentUser().getHistorique().getCalendriers().get(i).getPeriodeDebut().toString());
            HistContainer.setText("Calendrier du "+Modal.getMyPlannerApp().getCurrentUser().getHistorique().getCalendriers().get(i).getPeriodeDebut().toString());
            HistContainer.setPrefSize(383,42);
            HistContainer.setLayoutX(77);
            HistContainer.setLayoutY(11+i*70);
            HistContainer.setStyle("-fx-background-color: red;-fx-background-radius: 20px;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
            HistoriqueContainer.getChildren().add(HistContainer);
        }
        UserName.setText(Modal.getMyPlannerApp().getCurrentUser().getPseudo());
        MoyenneRendement.setText(String.valueOf(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getMoyenneDesRendements()));
        JourPlusRentable.setText(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJourPlusRentable().toString());
        ArrayList<String> categories=new ArrayList<>();
        for (int i=0;i<Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTachesSimple().size();i++){
            if (!categories.contains(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTachesSimple().get(i).getCategorie().getNom())){
                categories.add(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTachesSimple().get(i).getCategorie().getNom());
            }
        }
        String CategoriesAff="";
        for (String catName : categories){
            CategoriesAff+=catName+" : "+Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getDureeCategorie(catName)+" ";
        }
        DureeCategorie.setText(CategoriesAff);
        //put the badges in the badge container
        for (int i=0;i<Modal.getMyPlannerApp().getCurrentUser().getBadge().size();i++){
            Button badgeContainer=new Button();
            badgeContainer.setText(Modal.getMyPlannerApp().getCurrentUser().getBadge().get(i).getNom());
            badgeContainer.setPrefSize(100,42);
            badgeContainer.setLayoutX(14);
            badgeContainer.setLayoutY(11+i*70);
            badgeContainer.setStyle("-fx-background-color: yellow;-fx-background-radius: 20px;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
            BadgeContainer.getChildren().add(badgeContainer);
        }

    }
    public void OnGoToCalendar() throws Exception {
        Modal.getInstance().getAppView().ShowJournee();
    }
}
