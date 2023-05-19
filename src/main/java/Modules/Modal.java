package Modules;
import View.AppView;

import java.time.LocalDate;

public class Modal {
    // we will use singleton design pattern to make sure that we have only one instance of the modal
    private static Modal modal = null;
    private static MyPlannerApp myPlannerApp = null;
    private final AppView appView = new AppView();
    private static LocalDate selectedDay;
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
}
