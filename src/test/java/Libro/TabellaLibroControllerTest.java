/**
 * @file TabellaLibroControllerTest.java
 * @brief Questo file contiene il tester del controller della tabella che gestisce i libri della libreria
 *
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */

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
    private Button aggiuntaLib;  ///@brief Test del bottone che permette di aggiungere un libro alla tabella dei libri
    private TextField titolo; ///@brief test del TextField in cui è necessario inserire il titolo del libro da aggiungere alla tabella dei libri
    private TextField autore; ///@brief test del TextField in cui è necessario inserire l'autore del libro da aggiungere alla tabella dei libri
    private TextField isbn; ///@brief test del TextField in cui è necessario inserire l'ISBN del libro da aggiungere alla tabella dei libri
    private TextField prezzo; ///@brief test del TextField in cui è necessario inserire il prezzo del libro da aggiungere alla tabella dei libri
    private TextField usura; ///@brief test del TextField in cui è necessario inserire lo stato di usura del libro da aggiungere alla tabella dei libri
    private TextField annoPubblicazione; ///@brief test del TextField in cui è necessario inserire l'anno di pubblicazione del libro da aggiungere alla tabella dei libri
    private TextField copie; ///@brief test del TextField in cui è necessario inserire il numero di copie del libro da aggiungere alla tabella dei libri
    private Button searchType;  ///@brief test del bottone che ci permette di cambiare il criterio di ricerca (titolo, autore e codice isbn)
    private Button modifica; ///@brief test del bottone che permette di modificare le informazioni di un libro della tabella dei libri
    private Button rimozione; ///@brief test del bottone che permette di rimuovere un libro dalla tabella dei libri
    private TableView<Libro> tabella;  ///@brief test della tabella che contiene i libri della libreria universitaria
    private TextField cercaField; ///@brief test del TextField che permette di cercare un libro

    
    /**
     * @brief Inizializza il toolkit JavaFX.
     * Necessario per eseguire test che coinvolgono componenti JavaFX (come Platform.runLater) in un ambiente non grafico.
     * @pre Il toolkit JavaFX non è ancora avviato.
     * @post Il toolkit JavaFX è inizializzato e pronto all'uso.
     * @return void
     */
    @BeforeAll
    static void initJfx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
        }
    }

    /**
     * @brief Configura l'ambiente di test prima di ogni esecuzione.
     * Inizializza il controller, crea i mock per il modello e lo stage, istanzia i componenti UI e li inietta nel controller tramite Reflection.
     * @pre I campi della classe sono nulli o non inizializzati.
     * @post Il controller è istanziato e tutti i componenti UI privati sono collegati.
     * @return void
     */
    @BeforeEach
    void setUp() throws Exception {
        controller = new TabellaLibroController();
        model = mock(TabellaLibroModel.class);
        stage = mock(Stage.class);
        scene = mock(Scene.class);

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

    /**
     * @brief Metodo di utilità per impostare campi privati tramite Reflection.
     * Permette di accedere ai campi 'private' del controller per iniettare i componenti UI.
     * 
     * @pre L'oggetto controller è istanziato.
     * @post Il campo specificato del controller contiene l'oggetto passato.
     * 
     * @param[in] name Nome del campo privato da impostare.
     * @param[in] obj Oggetto da assegnare al campo.
     * 
     * @return void
     */
    private void setPrivateField(String name, Object obj) throws Exception {
        Field f = TabellaLibroController.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(controller, obj);
    }

    /**
     * @brief Metodo di utilità per invocare metodi privati tramite Reflection.
     * Permette di testare la logica contenuta in metodi 'private' del controller.
     * 
     * @pre Il metodo esiste nella classe del controller.
     * @post Il metodo privato viene eseguito.
     * @param[in] name Nome del metodo privato da invocare.
     * @return void
     */
    private void callPrivateMethod(String name) throws Exception {
        Method m = TabellaLibroController.class.getDeclaredMethod(name);
        m.setAccessible(true);
        m.invoke(controller);
    }

    /**
     * @brief Test del metodo setModel.
     * Verifica che il modello venga impostato correttamente e che la tabella venga popolata (o preparata) con gli elementi del modello.
     * 
     * @pre Il modello è mockato e restituisce una lista osservabile vuota.
     * @post La tabella contiene una lista di items non nulla.
     * @return void
     */
    @Test
    void testSetModel() {
        when(model.getLibri()).thenReturn(FXCollections.observableArrayList());
        controller.setModel(model, stage, scene);
        assertNotNull(tabella.getItems());
    }

    /**
     * @brief Test della logica di attivazione inserimento (onAggiungi).
     * Verifica che l'invocazione del metodo abiliti i campi di testo e i bottoni necessari per l'aggiunta di un nuovo libro.
     * 
     * @pre I componenti UI (bottone aggiunta, textfields) sono disabilitati.
     * @post I componenti UI risultano abilitati.
     * @return void
     */
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

    /**
     * @brief Test del cambio criterio di ricerca (onCambio).
     * Verifica che premendo il bottone di ricerca il testo cambi
     * tra "T" (Titolo), "A" (Autore) e "I" (ISBN).
     * @pre Il bottone di ricerca è impostato su "T".
     * @post Il testo del bottone cicla correttamente attraverso i valori previsti.
     * @return void
     */
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

    /**
     * @brief Test della modalità di modifica (onModifica).
     * Verifica che il sistema entri ed esca correttamente dalla modalità di modifica,
     * abilitando/disabilitando la tabella e il tasto di rimozione, e cambiando il testo del bottone.
     * @pre Il sistema è in stato di visualizzazione (tabella non editabile).
     * @post Il sistema alterna lo stato: prima in modifica (tabella editabile), poi torna in visualizzazione.
     * @return void
     */
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

    /**
     * @brief Test della cancellazione della ricerca (onCancellaCerca).
     * Verifica che il metodo pulisca il campo di testo della ricerca.
     * @pre Il campo di ricerca contiene del testo ("Qualcosa").
     * @post Il campo di ricerca è vuoto.
     * @return void
     */
    @Test
    void testCancellaCerca() throws Exception {
        when(model.getLibri()).thenReturn(FXCollections.observableArrayList());
        controller.setModel(model, stage, scene);
        
        cercaField.setText("Qualcosa");
        callPrivateMethod("onCancellaCerca");
        
        assertEquals("", cercaField.getText());
    }
}