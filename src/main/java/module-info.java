module com.example.tp_poo {
    requires javafx.controls;
    requires javafx.fxml;


    opens Controllers to javafx.fxml;
    exports Controllers;
}