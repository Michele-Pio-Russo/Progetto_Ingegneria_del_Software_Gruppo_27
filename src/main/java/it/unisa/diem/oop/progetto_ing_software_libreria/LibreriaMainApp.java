package it.unisa.diem.oop.progetto_ing_software_libreria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Libro.*;
import Utente.*;
import Prestito.*;
import java.net.URL;

public class LibreriaMainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Creo i model da associare ai controller
        TabellaUtenteModel tabUtenteMod= new TabellaUtenteModel();
        TabellaLibroModel tabLibroMod= new TabellaLibroModel();
        TabellaPrestitoModel tabPrestitoMod= new TabellaPrestitoModel();
        // Carico l'interfaccia principale (Menu)
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("Interfaccia_Main.fxml"));
        URL mainLoadertry = getClass().getResource("Interfaccia_Main.fxml");
       // System.out.println("\n\n\n\n\n\nprova= " + mainLoadertry);
        Parent rootMain = mainLoader.load();
        // Recupero il controller del menu per passargli le altre schermate
        MainController controller = mainLoader.getController();
        // Uso lo slash '/' iniziale perché sono in package diversi alla radice, come ad esempio ("package Libro;"
        // --- LIBRO ---
        FXMLLoader libroLoader = new FXMLLoader(getClass().getResource("/Libro/Interfaccia_Libro.fxml"));
        Parent rootLibro = libroLoader.load();
        Scene sceneLibro = new Scene(rootLibro);
        // --- UTENTE ---
        FXMLLoader utenteLoader = new FXMLLoader(getClass().getResource("/Utente/Interfaccia_Utente.fxml"));
        Parent rootUtente = utenteLoader.load();
        Scene sceneUtente = new Scene(rootUtente);
        // --- PRESTITO ---
        FXMLLoader prestitoLoader = new FXMLLoader(getClass().getResource("/Prestito/Interfaccia_Prestiti.fxml"));
        Parent rootPrestito = prestitoLoader.load();
        Scene scenePrestito = new Scene(rootPrestito);
        
        //Carico gli altri controller
        TabellaUtenteController utCotroller = utenteLoader.getController();
        TabellaLibroController libCotroller = libroLoader.getController();
        TabellaPrestitoController prestCotroller = prestitoLoader.getController();
        
        //scena principale
        Scene scenePrincipale = new Scene(rootMain);
        // collego tutto insieme usando associaStage
        controller.associaStage(stage, sceneUtente, sceneLibro, scenePrestito);
        
        //collego i controller ai propri model
        utCotroller.setModel(tabUtenteMod, stage, scenePrincipale);
        libCotroller.setModel(tabLibroMod, stage, scenePrincipale);
        prestCotroller.setModel(tabPrestitoMod, tabUtenteMod, tabLibroMod, stage, scenePrincipale);
                
        stage.setTitle("Gestione Libreria Universitaria");
        stage.setScene(scenePrincipale);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}