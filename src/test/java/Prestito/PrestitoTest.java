package Prestito;

import Libro.Libro; // Assumere che questa classe sia disponibile
import Utente.Utente; // Assumere che questa classe sia disponibile
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class PrestitoTest {
    
    // Oggetti Mock e Dati di test
    private Utente utenteTest;
    private Libro libroTest;
    private LocalDate dataScadenzaTest;
    private Prestito instance;

    // Dati specifici per il setup di Utente e Libro
    private final String NOME_UTENTE = "Mario";
    private final String COGNOME_UTENTE = "Rossi";
    private final String MATRICOLA_UTENTE = "M12345";
    private final String EMAIL_UTENTE = "mario.rossi@mail.it";
    private final LocalDate ISCRIZIONE_UTENTE = LocalDate.of(2024, 1, 1);
    
    private final String TITOLO_LIBRO = "Codice Pulito";
    private final String ISBN_LIBRO = "978-0132350884";
    private final LocalDate DATA_SCADENZA_TEST = LocalDate.of(2025, 12, 13).plusDays(30);
    private static final double DELTA = 0.001;

    @BeforeEach
    public void setUp() {
        // Setup di Utente con il costruttore completo
        utenteTest = new Utente(NOME_UTENTE, COGNOME_UTENTE, MATRICOLA_UTENTE, EMAIL_UTENTE, ISCRIZIONE_UTENTE); 
        
        // Setup di Libro (come precedentemente definito: titolo, autore, isbn, anno, prezzo, usura, numCopie)
        libroTest = new Libro(TITOLO_LIBRO, "Robert C. Martin", ISBN_LIBRO, 2008, 30.0, "Ottimo", 2);
        
        dataScadenzaTest = DATA_SCADENZA_TEST;
        
        instance = new Prestito(utenteTest, libroTest, dataScadenzaTest);
    }

    @Test
    public void testCostruttoreAndGetters() {
        System.out.println("Test: Costruttore e Getters");
        
        assertNotNull(instance);
        
        // Test Getters di convenienza (verificano che Utente e Libro siano accessibili)
        assertEquals(NOME_UTENTE, instance.getNome());
        assertEquals(COGNOME_UTENTE, instance.getCognome());
        assertEquals(TITOLO_LIBRO, instance.getTitolo());
        assertEquals(ISBN_LIBRO, instance.getIsbn());
        assertEquals(DATA_SCADENZA_TEST, instance.getDataDiScadenza());
    }

    @Test
    public void testSettersUtente() {
        System.out.println("Test: setNome / setCognome");
        
        String nuovoNome = "Giuseppe";
        String nuovoCognome = "Verdi";

        instance.setNome(nuovoNome);
        instance.setCognome(nuovoCognome);

        // Verifica che Utente interno sia stato modificato
        assertEquals(nuovoNome, instance.getNome());
        assertEquals(nuovoCognome, instance.getCognome());
        
        // Verifica effetto collaterale su utenteTest (importante per Utente.equals() in Prestito.equals())
        assertEquals(nuovoNome, utenteTest.getNome());
    }
    
    @Test
    public void testSettersLibro() {
        System.out.println("Test: setTitolo / setIsbn");
        
        String nuovoTitolo = "Il Cigno Nero";
        String nuovoIsbn = "978-8833917410";

        instance.setTitolo(nuovoTitolo);
        instance.setIsbn(nuovoIsbn);

        // Verifica che Libro interno sia stato modificato
        assertEquals(nuovoTitolo, instance.getTitolo());
        assertEquals(nuovoIsbn, instance.getIsbn());
        
        // Verifica effetto collaterale su libroTest
        assertEquals(nuovoTitolo, libroTest.getTitolo());
    }

    @Test
    public void testSetDataDiScadenza() {
        System.out.println("Test: setDataDiScadenza");
        
        LocalDate nuovaData = DATA_SCADENZA_TEST.plusYears(1);
        instance.setDataDiScadenza(nuovaData);
        
        assertEquals(nuovaData, instance.getDataDiScadenza());
    }
    
    /**
     * Test del metodo equals. Prestito.equals() si basa su (Nome Utente E ISBN Libro).
     */
    @Test
    public void testEquals() {
        System.out.println("Test: equals() (Nome Utente E ISBN Libro)");
        
        // Prestito 1 (Uguale): Stesso Nome Utente, Stesso ISBN, Dettagli Utente/Libro/Data diversi.
        // Poiché Utente.equals() si basa solo sul Nome, Utente(NOME_UTENTE,...) è "uguale" a utenteTest.
        Utente utenteUgualeNome = new Utente(NOME_UTENTE, "Bianchi", "M00000", "altro@mail.it", LocalDate.now());
        Libro libroUgualeIsbn = new Libro("Titolo Diverso", "Autore X", ISBN_LIBRO, 1900, 1.0, "Pessimo", 1);
        Prestito prestitoUguale = new Prestito(utenteUgualeNome, libroUgualeIsbn, DATA_SCADENZA_TEST.plusDays(10));
        
        // Prestito 2 (Diverso per Nome Utente): Nome diverso, ISBN uguale.
        Utente utenteDiversoNome = new Utente("Marco", COGNOME_UTENTE, MATRICOLA_UTENTE, EMAIL_UTENTE, ISCRIZIONE_UTENTE);
        Prestito prestitoDiversoNome = new Prestito(utenteDiversoNome, libroTest, DATA_SCADENZA_TEST);
        
        // Prestito 3 (Diverso per ISBN): Nome uguale, ISBN diverso.
        Libro libroDiversoIsbn = new Libro(TITOLO_LIBRO, "Autore X", "000-0000000000", 2020, 10.0, "Ottimo", 1);
        Prestito prestitoDiversoIsbn = new Prestito(utenteTest, libroDiversoIsbn, DATA_SCADENZA_TEST);

        // 1. Uguaglianza con se stesso
        assertTrue(instance.equals(instance));

        // 2. Uguaglianza (stesso Nome Utente E stesso ISBN)
        assertTrue(instance.equals(prestitoUguale));
        
        // 3. Diversità: Nome Utente diverso
        assertFalse(instance.equals(prestitoDiversoNome));
        
        // 4. Diversità: ISBN diverso
        assertFalse(instance.equals(prestitoDiversoIsbn));

        // 5. Confronto con null e classe diversa
        assertFalse(instance.equals(null));
        assertFalse(instance.equals("Sono una Stringa"));
    }

    /**
     * Test del metodo toString. Dipende da Utente.toString() (che usa "Studente") e Libro.toString().
     */
    @Test
    public void testToString() {
        System.out.println("Test: toString()");
        
        // Stringa Utente (deve usare "Studente{..." come da Utente.toString() fornito)
        String utenteString = "Studente{" +
                "nome='" + NOME_UTENTE + '\'' +
                ", cognome='" + COGNOME_UTENTE + '\'' +
                ", matricola='" + MATRICOLA_UTENTE + '\'' +
                ", email='" + EMAIL_UTENTE + '\'' +
                ", iscrizione=" + ISCRIZIONE_UTENTE +
                ", libriInPrestito=" + utenteTest.getLibriInPrestito() + // Sarà 0
                '}';
        
        // Stringa Libro
        String libroString = libroTest.toString(); // Assumendo che usi il formato "Libro{..."

        String expResult = "Prestito{" +
                "utente=" + utenteString +
                ", libro=" + libroString +
                ", dataDiScadenza=" + DATA_SCADENZA_TEST +
                '}';
        
        String result = instance.toString();
        
        assertEquals(expResult, result);
    }
}