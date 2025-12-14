package Libro;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TabellaLibroControllerTest {

    private TabellaLibroController controller;
    private TabellaLibroModel model;
    private Stage stage;
    private Scene scene;

    // Componenti GUI
    private Button aggiuntaLib;
    private TextField titolo;
    private TextField autore;
    private TextField isbn;
    private TextField prezzo;
    private TextField usura;
    private TextField annoPubblicazione;
    private TextField copie;
    private Button searchType;
    private Button modifica;
    private Button rimozione;
    private TableView<Libro> tabella;
    private TextField cercaField;

    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // JavaFX già avviato, ignoro
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        controller = new TabellaLibroController();
        model = mock(TabellaLibroModel.class);
        stage = mock(Stage.class);
        scene = mock(Scene.class);

        // Inizializzo tutti i componenti grafici finti
        aggiuntaLib = new Button();
        titolo = new TextField();
        autore = new TextField();
        isbn = new TextField();
        prezzo = new TextField();
        usura = new TextField();
        annoPubblicazione = new TextField();
        copie = new TextField();
        searchType = new Button();
        modifica = new Button();
        rimozione = new Button();
        tabella = new TableView<>();
        cercaField = new TextField();

        // Inietto i componenti nel controller
        setPrivateField("aggiuntaLib", aggiuntaLib);
        setPrivateField("titolo", titolo);
        setPrivateField("autore", autore);
        setPrivateField("isbn", isbn);
        setPrivateField("prezzo", prezzo);
        setPrivateField("usura", usura);
        setPrivateField("annoPubblicazione", annoPubblicazione);
        setPrivateField("copie", copie);
        setPrivateField("searchType", searchType);
        setPrivateField("modifica", modifica);
        setPrivateField("rimozione", rimozione);
        setPrivateField("tabella", tabella);
        setPrivateField("cercaField", cercaField);
    }

    // Metodo helper per accedere ai campi privati
    private void setPrivateField(String name, Object obj) throws Exception {
        Field f = TabellaLibroController.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(controller, obj);
    }

    // Metodo helper per chiamare metodi privati
    private void callPrivateMethod(String name) throws Exception {
        Method m = TabellaLibroController.class.getDeclaredMethod(name);
        m.setAccessible(true);
        m.invoke(controller);
    }

    @Test
    void testSetModel() {
        when(model.getLibri()).thenReturn(FXCollections.observableArrayList());
        controller.setModel(model, stage, scene);
        assertNotNull(tabella.getItems());
    }

    @Test
    void testOnAggiungi() throws Exception {
        aggiuntaLib.setDisable(true);
        titolo.setDisable(true);
        
        callPrivateMethod("onAggiungi");
        
        assertFalse(aggiuntaLib.isDisable());
        assertFalse(titolo.isDisable());
        assertFalse(autore.isDisable());
        assertFalse(isbn.isDisable());
    }

    @Test
    void testRicercaButtonChange() throws Exception {
        searchType.setText("T");
        callPrivateMethod("onCambio");
        assertEquals("A", searchType.getText());
        
        callPrivateMethod("onCambio");
        assertEquals("I", searchType.getText());
        
        callPrivateMethod("onCambio");
        assertEquals("T", searchType.getText());
    }

    @Test
    void testModalitaModifica() throws Exception {
        modifica.setText("Modifica");
        rimozione.setDisable(true);
        tabella.setEditable(false);
        
        callPrivateMethod("onModifica");
        
        assertEquals("Termina modifica", modifica.getText());
        assertFalse(rimozione.isDisable());
        assertTrue(tabella.isEditable());
        
        callPrivateMethod("onModifica");
        
        assertEquals("Modifica", modifica.getText());
        assertTrue(rimozione.isDisable());
        assertFalse(tabella.isEditable());
    }

    @Test
    void testCancellaCerca() throws Exception {
        when(model.getLibri()).thenReturn(FXCollections.observableArrayList());
        controller.setModel(model, stage, scene);
        
        cercaField.setText("Qualcosa");
        callPrivateMethod("onCancellaCerca");
        
        assertEquals("", cercaField.getText());
    }
}