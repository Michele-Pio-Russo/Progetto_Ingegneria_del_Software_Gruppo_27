/**
 * @file TabellaLibroModelTest.java
 * @brief Questo file contiene il tester del modello (Model) che gestisce i dati dei libri
 *
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */

package Libro;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;

public class TabellaLibroModelTest {

    private TabellaLibroModel model;  /// @brief Istanza del modello da testare
    private String nomeFile = "libri.bin"; /// @brief Nome del file binario usato per i test di salvataggio

    /**
     * @brief Configurazione iniziale prima di ogni test
     *
     * Questo metodo assicura un ambiente pulito eliminando eventuali file precedenti e inizializzando una nuova istanza del modello.
     *
     * @pre Nessuna condizione particolare
     * @post Il modello è inizializzato e non ci sono file residui
     *
     * @return void
     */
    @BeforeEach
    public void setUp() {
        File f = new File(nomeFile);
        if(f.exists()) {
            f.delete();
        }
        model = new TabellaLibroModel();
    }

    /**
     * @brief Pulizia finale dopo ogni test
     *
     * Questo metodo elimina il file binario creato durante i test per evitare che influenzi le esecuzioni successive.
     *
     * @pre Il test è stato eseguito
     * @post Il file di salvataggio viene rimosso dal sistema
     *
     * @return void
     */
    @AfterEach
    public void tearDown() {
        File f = new File(nomeFile);
        if(f.exists()) {
            f.delete();
        }
    }

    /**
     * @brief Test dello stato iniziale della lista
     *
     * Verifica che la lista dei libri sia istanziata correttamente ma vuota al momento della creazione del modello.
     *
     * @pre Il modello è appena stato creato
     * @post La lista deve esistere ma avere dimensione 0
     *
     * @return void
     */
    @Test
    public void testGetLibriIniziale() {
        assertNotNull(model.getLibri());
        assertEquals(0, model.getLibri().size());
    }

    /**
     * @brief Test per l'aggiunta di un nuovo libro
     *
     * Verifica che i dati inseriti vengano salvati correttamente nella lista e che la dimensione della lista incrementi.
     *
     * @pre La lista è vuota o in uno stato noto
     * @post La lista contiene il nuovo libro con i dati corretti
     *
     * @return void
     */
    @Test
    public void testAggiungiLibro() {
        model.aggiungiLibro("Harry Potter", "J.K. Rowling", "12345", 2001, 15.50, "Nuovo", 10);
        
        assertEquals(1, model.getLibri().size());
        
        Libro l = model.getLibri().get(0);
        assertEquals("Harry Potter", l.getTitolo());
        assertEquals("J.K. Rowling", l.getAutore());
        assertEquals("12345", l.getIsbn());
    }

    /**
     * @brief Test per la rimozione di un libro
     *
     * Aggiunge un libro e successivamente lo rimuove per verificare che la lista torni vuota.
     *
     * @pre La lista contiene almeno un libro
     * @post La lista non contiene più il libro rimosso
     *
     * @return void
     */
    @Test
    public void testRimuoviLibro() {
        model.aggiungiLibro("Harry Potter", "J.K. Rowling", "12345", 2001, 15.50, "Nuovo", 10);
        assertEquals(1, model.getLibri().size());

        Libro l = model.getLibri().get(0);
        model.rimuoviLibro(l);

        assertEquals(0, model.getLibri().size());
    }

    /**
     * @brief Test di persistenza dei dati (Salvataggio e Caricamento)
     *
     * Verifica che un libro salvato su file venga correttamente ricaricato creando una nuova istanza del modello.
     *
     * @pre Il modello contiene dati non salvati
     * @post I dati nel nuovo modello corrispondono a quelli salvati su file
     *
     * @return void
     */
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