/**
 * @file TabellaUtenteControllerTest.java
 * @brief Questo file contiene i test unitari per il controller della tabella degli utenti
 *
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */

package Utente;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TabellaUtenteControllerTest {

    private TabellaUtenteController controller;
    private TabellaUtenteModel model; /// @brief Model per la gestione degli utenti

    private TextField nomeField;      /// @brief Campo di testo per il nome
    private TextField cognomeField;   /// @brief Campo di testo per il cognome
    private TextField matricolaField; /// @brief Campo di testo per la matricola
    private TextField emailField;     /// @brief Campo di testo per l'email
    private TextField cercaField;     /// @brief Campo di testo per la ricerca

    private Button aggiuntaUtButton; /// @brief Bottone per confermare l'aggiunta dell'utente
    private Button aggiunta;         /// @brief Bottone per sbloccare i campi di inserimento
    private Button rimozione;        /// @brief Bottone per rimuovere un utente
    private Button modifica;         /// @brief Bottone per abilitare la modifica
    private Button esci;             /// @brief Bottone per uscire dalla schermata
    private Button searchTypeButton; /// @brief Bottone per cambiare il tipo di ricerca
    private Button X;                /// @brief Bottone per cancellare la ricerca

    private TableView<Utente> tabella; /// @brief Tabella che visualizza gli utenti

    /**
     * @brief Inizializzazione del toolkit JavaFX
     *
     * Avvia il thread JavaFX necessario per testare componenti UI come TextField e Button.
     * Utilizza un CountDownLatch per assicurarsi che l'inizializzazione sia completa prima di procedere.
     *
     * @pre Il toolkit JavaFX non è avviato
     * @post Il toolkit JavaFX è attivo e pronto
     *
     * @return void
     */
    @BeforeAll
    static void initJfx() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException e) {
            latch.countDown();
        }
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timeout JavaFX");
        }
    }

    /**
     * @brief Setup dell'ambiente di test
     *
     * Crea una nuova istanza del controller e del model, pulisce i dati precedenti,
     * istanzia tutti i componenti UI e li inietta nel controller tramite Reflection.
     * Simula inoltre il metodo initialize() di JavaFX.
     *
     * @pre I campi sono nulli o contengono dati di vecchi test
     * @post Il controller è configurato con componenti UI attivi e model vuoto
     *
     * @return void
     */
    @BeforeEach
    void setUp() throws Exception {
        controller = new TabellaUtenteController();
        model = new TabellaUtenteModel();
        
        if (model.getPersone() != null) {
            model.getPersone().clear();
        }

        nomeField = new TextField();
        cognomeField = new TextField();
        matricolaField = new TextField();
        emailField = new TextField();
        cercaField = new TextField();

        aggiuntaUtButton = new Button();
        aggiunta = new Button();
        rimozione = new Button();
        modifica = new Button();
        modifica.setText("Modifica"); 
        esci = new Button();
        searchTypeButton = new Button();
        searchTypeButton.setText("N");
        X = new Button();
        
        tabella = new TableView<>();

        impostaCampo(controller, "nome", nomeField);
        impostaCampo(controller, "cognome", cognomeField);
        impostaCampo(controller, "matricola", matricolaField);
        impostaCampo(controller, "email", emailField);
        impostaCampo(controller, "cercaField", cercaField);

        impostaCampo(controller, "aggiuntaUt", aggiuntaUtButton);
        impostaCampo(controller, "aggiunta", aggiunta);
        impostaCampo(controller, "rimozione", rimozione);
        impostaCampo(controller, "modifica", modifica);
        impostaCampo(controller, "esci", esci);
        impostaCampo(controller, "searchType", searchTypeButton);
        impostaCampo(controller, "X", X);
        impostaCampo(controller, "tabella", tabella);

        impostaCampo(controller, "nomeCol", new TableColumn<Utente, String>());
        impostaCampo(controller, "cognomeCol", new TableColumn<Utente, String>());
        impostaCampo(controller, "matricolaCol", new TableColumn<Utente, String>());
        impostaCampo(controller, "emailCol", new TableColumn<Utente, String>());
        impostaCampo(controller, "iscrizioneCol", new TableColumn<Utente, LocalDate>());
        impostaCampo(controller, "libriInPrestitoCol", new TableColumn<Utente, Integer>());

        Platform.runLater(() -> {
            try {
                Method initMethod = TabellaUtenteController.class.getDeclaredMethod("initialize");
                initMethod.setAccessible(true);
                initMethod.invoke(controller);
                
                controller.setModel(model, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(100); 
    }

    /**
     * @brief Metodo helper per l'iniezione delle dipendenze private
     *
     * @pre Il campo esiste nell'oggetto target
     * @post Il campo privato assume il valore passato
     * * @param[in] target L'oggetto in cui iniettare il valore
     * @param[in] fieldName Il nome del campo privato
     * @param[in] value Il valore da assegnare
     *
     * @return void
     */
    private void impostaCampo(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    /**
     * @brief Metodo helper per invocare metodi privati
     *
     * @pre Il metodo esiste nell'oggetto target
     * @post Il metodo viene eseguito
     * * @param[in] target L'oggetto su cui invocare il metodo
     * @param[in] methodName Il nome del metodo privato
     *
     * @return void
     */
    private void invocaMetodo(Object target, String methodName) throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(target);
    }

    /**
     * @brief Test per l'abilitazione dei campi di inserimento
     *
     * Verifica che premendo il tasto di aggiunta ("Aggiungi Utente"), i campi di testo
     * e il bottone di conferma vengano abilitati.
     *
     * @pre I campi sono disabilitati
     * @post I campi e il bottone di conferma sono abilitati
     *
     * @return void
     */
    @Test
    void testAbilitaInserimento() throws Exception {
        Platform.runLater(() -> {
            try {
                nomeField.setDisable(true);
                aggiuntaUtButton.setDisable(true);

                invocaMetodo(controller, "onAggiungi");

                assertFalse(nomeField.isDisable());
                assertFalse(cognomeField.isDisable());
                assertFalse(aggiuntaUtButton.isDisable());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        Thread.sleep(200);
    }

    /**
     * @brief Test completo del flusso di aggiunta di un utente
     *
     * Verifica che sia possibile aggiungere un nuovo utente popolando tutti i campi necessari.
     * Controlla anche che i campi vengano puliti dopo l'operazione.
     *
     * @pre Il model degli utenti è vuoto o pronto a ricevere dati
     * @post Un nuovo utente viene aggiunto al model e i campi vengono puliti
     *
     * @return void
     */
    @Test
    void testAggiungiUtenteSuccesso() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            try {
                nomeField.setText("Mario");
                cognomeField.setText("Rossi");
                matricolaField.setText("1234567890");
                emailField.setText("mario.rossi@studenti.it");
                
                aggiuntaUtButton.setDisable(false);
                
                invocaMetodo(controller, "onAggiungiUt");
                
            } catch (Exception e) {
                e.printStackTrace();
                fail(e.getMessage());
            } finally {
                latch.countDown();
            }
        });
        
        latch.await(2, TimeUnit.SECONDS);

        assertFalse(model.getPersone().isEmpty());
        Utente u = model.getPersone().get(model.getPersone().size() - 1);
        
        assertEquals("Mario", u.getNome());
        assertEquals("1234567890", u.getMatricola());
        
        assertTrue(nomeField.getText().isEmpty());
        assertTrue(nomeField.isDisable());
    }

    /**
     * @brief Test del cambio del criterio di ricerca
     *
     * Verifica che il bottone di ricerca cambi ciclicamente il suo testo
     * tra "N" (Nome), "C" (Cognome) e "M" (Matricola) ad ogni click.
     *
     * @pre Il bottone mostra "N"
     * @post Il testo del bottone alterna correttamente i valori previsti
     *
     * @return void
     */
    @Test
    void testCambioTipoRicerca() throws Exception {
        Platform.runLater(() -> {
            try {
                assertEquals("N", searchTypeButton.getText());
                
                invocaMetodo(controller, "onCambio");
                assertEquals("C", searchTypeButton.getText());

                invocaMetodo(controller, "onCambio");
                assertEquals("M", searchTypeButton.getText());

                invocaMetodo(controller, "onCambio");
                assertEquals("N", searchTypeButton.getText());
                
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        Thread.sleep(200);
    }

    /**
     * @brief Test della funzionalità di ricerca per Nome
     *
     * Popola la tabella con dati fittizi e verifica che la ricerca per nome
     * filtri correttamente i risultati visualizzati.
     *
     * @pre La tabella contiene due utenti diversi
     * @post La tabella visualizza solo l'utente che corrisponde al criterio di ricerca
     *
     * @return void
     */
    @Test
    void testRicercaPerNome() throws Exception {
        Utente u1 = new Utente("Luigi", "Verdi", "0000000001", "l.v@test.com", LocalDate.now());
        Utente u2 = new Utente("Anna", "Neri", "0000000002", "a.n@test.com", LocalDate.now());
        
        Platform.runLater(() -> {
            model.getPersone().addAll(u1, u2);
            tabella.setItems(model.getPersone());
            
            searchTypeButton.setText("N");
            cercaField.setText("Luigi");
            
            try {
                invocaMetodo(controller, "onCerca");
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        
        Thread.sleep(500);

        ObservableList<Utente> items = tabella.getItems();
        assertEquals(1, items.size());
        assertEquals("Luigi", items.get(0).getNome());
    }

    /**
     * @brief Test della cancellazione della ricerca
     *
     * Verifica che il bottone "X" svuoti il campo di ricerca
     * e ripristini la visualizzazione di tutti gli utenti nella tabella.
     *
     * @pre Il campo di ricerca contiene testo e la tabella è filtrata
     * @post Il campo è vuoto e la tabella mostra tutti gli elementi
     *
     * @return void
     */
    @Test
    void testCancellaCerca() throws Exception {
        Platform.runLater(() -> {
            cercaField.setText("Testo");
            try {
                invocaMetodo(controller, "onCancellaCerca");
            } catch (Exception e) {
               fail(e.getMessage());
            }
            
            assertTrue(cercaField.getText().isEmpty());
            assertEquals(model.getPersone(), tabella.getItems());
         });
         Thread.sleep(200);
    }

    /**
     * @brief Test dell'attivazione/disattivazione modalità modifica
     *
     * Verifica che il bottone di modifica cambi stato alla tabella (editabile/non editabile)
     * e abiliti/disabiliti il bottone di rimozione.
     *
     * @pre La modalità modifica è disattivata
     * @post La modalità modifica viene attivata e poi disattivata correttamente
     *
     * @return void
     */
    @Test
    void testAttivaModalitaModifica() throws Exception {
        Platform.runLater(() -> {
            try {
                modifica.setText("Modifica");
                rimozione.setDisable(true);
                tabella.setEditable(false);

                invocaMetodo(controller, "onModifica");

                assertEquals("Termina modifica", modifica.getText());
                assertTrue(tabella.isEditable());
                assertFalse(rimozione.isDisable());

                invocaMetodo(controller, "onModifica");

                assertEquals("Modifica", modifica.getText());
                assertFalse(tabella.isEditable());
                assertTrue(rimozione.isDisable());

            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        Thread.sleep(200);
    }
}