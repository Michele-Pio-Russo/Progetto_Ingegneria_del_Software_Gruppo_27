/**
 * @file TabellaUtenteTest.java
 * @brief Questo file contiene i test unitari per il modello (Model) che gestisce i dati dei prestiti
 *
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */

package Utente;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtenteTest {
    
    private final String NOME_TEST = "Giulia";
    private final String COGNOME_TEST = "Bianchi";
    private final String MATRICOLA_TEST = "M987654";
    private final String EMAIL_TEST = "giulia.bianchi@esempio.it";
    private final LocalDate ISCRIZIONE_TEST = LocalDate.of(2024, 9, 1);
    private final int LIBRI_IN_PRESTITO_BASE = 0; 

    private Utente createTestUser() {
        return new Utente(NOME_TEST, COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);
    }

    @Test
    public void testCostruttoreAndGetters() {
        System.out.println("Test: Costruttore e Getters");
        
        Utente instance = createTestUser();
        
        assertNotNull(instance);
        assertEquals(NOME_TEST, instance.getNome());
        assertEquals(COGNOME_TEST, instance.getCognome());
        assertEquals(MATRICOLA_TEST, instance.getMatricola());
        assertEquals(EMAIL_TEST, instance.getEmail());
        assertEquals(ISCRIZIONE_TEST, instance.getIscrizione());
        
        assertEquals(LIBRI_IN_PRESTITO_BASE, instance.getLibriInPrestito());
    }

    @Test
    public void testSetters() {
        System.out.println("Test: Setters");
        
        Utente instance = createTestUser();
        
        String nuovoNome = "Marco";
        String nuovoCognome = "Verdi";
        String nuovaMatricola = "M111111";
        String nuovaEmail = "marco.verdi@nuova.it";
        LocalDate nuovaIscrizione = LocalDate.of(2025, 1, 1);
        int nuoviLibri = 3;
        
        instance.setNome(nuovoNome);
        instance.setCognome(nuovoCognome);
        instance.setMatricola(nuovaMatricola);
        instance.setEmail(nuovaEmail);
        instance.setIscrizione(nuovaIscrizione);
        instance.setLibriInPrestito(nuoviLibri);
        
        assertEquals(nuovoNome, instance.getNome());
        assertEquals(nuovoCognome, instance.getCognome());
        assertEquals(nuovaMatricola, instance.getMatricola());
        assertEquals(nuovaEmail, instance.getEmail());
        assertEquals(nuovaIscrizione, instance.getIscrizione());
        assertEquals(nuoviLibri, instance.getLibriInPrestito());
        
        instance.setLibriInPrestito(0);
        assertEquals(0, instance.getLibriInPrestito());
    }

    @Test
    public void testEquals() {
        System.out.println("Test: equals() (Basato sul Nome)");
        
        Utente instance = createTestUser();
        
        Utente utenteUguale = new Utente(NOME_TEST, "Nero", "Z000000", "diverso@mail.it", LocalDate.now().minusYears(1));
        
        Utente utenteDiverso = new Utente("Marco", COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);

        assertTrue(instance.equals(instance));
        assertTrue(instance.equals(utenteUguale));
        
        assertFalse(instance.equals(utenteDiverso));

        assertFalse(instance.equals(null));
        
        Object altraClasse = "Sono una Stringa";
        assertFalse(instance.equals(altraClasse));
    }

    @Test
    public void testToString() {
        System.out.println("Test: toString()");
        
        Utente instance = createTestUser();
        
        int libriCorrenti = 1;
        instance.setLibriInPrestito(libriCorrenti);
        
        String expResult = "Studente{" +
                "nome='" + NOME_TEST + '\'' +
                ", cognome='" + COGNOME_TEST + '\'' +
                ", matricola='" + MATRICOLA_TEST + '\'' +
                ", email='" + EMAIL_TEST + '\'' +
                ", iscrizione=" + ISCRIZIONE_TEST +
                ", libriInPrestito=" + libriCorrenti +
                '}';
        
        String result = instance.toString();
        
        assertEquals(expResult, result);
    }
}