/**
 * @file TabellaPrestitoController.java
 * @brief Questo file contiene il controller della tabella che gestisce i prestiti della libreria
 *
 * Il controller permette di aggiungere, modificare, emilinare dei prestiti dalla tabella dei prestiti
 *
 * @author Michele Pio Russo
 * @date 8 Dicembre 2025
 * @version 1.0
 */


package Prestito;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class TabellaPrestitoController {
@FXML
    private Button aggiunta; ///@brief Bottone che permette di sbloccare la sezione dell'interfaccia per inserire nuovi prestiti nella tabella dei prestiti
    
    @FXML
    private Button rimozione; ///@brief Bottone che permette di rimuovere un prestito dalla tabella dei prestiti

    @FXML
    private Button cerca; ///@brief Bottone che permette di cercare un prestito nella tabella dei prestiti

    @FXML
    private Button esci; ///@brief Bottone che permette di uscire dall'interfaccia dei prestiti

    @FXML
    private Button modifica; ///@brief Bottone che permette di modificare le informazioni di un prestito della tabella dei prestiti

    @FXML
    private Button aggiuntaLib; ///@brief Bottone che permette di aggiungere un prestito alla tabella dei prestiti

    @FXML
    private TextField cercaField; ///@brief TextField che permette di cercare un prestito

    @FXML
    private TextField titolo; ///@brief TextField in cui è necessario inserire il titolo del libro associato al prestito

    @FXML
    private TextField autore; ///@brief TextField in cui è necessario inserire l'autore del libro associato al prestito

    @FXML
    private TextField ISBN; ///@brief TextField in cui è necessario inserire l'ISBN del libro associato al prestito

    @FXML
    private TextField copie; ///@brief TextField in cui è necessario inserire il numero di copie del libro associato al prestito

    @FXML
    private TextField prezzo; ///@brief TextField in cui è necessario inserire il prezzo del libro associato al prestito
    
    @FXML
    private TableView<Prestito> tabella; ///@brief Tabella che contiene i prestiti della libreria universitaria

    @FXML
    private TableColumn<Prestito, String> titoloCol; ///@brief Colonna che contiene i titoli dei libri associati ai prestiti
    
    @FXML
    private TableColumn<Prestito, String> autoreCol; ///@brief Colonna che contiene gli autori dei libri associati ai prestiti

    @FXML
    private TableColumn<Prestito, String> ISBNCol; ///@brief Colonna che contiene gli ISBN dei libri associati ai prestiti

    @FXML
    private TableColumn<Prestito, Integer> copieCol; ///@brief Colonna che contiene il numero di copie dei libri associati ai prestiti

    @FXML
    private TableColumn<Prestito, Integer> prezzoCol; ///@brief Colonna che contiene il prezzo dei libri associati ai prestiti

    private TabellaPrestitoModel tabellaPrestitoModel; ///@brief Model associato al controller, gestisce la logica dei dati dei prestiti
    
    /**
     * @brief Metodo di inizializzazione chiamato automaticamente dal JavaFX Loader
     *
     * Configura le 'cell value factories' per collegare le colonne agli attributi
     * della classe Prestito
     * * @pre Gli elementi da inizializzare devono corrispondere a quelli presenti in TabellaPrestitoView
     * @post Gli elementi sono stati inizializzati con successo
     *
     * @return void
     */
    private void initialize() {
        
    }

    /**
     * @brief Imposta e collega il Model al Controller e alla TableView
     *
     * Questo metodo è tipicamente chiamato dall'Application/Main
     * * @pre E' necessario che venga dato in ingresso un model di tipo TabellaPrestitoModel
     * @post Il model viene associato con successo
     *
     * @param model L'istanza di TabellaPrestitoModel da utilizzare.
     * * @return void
     */
    public void setModel(TabellaPrestitoModel model) {
        this.tabellaPrestitoModel = model;
    }

    /**
     * @brief Gestisce l'azione del pulsante 'aggiunta'
     *
     * Abilita la sezione di campi/pulsanti che permette l'inserimento di un nuovo prestito
     * * @return void
     */
    private void onAggiungi() {
        
    }

    /**
     * @brief Gestisce l'azione del pulsante 'rimozione'
     *
     * Rimuove un prestito selezionato dalla tabella e dall'ObservableList associata
     * * @pre E' necessario selezionare un prestito da eliminare dalla tabella
     * @post Il prestito viene rimosso con successo dall'ObservableList associata
     *
     * * @return void
     */
    private void onRimuovi() {
        
    }

    /**
     * @brief Gestisce l'azione del pulsante 'cerca'
     *
     * Cerca il prestito interessato nell'ObservableList
     * * @post Il prestito viene trovato con successo nell'ObservableList associata e mostrato nella tabella
     *
     * * @return void
     */ 
    private void onCerca() {
        
    }

    /**
     * @brief Gestisce l'azione del pulsante 'modifica'
     *
     * Permette di abilitare la tabella in modalità modifica
     * * @post Il prestito viene modificato con successo nell'ObservableList ed i cambiamenti sono visibili nella tabella
     *
     * * @return void
     */     
    private void onModifica() {
        
    }

    /**
     * @brief Gestisce l'azione del pulsante 'esci'
     *
     * Permette di uscire dall'interfaccia prestiti (Interfaccia_Prestiti) e riornare nella schermata principale (Interfaccia_Main)
     * * @pre E' necessario essere sull'interfaccia prestiti
     * @post Si ritorna alla schermata principale
     *
     * * @return void
     */ 
    private void onEsci() {
        
    }
    
    /**
     * @brief Gestisce l'azione del pulsante 'aggiuntaPrest'
     *
     * Acquisisce i dati necessari (Utente, Libro, data di scadenza) dalle TextField associate, convalida l'input
     * e aggiunge un nuovo Prestito al Model
     * * @post Il prestito viene salvato con successo nell'ObservableList associata
     *
     * * @return void
     */
    private void onAggiungiPrest() {
        
    }

    /**
     * @brief Mostra una finestra di dialogo di errore (Alert.AlertType.ERROR).
     *
     * Permette di visualizzare a schermo un messaggio di errore in caso errata compilazione di campi all'interno dell'interfaccia
     * * @return void
     */
    private void mostraErrore() {
        
    }
}
