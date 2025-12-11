/**
 * @file TabellaUtenteController.java
 * @brief Questo file contiene il controller della tabella che gestisce gli utenti della libreria
 *
 * Il controller permette di aggiungere, modificare, emilinare degli utenti dalla tabella dei utenti
 *
 * @author Michele Pio Russo
 * @date 8 Dicembre 2025
 * @version 1.0
 */



package Utente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import java.time.LocalDate;


public class TabellaUtenteController {

@FXML
    private Button aggiunta; ///@brief Bottone che permette di sbloccare la sezione dell'interfaccia per inserire nuovi utenti nella tabella degli utenti
    
    @FXML
    private Button rimozione; ///@brief Bottone che permette di rimuovere un utente dalla tabella degli utenti

    @FXML
    private Button cerca; ///@brief Bottone che permette di cercare un utente nella tabella degli utenti

    @FXML
    private Button esci; ///@brief Bottone che permette di uscire dall'interfaccia degli utenti

    @FXML
    private Button modifica; ///@brief Bottone che permette di modificare le informazioni di un utente della tabella degli utenti

    @FXML
    private Button aggiuntaUt; ///@brief Bottone che permette di aggiungere un utente alla tabella degli utenti

    @FXML
    private TextField cercaField; ///@brief TextField che permette di cercare un utente

    @FXML
    private TextField nome; ///@brief TextField in cui è necessario inserire il nome dell'utente da aggiungere alla tabella degli utenti

    @FXML
    private TextField cognome; ///@brief TextField in cui è necessario inserire il cognome dell'utente da aggiungere alla tabella degli utenti

    @FXML
    private TextField matricola; ///@brief TextField in cui è necessario inserire la matricola dell'utente da aggiungere alla tabella degli utenti

    @FXML
    private TextField email; ///@brief TextField in cui è necessario inserire l'email dell'utente da aggiungere alla tabella degli utenti

    @FXML
    private TextField iscrizione; ///@brief TextField in cui è necessario inserire la data di iscrizione dell'utente da aggiungere alla tabella degli utenti
 
    @FXML
    private TableView<Utente> tabella; ///@brief Tabella che contiene gli utenti della libreria universitaria

    @FXML
    private TableColumn<Utente, String> nomeCol; ///@brief Colonna che contiene i nomi degli utenti che sono stati iscrizione
    
    @FXML
    private TableColumn<Utente, String> cognomeCol; ///@brief Colonna che contiene i cognomi degli utenti che sono stati iscrizione

    @FXML
    private TableColumn<Utente, String> matricolaCol; ///@brief Colonna che contiene le matricole degli utenti che sono stati iscrizione

    @FXML
    private TableColumn<Utente, String> emailCol; ///@brief Colonna che contiene le email degli utenti che sono stati iscrizione

    @FXML
    private TableColumn<Utente, LocalDate> iscrizioneCol; ///@brief Colonna che contiene le date di iscrizione degli utenti che sono stati iscrizione

    @FXML
    private TableColumn<Utente, Integer> libriInPrestitoCol; ///@brief Colonna che contiene il numero dei libri in prestito associato ad ogni utente che è stato iscritto

    private TabellaUtenteModel tabellaUtenteModel; ///@brief Model associato al controller
    
    /**
    * @brief Metodo di inizializzazione chiamato automaticamente dal JavaFX Loader
    *
    * Configura le 'cell value factories' per collegare le colonne agli attributi
    * della classe Persona
    * 
    * @pre Gli elementi da inizializzare devono corrispondere a quelli presenti in TabellaUtenteView
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
    * 
    * @pre E' necessario che venga dato in ingresso un model di tipo TabellaUtenteModel
    * @post Il model viene associato con successo
    *
    * @param model L'istanza di TabellaUtenteModel da utilizzare.
    * 
    * @return void
    */
    public void setModel(TabellaUtenteModel model) {
        this.tabellaUtenteModel = model;
    }

    /**
    * @brief Gestisce l'azione del pulsante 'aggiunta'
    *
    * Abilita la sezione di campi/pulsanti che permette l'inserimento di un nuovo utente
    * 
    * @return void
    */
    private void onAggiungi() {
        
    }

    /**
    * @brief Gestisce l'azione del pulsante 'rimozione'
    *
    * Rimuove una persona selezionata dalla tabella e dall'ObservableList associata
    * 
    * @pre E' necessario selezionare un utente da elimiare dalla tabella
    * @post L'utente viene rimosso con successo dall'ObservableList associata
    *
    * 
    * @return void
    */
    private void onRimuovi() {
        
    }

    /**
    * @brief Gestisce l'azione del pulsante 'cerca'
    *
    * Cerca l'utente interessato nell'ObservableList
    * 
    * @post L'utente viene trovato con successo nell'ObservableList associata e mostrato nella tabella
    *
    * 
    * @return void
    */  
    private void onCerca() {
        
    }

    /**
    * @brief Gestisce l'azione del pulsante 'modifica'
    *
    * Permette di abilitare la tabella in modalità modifica
    * 
    * @post L'utente viene modificato con successo nell'ObservableList ed i cambiamenti sono visibili nella tabella
    *
    * 
    * @return void
    */     
    private void onModifica() {
        
    }

    /**
    * @brief Gestisce l'azione del pulsante 'esci'
    *
    * Permette di uscire dall'interfaccia utente (Interfaccia_Utente) e riornare nella schermata principale (Interfaccia_Main)
    * 
    * @pre E' necessario essere sull'interfaccia utente
    * @post Si ritorna alla schermata principale
    *
    * 
    * @return void
    */ 
    private void onEsci() {
        
    }
    
    /**
    * @brief Gestisce l'azione del pulsante 'aggiuntaUt'
    *
    * Acquisisce nome, cognome, matricola, email e la data di iscrizione dalle TextField associate, convalida l'input
    * e aggiunge una nuova Persona al Model
    * 
    * @post L'utente viene salvato con successo nell'ObservableList associata
    *
    * 
    * @return void
    */
    private void onAggiungiUt() {
        
    }

    /**
    * @brief Mostra una finestra di dialogo di errore (Alert.AlertType.ERROR).
    *
    * Permette di visualizzare a schermo un messaggio di errore in caso errata compilazione di campi all'interno dell'interfaccia
    * 
    * @return void
    */
    private void mostraErrore() {
        
    }
    
}
