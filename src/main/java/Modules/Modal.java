package Modules;
import View.AppView;

import java.time.LocalDate;
import java.util.ArrayList;

public class Modal {
    // we will use singleton design pattern to make sure that we have only one instance of the modal
    private static Modal modal = null;
    private static MyPlannerApp myPlannerApp = null;
    private final AppView appView = new AppView();
    private static LocalDate selectedDay;
    private static int selectedTaskID;
    private static int selectedProjetID;
    private static int numberOfTasksToAdd;
    private static ArrayList<Tache> TasksToAdd = new ArrayList<>();
    private static ArrayList<TacheSimple> Suggestions = new ArrayList<>();
    private Modal() {
    }
    public static Modal getInstance() {
        if (modal == null) {
            modal = new Modal();
        }
        return modal;
    }
    public static MyPlannerApp getMyPlannerApp() {
        if (myPlannerApp == null) {
            myPlannerApp = new MyPlannerApp();
        }
        return myPlannerApp;
    }
    public AppView getAppView() {
        return appView;
    }
    public static LocalDate getSelectedDay() {
        return selectedDay;
    }
    public static void setSelectedDay(LocalDate selectedDay) {
        Modal.selectedDay = selectedDay;
    }
    public static int getNumberOfTasksToAdd() {
        return numberOfTasksToAdd;
    }
    public static void setNumberOfTasksToAdd(int numberOfTasksToAdd) {
        Modal.numberOfTasksToAdd = numberOfTasksToAdd;
    }
    public static ArrayList<Tache> getTasksToAdd() {
        return TasksToAdd;
    }
    public static void setTasksToAdd(ArrayList<Tache> tasksToAdd) {
        TasksToAdd = tasksToAdd;
    }
    public static ArrayList<TacheSimple> getSuggestions() {
        return Suggestions;
    }
    public static void setSuggestions(ArrayList<TacheSimple> suggestions) {
        Suggestions = suggestions;
    }
    public static int getSelectedTaskID() {
        return selectedTaskID;
    }
    public static void setSelectedTaskID(int selectedTaskID) {
        Modal.selectedTaskID = selectedTaskID;
    }
    public static int getSelectedProjetID() {
        return selectedProjetID;
    }
    public static void setSelectedProjetID(int selectedProjetID) {
        Modal.selectedProjetID = selectedProjetID;
    }

}
