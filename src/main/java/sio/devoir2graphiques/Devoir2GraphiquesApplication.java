package sio.devoir2graphiques;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Devoir2GraphiquesApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Devoir2GraphiquesApplication.class.getResource("devoir2graphiques-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Devoir Graphiques ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}