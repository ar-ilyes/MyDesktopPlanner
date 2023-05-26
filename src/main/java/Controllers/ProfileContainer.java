package Controllers;

import Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
    @FXML
    Button changeParam;
    @FXML
    TextField NbMinTaches;
    @FXML
    TextField CreneauMin;
    @FXML
    Circle returnToCalendar;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        AtomicBoolean Clicked = new AtomicBoolean(false);
        NbMinTaches.setText(String.valueOf(Modal.getMyPlannerApp().getCurrentUser().getNbr_min_tache()));
        CreneauMin.setText(String.valueOf(Creneau.getMin()));
        // NbMinTaches and CreeauMin are not editable
        NbMinTaches.setEditable(false);
        CreneauMin.setEditable(false);
        try{
        changeParam.setOnMouseClicked(e -> {
            if (!Clicked.get()) {
                NbMinTaches.setEditable(true);
                CreneauMin.setEditable(true);
                changeParam.setText("Valider");
                Clicked.set(true);
            } else {
                NbMinTaches.setEditable(false);
                CreneauMin.setEditable(false);
                changeParam.setText("Changer");
                //if CreneauMin.getText() or NbMinTaches.getText() are empty or are not numbers or are equal to zero we throw an exception
                if (NbMinTaches.getText().isEmpty() || Integer.parseInt(NbMinTaches.getText()) <= 0 ) {
                    throw new NotFilledForm();
                }
                if(CreneauMin.getText().isEmpty() || Integer.parseInt(CreneauMin.getText()) < 30 ){
                    throw new InvalidCreneauMin();
                }
                Modal.getMyPlannerApp().getCurrentUser().setNbr_min_tache(Integer.parseInt(NbMinTaches.getText()));
                Creneau.setMin(Integer.parseInt(CreneauMin.getText()));
                Clicked.set(false);
            }
        });
        }catch (NotFilledForm e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong number of tasks");
            alert.setContentText("you must enter a number");
            alert.showAndWait();
        }catch (InvalidCreneauMin e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong creneau min");
            alert.setContentText("you must enter a number greater than 30");
            alert.showAndWait();
        }

        returnToCalendar.setOnMouseClicked(e -> {
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
            int finalI = i;
            HistContainer.setOnMouseClicked(e -> {
               try {
                   Modal.setNumHistSelected(finalI);
                   Stage stage= (Stage) HistContainer.getScene().getWindow();
                   stage.close();
                   Modal.getInstance().getAppView().ShowHistInfo();
               } catch (Exception ex) {
                   throw new RuntimeException(ex);
               }
              });
            HistoriqueContainer.getChildren().add(HistContainer);
        }
        UserName.setText(Modal.getMyPlannerApp().getCurrentUser().getPseudo());
        MoyenneRendement.setText(String.valueOf(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getMoyenneDesRendements()));
        JourPlusRentable.setText(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJourPlusRentable().toString());
        ArrayList<String> categories=new ArrayList<>();
        for (TacheSimple tache : Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getTachesSimple().values()) {
            if (!categories.contains(tache.getCategorie().getNom())) {
                categories.add(tache.getCategorie().getNom());
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
