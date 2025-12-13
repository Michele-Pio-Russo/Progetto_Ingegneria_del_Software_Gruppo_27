package Utente;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe di test per la classe Utente, basata sull'implementazione originale fornita.
 * * @author miche
 */
public class UtenteTest {
    
    // Dati di test
    private final String NOME_TEST = "Giulia";
    private final String COGNOME_TEST = "Bianchi";
    private final String MATRICOLA_TEST = "M987654";
    private final String EMAIL_TEST = "giulia.bianchi@esempio.it";
    private final LocalDate ISCRIZIONE_TEST = LocalDate.of(2024, 9, 1);
    private final int LIBRI_IN_PRESTITO_BASE = 0; // Inizializzato a 0 nel costruttore
    
    private Utente instance;

    /**
     * SetUp esegue l'inizializzazione prima di ogni test.
     */
    @BeforeEach
    public void setUp() {
        instance = new Utente(NOME_TEST, COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);
    }

    /**
     * Test combinato del Costruttore e dei Getter.
     */
    @Test
    public void testCostruttoreAndGetters() {
        System.out.println("Test: Costruttore e Getters");
        
        // Verifica inizializzazione del costruttore
        assertNotNull(instance, "L'istanza di Utente non deve essere null.");
        
        // Verifica Getters
        assertEquals(NOME_TEST, instance.getNome(), "getNome non corrisponde.");
        assertEquals(COGNOME_TEST, instance.getCognome(), "getCognome non corrisponde.");
        assertEquals(MATRICOLA_TEST, instance.getMatricola(), "getMatricola non corrisponde.");
        assertEquals(EMAIL_TEST, instance.getEmail(), "getEmail non corrisponde.");
        assertEquals(ISCRIZIONE_TEST, instance.getIscrizione(), "getIscrizione non corrisponde.");
        
        // Verifica che libriInPrestito sia inizializzato a 0
        assertEquals(LIBRI_IN_PRESTITO_BASE, instance.getLibriInPrestito(), "libriInPrestito non è 0 di default.");
    }

    /**
     * Test dei Setters.
     */
    @Test
    public void testSetters() {
        System.out.println("Test: Setters");
        
        // Nuovi dati di test
        String nuovoNome = "Marco";
        String nuovoCognome = "Verdi";
        String nuovaMatricola = "M111111";
        String nuovaEmail = "marco.verdi@nuova.it";
        LocalDate nuovaIscrizione = LocalDate.of(2025, 1, 1);
        int nuoviLibri = 3;
        
        // Impostazione dei nuovi valori
        instance.setNome(nuovoNome);
        instance.setCognome(nuovoCognome);
        instance.setMatricola(nuovaMatricola);
        instance.setEmail(nuovaEmail);
        instance.setIscrizione(nuovaIscrizione);
        instance.setLibriInPrestito(nuoviLibri);
        
        // Verifica che i valori siano stati aggiornati tramite i Getter
        assertEquals(nuovoNome, instance.getNome(), "setNome fallito.");
        assertEquals(nuovoCognome, instance.getCognome(), "setCognome fallito.");
        assertEquals(nuovaMatricola, instance.getMatricola(), "setMatricola fallito.");
        assertEquals(nuovaEmail, instance.getEmail(), "setEmail fallito.");
        assertEquals(nuovaIscrizione, instance.getIscrizione(), "setIscrizione fallito.");
        assertEquals(nuoviLibri, instance.getLibriInPrestito(), "setLibriInPrestito fallito.");
        
        // Test setLibriInPrestito a zero
        instance.setLibriInPrestito(0);
        assertEquals(0, instance.getLibriInPrestito(), "setLibriInPrestito a 0 fallito.");
    }

    /**
     * Test del metodo equals (basato sul NOME).
     */
    @Test
    public void testEquals() {
        System.out.println("Test: equals() (Basato sul Nome)");
        
        // Utente Uguale: Stesso Nome, tutti gli altri campi diversi
        Utente utenteUguale = new Utente(NOME_TEST, "Nero", "Z000000", "diverso@mail.it", LocalDate.now().minusYears(1));
        
        // Utente Diverso: Nome diverso, tutti gli altri campi uguali (Matricola inclusa, sebbene irrilevante per equals)
        Utente utenteDiverso = new Utente("Marco", COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);

        // 1. Uguaglianza con se stesso
        assertTrue(instance.equals(instance), "equals() fallito: stesso oggetto.");

        // 2. Uguaglianza basata sul Nome (come nell'implementazione originale)
        assertTrue(instance.equals(utenteUguale), "equals() fallito: stesso nome, matricola diversa (atteso True).");
        
        // 3. Diversità: Nome diverso
        assertFalse(instance.equals(utenteDiverso), "equals() fallito: nome diverso.");

        // 4. Confronto con null
        assertFalse(instance.equals(null), "equals() fallito: confronto con null.");
        
        // 5. Confronto con un oggetto di classe diversa
        Object altraClasse = "Sono una Stringa";
        assertFalse(instance.equals(altraClasse), "equals() fallito: confronto con classe diversa.");
    }

    /**
     * Test del metodo toString (basato su "Studente").
     */
    @Test
    public void testToString() {
        System.out.println("Test: toString()");
        
        int libriCorrenti = 1;
        instance.setLibriInPrestito(libriCorrenti);
        
        // NOTA: La stringa attesa usa "Studente" come da implementazione originale.
        String expResult = "Studente{" +
                "nome='" + NOME_TEST + '\'' +
                ", cognome='" + COGNOME_TEST + '\'' +
                ", matricola='" + MATRICOLA_TEST + '\'' +
                ", email='" + EMAIL_TEST + '\'' +
                ", iscrizione=" + ISCRIZIONE_TEST +
                ", libriInPrestito=" + libriCorrenti +
                '}';
        
        String result = instance.toString();
        
        assertEquals(expResult, result, "Il metodo toString() non produce il formato 'Studente' atteso.");
    }
}