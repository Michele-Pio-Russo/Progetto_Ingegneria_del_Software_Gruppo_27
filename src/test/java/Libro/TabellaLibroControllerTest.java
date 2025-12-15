/**
 * @file TabellaLibroControllerTest.java
 * @brief Questo file contiene i test unitari per il controller della tabella dei libri
 *
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */

package Libro;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TabellaLibroControllerTest {

    private TabellaLibroController controller;
    private TabellaLibroModel model; /// @brief Model per la gestione dei libri

    // Componenti FXML da iniettare
    private TextField titoloField;            /// @brief Campo di testo per il titolo del libro
    private TextField autoreField;            /// @brief Campo di testo per l'autore del libro
    private TextField isbnField;              /// @brief Campo di testo per l'ISBN del libro
    private TextField annoPubblicazioneField; /// @brief Campo di testo per l'anno di pubblicazione
    private TextField copieField;             /// @brief Campo di testo per il numero di copie
    private TextField prezzoField;            /// @brief Campo di testo per il prezzo del libro
    private TextField usuraField;             /// @brief Campo di testo per lo stato di usura
    private TextField cercaField;             /// @brief Campo di testo per la ricerca
    
    private Button aggiuntaLibButton; /// @brief Bottone per confermare l'aggiunta del libro
    private Button aggiuntaButton;    /// @brief Bottone per sbloccare i campi di inserimento
    private Button rimozioneButton;   /// @brief Bottone per rimuovere un libro
    private Button modificaButton;    /// @brief Bottone per abilitare la modifica
    private Button esciButton;        /// @brief Bottone per uscire dalla schermata
    private Button searchTypeButton;  /// @brief Bottone per cambiare il tipo di ricerca
    private Button xButton;           /// @brief Bottone per cancellare la ricerca
    
    private TableView<Libro> tabella; /// @brief Tabella che visualizza i libri

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
        controller = new TabellaLibroController();
        
        
        model = new TabellaLibroModel(); 
        
        if (model.getLibri() != null) {
            model.getLibri().clear();
        }

        titoloField = new TextField();
        autoreField = new TextField();
        isbnField = new TextField();
        annoPubblicazioneField = new TextField();
        copieField = new TextField();
        prezzoField = new TextField();
        usuraField = new TextField();
        cercaField = new TextField();

        aggiuntaLibButton = new Button();
        aggiuntaButton = new Button();
        rimozioneButton = new Button();
        modificaButton = new Button();
        modificaButton.setText("Modifica"); 
        esciButton = new Button();
        searchTypeButton = new Button();
        searchTypeButton.setText("T"); 
        xButton = new Button();
        
        tabella = new TableView<>();

        injectField(controller, "titolo", titoloField);
        injectField(controller, "autore", autoreField);
        injectField(controller, "isbn", isbnField);
        injectField(controller, "annoPubblicazione", annoPubblicazioneField);
        injectField(controller, "copie", copieField);
        injectField(controller, "prezzo", prezzoField);
        injectField(controller, "usura", usuraField);
        injectField(controller, "cercaField", cercaField);

        injectField(controller, "aggiuntaLib", aggiuntaLibButton);
        injectField(controller, "aggiunta", aggiuntaButton);
        injectField(controller, "rimozione", rimozioneButton);
        injectField(controller, "modifica", modificaButton);
        injectField(controller, "esci", esciButton);
        injectField(controller, "searchType", searchTypeButton);
        injectField(controller, "X", xButton);
        injectField(controller, "tabella", tabella);

        // Injection delle colonne (necessarie perché il metodo initialize() le usa)
        injectField(controller, "titoloCol", new TableColumn<Libro, String>());
        injectField(controller, "autoreCol", new TableColumn<Libro, String>());
        injectField(controller, "isbnCol", new TableColumn<Libro, String>());
        injectField(controller, "annoPubblicazioneCol", new TableColumn<Libro, Integer>());
        injectField(controller, "copieCol", new TableColumn<Libro, Integer>());
        injectField(controller, "prezzoCol", new TableColumn<Libro, Double>());
        injectField(controller, "usuraCol", new TableColumn<Libro, String>());

        Platform.runLater(() -> {
            try {
                Method initMethod = TabellaLibroController.class.getDeclaredMethod("initialize");
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
                titoloField.setDisable(true);
                aggiuntaLibButton.setDisable(true);

                invokeMethod(controller, "onAggiungi");

                assertFalse(titoloField.isDisable());
                assertFalse(autoreField.isDisable());
                assertFalse(aggiuntaLibButton.isDisable());
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        Thread.sleep(200);
    }

    /**
     * @brief Test completo del flusso di aggiunta di un libro
     *
     * Verifica che sia possibile aggiungere un nuovo libro popolando tutti i campi necessari.
     * Controlla anche che i campi vengano puliti e disabilitati dopo l'operazione.
     *
     * @pre Il model dei libri è vuoto o pronto a ricevere dati
     * @post Un nuovo libro viene aggiunto al model e i campi vengono puliti
     *
     * @return void
     */
    @Test
    void testAggiungiLibroSuccesso() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                titoloField.setText("Il Signore degli Anelli");
                autoreField.setText("Tolkien");
                isbnField.setText("888888");
                annoPubblicazioneField.setText("1954");
                prezzoField.setText("25.50");
                usuraField.setText("Nuovo");
                copieField.setText("10");
                
                aggiuntaLibButton.setDisable(false);

                invokeMethod(controller, "onAggiungiLib");

            } catch (Exception e) {
                e.printStackTrace();
                fail(e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        latch.await(2, TimeUnit.SECONDS);

        assertFalse(model.getLibri().isEmpty(), "La lista dei libri non deve essere vuota");
        
        Libro l = model.getLibri().get(model.getLibri().size() - 1);
        assertEquals("Il Signore degli Anelli", l.getTitolo());
        assertEquals("Tolkien", l.getAutore());
        
        assertTrue(titoloField.getText().isEmpty());
        assertTrue(titoloField.isDisable());
    }

    /**
     * @brief Test del cambio del criterio di ricerca
     *
     * Verifica che il bottone di ricerca cambi ciclicamente il suo testo
     * tra "T" (Titolo), "A" (Autore) e "I" (ISBN) ad ogni click.
     *
     * @pre Il bottone mostra "T"
     * @post Il testo del bottone alterna correttamente i valori previsti
     *
     * @return void
     */
    @Test
    void testCambioTipoRicerca() throws Exception {
        Platform.runLater(() -> {
            try {
                searchTypeButton.setText("T"); 
                
                invokeMethod(controller, "onCambio");
                assertEquals("A", searchTypeButton.getText()); 

                invokeMethod(controller, "onCambio");
                assertEquals("I", searchTypeButton.getText()); 

                invokeMethod(controller, "onCambio");
                assertEquals("T", searchTypeButton.getText()); 

            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        Thread.sleep(200);
    }

    /**
     * @brief Test della funzionalità di ricerca per Titolo
     *
     * Popola la tabella con dati fittizi e verifica che la ricerca per titolo
     * filtri correttamente i risultati visualizzati.
     *
     * @pre La tabella contiene due libri diversi
     * @post La tabella visualizza solo il libro che corrisponde al criterio di ricerca
     *
     * @return void
     */
    @Test
    void testRicercaPerTitolo() throws Exception {
        Libro l1 = new Libro("Java Programming", "Rossi", "111", 2020, 20.0f, "Nuovo", 5);
        Libro l2 = new Libro("C++ Guide", "Verdi", "222", 2019, 30.0f, "Usato", 3);

        Platform.runLater(() -> {
            model.getLibri().addAll(l1, l2);
            
            tabella.setItems(model.getLibri());

            searchTypeButton.setText("T");
            cercaField.setText("Java Programming");

            try {
                invokeMethod(controller, "onCerca");
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });

        Thread.sleep(500);

        ObservableList<Libro> items = tabella.getItems();
        assertEquals(1, items.size(), "Dovrebbe trovare esattamente un libro");
        assertEquals("Java Programming", items.get(0).getTitolo());
    }

    /**
     * @brief Test della cancellazione della ricerca
     *
     * Verifica che la funzione di cancellazione svuoti il campo di ricerca
     * e ripristini la visualizzazione di tutti i libri nella tabella.
     *
     * @pre Il campo di ricerca contiene testo e la tabella è filtrata
     * @post Il campo è vuoto e la tabella mostra tutti gli elementi
     *
     * @return void
     */
    @Test
    void testCancellaCerca() throws Exception {
        Platform.runLater(() -> {
            cercaField.setText("Testo di ricerca");
            
            try {
                invokeMethod(controller, "onCancellaCerca");
            } catch (Exception e) {
                fail(e.getMessage());
            }

            assertTrue(cercaField.getText().isEmpty());

            assertEquals(model.getLibri().size(), tabella.getItems().size());
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

                modificaButton.setText("Modifica");
                rimozioneButton.setDisable(true);
                tabella.setEditable(false);

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