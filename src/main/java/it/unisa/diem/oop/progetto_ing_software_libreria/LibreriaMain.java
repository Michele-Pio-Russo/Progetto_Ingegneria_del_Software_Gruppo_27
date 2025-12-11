package it.unisa.diem.oop.progetto_ing_software_libreria;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX LibreriaMain
 */
public class LibreriaMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("Interfaccia_Main.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.associaStage

        Scene principale = new Scene(root, 520, 320);
        stage.setScene(principale);
        stage.setTitle("Libreria universitaria");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}