package Controllers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.YearMonth;

public class MonthViewCalendar extends Application {
    private YearMonth currentYearMonth;
    private GridPane calendarGrid;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        currentYearMonth = YearMonth.now();

        // Create grid for calendar
        calendarGrid = new GridPane();
        calendarGrid.setPrefSize(400, 300);
        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.setHgap(50);
        calendarGrid.setVgap(50);
        calendarGrid.setPadding(new Insets(40));

        // Create buttons
        Button previousButton = new Button("Previous");
        previousButton.setOnAction(e -> previousMonth());

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> nextMonth());

        // Create calendar view
        updateCalendar();

        // Create layout
        HBox buttonBox = new HBox(10, previousButton, nextButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, buttonBox, calendarGrid);
        root.setPadding(new Insets(10));

        // Create scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("Month View Calendar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateCalendar() {
        calendarGrid.getChildren().clear();

        // Create label for month and year
        Label monthYearLabel = new Label(currentYearMonth.getMonth().toString() + " " + currentYearMonth.getYear());
        monthYearLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
        calendarGrid.add(monthYearLabel, 0, 0, 7, 1);

        // Create labels for days of the week
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            dayLabel.setStyle("-fx-font-weight: bold");
            calendarGrid.add(dayLabel, i, 1);
        }

        // Get the first day of the month
        LocalDate firstDayOfMonth = currentYearMonth.atDay(1);

        // Calculate the number of days in the month
        int daysInMonth = currentYearMonth.lengthOfMonth();

        // Calculate the index of the first day in the grid
        int startDayIndex = firstDayOfMonth.getDayOfWeek().getValue();

        // Populate the grid with day divs
        int row = 2;
        int col = startDayIndex;
        for (int day = 1; day <= daysInMonth; day++) {
            StackPane dayPane = new StackPane();
            dayPane.setPrefSize(50, 100);
            dayPane.setStyle("-fx-background-color: gray; -fx-border-radius: 50px;");

            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold");

            dayPane.getChildren().add(dayLabel);
            int finalDay = day;
            dayPane.setOnMouseClicked(e -> showAlert(finalDay, currentYearMonth.getMonthValue(), currentYearMonth.getYear()));

            calendarGrid.add(dayPane, col, row);

            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }
    }


    private void showAlert(int day, int month, int year) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Selected Date");
        alert.setHeaderText(null);
        alert.setContentText("Selected date: " + day + "-" + month + "-" + year);
        alert.showAndWait();
    }



    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateCalendar();
    }

    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateCalendar();
    }
}
