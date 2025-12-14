
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
    
    // I tre model necessari per il funzionamento del controller
    private TabellaPrestitoModel prestitoModel;
    private TabellaUtenteModel utenteModel;
    private TabellaLibroModel libroModel;

    // Componenti FXML da iniettare
    private TextField nomeField;
    private TextField cognomeField;
    private TextField titoloField;
    private TextField isbnField;
    private TextField scadenzaField;
    private TextField cercaField;
    
    private Button aggiuntaPreButton; // Bottone conferma aggiunta
    private Button aggiuntaButton;    // Bottone sblocca campi
    private Button rimozioneButton;
    private Button modificaButton;
    private Button esciButton;
    private Button searchTypeButton;
    private Button xButton;
    private Button cercaButton;
    
    private TableView<Prestito> tabella;

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

    @BeforeEach
    void setUp() throws Exception {
        controller = new TabellaPrestitoController();
        
        // Istanziamo i model reali
        prestitoModel = new TabellaPrestitoModel();
        utenteModel = new TabellaUtenteModel();
        libroModel = new TabellaLibroModel();
        
        // Puliamo i model per avere test isolati
        if (prestitoModel.getPrestiti() != null) prestitoModel.getPrestiti().clear();
        if (utenteModel.getPersone() != null) utenteModel.getPersone().clear();
        if (libroModel.getLibri() != null) libroModel.getLibri().clear();

        // Inizializzazione componenti UI
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

        // Injection via Reflection nei campi privati del controller
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

        // Injection delle colonne (necessarie per initialize)
        injectField(controller, "nomeCol", new TableColumn<Prestito, String>());
        injectField(controller, "cognomeCol", new TableColumn<Prestito, String>());
        injectField(controller, "titoloCol", new TableColumn<Prestito, String>());
        injectField(controller, "isbnCol", new TableColumn<Prestito, String>());
        injectField(controller, "scadenzaCol", new TableColumn<Prestito, LocalDate>());

        // Simuliamo l'initialize FXML
        Platform.runLater(() -> {
            try {
                Method initMethod = TabellaPrestitoController.class.getDeclaredMethod("initialize");
                initMethod.setAccessible(true);
                initMethod.invoke(controller);
                
                // Impostiamo tutti i model necessari
                controller.setModel(prestitoModel, utenteModel, libroModel, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(100);
    }

    private void injectField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private void invokeMethod(Object target, String methodName) throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(target);
    }

    @Test
    void testAbilitaInserimento() throws Exception {
        Platform.runLater(() -> {
            try {
                // Stato iniziale
                nomeField.setDisable(true);
                aggiuntaPreButton.setDisable(true);

                // Azione: click su aggiunta (il bottone "Nuovo")
                invokeMethod(controller, "onAggiungi");

                // Verifica
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

    @Test
    void testAggiungiPrestitoSuccesso() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                // 1. Preparazione Dati: Il controller verifica che Utente e Libro esistano
                // Dobbiamo aggiungere un utente e un libro validi ai rispettivi model
                Utente utenteTest = new Utente("Mario", "Rossi", "M01", "email", LocalDate.now());
                Libro libroTest = new Libro("Java", "Autore", "12345", 2020, 10.0f, "Ottimo", 5);
                
                // NOTA: il controller crea nuovi oggetti Utente/Libro parziali per fare il controllo .contains().
                // Affinché funzioni, le classi Utente e Libro devono avere equals() implementato correttamente
                // o il test deve usare dati che matchano la logica di uguaglianza.
                utenteModel.getPersone().add(utenteTest);
                libroModel.getLibri().add(libroTest);

                // 2. Popolamento campi UI
                nomeField.setText("Mario");
                cognomeField.setText("Rossi");
                titoloField.setText("Java");
                isbnField.setText("12345");
                
                // Data futura per evitare errore scadenza
                LocalDate dataFutura = LocalDate.now().plusDays(30);
                scadenzaField.setText(dataFutura.toString());

                // 3. Invocazione
                invokeMethod(controller, "onAggiungiPre");

            } catch (Exception e) {
                e.printStackTrace();
                fail(e.getMessage());
            } finally {
                latch.countDown();
            }
        });

        latch.await(2, TimeUnit.SECONDS);

        // 4. Verifiche
        assertFalse(prestitoModel.getPrestiti().isEmpty(), "Il prestito doveva essere aggiunto");
        
        Prestito p = prestitoModel.getPrestiti().get(0);
        assertEquals("Mario", p.getNome());
        assertEquals("Java", p.getTitolo());
        
        // Verifica pulizia campi
        assertTrue(nomeField.getText().isEmpty());
    }

    @Test
    void testCambioTipoRicerca() throws Exception {
        Platform.runLater(() -> {
            try {
                // Iniziale: N (Nome)
                assertEquals("N", searchTypeButton.getText());

                invokeMethod(controller, "onCambio");
                assertEquals("T", searchTypeButton.getText()); // Titolo

                invokeMethod(controller, "onCambio");
                assertEquals("N", searchTypeButton.getText()); // Torna a Nome

            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        Thread.sleep(200);
    }

    @Test
    void testRicercaPerNome() throws Exception {
        // Creiamo dati fittizi
        Utente u1 = new Utente("Mario", "Rossi", "", "", LocalDate.now());
        Libro l1 = new Libro("Libro1", "", "111", 0, 0, "", 0);
        Prestito p1 = new Prestito(u1, l1, LocalDate.now().plusDays(10));
        
        Utente u2 = new Utente("Luigi", "Verdi", "", "", LocalDate.now());
        Libro l2 = new Libro("Libro2", "", "222", 0, 0, "", 0);
        Prestito p2 = new Prestito(u2, l2, LocalDate.now().plusDays(10));

        Platform.runLater(() -> {
            prestitoModel.getPrestiti().addAll(p1, p2);
            tabella.setItems(prestitoModel.getPrestiti());

            // Setup ricerca
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
    
    @Test
    void testModificaToggle() throws Exception {
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