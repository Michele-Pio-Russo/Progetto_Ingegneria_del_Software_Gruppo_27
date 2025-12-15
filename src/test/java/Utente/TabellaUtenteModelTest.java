package Utente;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.time.LocalDate;

public class TabellaUtenteModelTest {
    
    private TabellaUtenteModel model;   /// @brief Istanza del modello da testare
    private final String FILE_BINARIO = "utenti.bin";   /// @brief Nome del file binario per il test di persistenza

    private final String NOME_TEST = "Giulia";  /// @brief nome dell'utente di test
    private final String COGNOME_TEST = "Bianchi";  /// @brief cognome dell'utente di test
    private final String MATRICOLA_TEST = "M12345"; /// @brief matricola dell'utente di test
    private final String EMAIL_TEST = "g.bianchi@mail.it";  /// @brief email dell'utente di test
    private final LocalDate ISCRIZIONE_TEST = LocalDate.of(2024, 10, 20);   /// @brief data d'iscrizione dell'utente di test
    
    private Utente utenteTest;  /// @brief Oggetto Utente di supporto per i test (mantenuto per coerenza)

    @BeforeEach
    public void setUp() {
        cleanupFile();
        
        utenteTest = new Utente(NOME_TEST, COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);
        
        model = new TabellaUtenteModel();
    }

    @AfterEach
    public void tearDown() {
        cleanupFile();
    }
    
    private void cleanupFile() {
        File file = new File(FILE_BINARIO);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testGetPersone() {
        System.out.println("testGetPersone");
        
        ObservableList<Utente> result = model.getPersone();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testAggiungiPersona() {
        System.out.println("testAggiungiPersona");
        
        int sizeIniziale = model.getPersone().size();
        
        model.aggiungiPersona(NOME_TEST, COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);
        
        assertEquals(sizeIniziale + 1, model.getPersone().size());
        
        assertTrue(model.getPersone().contains(utenteTest));
    }

    @Test
    public void testRimuoviPersona() {
        System.out.println("testRimuoviPersona");
        
        model.aggiungiPersona(NOME_TEST, COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);
        assertTrue(model.getPersone().contains(utenteTest));
        
        int sizeIniziale = model.getPersone().size();
        
        model.rimuoviPersona(utenteTest);
        
        assertFalse(model.getPersone().contains(utenteTest));
        assertEquals(sizeIniziale - 1, model.getPersone().size());
    }

    @Test
    public void testPersistenza() {
        System.out.println("testPersistenza");
        
        model.aggiungiPersona(NOME_TEST, COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);
        assertEquals(1, model.getPersone().size());
        
        model.salvaSuBinario();
        
        assertTrue(new File(FILE_BINARIO).exists());
        
        TabellaUtenteModel nuovoModel = new TabellaUtenteModel();
        
        ObservableList<Utente> utentiCaricati = nuovoModel.getPersone();
        assertEquals(1, utentiCaricati.size());
        
        assertTrue(utentiCaricati.contains(utenteTest));
        
        Utente utenteCaricato = utentiCaricati.get(0);
        assertEquals(MATRICOLA_TEST, utenteCaricato.getMatricola());
        assertEquals(EMAIL_TEST, utenteCaricato.getEmail());
    }
    
    @Test
    public void testCostruttoreSenzaFile() {
        System.out.println("testCostruttoreSenzaFile");
        
        assertFalse(new File(FILE_BINARIO).exists());
        
        TabellaUtenteModel modelSenzaFile = new TabellaUtenteModel();
        
        assertNotNull(modelSenzaFile.getPersone());
        assertTrue(modelSenzaFile.getPersone().isEmpty());
    }
}