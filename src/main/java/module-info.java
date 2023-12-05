module sio.devoir2graphiques {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens sio.devoir2graphiques.Tools;
    opens sio.devoir2graphiques to javafx.fxml;
    exports sio.devoir2graphiques;
}