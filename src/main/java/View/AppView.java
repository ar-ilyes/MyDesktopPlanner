package View;

import Controllers.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppView {
    public void ShowJournee() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JOURNEE-PAGE.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.show();
    }

    public void ShowRegister() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.show();
    }

    public void ShowCalendarFirstTimePeriodes() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CALENDAR-FIRST-TIME.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.show();
    }
    public void ShowCalendarFirstTimeCreneaux() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CALENDAR-FIRST-TIME-CRENEAUX.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.show();
    }
    public void ShowCalendarPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CalendarPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.show();
    }

    public void ShowAddTaskJournee() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTaskJournne.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.show();
    }
    public void ShowAddCreneauJournee() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Creneau.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.show();
    }
    public void ShowAllTasks() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllTasks.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.show();
    }
    public void ShowAddTasksForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTasksForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.show();
    }
    public void ShowSuggestionsPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuggestionsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.show();
    }
    public void ShowNumberTasksToAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTasksNumberToAdd.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.show();
    }



}
