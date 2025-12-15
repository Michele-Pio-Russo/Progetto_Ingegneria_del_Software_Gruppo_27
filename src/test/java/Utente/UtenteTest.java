    /**
 * @file TabellaUtenteTest.java
 * @brief Classe di test per la classe Utente
 * 
 * Questa classe verifica il corretto funzionamento dei metodi della classe Utente
 * inclusi costruttore, getter, setter, equals e toString.
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
    
    private final String NOME_TEST = "Giulia";  ///@brief String placeholder utilizzata per contenere il nome da usare nei test
    private final String COGNOME_TEST = "Bianchi";  ///@brief String placeholder utilizzata per contenere il cognome da usare nei test
    private final String MATRICOLA_TEST = "M987654";    ///@brief String placeholder utilizzata per contenere la matricola da usare nei test
    private final String EMAIL_TEST = "giulia.bianchi@esempio.it";  ///@brief String placeholder utilizzata per contenere l'email da usare nei test
    private final LocalDate ISCRIZIONE_TEST = LocalDate.of(2024, 9, 1); ///@brief String placeholder utilizzata per contenere la data d'iscrizione da usare nei test
    private final int LIBRI_IN_PRESTITO_BASE = 0;   ///@brief String placeholder utilizzata per contenere il numero di libri in prestito da usare nei test

    /**
     * @brief Metodo di supporto per creare un utente di test
     *
     * Istanzia un oggetto Utente precompilato con le costanti definite nella classe di test
     * per evitare la duplicazione del codice di inizializzazione.
     *
     * @pre Nessuna precondizione specifica.
     * @post Viene restituito un oggetto Utente valido e inizializzato.
     *
     * @return Utente Un'istanza della classe Utente con dati di test.
     */
    private Utente creaTestUtente() {
        return new Utente(NOME_TEST, COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);
    }

    /**
     * @brief Test del costruttore e dei metodi Getter
     *
     * Verifica che un oggetto Utente venga istanziato correttamente e che i metodi getter
     * restituiscano i valori passati al costruttore.
     *
     * @pre La classe Utente deve essere compilata correttamente.
     * @post Tutti i valori restituiti dai getter devono corrispondere alle costanti di test.
     *
     * @return void
     */
    @Test
    public void testCostruttoreEGetters() {
        System.out.println("Test: Costruttore e Getters");
        
        Utente istanza = creaTestUtente();
        
        assertNotNull(istanza);
        assertEquals(NOME_TEST, istanza.getNome());
        assertEquals(COGNOME_TEST, istanza.getCognome());
        assertEquals(MATRICOLA_TEST, istanza.getMatricola());
        assertEquals(EMAIL_TEST, istanza.getEmail());
        assertEquals(ISCRIZIONE_TEST, istanza.getIscrizione());
        
        assertEquals(LIBRI_IN_PRESTITO_BASE, istanza.getLibriInPrestito());
    }

    /**
     * @brief Test dei metodi Setter
     *
     * Verifica che i metodi setter modifichino correttamente lo stato dell'oggetto Utente.
     *
     * @pre Un oggetto Utente deve essere stato istanziato.
     * @post Gli attributi dell'oggetto devono corrispondere ai nuovi valori impostati.
     *
     * @return void
     */
    
    @Test
    public void testSetters() {
        System.out.println("Test: Setters");
        
        Utente istanza = creaTestUtente();
        
        String nuovoNome = "Marco";
        String nuovoCognome = "Verdi";
        String nuovaMatricola = "M111111";
        String nuovaEmail = "marco.verdi@nuova.it";
        LocalDate nuovaIscrizione = LocalDate.of(2025, 1, 1);
        int nuoviLibri = 3;
        
        istanza.setNome(nuovoNome);
        istanza.setCognome(nuovoCognome);
        istanza.setMatricola(nuovaMatricola);
        istanza.setEmail(nuovaEmail);
        istanza.setIscrizione(nuovaIscrizione);
        istanza.setLibriInPrestito(nuoviLibri);
        
        assertEquals(nuovoNome, istanza.getNome());
        assertEquals(nuovoCognome, istanza.getCognome());
        assertEquals(nuovaMatricola, istanza.getMatricola());
        assertEquals(nuovaEmail, istanza.getEmail());
        assertEquals(nuovaIscrizione, istanza.getIscrizione());
        assertEquals(nuoviLibri, istanza.getLibriInPrestito());
        
        istanza.setLibriInPrestito(0);
        assertEquals(0, istanza.getLibriInPrestito());
    }

    /**
     * @brief Test del metodo equals
     *
     * Verifica la logica di uguaglianza tra due oggetti Utente, basata principalmente sul nome.
     * Testa uguaglianza per riferimento, uguaglianza logica (stesso nome), disuguaglianza (nome diverso),
     * confronto con null e confronto con oggetti di tipo diverso.
     *
     * @pre Due o più istanze di Utente (o null/altri oggetti) per il confronto.
     * @post Il metodo equals deve restituire true solo se il nome coincide, false altrimenti.
     *
     * @return void
     */
    @Test
    public void testEquals() {
        System.out.println("Test: equals() (Basato sul Nome)");
        
        Utente istanza = creaTestUtente();
        
        Utente utenteUguale = new Utente(NOME_TEST, "Nero", "Z000000", "diverso@mail.it", LocalDate.now().minusYears(1));
        Utente utenteDiverso = new Utente("Marco", COGNOME_TEST, MATRICOLA_TEST, EMAIL_TEST, ISCRIZIONE_TEST);

        assertTrue(istanza.equals(istanza));
        assertTrue(istanza.equals(utenteUguale));
        
        assertFalse(istanza.equals(utenteDiverso));

        assertFalse(istanza.equals(null));
        
        Object altraClasse = "Sono una Stringa";
        assertFalse(istanza.equals(altraClasse));
    }

    /**
     * @brief Test del metodo toString
     *
     * Verifica che la stringa generata dal metodo toString rispetti il formato atteso.
     *
     * @pre Un oggetto Utente valido.
     * @post La stringa restituita deve contenere tutti i campi formattati correttamente.
     *
     * @return void
     */
    @Test
    public void testToString() {
        System.out.println("Test: toString()");
        
        Utente istanza = creaTestUtente();
        
        int libriCorrenti = 1;
        istanza.setLibriInPrestito(libriCorrenti);
        
        String expResult = "Studente{" +
                "nome='" + NOME_TEST + '\'' +
                ", cognome='" + COGNOME_TEST + '\'' +
                ", matricola='" + MATRICOLA_TEST + '\'' +
                ", email='" + EMAIL_TEST + '\'' +
                ", iscrizione=" + ISCRIZIONE_TEST +
                ", libriInPrestito=" + libriCorrenti +
                '}';
        
        String result = istanza.toString();
        
        assertEquals(expResult, result, "Il metodo toString() non produce il formato atteso.");
    }
}