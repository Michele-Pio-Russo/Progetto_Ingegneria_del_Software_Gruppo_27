/**
 * @file TabellaUtenteModelTest.java
 * @brief Questo file contiene i test unitari per il modello (Model) che gestisce i dati dell'utente
 *
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */

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

    /**
     * @brief Configurazione dell'ambiente prima di ogni test
     *
     * Inizializza l'oggetto Utente di test, pulisce eventuali file residui
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
        
        utenteTest = new Utente(NOME_TEST, COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);
        
        model = new TabellaUtenteModel();
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
     * @brief Test del metodo getter per la lista degli utenti
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
    public void testGetPersone() {
        System.out.println("testGetPersone");
        
        ObservableList<Utente> result = model.getPersone();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * @brief Test per l'aggiunta di un nuovo utente
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
    public void testAggiungiPersona() {
        System.out.println("testAggiungiPersona");
        
        int sizeIniziale = model.getPersone().size();
        
        model.aggiungiPersona(NOME_TEST, COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);
        
        assertEquals(sizeIniziale + 1, model.getPersone().size());
        
        assertTrue(model.getPersone().contains(utenteTest));
    }

    /**
     * @brief Test per la rimozione di un Utente esistente
     *
     * Verifica che la rimozione decrementi la dimensione della lista e che l'elemento
     * specificato non sia più presente nella collezione.
     *
     * @pre La lista contiene l'utente da rimuovere
     * @post l'utente è rimosso e la dimensione della lista diminuisce di 1
     *
     * @return void
     */
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

    /**
     * @brief Test della persistenza dei dati (Salvataggio e Caricamento)
     *
     * Verifica che gli utenti salvati su file binario vengano correttamente
     * ricaricati da una nuova istanza del modello, mantenendo l'integrità dei dati.
     *
     * @pre Il modello contiene degli utenti e viene invocato il salvataggio
     * @post Una nuova istanza del modello carica correttamente i dati dal file creato
     *
     * @return void
     */
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
        
        TabellaUtenteModel modelSenzaFile = new TabellaUtenteModel();
        
        assertNotNull(modelSenzaFile.getPersone());
        assertTrue(modelSenzaFile.getPersone().isEmpty());
    }
}