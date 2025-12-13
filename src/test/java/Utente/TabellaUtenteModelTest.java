package Utente;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.time.LocalDate;

public class TabellaUtenteModelTest {
    
    private TabellaUtenteModel model;
    private final String FILE_BINARIO = "utenti.bin";

    private final String NOME_TEST = "Giulia";
    private final String COGNOME_TEST = "Bianchi";
    private final String MATRICOLA_TEST = "M12345";
    private final String EMAIL_TEST = "g.bianchi@mail.it";
    private final LocalDate ISCRIZIONE_TEST = LocalDate.of(2024, 10, 20);
    
    private Utente utenteTest;

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
        
        // Verifica l'aggiunta basandosi sul metodo equals() di Utente (che usa solo il nome)
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
        
        // La verifica dell'uguaglianza si basa su Utente.equals() (che usa solo il Nome)
        assertTrue(utentiCaricati.contains(utenteTest));
        
        // Verifica dei dati caricati
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