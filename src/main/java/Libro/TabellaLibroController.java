/**
 * @file TabellaLibroController.java
 * @brief Questo file contiene il controller della tabella che gestisce i libri della libreria
 *
 * Il controller permette di aggiungere, modificare, emilinare dei libri dalla tabella dei libri
 *
 * @author Gruppo 27
 * @date 8 Dicembre 2025
 * @version 1.0
 */


package Libro;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;


public class TabellaLibroController {
    
    @FXML
    private Button aggiunta; ///@brief Bottone che permette di sbloccare la sezione dell'interfaccia per inserire nuovi libri nella tabella dei libri
    
    @FXML
    private Button rimozione; ///@brief Bottone che permette di rimuovere un libro dalla tabella dei libri

    @FXML
    private Button cerca; ///@brief Bottone che permette di cercare un libro nella tabella dei libri

    @FXML
    private Button esci; ///@brief Bottone che permette di uscire dall'interfaccia dei libri

    @FXML
    private Button modifica; ///@brief Bottone che permette di modificare le informazioni di un libro della tabella dei libri

    @FXML
    private Button aggiuntaLib; ///@brief Bottone che permette di aggiungere un libro alla tabella dei libri
    
    @FXML
    private Button X; ///@brief Bottone che ci permette di cancellare il campo di ricerca
    
    @FXML
    private Button searchType; ///@brief Bottone che ci permette di cambiare il criterio di ricerca (titolo, autore e codice isbn)

    @FXML
    private TextField cercaField; ///@brief @brief TextField che permette di cercare un libro

    @FXML
    private TextField titolo; ///@brief TextField in cui è necessario inserire il titolo del libro da aggiungere alla tabella dei libri

    @FXML
    private TextField autore; ///@brief TextField in cui è necessario inserire l'autore del libro da aggiungere alla tabella dei libri

    @FXML
    private TextField isbn; ///@brief TextField in cui è necessario inserire l'ISBN del libro da aggiungere alla tabella dei libri

    @FXML
    private TextField annoPubblicazione; ///@brief TextField in cui è necessario inserire l'anno di pubblicazione del libro da aggiungere alla tabella dei libri

    @FXML
    private TextField copie; ///@brief TextField in cui è necessario inserire il numero di copie del libro da aggiungere alla tabella dei libri

    @FXML
    private TextField prezzo; ///@brief TextField in cui è necessario inserire il prezzo del libro da aggiungere alla tabella dei libri
    
    @FXML
    private TextField usura; ///@brief TextField in cui è necessario inserire lo stato di usura del libro da aggiungere alla tabella dei libri

    @FXML
    private TableView<Libro> tabella; ///@brief Tabella che contiene i libri della libreria universitaria

    @FXML
    private TableColumn<Libro, String> titoloCol; ///@brief Colonna che contiene i titoli dei libri che sono stati inseriti
    
    @FXML
    private TableColumn<Libro, String> autoreCol; ///@brief Colonna che contiene gli autori dei libri che sono stati inseriti

    @FXML
    private TableColumn<Libro, String> isbnCol; ///@brief Colonna che contiene gli ISBN dei libri che sono stati inseriti

    @FXML
    private TableColumn<Libro, Integer> annoPubblicazioneCol; ///@brief Colonna che contiene gli anni di pubblicazione dei libri che sono stati inseriti

    @FXML
    private TableColumn<Libro, Integer> copieCol; ///@brief Colonna che contiene il numero delle copie dei libri che sono stati inseriti

    @FXML
    private TableColumn<Libro, Double> prezzoCol; ///@brief Colonna che contiene il prezzo dei libri che sono stati inseriti

    @FXML
    private TableColumn<Libro, String> usuraCol; ///@brief Colonna che contiene la condizione di usura dei libri che sono stati inseriti
    
    private TabellaLibroModel tabellaLibroModel; ///@brief Model associato al controller
    
    private Stage principale;///@brief Stage unico dell'applicazione
    
    private Scene scenaPrincipale;///@brief Scena iniziale dell'applicazione
    
   
    /**
     * @brief Metodo di inizializzazione chiamato automaticamente dal JavaFX Loader
     *
     * Configura le 'cell value factories' per collegare le colonne agli attributi
     * della classe Libro
     * @pre Gli elementi da inizializzare devono corrispondere a quelli presenti in TabellaLibroView
     * @post Gli elementi sono stati inizializzati con successo
     *
     * @return void
     */
    
