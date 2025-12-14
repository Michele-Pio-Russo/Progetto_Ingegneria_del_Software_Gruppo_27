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
    private TabellaUtenteModel model;

    private TextField nomeField;
    private TextField cognomeField;
    private TextField matricolaField;
    private TextField emailField;
    private TextField cercaField;
    private Button aggiuntaUtButton;
    private Button searchTypeButton;
    private TableView<Utente> tabella;

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
        controller = new TabellaUtenteController();
        model = new TabellaUtenteModel();
        
        model.getPersone().clear();

        nomeField = new TextField();
        cognomeField = new TextField();
        matricolaField = new TextField();
        emailField = new TextField();
        cercaField = new TextField();
        aggiuntaUtButton = new Button();
        searchTypeButton = new Button();
        searchTypeButton.setText("N");
        tabella = new TableView<>();

        injectField(controller, "nome", nomeField);
        injectField(controller, "cognome", cognomeField);
        injectField(controller, "matricola", matricolaField);
        injectField(controller, "email", emailField);
        injectField(controller, "cercaField", cercaField);
        injectField(controller, "aggiuntaUt", aggiuntaUtButton);
        injectField(controller, "searchType", searchTypeButton);
        injectField(controller, "tabella", tabella);
        
        injectField(controller, "nomeCol", new TableColumn<Utente, String>());
        injectField(controller, "cognomeCol", new TableColumn<Utente, String>());
        injectField(controller, "matricolaCol", new TableColumn<Utente, String>());
        injectField(controller, "emailCol", new TableColumn<Utente, String>());
        injectField(controller, "iscrizioneCol", new TableColumn<Utente, LocalDate>());
        injectField(controller, "libriInPrestitoCol", new TableColumn<Utente, Integer>());

        controller.setModel(model, null, null);
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
    void testAggiungiUtenteSuccesso() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            try {
                nomeField.setText("Mario");
                cognomeField.setText("Rossi");
                matricolaField.setText("1234567890");
                emailField.setText("mario.rossi@studenti.it");
                
                invokeMethod(controller, "onAggiungiUt");
                
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
    }

    @Test
    void testCambioTipoRicerca() throws Exception {
        Platform.runLater(() -> {
            try {
                assertEquals("N", searchTypeButton.getText());
                
                invokeMethod(controller, "onCambio");
                assertEquals("C", searchTypeButton.getText());

                invokeMethod(controller, "onCambio");
                assertEquals("M", searchTypeButton.getText());

                invokeMethod(controller, "onCambio");
                assertEquals("N", searchTypeButton.getText());
                
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        Thread.sleep(200);
    }

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
                invokeMethod(controller, "onCerca");
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        
        Thread.sleep(500);

        ObservableList<Utente> items = tabella.getItems();
        assertEquals(1, items.size());
        assertEquals("Luigi", items.get(0).getNome());
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
            assertEquals(model.getPersone(), tabella.getItems());
         });
         Thread.sleep(200);
    }
}