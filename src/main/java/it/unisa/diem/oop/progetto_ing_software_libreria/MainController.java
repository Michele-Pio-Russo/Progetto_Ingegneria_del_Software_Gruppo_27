package it.unisa.diem.oop.progetto_ing_software_libreria;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class MainController {
    @FXML
    private Button utenti;
    
    @FXML
    private Button libri;

    @FXML
    private Button prestiti;

    @FXML
    private Button esci;
    
    private Stage main;
    
    private Stage utente;
    
    private Stage libro;
    
    private Stage prestito;

    
    private void initialize() {
        
    }

    private void onUtentiClicked() {
        
    }

    private void onLibriClicked() {
        
    }

    private void onPrestitiClicked() {
        
    }

    private void onEsci() {
        
    }
    
    private void associaStage(Stage main, Stage utente, Stage libro, Stage prestito){
        this.main = main;
        this.utente = utente;
        this.libro = libro;
        this.prestito = prestito;
    }
}
