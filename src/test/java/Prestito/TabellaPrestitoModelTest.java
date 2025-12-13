package Prestito;

import Libro.Libro;
import Utente.Utente;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.time.LocalDate;

public class TabellaPrestitoModelTest {
    
    private TabellaPrestitoModel model;
    private final String FILE_BINARIO = "prestiti.bin";

    private final String NOME_UTENTE = "Mario";
    private final String COGNOME_UTENTE = "Rossi";
    private final String MATRICOLA_UTENTE = "M111";
    private final String EMAIL_UTENTE = "m.r@mail.it";
    private final LocalDate ISCRIZIONE_UTENTE = LocalDate.of(2024, 1, 1);
    
    private final String TITOLO_LIBRO = "Test Book";
    private final String AUTORE_LIBRO = "Test Author";
    private final String ISBN_LIBRO = "978-1111111111";
    private final int ANNO_LIBRO = 2020;
    private final double PREZZO_LIBRO = 25.00;
    private final String USURA_LIBRO = "Nuovo";
    private final int COPIE_LIBRO = 3;
    
    private Utente utenteTest;
    private Libro libroTest;
    private Prestito prestitoTest;
    private LocalDate dataScadenzaTest;

    @BeforeEach
    public void setUp() {
        cleanupFile();
        
        utenteTest = new Utente(NOME_UTENTE, COGNOME_UTENTE, MATRICOLA_UTENTE, EMAIL_UTENTE, ISCRIZIONE_UTENTE);
        libroTest = new Libro(TITOLO_LIBRO, AUTORE_LIBRO, ISBN_LIBRO, ANNO_LIBRO, PREZZO_LIBRO, USURA_LIBRO, COPIE_LIBRO);
        dataScadenzaTest = LocalDate.now().plusWeeks(2);
        
        prestitoTest = new Prestito(utenteTest, libroTest, dataScadenzaTest);
        
        model = new TabellaPrestitoModel();
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
    public void testGetPrestiti() {
        System.out.println("testGetPrestiti");
        
        ObservableList<Prestito> result = model.getPrestiti();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testAggiungiPrestito() {
        System.out.println("testAggiungiPrestito");
        
        int sizeIniziale = model.getPrestiti().size();
        
        model.aggiungiPrestito(utenteTest, libroTest, dataScadenzaTest);
        
        assertEquals(sizeIniziale + 1, model.getPrestiti().size());
        assertTrue(model.getPrestiti().contains(prestitoTest));
        
        Prestito prestitoDuplicato = new Prestito(utenteTest, libroTest, dataScadenzaTest.plusDays(1));
        model.aggiungiPrestito(utenteTest, libroTest, dataScadenzaTest.plusDays(1));
        assertEquals(sizeIniziale + 2, model.getPrestiti().size());
    }

    @Test
    public void testRimuoviPrestito() {
        System.out.println("testRimuoviPrestito");
        
        model.aggiungiPrestito(utenteTest, libroTest, dataScadenzaTest);
        assertTrue(model.getPrestiti().contains(prestitoTest));
        
        int sizeIniziale = model.getPrestiti().size();
        
        model.rimuoviPrestito(prestitoTest);
        
        assertFalse(model.getPrestiti().contains(prestitoTest));
        assertEquals(sizeIniziale - 1, model.getPrestiti().size());
    }

    @Test
    public void testPersistenza() {
        System.out.println("testPersistenza");
        
        model.aggiungiPrestito(utenteTest, libroTest, dataScadenzaTest);
        assertEquals(1, model.getPrestiti().size());
        
        model.salvaSuBinario();
        
        assertTrue(new File(FILE_BINARIO).exists());
        
        TabellaPrestitoModel nuovoModel = new TabellaPrestitoModel();
        
        ObservableList<Prestito> prestitiCaricati = nuovoModel.getPrestiti();
        assertEquals(1, prestitiCaricati.size());
        
        assertTrue(prestitiCaricati.contains(prestitoTest));
        
        Prestito prestitoCaricato = prestitiCaricati.get(0);
        assertEquals(NOME_UTENTE, prestitoCaricato.getNome());
        assertEquals(ISBN_LIBRO, prestitoCaricato.getIsbn());
        assertEquals(dataScadenzaTest, prestitoCaricato.getDataDiScadenza());
    }
    
    @Test
    public void testCostruttoreSenzaFile() {
        System.out.println("testCostruttoreSenzaFile");
        
        assertFalse(new File(FILE_BINARIO).exists());
        
        TabellaPrestitoModel modelSenzaFile = new TabellaPrestitoModel();
        
        assertNotNull(modelSenzaFile.getPrestiti());
        assertTrue(modelSenzaFile.getPrestiti().isEmpty());
    }
}