/**
 * @file MainController.java
 * @brief Questo file contiene il controller del main
 *
 * Questa classe permette di gestire il main
 *
 * @author Gruppo 27
 * @date 8 dicembre 2025
 * @version 1.0
 */
package it.unisa.diem.oop.progetto_ing_software_libreria;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * @file MainController.java
 * @brief Controller principale per la gestione del menu iniziale della libreria.
 * Questa classe gestisce la navigazione tra le varie sezioni dell'applicazione
 * (Utenti, Libri, Prestiti) cambiando la scena visualizzata sullo stage principale.
 */
public class MainController implements Initializable {

    @FXML
    private Button utenti;   ///@brief Questo bottone serve per accedere all'interfaccia Utente dalla schermata iniziale
    
    @FXML
    private Button libri;    ///@brief Questo bottone serve per accedere all'interfaccia Libri dalla schermata iniziale
    
    @FXML
    private Button prestiti; ///@brief Questo bottone serve per accedere all'interfaccia Prestito dalla schermata iniziale
    
    @FXML
    private Button esci;     ///@brief Questo bottone serve per chiudere l'applicazione

    
    private Stage mainStage;     ///@brief Riferimento allo stage (finestra) principale dell'applicazione
    private Scene sceneUtente;   ///@brief Riferimento alla scena che contiene l'interfaccia di gestione Utenti
    private Scene sceneLibro;    ///@brief Riferimento alla scena che contiene l'interfaccia di gestione Libri
    private Scene scenePrestito; ///@brief Riferimento alla scena che contiene l'interfaccia di gestione Prestiti

    /**
     * @brief Metodo di inizializzazione chiamato automaticamente da JavaFX.
     * 
     * @param[in] url L'URL utilizzato per risolvere i percorsi relativi all'oggetto radice, o null se non noto.
     * @param[in] rb Il ResourceBundle utilizzato per localizzare l'oggetto radice, o null se non localizzato.
     * 
     * @return void
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    /**
     * @brief Collega lo stage principale e le scene delle sottosezioni al controller.
     * Questo metodo permette al MainController di avere i riferimenti necessari
     * per effettuare il cambio di scena (navigazione) quando vengono premuti i bottoni.
     * 
     * @param[in] main Lo stage principale dell'applicazione.
     * @param[in] utente La scena pre-caricata per la gestione utenti.
     * @param[in] libro La scena pre-caricata per la gestione libri.
     * @param[in] prestito La scena pre-caricata per la gestione prestiti
     * 
     * @return void
     */
    public void associaStage(Stage main, Scene utente, Scene libro, Scene prestito) {
        this.mainStage = main;
        this.sceneUtente = utente;
        this.sceneLibro = libro;
        this.scenePrestito = prestito;
    }

    /**
     * @brief Gestisce il click sul bottone 'Gestione Utenti'.
     * Cambia la scena attuale dello stage principale visualizzando l'interfaccia Utenti.
     * 
     * 
     * @return void
     */
    @FXML
    private void onUtentiClicked() {
        if (sceneUtente != null) {
            mainStage.setScene(sceneUtente);
        }
    }

    /**
     * @brief Gestisce il click sul bottone 'Gestione Libri'.
     * Cambia la scena attuale dello stage principale visualizzando l'interfaccia Libri.
     * 
     * @return void
     */
    @FXML
    private void onLibriClicked() {
        if (sceneLibro != null) {
            mainStage.setScene(sceneLibro);
        }
    }

    /**
     * @brief Gestisce il click sul bottone 'Gestione Prestiti'.
     * Cambia la scena attuale dello stage principale visualizzando l'interfaccia Prestiti.
     * 
     * @return void
     */
    @FXML
    private void onPrestitiClicked() {
        if (scenePrestito != null) {
            mainStage.setScene(scenePrestito);
        }
    }

    /**
     * @brief Gestisce il click sul bottone 'Esci'.
     * Esegue le operazioni di chiusura, come il salvataggio dei dati (se implementato)
     * e termina l'esecuzione dell'applicazione.
     * 
     * @return void
     */
    @FXML
    private void onEsci() {
        // DataManager.salvaTutto(); 
        System.out.println("Salvataggio dati e chiusura...");
        Platform.exit();
        System.exit(0);
    }
}