    @FXML
    private void initialize() {
        
        // Collego le colonne ai getter di Libro
        titoloCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("titolo"));
        autoreCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("autore"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("isbn"));
        copieCol.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("copie"));
        annoPubblicazioneCol.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("annoPubblicazione"));
        prezzoCol.setCellValueFactory(new PropertyValueFactory<Libro, Double>("prezzo"));
        usuraCol.setCellValueFactory(new PropertyValueFactory<Libro, String>("usura"));

        // Rendo la tabella non editabile
        tabella.setEditable(false);
        
        rimozione.setDisable(true);
        
        aggiuntaLib.setDisable(true);
        titolo.setDisable(true);
        autore.setDisable(true);
        isbn.setDisable(true);
        prezzo.setDisable(true);
        usura.setDisable(true);
        annoPubblicazione.setDisable(true);
        copie.setDisable(true);
        
        //setting editabile delle celle
        // Colonna Titolo: celle editabili
        titoloCol.setCellFactory(TextFieldTableCell.<Libro>forTableColumn());
        titoloCol.setOnEditCommit(event -> {
            Libro l = event.getRowValue();
            String nuovoTitolo = event.getNewValue();
            if (nuovoTitolo != null && !nuovoTitolo.trim().isEmpty()) {
                l.setTitolo(nuovoTitolo.trim());
                    tabellaLibroModel.salvaSuBinario();
            } else {
                mostraErrore("Titiolo non valido", "Il titolo non può essere vuoto.");
                tabella.refresh();
            }
        });
        
        autoreCol.setCellFactory(TextFieldTableCell.<Libro>forTableColumn());
        autoreCol.setOnEditCommit(event -> {
            Libro l = event.getRowValue();
            String nuovoAutore = event.getNewValue();
            if (nuovoAutore != null && !nuovoAutore.trim().isEmpty()) {
                l.setAutore(nuovoAutore.trim());
                    tabellaLibroModel.salvaSuBinario();
            } else {
                mostraErrore("Autore non valido", "L'autore non può essere vuoto.");
                tabella.refresh();
            }
        });
        
        isbnCol.setCellFactory(TextFieldTableCell.<Libro>forTableColumn());
        isbnCol.setOnEditCommit(event -> {
            Libro l = event.getRowValue();
            String nuovoIsbn = event.getNewValue();
            if (nuovoIsbn != null && !nuovoIsbn.trim().isEmpty()) {
                l.setIsbn(nuovoIsbn.trim());
                    tabellaLibroModel.salvaSuBinario();
            } else {
                mostraErrore("Isbn non valido", "L'isbn non può essere vuoto.");
                tabella.refresh();
            }
        });
        
        copieCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        copieCol.setOnEditCommit(event -> {
            Libro l = event.getRowValue();
            Integer nuovoCopie = event.getNewValue();
            if (nuovoCopie != null && nuovoCopie < 0) {
                l.setCopie(nuovoCopie);
                    tabellaLibroModel.salvaSuBinario();
            } else {
                mostraErrore("Numero di copie non valido", "Il numero di copie non può essere vuoto.");
                tabella.refresh();
            }
        });
        
        annoPubblicazioneCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        annoPubblicazioneCol.setOnEditCommit(event -> {
            Libro l = event.getRowValue();
            Integer nuovoAnnoPubblicazione = event.getNewValue();
            if (nuovoAnnoPubblicazione != null && nuovoAnnoPubblicazione < 0) {
                l.setAnnoPubblicazione(nuovoAnnoPubblicazione);
                    tabellaLibroModel.salvaSuBinario();
            } else {
                mostraErrore("Anno di pubblicazione non valido", "L'anno di pubblicazione non può essere vuoto.");
                tabella.refresh();
            }
        });
        
        prezzoCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        prezzoCol.setOnEditCommit(event -> {
            Libro l = event.getRowValue();
            Double nuovoPrezzo = event.getNewValue();
            if (nuovoPrezzo != null && nuovoPrezzo > 0) {
                l.setPrezzo(nuovoPrezzo);
                    tabellaLibroModel.salvaSuBinario();
            } else {
                mostraErrore("Prezzo non valido", "Il prezzo non può essere vuoto.");
                tabella.refresh();
            }
        });
        
        usuraCol.setCellFactory(TextFieldTableCell.<Libro>forTableColumn());
        usuraCol.setOnEditCommit(event -> {
            Libro l = event.getRowValue();
            String nuovaUsura = event.getNewValue();
            if (nuovaUsura != null && !nuovaUsura.trim().isEmpty()) {
                l.setUsura(nuovaUsura.trim());
                    tabellaLibroModel.salvaSuBinario();
            } else {
                mostraErrore("Usura non valido", "L'usura non può essere vuota.");
                tabella.refresh();
            }
        });
    }

    /**
     * @brief Imposta e collega il Model al Controller e alla TableView
     *
     * Questo metodo è tipicamente chiamato dall'Application/Main
     * * @pre E' necessario che venga dato in ingresso un model di tipo TabellaLibriModel
     * @post Il model viene associato con successo
     *
     * @param[in] model L'istanza di TabellaLibriModel da utilizzare.
     * @param[in] principale L'istanza dello stage univoco dell'applicazione
     * @param[in] scenaPrincipale L'istanza della scena della schermata principale
     * 
     * * @return void
     */
    public void setModel(TabellaLibroModel model, Stage principale, Scene scenaPrincipale) {
        this.tabellaLibroModel = model;
        this.principale = principale;
        this.scenaPrincipale = scenaPrincipale;
        tabella.setItems(model.getLibri());
    }

    /**
     * @brief Gestisce l'azione del pulsante 'aggiunta'
     *
     * Abilita la sezione di campi/pulsanti che permette l'inserimento di un nuovo libro
     * * @return void
     */
    
    @FXML
    private void onAggiungi() {
        aggiuntaLib.setDisable(false);
        
        titolo.setDisable(false);
        autore.setDisable(false);
        isbn.setDisable(false);
        prezzo.setDisable(false);
        usura.setDisable(false);
        annoPubblicazione.setDisable(false);
        copie.setDisable(false);
    }

    /**
     * @brief Gestisce l'azione del pulsante 'rimozione'
     *
     * Rimuove un libro selezionato dalla tabella e dall'ObservableList associata
     * * @pre E' necessario selezionare un libro da elimiare dalla tabella
     * @post Il libro viene rimosso con successo dall'ObservableList associata
     *
     * * @return void
     */
    
    @FXML   
        private void onRimuovi() {
        // controllo se il model esiste 
        if(tabellaLibroModel == null){
            return;
        }
        
        // otteniamo il libro selezionato da interfaccia libro
        Libro libroSelezionato = tabella.getSelectionModel().getSelectedItem();
        
        // nel caso non sia stato selezionato nessun libro, diamo un errore
        if(libroSelezionato == null) {
            mostraErrore("Rimozione fallita!", "Libro da rimuovere non trovato");
            return;
        }
        
        // Parte di conferma: 
        Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
        conferma.setTitle("Conferma rimozione");
        conferma.setHeaderText("Rimuovere il libro selezionato?");
        conferma.setContentText("Libro: " + libroSelezionato.toString());

        Optional<ButtonType> risultato = conferma.showAndWait();

        // controllo finale e rimozione
        if (risultato.isPresent() && risultato.get() == ButtonType.OK) {
            tabellaLibroModel.rimuoviLibro(libroSelezionato);
                tabellaLibroModel.salvaSuBinario();

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
        if(searchType.getText().compareTo("T") == 0){
            searchType.setText("A");
        }
        else if(searchType.getText().compareTo("A") == 0){
            searchType.setText("I"); 
        }
        else if(searchType.getText().compareTo("I") == 0){
            searchType.setText("T"); 
        }
    }
        
     /**
     * @brief Gestisce l'azione del pulsante 'cerca'
     *
     * Cerca il libro interessato nell'ObservableList
     * * @post Il libro viene trovato con successo nell'ObservableList associata e mostrato nella tabella
     *
     * * @return void
     */ 
      @FXML  
    private void onCerca() {
                ObservableList<Libro> ricercaLibri = FXCollections.observableArrayList();
                String contenuto = cercaField.getText().trim();
                if(contenuto.isEmpty())
                {
                    mostraErrore("Attenzione!", "Inserire dei parametri di ricerca");
                    return;
                }
                if(searchType.getText().compareTo("T") == 0){
                        for(Libro l : tabellaLibroModel.getLibri()){
                                if(l.getTitolo().compareTo(contenuto)== 0){
                                    ricercaLibri.add(l);
                                }
                        }
                }
                if(searchType.getText().compareTo("A") == 0){
                        for(Libro l : tabellaLibroModel.getLibri()){
                                if(l.getTitolo().compareTo(contenuto)== 0){
                                    ricercaLibri.add(l);
                                }
                        }
                }
                if(searchType.getText().compareTo("I") == 0){
                        for(Libro l : tabellaLibroModel.getLibri()){
                                if(l.getTitolo().compareTo(contenuto)== 0){
                                    ricercaLibri.add(l);
                                }
                        }
                }
                if (!ricercaLibri.isEmpty()) {
                tabella.setItems(ricercaLibri);
                }
                else {
                mostraErrore("Attenzione!", "Nessun libro trovato");
                }
    }

    /**
     * @brief Gestisce l'azione del pulsante 'modifica'
     *
     * Permette di abilitare la tabella in modalità modifica
     * * @post Il libro viene modificato con successo nell'ObservableList ed i cambiamenti sono visibili nella tabella
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
     * Permette di uscire dall'interfaccia libri (Interfaccia_Libri) e riornare nella schermata principale (Interfaccia_Main)
     * * @pre E' necessario essere sull'interfaccia libri
     * @post Si ritorna alla schermata principale
     *
     * * @return void
     */ 
    @FXML
    private void onEsci() throws Exception{
        if(modifica.getText().trim().equals("Termina modifica")){
        modifica.setText("Modifica");
        tabella.setEditable(false);
        rimozione.setDisable(true);
        }
        principale.setScene(scenaPrincipale);
    }
    
    /**
     * @brief Gestisce l'azione del pulsante 'aggiuntaLib'
     *
     * Acquisisce titolo, autore, ISBN, anno, copie e prezzo dalle TextField associate, convalida l'input
     * e aggiunge un nuovo Libro al Model
     * * @post Il libro viene salvato con successo nell'ObservableList associata
     *
     * * @return void
     */
    @FXML
    private void onAggiungiLib() {
        // controllo se il model esiste    
        if (tabellaLibroModel == null) {
                return;
            }
            
            // Prendiamo tutti gli attributi di libro sottoforma di stringa
            String strTitolo = titolo.getText().trim();
            String strAutore = autore.getText().trim();
            String strIsbn = isbn.getText().trim();
            String strAnnoPubblicazione = annoPubblicazione.getText().trim();
            String strPrezzo = prezzo.getText().trim();
            String strUsura = usura.getText().trim();
            String strCopie = copie.getText().trim();
            
            // controllo se uno o più parametri sono vuoti, nel caso mando un messaggio di errore
            if(strTitolo.isEmpty() || strAutore.isEmpty() || strIsbn.isEmpty() || strAnnoPubblicazione.isEmpty() || strPrezzo.isEmpty() || strCopie.isEmpty()){
                mostraErrore("Dati mancanti!", "Inserire ogni attributo");
                return;
            }
            
            // blocco try-catch per gestire un formato numeri invalido
            try {
                // portiamo sottoforma numerica i valori
                int intNumCopie = Integer.parseInt(strCopie);
                int intAnno = Integer.parseInt(strAnnoPubblicazione);
                double doublePrezzo = Double.parseDouble(strPrezzo);
                
                // controlli sul numero di copie e sul prezzo con eventuali messaggi di errore
                if(intNumCopie < 0){
                    mostraErrore("Attenzione!", "Aggiungere almeno una copia");
                }
                if(doublePrezzo <= 0){
                    mostraErrore("Attenzione!", "Il prezzo non può essere negativo o nullo");
                }
                
                // aggiunta e salvataggio sul file di testo del libro
                tabellaLibroModel.aggiungiLibro(strTitolo, strAutore, strIsbn, Integer.parseInt(strAnnoPubblicazione), Float.parseFloat(strPrezzo), strUsura, Integer.parseInt(strCopie));
                tabellaLibroModel.salvaSuBinario();

                // puliamo i campi di libro
                titolo.clear();
                autore.clear();
                isbn.clear();
                annoPubblicazione.clear();
                prezzo.clear();
                usura.clear();
                copie.clear();
                
            } catch (NumberFormatException ex) { // nel caso in cui sia stato inserito un valore invalido dove andavano inseriti valori numerici
                            mostraErrore("Attenzione!" , "Inserire correttamente i dati");
            }
            
        //Disabilito le textfield e il bottone di aggiunta
        aggiuntaLib.setDisable(true);
        titolo.setDisable(true);
        autore.setDisable(true);
        isbn.setDisable(true);
        prezzo.setDisable(true);
        usura.setDisable(true);
        annoPubblicazione.setDisable(true);
        copie.setDisable(true);
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
        tabella.setItems(tabellaLibroModel.getLibri());
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
}
