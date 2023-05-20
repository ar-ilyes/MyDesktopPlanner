package Controllers;

import Modules.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SuggestionsController implements Initializable {
    @FXML
    AnchorPane SuggestionsContainer;
    @FXML
    Button applySugg;
    @FXML
    Label etaleePeriodeMessage;
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        if (Modal.getSuggestions().size()==Modal.getTasksToAdd().size()){
            etaleePeriodeMessage.setVisible(false);
        }else{
            etaleePeriodeMessage.setVisible(true);
            //make textfields not editable
            for(int i=0;i<Modal.getSuggestions().size();i++){
                TextField suggDate=(TextField) SuggestionsContainer.lookup("#SuggDate"+Modal.getSuggestions().get(i).getID());
                suggDate.setEditable(false);
            }
        }
        for(int i=0;i< Modal.getSuggestions().size();i++){
            AnchorPane SuggContainer=new AnchorPane();
            SuggContainer.setId("Sugg"+Modal.getSuggestions().get(i).getID());
            Label suggName=new Label();
            suggName.setId("SuggName"+Modal.getSuggestions().get(i).getID());
            TextField suggDate=new TextField();
            suggDate.setId("SuggDate"+Modal.getSuggestions().get(i).getID());
            suggDate.setText(Modal.getSuggestions().get(i).getDate().toString());
            suggName.setText(Modal.getSuggestions().get(i).getNom());
            SuggContainer.setPrefSize(570,52);
            SuggContainer.setLayoutX(30.0);
            SuggContainer.setLayoutY(16+i*80);
            suggName.setLayoutX(41.0);
            suggName.setLayoutY(10.0);
            suggDate.setLayoutX(300.0);
            suggDate.setLayoutY(6.0);
            suggDate.setPrefSize(200,30);
            SuggContainer.setStyle("-fx-background-color: RED; -fx-background-radius: 20PX;-fx-font-family: 'Segoe UI Black'; -fx-font-size: 22px; -fx-text-fill: #FFFFFF;");
            SuggContainer.getChildren().add(suggName);
            SuggContainer.getChildren().add(suggDate);
            SuggestionsContainer.getChildren().add(SuggContainer);
        }
        applySugg.setOnAction(e -> {
            try {
                Onapply();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    public void Onapply() throws IOException {
        //if suggestions.size()==tasksToAdd.size() then we can apply the suggestions
        //else we will show a message that says that the number of suggestions is not equal to the number of tasks to add and if he wants to etale the periode and the user can click to call etaleeperiode(tasksToAdd)
        //if he clicks yes then we will call etaleeperiode(tasksToAdd)
        if(Modal.getSuggestions().size()==Modal.getTasksToAdd().size()) {
            //!!!!!!!!!!!!!!! ajouter la fonctionnalité de modifier les suggestions
            Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().applySuggestions(Modal.getSuggestions());
        }else{
            Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().etaleLaPeriode(Modal.getTasksToAdd());
        }
        Modal.getInstance().getAppView().ShowAllTasks();
    }
}
