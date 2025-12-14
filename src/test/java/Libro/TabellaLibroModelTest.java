package Libro;



import javafx.collections.ObservableList;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import java.util.Optional;



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

    

    private Libro libroTest; // Mantenuto solo per coerenza dei dati



    @BeforeEach

    public void setUp() {

        cleanupFile(); // Assicura che il file sia rimosso

        model = new TabellaLibroModel(); // Inizializza il model

        libroTest = new Libro(TITOLO, AUTORE, ISBN, ANNO, PREZZO, USURA, COPIE);

    }



    @AfterEach

    public void tearDown() {

        cleanupFile();

    }

    

    private void cleanupFile() {

        File file = new File(FILE_BINARIO);

        if (file.exists()) {

            // Nota: Se questa riga fallisce (file bloccato), vedrai un errore.

            // La correzione in TabellaLibroModel dovrebbe prevenire questo.

            file.delete(); 

        }

    }

    

    // Metodo di supporto per trovare un libro tramite ISBN

    private Optional<Libro> findLibroByIsbn(ObservableList<Libro> libri, String isbn) {

        return libri.stream()

                .filter(l -> l.getIsbn().equals(isbn))

                .findFirst();

    }



    @Test

    public void testGetLibri() {

        System.out.println("testGetLibri");

        

        ObservableList<Libro> result = model.getLibri();

        

        assertNotNull(result, "La lista dei libri non deve essere null.");

        assertTrue(result.isEmpty(), "La lista deve essere vuota all'inizio (dopo cleanup).");

    }



    @Test

    public void testAggiungiLibro() {

        System.out.println("testAggiungiLibro");

        

        int sizeIniziale = model.getLibri().size();

        

        model.aggiungiLibro(TITOLO, AUTORE, ISBN, ANNO, PREZZO, USURA, COPIE);

        

        // Verifica dimensione

        assertEquals(sizeIniziale + 1, model.getLibri().size());

        

        // CORREZIONE: Cerca il libro per ISBN, non il riferimento.

        Libro libroAggiunto = findLibroByIsbn(model.getLibri(), ISBN)

                                .orElse(null);

        

        assertNotNull(libroAggiunto, "Il libro con ISBN atteso non è stato trovato.");

        

        // Verifica l'integrità dei dati

        assertEquals(TITOLO, libroAggiunto.getTitolo());

    }



    @Test

    public void testRimuoviLibro() {

        System.out.println("testRimuoviLibro");

        

        // **Fase 1: Aggiunta e Recupero del Riferimento**

        model.aggiungiLibro(TITOLO, AUTORE, ISBN, ANNO, PREZZO, USURA, COPIE);

        

        // CORREZIONE: Recupera l'oggetto *effettivamente aggiunto* dal model.

        Libro libroDaRimuovere = findLibroByIsbn(model.getLibri(), ISBN)

                                    .orElseThrow(() -> new AssertionError("Il libro non è stato aggiunto correttamente."));

        

        int sizeIniziale = model.getLibri().size();

        

        // **Fase 2: Rimozione**

        // Si usa l'oggetto recuperato, che è quello che si trova nella lista.

        model.rimuoviLibro(libroDaRimuovere); 

        

        // **Fase 3: Verifica**

        assertEquals(sizeIniziale - 1, model.getLibri().size(), "La dimensione della lista non è diminuita.");

        

        // Verifica che non sia più presente (cercando per ISBN)

        assertFalse(findLibroByIsbn(model.getLibri(), ISBN).isPresent(), "Il libro non è stato rimosso dalla lista.");

    }



    @Test

    public void testPersistenza() {

        System.out.println("testPersistenza");

        

        model.aggiungiLibro(TITOLO, AUTORE, ISBN, ANNO, PREZZO, USURA, COPIE);

        assertEquals(1, model.getLibri().size());

        

        model.salvaSuBinario();

        

        assertTrue(new File(FILE_BINARIO).exists());

        

        TabellaLibroModel nuovoModel = new TabellaLibroModel(); // Carica da binario nel costruttore

        

        ObservableList<Libro> libriCaricati = nuovoModel.getLibri();

        assertEquals(1, libriCaricati.size());

        

        // CORREZIONE: Trova l'oggetto caricato tramite ISBN.

        Libro libroCaricato = findLibroByIsbn(libriCaricati, ISBN)

                                    .orElseThrow(() -> new AssertionError("Il libro non è stato caricato dal binario."));

        

        assertEquals(TITOLO, libroCaricato.getTitolo(), "Titolo non corrisponde dopo la deserializzazione.");

        assertEquals(PREZZO, libroCaricato.getPrezzo(), DELTA, "Prezzo non corrisponde dopo la deserializzazione.");

    }

    

    @Test

    public void testCostruttoreSenzaFile() {

        System.out.println("testCostruttoreSenzaFile");

        

        // CORREZIONE: Grazie al cleanupFile() in @BeforeEach, questo dovrebbe passare ora.

        assertFalse(new File(FILE_BINARIO).exists(), "Pre-condizione: Il file binario non deve esistere.");

        

        TabellaLibroModel modelSenzaFile = new TabellaLibroModel();

        

        assertNotNull(modelSenzaFile.getLibri());

        assertTrue(modelSenzaFile.getLibri().isEmpty());

    }

}