/**
 * @file TabellaLibroModelTest.java
 * @brief Questo file contiene i test unitari per il modello (Model) che gestisce i dati dei libri
 *
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */

package Libro;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.Optional;

public class TabellaLibroModelTest {
    
    private TabellaLibroModel model; /// @brief Istanza del modello da testare
    private final String FILE_BINARIO = "libri.bin"; /// @brief Nome del file binario per il test di persistenza
    
    private final String TITOLO = "Test Book";          /// @brief Titolo del libro di test
    private final String AUTORE = "Test Author";        /// @brief Autore del libro di test
    private final String ISBN = "123-4567890123";       /// @brief ISBN del libro di test
    private final int ANNO = 2020;                      /// @brief Anno pubblicazione libro
    private final double PREZZO = 19.99;                /// @brief Prezzo libro
    private final String USURA = "Ottimo";              /// @brief Stato usura libro
    private final int COPIE = 2;                        /// @brief Numero copie libro
    private static final double DELTA = 0.001;          /// @brief Tolleranza per confronti double
    
    private Libro libroTest; /// @brief Oggetto Libro di supporto per i test (mantenuto per coerenza)

    /**
     * @brief Configurazione dell'ambiente prima di ogni test
     *
     * Inizializza l'oggetto Libro di test, pulisce eventuali file residui
     * e istanzia un nuovo modello vuoto.
     *
     * @pre Nessun file residuo deve interferire con il test
     * @post Il modello è pronto e l'oggetto di test è inizializzato
     *
     * @return void
     */
    @BeforeEach
    public void setUp() {
        cleanupFile(); 
        model = new TabellaLibroModel(); 
        libroTest = new Libro(TITOLO, AUTORE, ISBN, ANNO, PREZZO, USURA, COPIE);
    }

    /**
     * @brief Pulizia dell'ambiente dopo ogni test
     *
     * Rimuove il file binario creato durante l'esecuzione del test per garantire
     * l'idempotenza delle esecuzioni successive.
     *
     * @pre Il test è terminato
     * @post Il file di salvataggio viene eliminato
     *
     * @return void
     */
    @AfterEach
    public void tearDown() {
        cleanupFile();
    }
    
    /**
     * @brief Metodo helper privato per eliminare il file binario
     *
     * Controlla l'esistenza del file e, se presente, lo cancella.
     *
     * @pre Nessuna condizione specifica
     * @post Se il file esisteva, viene cancellato
     *
     * @return void
     */
    private void cleanupFile() {
        File file = new File(FILE_BINARIO);
        if (file.exists()) {
            file.delete(); 
        }
    }
    
    /**
     * @brief Metodo di supporto per trovare un libro tramite ISBN
     * * Cerca un libro all'interno di una lista osservabile utilizzando il suo codice ISBN univoco.
     *
     * @param[in] libri La lista di libri in cui cercare
     * @param[in] isbn L'ISBN del libro da cercare
     * @return Optional<Libro> contenente il libro se trovato, altrimenti vuoto
     */
    private Optional<Libro> findLibroByIsbn(ObservableList<Libro> libri, String isbn) {
        return libri.stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .findFirst();
    }

    /**
     * @brief Test del metodo getter per la lista dei libri
     *
     * Verifica che la lista restituita sia valida (non nulla) e inizialmente vuota
     * appena creato il modello.
     *
     * @pre Il modello è appena stato istanziato e il file binario rimosso
     * @post Ritorna una ObservableList non nulla e vuota
     *
     * @return void
     */
    @Test
    public void testGetLibri() {
        System.out.println("testGetLibri");
        
        ObservableList<Libro> result = model.getLibri();
        
        assertNotNull(result, "La lista dei libri non deve essere null.");
        assertTrue(result.isEmpty(), "La lista deve essere vuota all'inizio (dopo cleanup).");
    }

