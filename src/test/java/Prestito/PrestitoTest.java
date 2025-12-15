/**
 * @file PrestitoTest.java
 * @brief Questo file contiene i test unitari per la classe Prestito che gestisce l'associazione tra Utente e Libro
 *
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */

package Prestito;

import Libro.Libro;
import Utente.Utente;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PrestitoTest {
    
    private final String NOME_UTENTE = "Mario";
    private final String COGNOME_UTENTE = "Rossi";
    private final String MATRICOLA_UTENTE = "M12345";
    private final String EMAIL_UTENTE = "mario.rossi@mail.it";
    private final LocalDate ISCRIZIONE_UTENTE = LocalDate.of(2024, 1, 1);
    
    private final String TITOLO_LIBRO = "Codice Pulito";
    private final String ISBN_LIBRO = "978-0132350884";
    private final LocalDate DATA_SCADENZA_TEST = LocalDate.of(2025, 12, 13).plusDays(30);
    
    /**
     * @brief Metodo helper per creare un Utente di test standardizzato
     *
     * @pre Nessuna
     * @post Restituisce un oggetto Utente popolato con le costanti definite
     *
     * @return Un'istanza valida di Utente
     */
    private Utente creaTestUtente() {
        return new Utente(NOME_UTENTE, COGNOME_UTENTE, MATRICOLA_UTENTE, EMAIL_UTENTE, ISCRIZIONE_UTENTE);
    }
    
    /**
     * @brief Metodo helper per creare un Libro di test standardizzato
     *
     * @pre Nessuna
     * @post Restituisce un oggetto Libro popolato con le costanti definite
     *
     * @return Un'istanza valida di Libro
     */
    private Libro creaTestLibro() {
        return new Libro(TITOLO_LIBRO, "Robert C. Martin", ISBN_LIBRO, 2008, 30.0, "Ottimo", 2);
    }
    
    /**
     * @brief Metodo helper per creare un Prestito di test
     *
     * @pre Devono essere forniti un Utente, un Libro e una data valida
     * @post Restituisce un oggetto Prestito che associa l'utente al libro
     * @param[in] utente L'utente che richiede il prestito
     * @param[in] libro Il libro oggetto del prestito
     * @param[in] scadenza La data di scadenza del prestito
     *
     * @return Un'istanza valida di Prestito
     */
    private Prestito creaTestPrestito(Utente utente, Libro libro, LocalDate scadenza) {
        return new Prestito(utente, libro, scadenza);
    }

    /**
     * @brief Test del costruttore e dei metodi getter per verificare la corretta inizializzazione
     *
     * @pre Nessuna precondizione
     * @post L'oggetto viene creato non nullo e i valori restituiti corrispondono a quelli attesi
     *
     * @return void
     */
    @Test
    public void testCostruttoreEGetters() {
        System.out.println("Test: Costruttore e Getters");
        
        Utente utenteTest = creaTestUtente();
        Libro libroTest = creaTestLibro();
        Prestito istanza = creaTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
        assertNotNull(istanza);
        
        assertEquals(NOME_UTENTE, istanza.getNome());
        assertEquals(COGNOME_UTENTE, istanza.getCognome());
        assertEquals(TITOLO_LIBRO, istanza.getTitolo());
        assertEquals(ISBN_LIBRO, istanza.getIsbn());
        assertEquals(DATA_SCADENZA_TEST, istanza.getDataDiScadenza());
    }

    /**
     * @brief Test dei setter relativi all'Utente associato al prestito
     *
     * @pre Esiste un'istanza valida di Prestito
     * @post I dati dell'utente vengono aggiornati sia nel prestito che nell'oggetto Utente originale
     *
     * @return void
     */
    @Test
    public void testSettersUtente() {
        System.out.println("Test: setNome / setCognome");
        
        Utente utenteTest = creaTestUtente();
        Libro libroTest = creaTestLibro();
        Prestito istanza = creaTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
        String nuovoNome = "Giuseppe";
        String nuovoCognome = "Verdi";

        istanza.setNome(nuovoNome);
        istanza.setCognome(nuovoCognome);

        assertEquals(nuovoNome, istanza.getNome());
        assertEquals(nuovoCognome, istanza.getCognome());
        
        // Verifica che l'oggetto Utente originale sia stato modificato
        assertEquals(nuovoNome, utenteTest.getNome());
    }
    
    /**
     * @brief Test dei setter relativi al Libro associato al prestito
     *
     * @pre Esiste un'istanza valida di Prestito
     * @post I dati del libro vengono aggiornati sia nel prestito che nell'oggetto Libro originale
     *
     * @return void
     */
    @Test
    public void testSettersLibro() {
        System.out.println("Test: setTitolo / setIsbn");
        
        Utente utenteTest = creaTestUtente();
        Libro libroTest = creaTestLibro();
        Prestito istanza = creaTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
        String nuovoTitolo = "Il Cigno Nero";
        String nuovoIsbn = "978-8833917410";

        istanza.setTitolo(nuovoTitolo);
        istanza.setIsbn(nuovoIsbn);

        assertEquals(nuovoTitolo, istanza.getTitolo());
        assertEquals(nuovoIsbn, istanza.getIsbn());
        
        // Verifica che l'oggetto Libro originale sia stato modificato
        assertEquals(nuovoTitolo, libroTest.getTitolo());
    }

    /**
     * @brief Test della modifica della data di scadenza del prestito
     *
     * @pre Esiste un'istanza valida di Prestito con una data iniziale
     * @post La data di scadenza viene aggiornata correttamente al nuovo valore
     *
     * @return void
     */
    @Test
    public void testSetDataDiScadenza() {
        System.out.println("Test: setDataDiScadenza");
        
        Utente utenteTest = creaTestUtente();
        Libro libroTest = creaTestLibro();
        Prestito istanza = creaTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
        LocalDate nuovaData = DATA_SCADENZA_TEST.plusYears(1);
        istanza.setDataDiScadenza(nuovaData);
        
        assertEquals(nuovaData, istanza.getDataDiScadenza());
    }
    
    /**
     * @brief Test del metodo equals per verificare l'uguaglianza tra prestiti
     *
     * @pre Vengono create istanze diverse con combinazioni di Nome Utente e ISBN uguali o diversi
     * @post Restituisce true se Nome Utente e ISBN corrispondono, false altrimenti
     *
     * @return void
     */
    @Test
    public void testEquals() {
        System.out.println("Test: equals() (Nome Utente E ISBN Libro)");
        
        Utente utenteTest = creaTestUtente();
        Libro libroTest = creaTestLibro();
        Prestito istanza = creaTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
        Utente utenteUgualeNome = new Utente(NOME_UTENTE, "Bianchi", "M00000", "altro@mail.it", LocalDate.now());
        Libro libroUgualeIsbn = new Libro("Titolo Diverso", "Autore X", ISBN_LIBRO, 1900, 1.0, "Pessimo", 1);
        Prestito prestitoUguale = new Prestito(utenteUgualeNome, libroUgualeIsbn, DATA_SCADENZA_TEST.plusDays(10));
        
        Utente utenteDiversoNome = new Utente("Marco", COGNOME_UTENTE, MATRICOLA_UTENTE, EMAIL_UTENTE, ISCRIZIONE_UTENTE);
        Prestito prestitoDiversoNome = new Prestito(utenteDiversoNome, libroTest, DATA_SCADENZA_TEST);
        
        Libro libroDiversoIsbn = new Libro(TITOLO_LIBRO, "Autore X", "000-0000000000", 2020, 10.0, "Ottimo", 1);
        Prestito prestitoDiversoIsbn = new Prestito(utenteTest, libroDiversoIsbn, DATA_SCADENZA_TEST);

        assertTrue(istanza.equals(istanza));
        assertTrue(istanza.equals(prestitoUguale));
        
        assertFalse(istanza.equals(prestitoDiversoNome));
        
        assertFalse(istanza.equals(prestitoDiversoIsbn));

        assertFalse(istanza.equals(null));
        assertFalse(istanza.equals("Sono una Stringa"));
    }

    /**
     * @brief Test del metodo toString per la rappresentazione testuale dell'oggetto
     *
     * @pre L'oggetto Prestito e i suoi componenti (Utente, Libro) sono inizializzati
     * @post La stringa generata deve contenere la formattazione corretta dei dati interni
     *
     * @return void
     */
    @Test
    public void testToString() {
        System.out.println("Test: toString()");
        
        Utente utenteTest = creaTestUtente();
        Libro libroTest = creaTestLibro();
        Prestito istanza = creaTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
        String utenteString = "Studente{" +
                "nome='" + NOME_UTENTE + '\'' +
                ", cognome='" + COGNOME_UTENTE + '\'' +
                ", matricola='" + MATRICOLA_UTENTE + '\'' +
                ", email='" + EMAIL_UTENTE + '\'' +
                ", iscrizione=" + ISCRIZIONE_UTENTE +
                ", libriInPrestito=" + utenteTest.getLibriInPrestito() +
                '}';
        
        String libroString = libroTest.toString();

        String expResult = "Prestito{" +
                "utente=" + utenteString +
                ", libro=" + libroString +
                ", dataDiScadenza=" + DATA_SCADENZA_TEST +
                '}';
        
        String result = istanza.toString();
        
        assertEquals(expResult, result);
    }
}