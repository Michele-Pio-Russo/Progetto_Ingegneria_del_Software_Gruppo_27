package Libro;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;

public class TabellaLibroModelTest {

    private TabellaLibroModel model;
    private String nomeFile = "libri.bin";

    @BeforeEach
    public void setUp() {
        File f = new File(nomeFile);
        if(f.exists()) {
            f.delete();
        }
        model = new TabellaLibroModel();
    }

    @AfterEach
    public void tearDown() {
        File f = new File(nomeFile);
        if(f.exists()) {
            f.delete();
        }
    }

    @Test
    public void testGetLibriIniziale() {
        assertNotNull(model.getLibri());
        assertEquals(0, model.getLibri().size());
    }

    @Test
    public void testAggiungiLibro() {
        model.aggiungiLibro("Harry Potter", "J.K. Rowling", "12345", 2001, 15.50, "Nuovo", 10);
        
        assertEquals(1, model.getLibri().size());
        
        Libro l = model.getLibri().get(0);
        assertEquals("Harry Potter", l.getTitolo());
        assertEquals("J.K. Rowling", l.getAutore());
        assertEquals("12345", l.getIsbn());
    }

    @Test
    public void testRimuoviLibro() {
        model.aggiungiLibro("Harry Potter", "J.K. Rowling", "12345", 2001, 15.50, "Nuovo", 10);
        assertEquals(1, model.getLibri().size());

        Libro l = model.getLibri().get(0);
        model.rimuoviLibro(l);

        assertEquals(0, model.getLibri().size());
    }

    @Test
    public void testSalvataggioCaricamento() {
        model.aggiungiLibro("Libro Test", "Autore Test", "999", 2020, 20.0, "Usato", 5);
        model.salvaSuBinario();

        File f = new File(nomeFile);
        assertTrue(f.exists());

        TabellaLibroModel model2 = new TabellaLibroModel();
        assertEquals(1, model2.getLibri().size());

        Libro caricato = model2.getLibri().get(0);
        assertEquals("Libro Test", caricato.getTitolo());
        assertEquals("999", caricato.getIsbn());
    }
}