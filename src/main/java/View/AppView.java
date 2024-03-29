package View;

import Controllers.HelloApplication;
import Modules.Modal;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class AppView implements java.io.Serializable {

    public void ShowJournee() throws IOException  {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JOURNEE-PAGE.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }


    public void ShowRegister() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }

    public void ShowCalendarFirstTimePeriodes() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CALENDAR-FIRST-TIME.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowCalendarFirstTimeCreneaux() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CALENDAR-FIRST-TIME-CRENEAUX.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowCalendarPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CalendarPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }

    public void ShowAddTaskJournee() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTaskJournne.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowAddCreneauJournee() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Creneau.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowAllTasks() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllTasks.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowAddTasksForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTasksForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowSuggestionsPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuggestionsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowNumberTasksToAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTasksNumberToAdd.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowTaskInfoSimple() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaskInfoSimple.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }

    public void ShowAddSingleTaskForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddSingleTaskForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowProjet() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Projets.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowProjetInfo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProjetInfo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        //hadi bach
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowProfilePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProfilePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        //hadi bach
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }
    public void ShowLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LOGIN.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        //hadi bach
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }

    public void ShowHistInfo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HistoriqueInfo.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage=new Stage();
        stage.setTitle("MyDesktopPlanner");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        //hadi bach
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);
        stage.setFullScreenExitHint("");
        stage.setOnCloseRequest((WindowEvent event) -> {
            Modal.serialize(Modal.getInstance());
        });
        stage.show();
    }






}
