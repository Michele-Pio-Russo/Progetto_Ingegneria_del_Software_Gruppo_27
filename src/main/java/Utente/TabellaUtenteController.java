    /**
     * @file TabellaUtenteController.java
     * @brief Questo file contiene il controller della tabella che gestisce gli utenti della libreria
     *
     * Il controller permette di aggiungere, modificare, emilinare degli utenti dalla tabella dei utenti
     *
     * @author Gruppo 27
     * @date 8 Dicembre 2025
     * @version 1.0
     */



    package Utente;

    import Libro.Libro;
    import javafx.fxml.FXML;
    import javafx.scene.control.Button;
    import javafx.scene.control.TextField;
    import javafx.scene.control.TableView;
    import javafx.scene.control.TableColumn;
    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    import java.util.Optional;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Alert;
    import javafx.scene.control.ButtonType;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.control.cell.TextFieldTableCell;
    import javafx.stage.Stage;
    import javafx.util.converter.IntegerStringConverter;
    import javafx.util.converter.LocalDateStringConverter;


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
        private Button X; ///@brief Bottone che ci permette di cancellare il campo di ricerca

        @FXML
        private Button searchType; ///@brief Bottone che ci permette di cambiare il criterio di ricerca (nome, cognome, matricola)

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

        private TabellaUtenteModel tabellaUtenteModel; ///@brief Model associato al cotroller

        private Stage principale;///@brief Stage unico dell'applicazione

        private Scene scenaPrincipale;///@brief Scena iniziale dell'applicazione
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
        @FXML
        private void initialize() {

               //collego le colonne ai getter di utente
            nomeCol.setCellValueFactory(new PropertyValueFactory<Utente, String>("nome"));
            cognomeCol.setCellValueFactory(new PropertyValueFactory<Utente, String>("cognome"));
            matricolaCol.setCellValueFactory(new PropertyValueFactory<Utente, String>("matricola"));
            emailCol.setCellValueFactory(new PropertyValueFactory<Utente, String>("email"));
            iscrizioneCol.setCellValueFactory(new PropertyValueFactory<Utente, LocalDate>("iscrizione"));
            libriInPrestitoCol.setCellValueFactory(new PropertyValueFactory<Utente, Integer>("libriInPrestito"));


        // Rendo la tabella non editabile
            tabella.setEditable(false);

            rimozione.setDisable(true);

            aggiuntaUt.setDisable(true);
            nome.setDisable(true);
            cognome.setDisable(true);
            matricola.setDisable(true);
            email.setDisable(true);

            nomeCol.setCellFactory(TextFieldTableCell.<Utente>forTableColumn());
            nomeCol.setOnEditCommit(event -> {
                Utente U = event.getRowValue();
                String nuovoNome = event.getNewValue();
                if (nuovoNome != null && !nuovoNome.trim().isEmpty()) {
                    U.setNome(nuovoNome.trim());
                    tabellaUtenteModel.salvaSuBinario();
                } else {
                    mostraErrore("Nome non valido", "Il Nome non può essere vuoto.");
                    tabella.refresh();
                }
            });

            cognomeCol.setCellFactory(TextFieldTableCell.<Utente>forTableColumn());
            cognomeCol.setOnEditCommit(event -> {
                Utente U = event.getRowValue();
                String nuovoCognome = event.getNewValue();
                if (nuovoCognome != null && !nuovoCognome.trim().isEmpty()) {
                    U.setNome(nuovoCognome.trim());
                    tabellaUtenteModel.salvaSuBinario();
                } else {
                    mostraErrore("Cognome non valido", "Il Cognome non può essere vuoto.");
                    tabella.refresh();
                }
            });

            matricolaCol.setCellFactory(TextFieldTableCell.<Utente>forTableColumn());
            matricolaCol.setOnEditCommit(event -> {
                Utente U = event.getRowValue();
                String nuovaMatricola = event.getNewValue();
                if (nuovaMatricola != null && !nuovaMatricola.trim().isEmpty()) {
                    U.setNome(nuovaMatricola.trim());
                    tabellaUtenteModel.salvaSuBinario();
                } else {
                    mostraErrore("Matricola non valida", "La matricola non può essere vuota.");
                    tabella.refresh();
                }
            });

            emailCol.setCellFactory(TextFieldTableCell.<Utente>forTableColumn());
            emailCol.setOnEditCommit(event -> {
                Utente U = event.getRowValue();
                String nuovaEmail = event.getNewValue();
                if (nuovaEmail != null && !nuovaEmail.trim().isEmpty()) {
                    U.setNome(nuovaEmail.trim());
                    tabellaUtenteModel.salvaSuBinario();
                } else {
                    mostraErrore("Email non valida", "L'email non può essere vuota.");
                    tabella.refresh();
                }
            });

        }

        /**
        * @brief Imposta e collega il Model al Controller e alla TableView
        *
        * Questo metodo è tipicamente chiamato dall'Application/Main
        * 
        * @pre E' necessario che venga dato in ingresso un model di tipo TabellaUtenteModel
        * @post Il model viene associato con successo
        *
        * @param[in] model L'istanza di TabellaUtentiModel da utilizzare.
        * @param[in] principale L'istanza dello stage univoco dell'applicazione
        * @param[in] scenaPrincipale L'istanza della scena della schermata principale
        * 
        * @return void
        */
        public void setModel(TabellaUtenteModel model, Stage principale, Scene scenaPrincipale) {
            this.tabellaUtenteModel = model;
            this.principale = principale;
            this.scenaPrincipale = scenaPrincipale;
            tabella.setItems(model.getPersone());
        }

        /**
        * @brief Gestisce l'azione del pulsante 'aggiunta'
        *
        * Abilita la sezione di campi/pulsanti che permette l'inserimento di un nuovo utente
        * 
        * @return void
        */

        @FXML
        private void onAggiungi() {
            aggiuntaUt.setDisable(false);

            nome.setDisable(false);
            cognome.setDisable(false);
            matricola.setDisable(false);
            email.setDisable(false);
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
        @FXML
        private void onRimuovi() {
            if(tabellaUtenteModel == null){
                return;
            }

            // otteniamo l'utente selezionato da interfaccia libro
            Utente utenteSelezionato = tabella.getSelectionModel().getSelectedItem();

            // nel caso non sia stato selezionato nessun utente, diamo un errore
            if(utenteSelezionato == null) {
                mostraErrore("Rimozione fallita!", "Utente da rimuovere non trovato");
                return;
            }

            // Parte di conferma: 
            Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
            conferma.setTitle("Conferma rimozione");
            conferma.setHeaderText("Rimuovere l'utente selezionato?");
            conferma.setContentText("Utente: " + utenteSelezionato.toString());

            Optional<ButtonType> risultato = conferma.showAndWait();

            // controllo finale e rimozione
            if (risultato.isPresent() && risultato.get() == ButtonType.OK) {
                tabellaUtenteModel.rimuoviPersona(utenteSelezionato);
                tabellaUtenteModel.salvaSuBinario();
            }
        }

          /**
         * @brief Gestisce il pulsante 'searchType'
         *
         *  Cambia criterio di ricerca dell'utente
         * * @post L'utente viene cercato con successo seguendo il criterio specifico
         *
         * * @return void
         */ 

        @FXML
        private void onCambio(){
            if(searchType.getText().compareTo("N") == 0){
                searchType.setText("C"); 
            }
            else if(searchType.getText().compareTo("C") == 0){
                searchType.setText("M"); 
            }
            else if(searchType.getText().compareTo("M") == 0){
                searchType.setText("N"); 
            }
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

        @FXML
        private void onCerca() {
            ObservableList<Utente> ricercaUtenti = FXCollections.observableArrayList();
                    String contenuto = cercaField.getText().trim();
                    if(contenuto.isEmpty())
                    {
                        mostraErrore("Attenzione!", "Inserire dei parametri di ricerca");
                        return;
                    }
                    if(searchType.getText().compareTo("N") == 0){
                            for(Utente U : tabellaUtenteModel.getPersone()){
                                    if(U.getNome().compareTo(contenuto)== 0){
                                        ricercaUtenti.add(U);
                                    }
                            }
                    }
                    if(searchType.getText().compareTo("C") == 0){
                            for(Utente U : tabellaUtenteModel.getPersone()){
                                    if(U.getCognome().compareTo(contenuto)== 0){
                                        ricercaUtenti.add(U);
                                    }
                            }
                    }
                    if(searchType.getText().compareTo("M") == 0){
                            for(Utente U : tabellaUtenteModel.getPersone()){
                                    if(U.getMatricola().compareTo(contenuto)== 0){
                                        ricercaUtenti.add(U);
                                    }
                            }
                    }
                    if (!ricercaUtenti.isEmpty()) {
                    tabella.setItems(ricercaUtenti);
                    }
                    else {
                    mostraErrore("Attenzione!", "Nessun utente trovato");
                    }
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
        @FXML
        private void onModifica() {
            if (modifica.getText().trim().equals("Modifica")) {
            modifica.setText("Termina modifica");
            tabella.setEditable(true);
            rimozione.setDisable(false);
            mostraErrore("Avviso!", "Ora è possibile modifcare o eliminare gli utenti dalla tabella");
            } else {
                modifica.setText("Modifica");
            tabella.setEditable(false);
            rimozione.setDisable(true);
            mostraErrore("Avviso!", "Ora non è possibile modifcare o eliminare gli utenti dalla tabella");
            }
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
        @FXML
        private void onEsci() throws Exception {
            if(modifica.getText().trim().equals("Termina modifica")){
            modifica.setText("Modifica");
            tabella.setEditable(false);
            rimozione.setDisable(true);
            }
            principale.setScene(scenaPrincipale);
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
        @FXML
        private void onAggiungiUt() {
            //Controllo se il model esiste
            if (tabellaUtenteModel == null) {
                return;
            }

            // Prendiamo tutti gli attributi di utente sottoforma di stringa
            String strNome = nome.getText().trim();
            String strCognome = cognome.getText().trim();
            String strMatricola = matricola.getText().trim();
            String strEmail = email.getText().trim();

            // controllo se uno o più parametri sono vuoti, nel caso mando un messaggio di errore
            if (strNome.isEmpty() || strCognome.isEmpty() || strMatricola.isEmpty() || strEmail.isEmpty()) {
                mostraErrore("Dati mancanti!", "Inserire ogni attributo");
                return;
            }

            //Gestisco eventuali incongruenze sulla matricola
            if (strMatricola.length() != 10) {
                mostraErrore("Attenzione!", "Inserire una matricola valida");
                return;
            }

            //Aggiunta e salvataggio su file di testo dell'utente
            tabellaUtenteModel.aggiungiPersona(strNome, strCognome, strMatricola, strEmail, LocalDate.now());
            tabellaUtenteModel.salvaSuBinario();

            //Puliamo i campi di utente
            nome.clear();
            cognome.clear();
            matricola.clear();
            email.clear();

            //Disabilito le textfield e il bottone di aggiunta
            aggiuntaUt.setDisable(true);
            nome.setDisable(true);
            cognome.setDisable(true);
            matricola.setDisable(true);
            email.setDisable(true);
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
            tabella.setItems(tabellaUtenteModel.getPersone());
        }

        /**
        * @brief Mostra una finestra di dialogo di errore (Alert.AlertType.ERROR).
        *
        * Permette di visualizzare a schermo un messaggio di errore in caso errata compilazione di campi all'interno dell'interfaccia
        * 
        * @param[in] titolo L'intestazione dell'errore
        * @param[in] messaggio Il messaggio d'errore
        * 
        * @return void
        */

        private void mostraErrore(String titolo, String messaggio) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titolo);
            alert.setHeaderText(null);
            alert.setContentText(messaggio);
            alert.showAndWait();
        }
    }