    /**
     * @brief Test per l'aggiunta di un nuovo libro
     *
     * Verifica che l'aggiunta incrementi la dimensione della lista e che l'elemento
     * aggiunto sia effettivamente presente e con i dati corretti.
     *
     * @pre La lista ha una dimensione N
     * @post La lista ha dimensione N+1 e contiene il nuovo libro con ISBN specificato
     *
     * @return void
     */
    @Test
    public void testAggiungiLibro() {
        System.out.println("testAggiungiLibro");
        
        int sizeIniziale = model.getLibri().size();
        
        model.aggiungiLibro(TITOLO, AUTORE, ISBN, ANNO, PREZZO, USURA, COPIE);
        
        assertEquals(sizeIniziale + 1, model.getLibri().size());
        
        Libro libroAggiunto = findLibroByIsbn(model.getLibri(), ISBN)
                                .orElse(null);
        
        assertNotNull(libroAggiunto, "Il libro con ISBN atteso non è stato trovato.");
        
        assertEquals(TITOLO, libroAggiunto.getTitolo());
    }

    /**
     * @brief Test per la rimozione di un libro esistente
     *
     * Verifica che la rimozione decrementi la dimensione della lista e che l'elemento
     * specificato non sia più presente nella collezione.
     *
     * @pre La lista contiene il libro da rimuovere
     * @post Il libro è rimosso e la dimensione della lista diminuisce di 1
     *
     * @return void
     */
    @Test
    public void testRimuoviLibro() {
        System.out.println("testRimuoviLibro");
        
        model.aggiungiLibro(TITOLO, AUTORE, ISBN, ANNO, PREZZO, USURA, COPIE);
        
        Libro libroDaRimuovere = findLibroByIsbn(model.getLibri(), ISBN)
                                    .orElseThrow(() -> new AssertionError("Il libro non è stato aggiunto correttamente."));
        
        int sizeIniziale = model.getLibri().size();
        
        model.rimuoviLibro(libroDaRimuovere); 
        
        assertEquals(sizeIniziale - 1, model.getLibri().size(), "La dimensione della lista non è diminuita.");
        
        assertFalse(findLibroByIsbn(model.getLibri(), ISBN).isPresent(), "Il libro non è stato rimosso dalla lista.");
    }

    /**
     * @brief Test della persistenza dei dati (Salvataggio e Caricamento)
     *
     * Verifica che i libri salvati su file binario vengano correttamente
     * ricaricati da una nuova istanza del modello, mantenendo l'integrità dei dati.
     *
     * @pre Il modello contiene dei libri e viene invocato il salvataggio
     * @post Una nuova istanza del modello carica correttamente i dati dal file creato
     *
     * @return void
     */
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
        
        Libro libroCaricato = findLibroByIsbn(libriCaricati, ISBN)
                                    .orElseThrow(() -> new AssertionError("Il libro non è stato caricato dal binario."));
        
        assertEquals(TITOLO, libroCaricato.getTitolo(), "Titolo non corrisponde dopo la deserializzazione.");
        assertEquals(PREZZO, libroCaricato.getPrezzo(), DELTA, "Prezzo non corrisponde dopo la deserializzazione.");
    }
    
    /**
     * @brief Test del comportamento del costruttore in assenza di file di salvataggio
     *
     * Verifica che il modello venga inizializzato correttamente (con lista vuota)
     * anche se il file binario non esiste, senza lanciare eccezioni.
     *
     * @pre Il file binario non esiste
     * @post Il modello è inizializzato senza errori e con lista vuota
     *
     * @return void
     */
    @Test
    public void testCostruttoreSenzaFile() {
        System.out.println("testCostruttoreSenzaFile");
        
        assertFalse(new File(FILE_BINARIO).exists(), "Pre-condizione: Il file binario non deve esistere.");
        
        TabellaLibroModel modelSenzaFile = new TabellaLibroModel();
        
        assertNotNull(modelSenzaFile.getLibri());
        assertTrue(modelSenzaFile.getLibri().isEmpty());
    }
}