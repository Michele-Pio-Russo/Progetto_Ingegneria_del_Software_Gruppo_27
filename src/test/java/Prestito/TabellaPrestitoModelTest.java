/**
 * @file TabellaPrestitoModelTest.java
 * @brief Questo file contiene i test unitari per il modello (Model) che gestisce i dati dei prestiti
 *
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */

package Prestito;

import Libro.Libro;
import Utente.Utente;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.time.LocalDate;

public class TabellaPrestitoModelTest {
    
    private TabellaPrestitoModel model; /// @brief Istanza del modello da testare
    private final String FILE_BINARIO = "prestiti.bin"; /// @brief Nome del file binario per il test di persistenza

    // Costanti per i dati di test
    private final String NOME_UTENTE = "Mario";         /// @brief Nome dell'utente di test
    private final String COGNOME_UTENTE = "Rossi";      /// @brief Cognome dell'utente di test
    private final String MATRICOLA_UTENTE = "M111";     /// @brief Matricola dell'utente di test
    private final String EMAIL_UTENTE = "m.r@mail.it";  /// @brief Email dell'utente di test
    private final LocalDate ISCRIZIONE_UTENTE = LocalDate.of(2024, 1, 1); /// @brief Data iscrizione utente
    
    private final String TITOLO_LIBRO = "Test Book";      /// @brief Titolo del libro di test
    private final String AUTORE_LIBRO = "Test Author";    /// @brief Autore del libro di test
    private final String ISBN_LIBRO = "978-1111111111";   /// @brief ISBN del libro di test
    private final int ANNO_LIBRO = 2020;                  /// @brief Anno pubblicazione libro
    private final double PREZZO_LIBRO = 25.00;            /// @brief Prezzo libro
    private final String USURA_LIBRO = "Nuovo";           /// @brief Stato usura libro
    private final int COPIE_LIBRO = 3;                    /// @brief Numero copie libro
    
    private Utente utenteTest;        /// @brief Oggetto Utente di supporto per i test
    private Libro libroTest;          /// @brief Oggetto Libro di supporto per i test
    private Prestito prestitoTest;    /// @brief Oggetto Prestito di supporto per i test
    private LocalDate dataScadenzaTest; /// @brief Data di scadenza per il prestito di test

    /**
     * @brief Configurazione dell'ambiente prima di ogni test
     *
     * Inizializza gli oggetti Utente, Libro e Prestito di test, pulisce eventuali file residui
     * e istanzia un nuovo modello vuoto.
     *
     * @pre Nessun file residuo deve interferire con il test
     * @post Il modello è pronto e gli oggetti di test sono inizializzati
     *
     * @return void
     */
    @BeforeEach
    public void setUp() {
        cleanupFile();
        
        utenteTest = new Utente(NOME_UTENTE, COGNOME_UTENTE, MATRICOLA_UTENTE, EMAIL_UTENTE, ISCRIZIONE_UTENTE);
        libroTest = new Libro(TITOLO_LIBRO, AUTORE_LIBRO, ISBN_LIBRO, ANNO_LIBRO, PREZZO_LIBRO, USURA_LIBRO, COPIE_LIBRO);
        dataScadenzaTest = LocalDate.now().plusWeeks(2);
        
        prestitoTest = new Prestito(utenteTest, libroTest, dataScadenzaTest);
        
        model = new TabellaPrestitoModel();
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
     * @brief Test del metodo getter per la lista dei prestiti
     *
     * Verifica che la lista restituita sia valida (non nulla) e inizialmente vuota
     * appena creato il modello.
     *
     * @pre Il modello è appena stato istanziato
     * @post Ritorna una ObservableList non nulla e vuota
     *
     * @return void
     */
    @Test
    public void testGetPrestiti() {
        System.out.println("testGetPrestiti");
        
        ObservableList<Prestito> result = model.getPrestiti();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * @brief Test per l'aggiunta di un nuovo prestito
     *
     * Verifica che l'aggiunta incrementi la dimensione della lista e che l'elemento
     * aggiunto sia effettivamente presente nella collezione.
     *
     * @pre La lista ha una dimensione N
     * @post La lista ha dimensione N+1 e contiene il nuovo prestito
     *
     * @return void
     */
    @Test
    public void testAggiungiPrestito() {
        System.out.println("testAggiungiPrestito");
        
        int sizeIniziale = model.getPrestiti().size();
        
        model.aggiungiPrestito(utenteTest, libroTest, dataScadenzaTest);
        
        assertEquals(sizeIniziale + 1, model.getPrestiti().size());
        assertTrue(model.getPrestiti().contains(prestitoTest));
        
        // Test aggiunta secondo elemento
        Prestito prestitoDuplicato = new Prestito(utenteTest, libroTest, dataScadenzaTest.plusDays(1));
        model.aggiungiPrestito(utenteTest, libroTest, dataScadenzaTest.plusDays(1));
        assertEquals(sizeIniziale + 2, model.getPrestiti().size());
    }

    /**
     * @brief Test per la rimozione di un prestito esistente
     *
     * Verifica che la rimozione decrementi la dimensione della lista e che l'elemento
     * specificato non sia più presente nella collezione.
     *
     * @pre La lista contiene il prestito da rimuovere
     * @post Il prestito è rimosso e la dimensione della lista diminuisce di 1
     *
     * @return void
     */
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

    /**
     * @brief Test della persistenza dei dati (Salvataggio e Caricamento)
     *
     * Verifica che i prestiti salvati su file binario vengano correttamente
     * ricaricati da una nuova istanza del modello, mantenendo l'integrità dei dati.
     *
     * @pre Il modello contiene dei prestiti e viene invocato il salvataggio
     * @post Una nuova istanza del modello carica correttamente i dati dal file creato
     *
     * @return void
     */
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
        
        assertFalse(new File(FILE_BINARIO).exists());
        
        TabellaPrestitoModel modelSenzaFile = new TabellaPrestitoModel();
        
        assertNotNull(modelSenzaFile.getPrestiti());
        assertTrue(modelSenzaFile.getPrestiti().isEmpty());
    }
}