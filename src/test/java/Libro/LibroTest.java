package Libro;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibroTest {

    private static final double DELTA = 0.001;
    
    private final String TITOLO_TEST = "Il Signore degli Anelli";
    private final String AUTORE_TEST = "J.R.R. Tolkien";
    private final String ISBN_TEST = "978-8845292613";
    private final int ANNO_TEST = 1954;
    private final double PREZZO_TEST = 18.50;
    private final String USURA_TEST = "Nuovo";
    private final int COPIE_TEST = 5;

    private Libro createTestBook() {
        return new Libro(TITOLO_TEST, AUTORE_TEST, ISBN_TEST, ANNO_TEST, PREZZO_TEST, USURA_TEST, COPIE_TEST);
    }

    @Test
    public void testCostruttoreAndGetters() {
        System.out.println("Test: Costruttore e Getters");
        
        Libro instance = createTestBook();

        assertEquals(TITOLO_TEST, instance.getTitolo(), "Il titolo non corrisponde.");
        assertEquals(AUTORE_TEST, instance.getAutore(), "L'autore non corrisponde.");
        assertEquals(ISBN_TEST, instance.getIsbn(), "L'ISBN non corrisponde.");
        assertEquals(COPIE_TEST, instance.getCopie(), "Il numero di copie non corrisponde.");
        assertEquals(ANNO_TEST, instance.getAnnoPubblicazione(), "L'anno di pubblicazione non corrisponde.");
        assertEquals(PREZZO_TEST, instance.getPrezzo(), DELTA, "Il prezzo non corrisponde.");
        assertEquals(USURA_TEST, instance.getUsura(), "Lo stato di usura non corrisponde.");
    }

    @Test
    public void testSetters() {
        System.out.println("Test: Setters");
        
        Libro instance = createTestBook();
        
        String nuovoTitolo = "Lo Hobbit";
        String nuovoAutore = "Christopher Tolkien";
        String nuovoIsbn = "123-4567890123";
        int nuoveCopie = 10;
        int nuovoAnno = 2000;
        double nuovoPrezzo = 25.99;
        String nuovaUsura = "Usato";
        
        instance.setTitolo(nuovoTitolo);
        instance.setAutore(nuovoAutore);
        instance.setIsbn(nuovoIsbn);
        instance.setCopie(nuoveCopie);
        instance.setAnnoPubblicazione(nuovoAnno);
        instance.setPrezzo(nuovoPrezzo);
        instance.setUsura(nuovaUsura);
        
        assertEquals(nuovoTitolo, instance.getTitolo(), "setTitolo non ha funzionato.");
        assertEquals(nuovoAutore, instance.getAutore(), "setAutore non ha funzionato.");
        assertEquals(nuovoIsbn, instance.getIsbn(), "setIsbn non ha funzionato.");
        assertEquals(nuoveCopie, instance.getCopie(), "setCopie non ha funzionato.");
        assertEquals(nuovoAnno, instance.getAnnoPubblicazione(), "setAnnoPubblicazione non ha funzionato.");
        assertEquals(nuovoPrezzo, instance.getPrezzo(), DELTA, "setPrezzo non ha funzionato.");
        assertEquals(nuovaUsura, instance.getUsura(), "setUsura non ha funzionato.");
    }

    @Test
    public void testEquals() {
        System.out.println("Test: equals()");
        
        Libro libro1 = createTestBook();
        Libro libro2 = createTestBook();
        
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

    @Test
    public void testToString() {
        System.out.println("Test: toString()");
        
        Libro instance = createTestBook();
        
        String expResult = "Libro{" +
                "titolo='" + TITOLO_TEST + '\'' +
                ", autore='" + AUTORE_TEST + '\'' +
                ", isbn='" + ISBN_TEST + '\'' +
                ", numCopie=" + COPIE_TEST +
                ", annoPublicazione=" + ANNO_TEST +
                ", prezzo=" + PREZZO_TEST +
                ", usura='" + USURA_TEST + '\'' +
                '}';
        
        String result = instance.toString();
        
        assertEquals(expResult, result, "Il metodo toString() non produce il formato atteso.");
    }
}