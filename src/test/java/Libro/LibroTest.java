/**
 * @file LibroTest.java
 * @brief Classe di test per la classe Libro
 *
 * Questa classe verifica il corretto funzionamento dei metodi della classe Libro
 * inclusi costruttore, getter, setter, equals e toString.
 *
 * @author Gruppo 27
 * @date 15 Dicembre 2025
 * @version 1.0
 */
package Libro;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibroTest {

    private static final double DELTA = 0.001; ///@brief Tolleranza per il confronto di valori double
    
    private final String TITOLO_TEST = "Il Signore degli Anelli";
    private final String AUTORE_TEST = "J.R.R. Tolkien";
    private final String ISBN_TEST = "978-8845292613";
    private final int ANNO_TEST = 1954;
    private final double PREZZO_TEST = 18.50;
    private final String USURA_TEST = "Nuovo";
    private final int COPIE_TEST = 5;

    /**
     * @brief Metodo di supporto per creare un libro di test
     *
     * Istanzia un oggetto Libro precompilato con le costanti definite nella classe di test
     * per evitare la duplicazione del codice di inizializzazione.
     *
     * @pre Nessuna precondizione specifica.
     * @post Viene restituito un oggetto Libro valido e inizializzato.
     *
     * @return Libro Un'istanza della classe Libro con dati di test.
     */
    private Libro creaTestLibro() {
        return new Libro(TITOLO_TEST, AUTORE_TEST, ISBN_TEST, ANNO_TEST, PREZZO_TEST, USURA_TEST, COPIE_TEST);
    }

    /**
     * @brief Test del costruttore e dei metodi Getter
     *
     * Verifica che un oggetto Libro venga istanziato correttamente e che i metodi getter
     * restituiscano i valori passati al costruttore.
     *
     * @pre La classe Libro deve essere compilata correttamente.
     * @post Tutti i valori restituiti dai getter devono corrispondere alle costanti di test.
     *
     * @return void
     */
    @Test
    public void testCostruttoreEGetters() {
        System.out.println("Test: Costruttore e Getters");
        
        Libro istanza = creaTestLibro();

        assertEquals(TITOLO_TEST, istanza.getTitolo(), "Il titolo non corrisponde.");
        assertEquals(AUTORE_TEST, istanza.getAutore(), "L'autore non corrisponde.");
        assertEquals(ISBN_TEST, istanza.getIsbn(), "L'ISBN non corrisponde.");
        assertEquals(COPIE_TEST, istanza.getCopie(), "Il numero di copie non corrisponde.");
        assertEquals(ANNO_TEST, istanza.getAnnoPubblicazione(), "L'anno di pubblicazione non corrisponde.");
        assertEquals(PREZZO_TEST, istanza.getPrezzo(), DELTA, "Il prezzo non corrisponde.");
        assertEquals(USURA_TEST, istanza.getUsura(), "Lo stato di usura non corrisponde.");
    }

    /**
     * @brief Test dei metodi Setter
     *
     * Verifica che i metodi setter modifichino correttamente lo stato dell'oggetto Libro.
     *
     * @pre Un oggetto Libro deve essere stato istanziato.
     * @post Gli attributi dell'oggetto devono corrispondere ai nuovi valori impostati.
     *
     * @return void
     */
    @Test
    public void testSetters() {
        System.out.println("Test: Setters");
        
        Libro istanza = creaTestLibro();
        
        String nuovoTitolo = "Lo Hobbit";
        String nuovoAutore = "Christopher Tolkien";
        String nuovoIsbn = "123-4567890123";
        int nuoveCopie = 10;
        int nuovoAnno = 2000;
        double nuovoPrezzo = 25.99;
        String nuovaUsura = "Usato";
        
        istanza.setTitolo(nuovoTitolo);
        istanza.setAutore(nuovoAutore);
        istanza.setIsbn(nuovoIsbn);
        istanza.setCopie(nuoveCopie);
        istanza.setAnnoPubblicazione(nuovoAnno);
        istanza.setPrezzo(nuovoPrezzo);
        istanza.setUsura(nuovaUsura);
        
        assertEquals(nuovoTitolo, istanza.getTitolo(), "setTitolo non ha funzionato.");
        assertEquals(nuovoAutore, istanza.getAutore(), "setAutore non ha funzionato.");
        assertEquals(nuovoIsbn, istanza.getIsbn(), "setIsbn non ha funzionato.");
        assertEquals(nuoveCopie, istanza.getCopie(), "setCopie non ha funzionato.");
        assertEquals(nuovoAnno, istanza.getAnnoPubblicazione(), "setAnnoPubblicazione non ha funzionato.");
        assertEquals(nuovoPrezzo, istanza.getPrezzo(), DELTA, "setPrezzo non ha funzionato.");
        assertEquals(nuovaUsura, istanza.getUsura(), "setUsura non ha funzionato.");
    }

    /**
     * @brief Test del metodo equals
     *
     * Verifica la logica di uguaglianza tra due oggetti Libro, basata principalmente sul codice ISBN.
     * Testa uguaglianza per riferimento, uguaglianza logica (stesso ISBN), disuguaglianza (ISBN diverso),
     * confronto con null e confronto con oggetti di tipo diverso.
     *
     * @pre Due o più istanze di Libro (o null/altri oggetti) per il confronto.
     * @post Il metodo equals deve restituire true solo se l'ISBN coincide, false altrimenti.
     *
     * @return void
     */
    @Test
    public void testEquals() {
        System.out.println("Test: equals()");
        
        Libro libro1 = creaTestLibro();
        Libro libro2 = creaTestLibro();
        
        Libro libroDiverso = new Libro("Un altro libro", "Autore B", "000-0000000000", 2020, 10.0, "Ottimo", 1);
        
        assertTrue(libro1.equals(libro1), "equals() fallito: stesso oggetto.");

        assertTrue(libro1.equals(libro2), "equals() fallito: oggetti diversi ma stesso ISBN.");
        
        assertFalse(libro1.equals(libroDiverso), "equals() fallito: ISBN diversi.");

        libroDiverso.setIsbn(ISBN_TEST);
        libroDiverso.setTitolo("Titolo Modificato");
        assertTrue(libro1.equals(libroDiverso), "equals() fallito: ISBN uguale, altri campi diversi (dovrebbe essere True).");

        assertFalse(libro1.equals(null), "equals() fallito: confronto con null.");
        
        Object altraClasse = "Sono una Stringa";
        assertFalse(libro1.equals(altraClasse), "equals() fallito: confronto con classe diversa.");
    }

    /**
     * @brief Test del metodo toString
     *
     * Verifica che la stringa generata dal metodo toString rispetti il formato atteso.
     *
     * @pre Un oggetto Libro valido.
     * @post La stringa restituita deve contenere tutti i campi formattati correttamente.
     *
     * @return void
     */
    @Test
    public void testToString() {
        System.out.println("Test: toString()");
        
        Libro istanza = creaTestLibro();
        
        String expResult = "Libro{" +
                "titolo='" + TITOLO_TEST + '\'' +
                ", autore='" + AUTORE_TEST + '\'' +
                ", isbn='" + ISBN_TEST + '\'' +
                ", numCopie=" + COPIE_TEST +
                ", annoPublicazione=" + ANNO_TEST +
                ", prezzo=" + PREZZO_TEST +
                ", usura='" + USURA_TEST + '\'' +
                '}';
        
        String result = istanza.toString();
        
        assertEquals(expResult, result, "Il metodo toString() non produce il formato atteso.");
    }
}