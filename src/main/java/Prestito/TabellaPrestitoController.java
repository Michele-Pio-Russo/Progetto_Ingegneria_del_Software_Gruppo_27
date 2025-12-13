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

import Libro.Libro;
import Libro.TabellaLibroModel;
import Utente.TabellaUtenteModel;
import Utente.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

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
    private Button aggiuntaPre; ///@brief Bottone che permette di aggiungere un prestito alla tabella dei prestiti

    @FXML
    private TextField cercaField; ///@brief TextField che permette di cercare un prestito

    @FXML
    private TextField titolo; ///@brief TextField in cui è necessario inserire il titolo del libro associato al prestito

    @FXML
    private Button X; ///@brief Bottone che permette di cancellare il contenuto della TextField "cercaField" e reimposta il contenuto della tabella
    
    @FXML
    private Button searchType; ///@brief Bottone che permette di cambiare il parametro di ricerca all'interno della tabella
    
    @FXML
    private TextField scadenza; ///@brief TextField in cui è necessario inserire la data di scadenza del libro prestito

    @FXML
    private TextField isbn; ///@brief TextField in cui è necessario inserire l'ISBN del libro associato al prestito

    @FXML
    private TextField nome; ///@brief TextField in cui è necessario inserire il nome dell'utente associato al prestito

    @FXML
    private TextField cognome; ///@brief TextField in cui è necessario inserire il cognome dell'utente associato al prestito
    
    @FXML
    private TableView<Prestito> tabella; ///@brief Tabella che contiene i prestiti della libreria universitaria

    @FXML
    private TableColumn<Prestito, String> titoloCol; ///@brief Colonna che contiene i titoli dei libri associati ai prestiti
    
    @FXML
    private TableColumn<Prestito, LocalDate> scadenzaCol; ///@brief Colonna che contiene gli autori dei libri associati ai prestiti

    @FXML
    private TableColumn<Prestito, String> isbnCol; ///@brief Colonna che contiene gli ISBN dei libri associati ai prestiti

    @FXML
    private TableColumn<Prestito, String> nomeCol; ///@brief Colonna che contiene il numero di copie dei libri associati ai prestiti

    @FXML
    private TableColumn<Prestito, String> cognomeCol; ///@brief Colonna che contiene il prezzo dei libri associati ai prestiti

    private TabellaPrestitoModel tabellaPrestitoModel; ///@brief Model associato al controller, gestisce la logica dei dati dei prestiti
    private Scene scenaPrincipale;
    private Stage principale;
    private TabellaLibroModel tabellaLibroModel;
    private TabellaUtenteModel tabellaUtenteModel;
    
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
            
    @FXML
    private void initialize() {
        try{
            tabellaPrestitoModel.caricaDaBinario();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        tabella.setEditable(false);
        rimozione.setDisable(true);
        aggiuntaPre.setDisable(true);
        titolo.setDisable(true);
        scadenza.setDisable(true);
        isbn.setDisable(true);
        nome.setDisable(true);
        cognome.setDisable(true);
        nomeCol.setCellValueFactory(new PropertyValueFactory<Prestito, String>("nome"));
        cognomeCol.setCellValueFactory(new PropertyValueFactory<Prestito, String>("cognome"));
        titoloCol.setCellValueFactory(new PropertyValueFactory<Prestito, String>("titolo"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<Prestito, String>("isbn"));
        scadenzaCol.setCellValueFactory(new PropertyValueFactory<Prestito, LocalDate>("dataDiScadenza"));
        nomeCol.setCellFactory(TextFieldTableCell.<Prestito>forTableColumn());
        nomeCol.setOnEditCommit(event -> {
            Prestito P = event.getRowValue();
            String nuovoNome = event.getNewValue();
            if (nuovoNome != null && !nuovoNome.trim().isEmpty()) {
                P.setNome(nuovoNome.trim());
                            tabellaPrestitoModel.salvaSuBinario();
            } else {
                mostraErrore("Nome non valido", "Il Nome non può essere vuoto.");
                tabella.refresh();
            }
        });
        
        cognomeCol.setCellFactory(TextFieldTableCell.<Prestito>forTableColumn());
        cognomeCol.setOnEditCommit(event -> {
            Prestito P = event.getRowValue();
            String nuovoCognome = event.getNewValue();
            if (nuovoCognome != null && !nuovoCognome.trim().isEmpty()) {
                P.setCognome(nuovoCognome.trim());
                tabellaPrestitoModel.salvaSuBinario();
            } else {
                mostraErrore("Cognome non valido", "Il Cognome non può essere vuoto.");
                tabella.refresh();
            }
        });
        
        titoloCol.setCellFactory(TextFieldTableCell.<Prestito>forTableColumn());
        titoloCol.setOnEditCommit(event -> {
            Prestito P = event.getRowValue();
            String nuovoTitolo = event.getNewValue();
            if (nuovoTitolo != null && !nuovoTitolo.trim().isEmpty()) {
                P.setTitolo(nuovoTitolo.trim());
                    tabellaPrestitoModel.salvaSuBinario();
            } else {
                mostraErrore("Titolo non valido", "Il titolo non può essere vuoto.");
                tabella.refresh();
            }
        });
        
        
        isbnCol.setCellFactory(TextFieldTableCell.<Prestito>forTableColumn());
        isbnCol.setOnEditCommit(event -> {
            Prestito P = event.getRowValue();
            String nuovoIsbn = event.getNewValue();
            if (nuovoIsbn != null && !nuovoIsbn.trim().isEmpty()) {
                P.setIsbn(nuovoIsbn.trim());
                tabellaPrestitoModel.salvaSuBinario();
            } else {
                mostraErrore("Isbn non valido", "L'Isbn non può essere vuoto.");
                tabella.refresh();
            }
        });
        
        scadenzaCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter(DateTimeFormatter.ofPattern("yyyy-MM-dd"),null)));
        scadenzaCol.setOnEditCommit(event -> {
        Prestito P = event.getRowValue();
        LocalDate nuovaScadenza = event.getNewValue();
        if (nuovaScadenza != null) {
         P.setDataDiScadenza(nuovaScadenza);
        tabellaPrestitoModel.salvaSuBinario();
        } else {
        P.setDataDiScadenza(event.getOldValue());
        tabella.refresh();
        mostraErrore("Data non valida", "La data deve essere nel formato YYYY-MM-DD e non può essere vuota.");
    }
});
        
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
    
    public void setModel(TabellaPrestitoModel model, TabellaUtenteModel utModel, TabellaLibroModel libModel, Stage principale, Scene scenaPrincipale) {
        this.tabellaPrestitoModel = model;
        this.principale=principale;
        this.scenaPrincipale=scenaPrincipale;
        tabellaLibroModel = libModel;
        tabellaUtenteModel = utModel;
        tabella.setItems(model.getPrestiti());

    }

    /**
     * @brief Gestisce l'azione del pulsante 'aggiunta'
     *
     * Abilita la sezione di campi/pulsanti che permette l'inserimento di un nuovo prestito
     * * @return void
     */
    @FXML
    private void onAggiungi() {
        aggiuntaPre.setDisable(false);
        
        titolo.setDisable(false);
        scadenza.setDisable(false);
        isbn.setDisable(false);
        nome.setDisable(false);
        cognome.setDisable(false);
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
    @FXML
    private void onRimuovi() {
                // controllo se il model esiste 
        if(tabellaPrestitoModel == null){
            return;
        }
        
        // otteniamo il libro selezionato da interfaccia libro
        Prestito prestitoSelezionato = tabella.getSelectionModel().getSelectedItem();
        
        // nel caso non sia stato selezionato nessun libro, diamo un errore
        if(prestitoSelezionato == null) {
            mostraErrore("Rimozione fallita!", "Libro da rimuovere non trovato");
            return;
        }
        
        // Parte di conferma: 
        Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
        conferma.setTitle("Conferma rimozione");
        conferma.setHeaderText("Rimuovere il prestito selezionato?");
        conferma.setContentText("Prestito: " + prestitoSelezionato.toString());

        Optional<ButtonType> risultato = conferma.showAndWait();

        // controllo finale e rimozione
        if (risultato.isPresent() && risultato.get() == ButtonType.OK) {
            tabellaPrestitoModel.rimuoviPrestito(prestitoSelezionato);
            tabellaPrestitoModel.salvaSuBinario();
            
        }
    }
    
        /**
     * @brief Gestisce il pulsante 'searchType'
     *
     *  Cambia criterio di ricerca del libro
     * * @post Il libro viene cercato con successo seguendo il criterio specifico
     *
     * * @return void
     */ 
        
    @FXML
    private void onCambio(){
        System.out.println("Cambio");
        if(searchType.getText().compareTo("N") == 0){
            searchType.setText("T");
        }
        else
        {
            searchType.setText("N"); 
        }
    }




    /**
     * @brief Gestisce l'azione del pulsante 'modifica'
     *
     * Permette di abilitare la tabella in modalità modifica
     * * @post Il prestito viene modificato con successo nell'ObservableList ed i cambiamenti sono visibili nella tabella
     *
     * * @return void
     */
    
    @FXML
    private void onModifica() {
        if (modifica.getText().trim().equals("Modifica")) {
        modifica.setText("Termina modifica");
        tabella.setEditable(true);
        rimozione.setDisable(false);
        mostraErrore("Avviso!", "Ora è possibile modifcare o eliminare i libri dalla tabella");
        } else {
            modifica.setText("Modifica");
        tabella.setEditable(false);
        rimozione.setDisable(true);
        mostraErrore("Avviso!", "Ora non è possibile modifcare o eliminare i libri dalla tabella");
        }
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
    @FXML
    private void onEsci() {
        principale.setScene(scenaPrincipale);
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
    @FXML
    private void onAggiungiPre() {
            if (tabellaPrestitoModel == null) {
            return;
        }

        // Prendiamo tutti gli attributi di prestito sottoforma di stringa
        String n = nome.getText().trim();
        String  c = cognome.getText().trim();
        String tit = titolo.getText().trim();
        String  strIsbn = isbn.getText().trim();
        String  scad = scadenza.getText().trim();

        if (n.isEmpty() || c.isEmpty() || tit.isEmpty() || strIsbn.isEmpty() || scad.isEmpty() ) {
            mostraErrore("Dati mancanti", "Inserire tutti i dati richiesti.");
            return;
        }
        if(LocalDate.now().isAfter(LocalDate.parse(scad)))
            {
                mostraErrore("Errore scadenza", "Inserire una scadenza valida");
                return;
            }
        if(!tabellaUtenteModel.getPersone().contains(new Utente(n, c, "", "", LocalDate.parse("2000-10-10"))))
        {
            mostraErrore("Errore utente", "Utente non presente nell'elenco degli utenti");
            return;
        }
        if(!tabellaLibroModel.getLibri().contains(new Libro(tit,"",strIsbn,0,0,"",0)))
        {
            mostraErrore("Errore libro", "Libro non presente nell'elenco dei libri");
            return;
        }
        try {   
            tabellaPrestitoModel.aggiungiPrestito(new Utente(n, c, "", "", LocalDate.parse("2000-10-10")), new Libro(tit,"",strIsbn,0,0,"",0), LocalDate.parse(scad));
           tabellaPrestitoModel.salvaSuBinario();
            nome.clear();
            cognome.clear();
            titolo.clear();
            isbn.clear();
            scadenza.clear();
        } catch (NumberFormatException ex) {
            mostraErrore("Formato dei dati non valido", "Inserire nuovamente i dati e riprovare.");
        }
        aggiuntaPre.setDisable(true);
        titolo.setDisable(true);
        scadenza.setDisable(true);
        isbn.setDisable(true);
        nome.setDisable(true);
        cognome.setDisable(true);
    }

    /**
     * @brief Mostra una finestra di dialogo di errore (Alert.AlertType.ERROR).
     *
     * Permette di visualizzare a schermo un messaggio di errore in caso errata compilazione di campi all'interno dell'interfaccia
     * 
     * @param[in] titolo Titolo mostrato nella finestra di alert
     * @param[in] messaggio Messaggio di errore
     * * @return void
     */
    private void mostraErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
    
    /**
     * @brief Gestisce l'azione del pulsante 'cerca'
     *
     * Cerca il prestito interessato nell'ObservableList
     * * @post Il prestito viene trovato con successo nell'ObservableList associata e mostrato nella tabella
     *
     * * @return void
     */
@FXML
    private void onCerca() {
        ObservableList<Prestito> ricercaPrestiti = FXCollections.observableArrayList();
        String contenuto = cercaField.getText().trim();
        if(contenuto.isEmpty())
                {
                    mostraErrore("Attenzione!", "Inserire dei parametri di ricerca");
                    return;
                }
        if(searchType.getText().equals("N"))
        {
            for(Prestito P : tabellaPrestitoModel.getPrestiti())
            {
                if(P.getNome().equals(contenuto))
                {
                    ricercaPrestiti.add(P);
                }
            }
        }
        if(searchType.getText().equals("T"))
        {
            for(Prestito P : tabellaPrestitoModel.getPrestiti())
            {
                if(P.getTitolo().equals(contenuto))
                {
                    ricercaPrestiti.add(P);
                }
            }
        }
        if(!ricercaPrestiti.isEmpty())
        {
            tabella.setItems(ricercaPrestiti);
        }
        else
        {
            mostraErrore("Attenzione!", "Nessun libro trovato");
        }
    }
    
    
    /**
     * @brief Gestisce l'azione del pulsante "X"
     *
     *  Cancella il contenuto della textField "cercaField" e reimposta il
     *  contenuto della tabella
     * * @post Il contenuto della textField è stato cancellato con successo
     *      e il contenuto della tabella è stato reimpostato
     *
     * * @return void
     */
    @FXML
    private void onCancellaCerca() {
        cercaField.clear();
        tabella.setItems(tabellaPrestitoModel.getPrestiti());
    }
}
