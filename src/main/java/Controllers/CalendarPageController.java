package Controllers;

import Modules.Modal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class CalendarPageController implements Initializable {
    @FXML
    Label DisplayMonth;
    @FXML
    Button PreviousMonth;
    @FXML
    Button NextMonth;
    @FXML
    Button CalDay1;
    @FXML
    Button CalDay2;
    @FXML
    Button CalDay3;
    @FXML
    Button CalDay4;
    @FXML
    Button CalDay5;
    @FXML
    Button CalDay6;
    @FXML
    Button CalDay7;
    @FXML
    Button CalDay8;
    @FXML
    Button CalDay9;
    @FXML
    Button CalDay10;
    @FXML
    Button CalDay11;
    @FXML
    Button CalDay12;
    @FXML
    Button CalDay13;
    @FXML
    Button CalDay14;
    @FXML
    Button CalDay15;
    @FXML
    Button CalDay16;
    @FXML
    Button CalDay17;
    @FXML
    Button CalDay18;
    @FXML
    Button CalDay19;
    @FXML
    Button CalDay20;
    @FXML
    Button CalDay21;
    @FXML
    Button CalDay22;
    @FXML
    Button CalDay23;
    @FXML
    Button CalDay24;
    @FXML
    Button CalDay25;
    @FXML
    Button CalDay26;
    @FXML
    Button CalDay27;
    @FXML
    Button CalDay28;
    @FXML
    Button CalDay29;
    @FXML
    Button CalDay30;
    @FXML
    Button CalDay31;
    @FXML
    Button CalDay32;
    @FXML
    Button CalDay33;
    @FXML
    Button CalDay34;
    @FXML
    Button CalDay35;
    @FXML
    Button CalDay36;
    @FXML
    Button CalDay37;
    private int currentYear;
    private int currentMonth;
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
            // Get the current date
           initCalendar();
           for (int i = 1; i <= 37; i++) {
               Button button = getDayButton(i);
               button.setOnAction(e -> {
                   try {
                       OnClickDay(button);
                   } catch (IOException ex) {
                       throw new RuntimeException(ex);
                   }
               });
           }
        }

        public void OnClickDay(Button button) throws IOException {
            String dayStr = button.getText();
            // day from string to int
            int day = Integer.parseInt(dayStr);
            int month = currentMonth;
            int year = currentYear;
            LocalDate date = LocalDate.of(year, month, day);
            if((date.isBefore(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeFin()) && date.isAfter(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeDebut())) || date.isEqual(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeDebut()) || date.isEqual(Modal.getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeFin())){
                Modal.setSelectedDay(date);
                Stage stage= (Stage) NextMonth.getScene().getWindow();
                stage.close();
                Modal.getInstance().getAppView().ShowJournee();
            }
        }

        private void initCalendar(){
            LocalDate currentDate = LocalDate.now();
            currentYear = currentDate.getYear();
            currentMonth = currentDate.getMonthValue();

            // Display the current month and year
            updateDisplayMonth();


            YearMonth yearMonthObject = YearMonth.of(currentYear, currentMonth);
            LocalDate firstDayOfMonth = yearMonthObject.atDay(1);
            // Get the day of the week for the first day of the month
            String startDayOfWeek = firstDayOfMonth.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            int numStartDayOfWeek = 0;
            if(startDayOfWeek.equals("Sunday")){
                numStartDayOfWeek = 1;
            }else if(startDayOfWeek.equals("Monday")){
                numStartDayOfWeek = 2;
            }else if(startDayOfWeek.equals("Tuesday")){
                numStartDayOfWeek = 3;
            }else if(startDayOfWeek.equals("Wednesday")){
                numStartDayOfWeek = 4;
            }else if(startDayOfWeek.equals("Thursday")){
                numStartDayOfWeek = 5;
            }else if(startDayOfWeek.equals("Friday")){
                numStartDayOfWeek = 6;
            }else if(startDayOfWeek.equals("Saturday")){
                numStartDayOfWeek = 7;
            }
            // Calculate the number of days in the current month
            int daysInMonth = yearMonthObject.lengthOfMonth();
            for(int i=1;i<numStartDayOfWeek;i++){
                Button dayButton = getDayButton(i);
                dayButton.setVisible(false);
            }

            // Set the number of days in the visible buttons and make others invisible
            for (int i = numStartDayOfWeek; i <= 37; i++) {
                Button dayButton = getDayButton(i);

                if (i-numStartDayOfWeek+1 <= daysInMonth) {
                    dayButton.setText(Integer.toString(i-numStartDayOfWeek+1));
                    dayButton.setVisible(true);
                    LocalDate dateColorized = LocalDate.of(currentYear, currentMonth, i-numStartDayOfWeek+1);
                    if((dateColorized.isBefore(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeFin()) && dateColorized.isAfter(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeDebut()))||dateColorized.equals(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeDebut())||dateColorized.equals(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeFin())){
                        dayButton.setStyle("-fx-background-color: #ff0000");
                    }
                } else {
                    dayButton.setVisible(false);
                }

            }
            NextMonth.setOnAction(this::handleNextMonth);
            PreviousMonth.setOnAction(this::handlePreviousMonth);
        }

        @FXML
        private void handleNextMonth(ActionEvent event) {
            currentMonth++;
            if (currentMonth > 12) {
                currentMonth = 1;
                currentYear++;
            }
            updateCalendar();
        }

        @FXML
        private void handlePreviousMonth(ActionEvent event) {
            currentMonth--;
            if (currentMonth < 1) {
                currentMonth = 12;
                currentYear--;
            }
            updateCalendar();
        }

        private void updateCalendar() {
            // Display the current month and year
            updateDisplayMonth();

            YearMonth yearMonthObject = YearMonth.of(currentYear, currentMonth);
            LocalDate firstDayOfMonth = yearMonthObject.atDay(1);
            // Get the day of the week for the first day of the month
            String startDayOfWeek = firstDayOfMonth.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            int numStartDayOfWeek = 0;
            if(startDayOfWeek.equals("Sunday")){
                numStartDayOfWeek = 1;
            }else if(startDayOfWeek.equals("Monday")){
                numStartDayOfWeek = 2;
            }else if(startDayOfWeek.equals("Tuesday")){
                numStartDayOfWeek = 3;
            }else if(startDayOfWeek.equals("Wednesday")){
                numStartDayOfWeek = 4;
            }else if(startDayOfWeek.equals("Thursday")){
                numStartDayOfWeek = 5;
            }else if(startDayOfWeek.equals("Friday")){
                numStartDayOfWeek = 6;
            }else if(startDayOfWeek.equals("Saturday")){
                numStartDayOfWeek = 7;
            }
            // Calculate the number of days in the current month
            int daysInMonth = yearMonthObject.lengthOfMonth();
            for(int i=1;i<numStartDayOfWeek;i++){
                Button dayButton = getDayButton(i);
                dayButton.setVisible(false);
            }

            // Set the number of days in the visible buttons and make others invisible
            for (int i = numStartDayOfWeek; i <= 37; i++) {
                Button dayButton = getDayButton(i);

                if (i-numStartDayOfWeek+1 <= daysInMonth) {
                    dayButton.setText(Integer.toString(i-numStartDayOfWeek+1));
                    dayButton.setVisible(true);
                    LocalDate dateColorized = LocalDate.of(currentYear, currentMonth, i-numStartDayOfWeek+1);
                    if((dateColorized.isBefore(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeFin()) && dateColorized.isAfter(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeDebut()))||dateColorized.equals(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeDebut())||dateColorized.equals(Modal.getInstance().getMyPlannerApp().getCurrentUser().getCalendrier_perso().getPeriodeFin())){
                        dayButton.setStyle("-fx-background-color: #ff0000");
                    }
                } else {
                    dayButton.setVisible(false);
                }

            }
        }

        // Helper method to get the corresponding day button based on the given index
        private Button getDayButton(int index) {
            switch (index) {
                case 1:
                    return CalDay1;
                case 2:
                    return CalDay2;
                case 3:
                    return CalDay3;
                case 4:
                    return CalDay4;
                case 5:
                    return CalDay5;
                case 6:
                    return CalDay6;
                case 7:
                    return CalDay7;
                case 8:
                    return CalDay8;
                case 9:
                    return CalDay9;
                case 10:
                    return CalDay10;
                case 11:
                    return CalDay11;
                case 12:
                    return CalDay12;
                case 13:
                    return CalDay13;
                case 14:
                    return CalDay14;
                case 15:
                    return CalDay15;
                case 16:
                    return CalDay16;
                case 17:
                    return CalDay17;
                case 18:
                    return CalDay18;
                case 19:
                    return CalDay19;
                case 20:
                    return CalDay20;
                case 21:
                    return CalDay21;
                case 22:
                    return CalDay22;
                case 23:
                    return CalDay23;
                case 24:
                    return CalDay24;
                case 25:
                    return CalDay25;
                case 26:
                    return CalDay26;
                case 27:
                    return CalDay27;
                case 28:
                    return CalDay28;
                case 29:
                    return CalDay29;
                case 30:
                    return CalDay30;
                case 31:
                    return CalDay31;
                case 32:
                    return CalDay32;
                case 33:
                    return CalDay33;
                case 34:
                    return CalDay34;
                case 35:
                    return CalDay35;
                case 36:
                    return CalDay36;
                case 37:
                    return CalDay37;
                default:
                    throw new IllegalArgumentException("Invalid index: " + index);
            }
        }

        // Helper method to update the display month label
        private void updateDisplayMonth() {
            LocalDate displayDate = LocalDate.of(currentYear, currentMonth, 1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
            String formattedMonth = displayDate.format(formatter);
            DisplayMonth.setText(formattedMonth);

        }
    }


