package Controllers;

import Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class SuggestionsController implements Initializable {
    @FXML
    AnchorPane SuggestionsContainer;
    @FXML
    Button applySugg;
    @FXML
    Label etaleePeriodeMessage;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {

        for(int i=0;i< Modal.getSuggestions().size();i++){
            AnchorPane SuggContainer=new AnchorPane();
            SuggContainer.setId("Sugg"+Modal.getSuggestions().get(i).getID());
            Label suggName=new Label();
            suggName.setId("SuggName"+Modal.getSuggestions().get(i).getID());
            TextField suggDate=new TextField();
            suggDate.setId("SuggDate"+Modal.getSuggestions().get(i).getID());
            suggDate.setText(Modal.getSuggestions().get(i).getDate().toString() + " " + Modal.getSuggestions().get(i).getCreneau().getDebut().toString() + " " + Modal.getSuggestions().get(i).getCreneau().getFin().toString());
            suggName.setText(Modal.getSuggestions().get(i).getNom());
            SuggContainer.setPrefSize(570,52);
            SuggContainer.setLayoutX(30.0);
            SuggContainer.setLayoutY(16+i*80);
            suggName.setLayoutX(25.0);
            suggName.setLayoutY(10.0);
            suggDate.setLayoutX(260.0);
            suggDate.setLayoutY(6.0);
            suggDate.setPrefSize(200,30);
            //suggdate is not editable
            suggDate.setEditable(false);
            suggDate.setPrefWidth(USE_COMPUTED_SIZE);



            SuggContainer.setStyle("-fx-background-color: RED; -fx-background-radius: 20PX;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
            SuggContainer.getChildren().add(suggName);
            SuggContainer.getChildren().add(suggDate);
            SuggestionsContainer.getChildren().add(SuggContainer);
        }
        if (Modal.getSuggestions().size()>=Modal.getTasksToAdd().size()){
            etaleePeriodeMessage.setVisible(false);
        }else{
            etaleePeriodeMessage.setVisible(true);
            //make textfields not editable
            for(int i=0;i<Modal.getSuggestions().size();i++){
                TextField suggDate=(TextField) SuggestionsContainer.lookup("#SuggDate"+Modal.getSuggestions().get(i).getID());
                suggDate.setEditable(false);
            }
        }
        applySugg.setOnAction(e -> {
            try {
                Onapply();
            } catch (WrongCreneauFormat ex) {
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("le format du creneau est incorrect");
                alert.showAndWait();
                Stage stage = (Stage) applySugg.getScene().getWindow();
                stage.close();
                try {
                    Modal.getInstance().getAppView().ShowAllTasks();
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            } catch (WrongDateFormat ex) {
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("le format de la date est incorrect");
                alert.showAndWait();
                Stage stage = (Stage) applySugg.getScene().getWindow();
                stage.close();
                try {
                    Modal.getInstance().getAppView().ShowAllTasks();
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            } catch (IOException ex) {
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("une erreur est survenue");
                alert.showAndWait();
            } catch (DeadLinePassed exp){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("deadline depassé");
                alert.showAndWait();
                Stage stage = (Stage) applySugg.getScene().getWindow();
                stage.close();
                try {
                    Modal.getInstance().getAppView().ShowAllTasks();
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
            }
        });
    }
    public void Onapply() throws IOException, WrongCreneauFormat, WrongDateFormat , DeadLinePassed {
        //if suggestions.size()==tasksToAdd.size() then we can apply the suggestions
        //else we will show a message that says that the number of suggestions is not equal to the number of tasks to add and if he wants to etale the periode and the user can click to call etaleeperiode(tasksToAdd)
        //if he clicks yes then we will call etaleeperiode(tasksToAdd)
        if(Modal.getSuggestions().size()>=Modal.getTasksToAdd().size()) {
            //we read the modifications made by the user
            for (int i = 0; i < Modal.getSuggestions().size(); i++) {
                TextField suggDate = (TextField) SuggestionsContainer.lookup("#SuggDate" + Modal.getSuggestions().get(i).getID());
                String[] date = suggDate.getText().split(" ");
                Modal.getSuggestions().get(i).setDate(LocalDate.parse(date[0]));
                Modal.getSuggestions().get(i).getCreneau().setDebut(date[1]);
                Modal.getSuggestions().get(i).getCreneau().setFin(date[2]);
            }
            Boolean bool=Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().applySuggestions(Modal.getSuggestions());
            if(!bool){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("les suggestions n'ont pas put etre appliquées correctement,les modifications sur le plan ne sont pas cohérentes");
                alert.showAndWait();
                Stage stage = (Stage) applySugg.getScene().getWindow();
                stage.close();
                Modal.getInstance().getAppView().ShowAllTasks();
            }
        }else{
            System.out.println("tasks :"+Modal.getTasksToAdd().size());
            Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().etaleLaPeriode(Modal.getTasksToAdd());
        }
        Modal.getInstance().getAppView().ShowAllTasks();
    }
}
