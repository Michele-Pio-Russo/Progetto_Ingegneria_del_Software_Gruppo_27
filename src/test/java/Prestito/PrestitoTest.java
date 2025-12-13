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
    
    private Utente createTestUtente() {
        return new Utente(NOME_UTENTE, COGNOME_UTENTE, MATRICOLA_UTENTE, EMAIL_UTENTE, ISCRIZIONE_UTENTE);
    }
    
    private Libro createTestLibro() {
        return new Libro(TITOLO_LIBRO, "Robert C. Martin", ISBN_LIBRO, 2008, 30.0, "Ottimo", 2);
    }
    
    private Prestito createTestPrestito(Utente utente, Libro libro, LocalDate scadenza) {
        return new Prestito(utente, libro, scadenza);
    }

    @Test
    public void testCostruttoreAndGetters() {
        System.out.println("Test: Costruttore e Getters");
        
        Utente utenteTest = createTestUtente();
        Libro libroTest = createTestLibro();
        Prestito instance = createTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
        assertNotNull(instance);
        
        assertEquals(NOME_UTENTE, instance.getNome());
        assertEquals(COGNOME_UTENTE, instance.getCognome());
        assertEquals(TITOLO_LIBRO, instance.getTitolo());
        assertEquals(ISBN_LIBRO, instance.getIsbn());
        assertEquals(DATA_SCADENZA_TEST, instance.getDataDiScadenza());
    }

    @Test
    public void testSettersUtente() {
        System.out.println("Test: setNome / setCognome");
        
        Utente utenteTest = createTestUtente();
        Libro libroTest = createTestLibro();
        Prestito instance = createTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
        String nuovoNome = "Giuseppe";
        String nuovoCognome = "Verdi";

        instance.setNome(nuovoNome);
        instance.setCognome(nuovoCognome);

        assertEquals(nuovoNome, instance.getNome());
        assertEquals(nuovoCognome, instance.getCognome());
        
        // Verifica che l'oggetto Utente originale sia stato modificato
        assertEquals(nuovoNome, utenteTest.getNome());
    }
    
    @Test
    public void testSettersLibro() {
        System.out.println("Test: setTitolo / setIsbn");
        
        Utente utenteTest = createTestUtente();
        Libro libroTest = createTestLibro();
        Prestito instance = createTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
        String nuovoTitolo = "Il Cigno Nero";
        String nuovoIsbn = "978-8833917410";

        instance.setTitolo(nuovoTitolo);
        instance.setIsbn(nuovoIsbn);

        assertEquals(nuovoTitolo, instance.getTitolo());
        assertEquals(nuovoIsbn, instance.getIsbn());
        
        // Verifica che l'oggetto Libro originale sia stato modificato
        assertEquals(nuovoTitolo, libroTest.getTitolo());
    }

    @Test
    public void testSetDataDiScadenza() {
        System.out.println("Test: setDataDiScadenza");
        
        Utente utenteTest = createTestUtente();
        Libro libroTest = createTestLibro();
        Prestito instance = createTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
        LocalDate nuovaData = DATA_SCADENZA_TEST.plusYears(1);
        instance.setDataDiScadenza(nuovaData);
        
        assertEquals(nuovaData, instance.getDataDiScadenza());
    }
    
    @Test
    public void testEquals() {
        System.out.println("Test: equals() (Nome Utente E ISBN Libro)");
        
        Utente utenteTest = createTestUtente();
        Libro libroTest = createTestLibro();
        Prestito instance = createTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
        Utente utenteUgualeNome = new Utente(NOME_UTENTE, "Bianchi", "M00000", "altro@mail.it", LocalDate.now());
        Libro libroUgualeIsbn = new Libro("Titolo Diverso", "Autore X", ISBN_LIBRO, 1900, 1.0, "Pessimo", 1);
        Prestito prestitoUguale = new Prestito(utenteUgualeNome, libroUgualeIsbn, DATA_SCADENZA_TEST.plusDays(10));
        
        Utente utenteDiversoNome = new Utente("Marco", COGNOME_UTENTE, MATRICOLA_UTENTE, EMAIL_UTENTE, ISCRIZIONE_UTENTE);
        Prestito prestitoDiversoNome = new Prestito(utenteDiversoNome, libroTest, DATA_SCADENZA_TEST);
        
        Libro libroDiversoIsbn = new Libro(TITOLO_LIBRO, "Autore X", "000-0000000000", 2020, 10.0, "Ottimo", 1);
        Prestito prestitoDiversoIsbn = new Prestito(utenteTest, libroDiversoIsbn, DATA_SCADENZA_TEST);

        assertTrue(instance.equals(instance));
        assertTrue(instance.equals(prestitoUguale));
        
        assertFalse(instance.equals(prestitoDiversoNome));
        
        assertFalse(instance.equals(prestitoDiversoIsbn));

        assertFalse(instance.equals(null));
        assertFalse(instance.equals("Sono una Stringa"));
    }

    @Test
    public void testToString() {
        System.out.println("Test: toString()");
        
        Utente utenteTest = createTestUtente();
        Libro libroTest = createTestLibro();
        Prestito instance = createTestPrestito(utenteTest, libroTest, DATA_SCADENZA_TEST);
        
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
        
        String result = instance.toString();
        
        assertEquals(expResult, result);
    }
}