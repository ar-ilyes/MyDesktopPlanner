package Modules;
import View.AppView;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Modal implements Serializable {
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
    private static int numHistSelected;
    private Modal() {
    }

    public static Modal getInstance() {
        if (modal == null) {
            // check if the serialized file exists, and deserialize it if it does
            if (new File("modal.ser").exists()) {
                modal = deserialize();
            } else {
                modal = new Modal();
            }
        }
        return modal;
    }

    public static void serialize(Modal modal) {
        try {
            System.out.println(Modal.getMyPlannerApp().getUsers().size());
            FileOutputStream fileOut = new FileOutputStream("modal.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(modal);
            //serialize the static variables
            out.writeObject(selectedDay);
            out.writeObject(selectedTaskID);
            out.writeObject(selectedProjetID);
            out.writeObject(numberOfTasksToAdd);
            out.writeObject(TasksToAdd);
            out.writeObject(Suggestions);
            //serialize myPlannerApp
            out.writeObject(myPlannerApp);
            out.writeInt(Tache.IDtemp);
            out.writeInt(Projet.IDtemp);
            out.writeInt(numHistSelected);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in modal.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Modal deserialize() {
        Modal modal = null;
        try {
            System.out.println("deserializing");
            FileInputStream fileIn = new FileInputStream("modal.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            modal = (Modal) in.readObject();
            //deserialize the static variables
            selectedDay = (LocalDate) in.readObject();
            selectedTaskID = (int) in.readObject();
            selectedProjetID = (int) in.readObject();
            numberOfTasksToAdd = (int) in.readObject();
            TasksToAdd = (ArrayList<Tache>) in.readObject();
            Suggestions = (ArrayList<TacheSimple>) in.readObject();
            //deserialize myPlannerApp
            myPlannerApp = (MyPlannerApp) in.readObject();
            //deserialize the static variables idtemp
            Tache.IDtemp = in.readInt();
            Projet.IDtemp = in.readInt();
            numHistSelected = in.readInt();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
    public static int getNumHistSelected() {
        return numHistSelected;
    }
    public static void setNumHistSelected(int numHistSelected) {
        Modal.numHistSelected = numHistSelected;
    }
}
