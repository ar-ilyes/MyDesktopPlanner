package Controllers;

import Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class JourneeController implements Initializable {
    @FXML
    Label JourneeDate;
    @FXML
    AnchorPane AllTasksContainer;
    @FXML
    AnchorPane AllCreneauxContainer;
    @FXML
    Button AddCreneau;
    @FXML
    Button AddTask;
    @FXML
    Circle returnToCalendar;
    @FXML
    Circle GoToAllTasks;
    @FXML
    Circle GoToProfile;
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
    JourneeDate.setText(Modal.getSelectedDay().toString());

    //testing////
    /*TacheSimple tache1 = new TacheSimple("testTache", 60, new Creneau("08:00","09:00"), Priorite.LOW, LocalDate.of(2023,5,23), new Categorie("Study",Couleur.BLANC), Couleur.BLEU, Etat.In_Progress, Etat_realisation.EN_COURS, 2);
    Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(Modal.getSelectedDay()).introduireTacheAuto(tache1);
    */


    ///testing////
        for (Journee j : Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getLesJournees().values()){
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("===========>>Jour :"+j.getDate());
            System.out.println("Taches :");
            for (TacheSimple t : j.getTaches().values()){
                System.out.println(t.getNom()+" debut: "+t.getCreneau().getDebut()+" fin: "+t.getCreneau().getFin());
            }
            System.out.println("Creneaux libres :");
            for (Creneau c : j.getCreneauxLibres()){
                System.out.println(c.getDebut()+" "+c.getFin());
            }
        }
    //////////////

        //to loop over elements in the hashmap whithout using entryset we can turn it into an arraylist and loop over it
        ArrayList<TacheSimple> taches = new ArrayList<>(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(Modal.getSelectedDay()).getTaches().values());

        //put every task from all simple tasks in calendrier_perso in the container as a form of a button with the following style : style="-fx-background-color: #FF0000; -fx-background-radius: 20px;" and an id of the form Task+number of the task in the array list
        for (int i=0;i<taches.size();i++){
            Button taskContainer=new Button();
            taskContainer.setId("TaskJour"+taches.get(i).getID());
            taskContainer.setText(taches.get(i).getNom());
            taskContainer.setPrefSize(220,48);
            taskContainer.setLayoutX(64.0);
            taskContainer.setLayoutY(14+i*80);
            taskContainer.setStyle("-fx-background-color: red;-fx-background-radius: 20px;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
            taskContainer.setOnAction(event -> {
                System.out.println("Task"+taskContainer.getId().substring(8));
                Modal.setSelectedTaskID(Integer.parseInt(taskContainer.getId().substring(8)));
                try {
                    Stage stage= (Stage) taskContainer.getScene().getWindow();
                    stage.close();
                    Modal.getInstance().getAppView().ShowTaskInfoSimple();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            AllTasksContainer.getChildren().add(taskContainer);
        }

        for (int i=0;i<Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(Modal.getSelectedDay()).getCreneauxLibres().size();i++) {
            Label creneauContainer = new Label();
            creneauContainer.setId("CreneauJour" + i);
            creneauContainer.setText(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(Modal.getSelectedDay()).getCreneauxLibres().get(i).getDebut() + " - " + Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getJournee(Modal.getSelectedDay()).getCreneauxLibres().get(i).getFin());
            creneauContainer.setPrefSize(155, 54);
            creneauContainer.setLayoutX(44.0);
            creneauContainer.setLayoutY(3 + i * 20);
            creneauContainer.setStyle("-fx-border-width: 80px;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 24px; -fx-text-fill: #3c486b;");
            AllCreneauxContainer.getChildren().add(creneauContainer);
        }

            AddTask.setOnAction(e -> {
            try {
                Stage stage= (Stage) AddTask.getScene().getWindow();
                stage.close();
                Modal.getInstance().getAppView().ShowAddTaskJournee();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        returnToCalendar.setOnMouseClicked(e -> {
            try {
                Stage stage= (Stage) returnToCalendar.getScene().getWindow();
                stage.close();
                Modal.getInstance().getAppView().ShowCalendarPage();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        AddCreneau.setOnAction(e -> {
            try {
                Stage stage= (Stage) AddCreneau.getScene().getWindow();
                stage.close();
                Modal.getInstance().getAppView().ShowAddCreneauJournee();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        GoToAllTasks.setOnMouseClicked(e -> {
            try {
                Stage stage= (Stage) GoToAllTasks.getScene().getWindow();
                stage.close();
                Modal.getInstance().getAppView().ShowAllTasks();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        GoToProfile.setOnMouseClicked(e -> {
            try {
                Stage stage= (Stage) GoToProfile.getScene().getWindow();
                stage.close();
                Modal.getInstance().getAppView().ShowProfilePage();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
