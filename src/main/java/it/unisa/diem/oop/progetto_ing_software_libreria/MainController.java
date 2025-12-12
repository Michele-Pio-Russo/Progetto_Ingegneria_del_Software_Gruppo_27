package it.unisa.diem.oop.progetto_ing_software_libreria;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainController implements Initializable {

    @FXML
    private Button utenti;
    @FXML
    private Button libri;
    @FXML
    private Button prestiti;
    @FXML
    private Button esci;

    // Riferimenti: mainStage finestra, altri contenuti (Scene)
    private Stage mainStage;
    private Scene sceneUtente;
    private Scene sceneLibro;
    private Scene scenePrestito;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }


    public void associaStage(Stage main, Scene utente, Scene libro, Scene prestito) {
        this.mainStage = main;
        this.sceneUtente = utente;
        this.sceneLibro = libro;
        this.scenePrestito = prestito;
    }

    @FXML
    private void onUtentiClicked() {
        if (sceneUtente != null) {
            mainStage.setScene(sceneUtente);
        }
    }

    @FXML
    private void onLibriClicked() {
        if (sceneLibro != null) {
            mainStage.setScene(sceneLibro);
        }
    }

    @FXML
    private void onPrestitiClicked() {
        if (scenePrestito != null) {
            mainStage.setScene(scenePrestito);
        }
    }

    @FXML
    private void onEsci() {
        // DataManager.salvaTutto(); 
        System.out.println("Salvataggio dati e chiusura...");
        Platform.exit();
        System.exit(0);
    }
}