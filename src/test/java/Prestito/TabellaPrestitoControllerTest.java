/**
 * @file TabellaPrestitoControllerTest.java
 * @brief Questo file contiene i test unitari per il controller della tabella dei prestiti
 *
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */

package Prestito;

import Libro.Libro;
import Libro.TabellaLibroModel;
import Utente.TabellaUtenteModel;
import Utente.Utente;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TabellaPrestitoControllerTest {

    private TabellaPrestitoController controller;
    
    private TabellaPrestitoModel prestitoModel; /// @brief Model per la gestione dei prestiti
    private TabellaUtenteModel utenteModel;     /// @brief Model per la gestione degli utenti
    private TabellaLibroModel libroModel;       /// @brief Model per la gestione dei libri

    private TextField nomeField;    /// @brief Campo di testo per il nome dell'utente
    private TextField cognomeField; /// @brief Campo di testo per il cognome dell'utente
    private TextField titoloField;  /// @brief Campo di testo per il titolo del libro
    private TextField isbnField;    /// @brief Campo di testo per l'ISBN del libro
    private TextField scadenzaField;/// @brief Campo di testo per la data di scadenza
    private TextField cercaField;   /// @brief Campo di testo per la ricerca
    
    private Button aggiuntaPreButton; /// @brief Bottone per confermare l'aggiunta del prestito
    private Button aggiuntaButton;    /// @brief Bottone per sbloccare i campi di inserimento
    private Button rimozioneButton;   /// @brief Bottone per rimuovere un prestito
    private Button modificaButton;    /// @brief Bottone per abilitare la modifica
    private Button esciButton;        /// @brief Bottone per uscire dalla schermata
    private Button searchTypeButton;  /// @brief Bottone per cambiare il tipo di ricerca (Nome/Titolo)
    private Button xButton;           /// @brief Bottone per cancellare la ricerca
    private Button cercaButton;       /// @brief Bottone per avviare la ricerca
    
    private TableView<Prestito> tabella; /// @brief Tabella che visualizza i prestiti

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
     * Crea una nuova istanza del controller e dei model, pulisce i dati precedenti,
     * istanzia tutti i componenti UI e li inietta nel controller tramite Reflection.
     * Simula inoltre il metodo initialize() di JavaFX.
     *
     * @pre I campi sono nulli o contengono dati di vecchi test
     * @post Il controller è configurato con componenti UI attivi e model vuoti
     *
     * @return void
     */
    @BeforeEach
    void setUp() throws Exception {
        controller = new TabellaPrestitoController();
        
        prestitoModel = new TabellaPrestitoModel();
        utenteModel = new TabellaUtenteModel();
        libroModel = new TabellaLibroModel();
        
        if (prestitoModel.getPrestiti() != null) prestitoModel.getPrestiti().clear();
        if (utenteModel.getPersone() != null) utenteModel.getPersone().clear();
        if (libroModel.getLibri() != null) libroModel.getLibri().clear();

        nomeField = new TextField();
        cognomeField = new TextField();
        titoloField = new TextField();
        isbnField = new TextField();
        scadenzaField = new TextField();
        cercaField = new TextField();

        aggiuntaPreButton = new Button();
        aggiuntaButton = new Button();
        rimozioneButton = new Button();
        modificaButton = new Button();
        modificaButton.setText("Modifica");
        esciButton = new Button();
        searchTypeButton = new Button();
        searchTypeButton.setText("N");
        xButton = new Button();
        cercaButton = new Button();
        
        tabella = new TableView<>();

        injectField(controller, "nome", nomeField);
        injectField(controller, "cognome", cognomeField);
        injectField(controller, "titolo", titoloField);
        injectField(controller, "isbn", isbnField);
        injectField(controller, "scadenza", scadenzaField);
        injectField(controller, "cercaField", cercaField);

        injectField(controller, "aggiuntaPre", aggiuntaPreButton);
        injectField(controller, "aggiunta", aggiuntaButton);
        injectField(controller, "rimozione", rimozioneButton);
        injectField(controller, "modifica", modificaButton);
        injectField(controller, "esci", esciButton);
        injectField(controller, "searchType", searchTypeButton);
        injectField(controller, "X", xButton);
        injectField(controller, "cerca", cercaButton);
        injectField(controller, "tabella", tabella);

        injectField(controller, "nomeCol", new TableColumn<Prestito, String>());
        injectField(controller, "cognomeCol", new TableColumn<Prestito, String>());
        injectField(controller, "titoloCol", new TableColumn<Prestito, String>());
        injectField(controller, "isbnCol", new TableColumn<Prestito, String>());
        injectField(controller, "scadenzaCol", new TableColumn<Prestito, LocalDate>());

        Platform.runLater(() -> {
            try {
                Method initMethod = TabellaPrestitoController.class.getDeclaredMethod("initialize");
                initMethod.setAccessible(true);
                initMethod.invoke(controller);
                
                controller.setModel(prestitoModel, utenteModel, libroModel, null, null);
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
    private void injectField(Object target, String fieldName, Object value) throws Exception {
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
    private void invokeMethod(Object target, String methodName) throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(target);
    }

    /**
     * @brief Test per l'abilitazione dei campi di inserimento
     *
     * Verifica che premendo il tasto di aggiunta ("Nuovo"), i campi di testo
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
                aggiuntaPreButton.setDisable(true);

                invokeMethod(controller, "onAggiungi");

                assertFalse(nomeField.isDisable());
                assertFalse(cognomeField.isDisable());
                assertFalse(titoloField.isDisable());
                assertFalse(aggiuntaPreButton.isDisable());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        Thread.sleep(200);
    }

    /**
     * @brief Test completo del flusso di aggiunta di un prestito
     *
     * Verifica che, dato un Utente e un Libro esistenti, sia possibile aggiungere
     * un nuovo prestito. Controlla anche che i campi vengano puliti dopo l'operazione.
     *
     * @pre Utente e Libro esistono nei rispettivi model
     * @post Un nuovo prestito viene aggiunto al model dei prestiti e i campi vengono puliti
     *
     * @return void
     */
    @Test
    void testAggiungiPrestitoSuccesso() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Utente utenteTest = new Utente("Mario", "Rossi", "M01", "email", LocalDate.parse("2000-10-10"));
                
                Libro libroTest = new Libro("Java", "Autore", "12345", 0, 0, "", 0);
                
                utenteModel.getPersone().add(utenteTest);
                libroModel.getLibri().add(libroTest);

                nomeField.setText("Mario");
                cognomeField.setText("Rossi");
                titoloField.setText("Java");
                isbnField.setText("12345");
                
                LocalDate dataFutura = LocalDate.now().plusDays(30);
                scadenzaField.setText(dataFutura.toString());
                
                aggiuntaPreButton.setDisable(false);

                invokeMethod(controller, "onAggiungiPre");

            } catch (Exception e) {
                e.printStackTrace();
                fail(e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        latch.await(3, TimeUnit.SECONDS);

        assertFalse(prestitoModel.getPrestiti().isEmpty(), "Il prestito deve essere aggiunto al model");
        
        Prestito p = prestitoModel.getPrestiti().get(0);
        assertEquals("Mario", p.getNome());
        assertEquals("Java", p.getTitolo());
        
        assertTrue(nomeField.getText().isEmpty());
        assertTrue(isbnField.getText().isEmpty());
    }

    /**
     * @brief Test del cambio del criterio di ricerca
     *
     * Verifica che il bottone di ricerca cambi ciclicamente il suo testo
     * tra "N" (Nome) e "T" (Titolo) ad ogni click.
     *
     * @pre Il bottone mostra "N"
     * @post Il testo del bottone alterna correttamente i valori
     *
     * @return void
     */
    @Test
    void testCambioTipoRicerca() throws Exception {
        Platform.runLater(() -> {
            try {
                assertEquals("N", searchTypeButton.getText());

                invokeMethod(controller, "onCambio");
                assertEquals("T", searchTypeButton.getText());

                invokeMethod(controller, "onCambio");
                assertEquals("N", searchTypeButton.getText());

            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        Thread.sleep(200);
    }

    /**
     * @brief Test della funzionalità di ricerca
     *
     * Popola la tabella con dati fittizi e verifica che la ricerca per nome
     * filtri correttamente i risultati visualizzati.
     *
     * @pre La tabella contiene due prestiti diversi
     * @post La tabella visualizza solo il prestito che corrisponde al criterio di ricerca
     *
     * @return void
     */
    @Test
    void testRicercaPerNome() throws Exception {
        Utente u1 = new Utente("Mario", "Rossi", "", "", LocalDate.now());
        Libro l1 = new Libro("Libro1", "", "111", 0, 0, "", 0);
        Prestito p1 = new Prestito(u1, l1, LocalDate.now().plusDays(10));
        
        Utente u2 = new Utente("Luigi", "Verdi", "", "", LocalDate.now());
        Libro l2 = new Libro("Libro2", "", "222", 0, 0, "", 0);
        Prestito p2 = new Prestito(u2, l2, LocalDate.now().plusDays(10));

        Platform.runLater(() -> {
            prestitoModel.getPrestiti().addAll(p1, p2);
            tabella.setItems(prestitoModel.getPrestiti());

            searchTypeButton.setText("N");
            cercaField.setText("Mario");

            try {
                invokeMethod(controller, "onCerca");
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });

        Thread.sleep(500);

        ObservableList<Prestito> items = tabella.getItems();
        assertEquals(1, items.size());
        assertEquals("Mario", items.get(0).getNome());
    }

    /**
     * @brief Test della cancellazione della ricerca
     *
     * Verifica che la funzione di cancellazione svuoti il campo di ricerca
     * e ripristini la visualizzazione di tutti i prestiti nella tabella.
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
                invokeMethod(controller, "onCancellaCerca");
            } catch (Exception e) {
                fail(e.getMessage());
            }

            assertTrue(cercaField.getText().isEmpty());
            assertEquals(prestitoModel.getPrestiti(), tabella.getItems());
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
                assertEquals("Modifica", modificaButton.getText());
                assertFalse(tabella.isEditable());

                invokeMethod(controller, "onModifica");
                
                assertEquals("Termina modifica", modificaButton.getText());
                assertTrue(tabella.isEditable());
                assertFalse(rimozioneButton.isDisable());

                invokeMethod(controller, "onModifica");
                
                assertEquals("Modifica", modificaButton.getText());
                assertFalse(tabella.isEditable());
                assertTrue(rimozioneButton.isDisable());

            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        Thread.sleep(200);
    }
}