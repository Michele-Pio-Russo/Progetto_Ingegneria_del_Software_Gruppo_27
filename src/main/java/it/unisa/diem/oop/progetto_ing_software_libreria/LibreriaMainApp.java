/**
 * @file TabellaPrestitoModelTest.java
 * @brief principale (Entry Point) dell'applicazione Gestione Libreria.
 * Questa classe si occupa di inizializzare l'ambiente JavaFX, caricare le viste FXML, istanziare i modelli (Model) e i controller, e collegare le dipendenze tra loro secondo il pattern MVC.
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */
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

    /**
     * @brief Metodo di avvio dell'applicazione JavaFX.
     * Esegue le seguenti operazioni:
     * 1. Inizializza i Modelli per la gestione dei dati.
     * 2. Carica le interfacce grafiche (View) dai file FXML.
     * 3. Recupera i Controller associati alle viste.
     * 4. Collega i Controller ai relativi Modelli e imposta la navigazione tra le scene.
     * 
     * @param[in] stage Lo stage primario (finestra principale) dell'applicazione.
     * 
     * @throws Exception Se si verificano errori durante il caricamento dei file FXML.
     * 
     * @return void
     */
    
    @Override
    public void start(Stage stage) throws Exception {
        
        // Inizializzazione dei modelli (Model) per la gestione dei dati persistenti
        TabellaUtenteModel tabUtenteMod = new TabellaUtenteModel();
        TabellaLibroModel tabLibroMod = new TabellaLibroModel();
        TabellaPrestitoModel tabPrestitoMod = new TabellaPrestitoModel();

        //Caricamento dell'interfaccia principale (Menu)
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("Interfaccia_Main.fxml"));
        Parent rootMain = mainLoader.load();
        
        //Recupero del controller principale per impostare la navigazione
        MainController mainController = mainLoader.getController();

        
        // Caricamento vista Sezione Libri
        FXMLLoader libroLoader = new FXMLLoader(getClass().getResource("/Libro/Interfaccia_Libro.fxml"));
        Parent rootLibro = libroLoader.load();
        Scene sceneLibro = new Scene(rootLibro);

        // Caricamento vista Sezione Utenti
        FXMLLoader utenteLoader = new FXMLLoader(getClass().getResource("/Utente/Interfaccia_Utente.fxml"));
        Parent rootUtente = utenteLoader.load();
        Scene sceneUtente = new Scene(rootUtente);

        //Caricamento vista Sezione Prestiti
        FXMLLoader prestitoLoader = new FXMLLoader(getClass().getResource("/Prestito/Interfaccia_Prestiti.fxml"));
        Parent rootPrestito = prestitoLoader.load();
        Scene scenePrestito = new Scene(rootPrestito);
        
        //Recupero dei Controller specifici per ogni sezione
        TabellaUtenteController utController = utenteLoader.getController();
        TabellaLibroController libController = libroLoader.getController();
        TabellaPrestitoController prestController = prestitoLoader.getController();
        
        //Creazione della scena principale
        Scene scenePrincipale = new Scene(rootMain);

        //Configurazione della navigazione nel MainController (passaggio delle scene)
        mainController.associaStage(stage, sceneUtente, sceneLibro, scenePrestito);
        
        //Iniezione delle dipendenze: Collegamento dei Controller ai relativi Model e allo Stage per la navigazione inversa
        utController.setModel(tabUtenteMod, stage, scenePrincipale);
        libController.setModel(tabLibroMod, stage, scenePrincipale);
        
        //Il controller dei prestiti necessita anche dei modelli Utente e Libro per i controlli incrociati
        prestController.setModel(tabPrestitoMod, tabUtenteMod, tabLibroMod, stage, scenePrincipale);
                
        //Configurazione e avvio dello stage principale
        stage.setTitle("Gestione Libreria Universitaria");
        stage.setScene(scenePrincipale);
        stage.show();
    }

    /**
     * @brief Metodo main standard per l'avvio dell'applicazione.
     * 
     * @param[in] args Argomenti da riga di comando.
     * 
     * @return void
     */
    public static void main(String[] args) {
        launch(args);
    }
}