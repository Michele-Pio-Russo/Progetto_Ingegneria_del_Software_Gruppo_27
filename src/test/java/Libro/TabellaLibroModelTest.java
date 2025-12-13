package Libro;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class TabellaLibroModelTest {
    
    private TabellaLibroModel model;
    private final String FILE_BINARIO = "libri.bin";
    
    private final String TITOLO = "Test Book";
    private final String AUTORE = "Test Author";
    private final String ISBN = "123-4567890123";
    private final int ANNO = 2020;
    private final double PREZZO = 19.99;
    private final String USURA = "Ottimo";
    private final int COPIE = 2;
    private static final double DELTA = 0.001;
    
    private Libro libroTest;

    @BeforeEach
    public void setUp() {
        cleanupFile();
        model = new TabellaLibroModel();
        libroTest = new Libro(TITOLO, AUTORE, ISBN, ANNO, PREZZO, USURA, COPIE);
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
    public void testGetLibri() {
        System.out.println("testGetLibri");
        
        ObservableList<Libro> result = model.getLibri();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testAggiungiLibro() {
        System.out.println("testAggiungiLibro");
        
        int sizeIniziale = model.getLibri().size();
        
        model.aggiungiLibro(TITOLO, AUTORE, ISBN, ANNO, PREZZO, USURA, COPIE);
        
        assertEquals(sizeIniziale + 1, model.getLibri().size());
        assertTrue(model.getLibri().contains(libroTest));
    }

    @Test
    public void testRimuoviLibro() {
        System.out.println("testRimuoviLibro");
        
        model.aggiungiLibro(TITOLO, AUTORE, ISBN, ANNO, PREZZO, USURA, COPIE);
        assertTrue(model.getLibri().contains(libroTest));
        
        int sizeIniziale = model.getLibri().size();
        
        model.rimuoviLibro(libroTest);
        
        assertFalse(model.getLibri().contains(libroTest));
        assertEquals(sizeIniziale - 1, model.getLibri().size());
    }

    @Test
    public void testPersistenza() {
        System.out.println("testPersistenza");
        
        model.aggiungiLibro(TITOLO, AUTORE, ISBN, ANNO, PREZZO, USURA, COPIE);
        assertEquals(1, model.getLibri().size());
        
        model.salvaSuBinario();
        
        assertTrue(new File(FILE_BINARIO).exists());
        
        TabellaLibroModel nuovoModel = new TabellaLibroModel();
        
        ObservableList<Libro> libriCaricati = nuovoModel.getLibri();
        assertEquals(1, libriCaricati.size());
        
        assertTrue(libriCaricati.contains(libroTest));
        
        Libro libroCaricato = libriCaricati.get(0);
        assertEquals(TITOLO, libroCaricato.getTitolo());
        assertEquals(PREZZO, libroCaricato.getPrezzo(), DELTA);
    }
    
    @Test
    public void testCostruttoreSenzaFile() {
        System.out.println("testCostruttoreSenzaFile");
        
        assertFalse(new File(FILE_BINARIO).exists());
        
        TabellaLibroModel modelSenzaFile = new TabellaLibroModel();
        
        assertNotNull(modelSenzaFile.getLibri());
        assertTrue(modelSenzaFile.getLibri().isEmpty());
    }
}