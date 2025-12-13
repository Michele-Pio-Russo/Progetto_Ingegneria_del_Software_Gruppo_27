/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Libro;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author miche
 */
public class LibroTest {
    
    public LibroTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getTitolo method, of class Libro.
     */
    @Test
    public void testGetTitolo() {
        System.out.println("getTitolo");
        Libro instance = null;
        String expResult = "";
        String result = instance.getTitolo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAutore method, of class Libro.
     */
    @Test
    public void testGetAutore() {
        System.out.println("getAutore");
        Libro instance = null;
        String expResult = "";
        String result = instance.getAutore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIsbn method, of class Libro.
     */
    @Test
    public void testGetIsbn() {
        System.out.println("getIsbn");
        Libro instance = null;
        String expResult = "";
        String result = instance.getIsbn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCopie method, of class Libro.
     */
    @Test
    public void testGetCopie() {
        System.out.println("getCopie");
        Libro instance = null;
        int expResult = 0;
        int result = instance.getCopie();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAnnoPubblicazione method, of class Libro.
     */
    @Test
    public void testGetAnnoPubblicazione() {
        System.out.println("getAnnoPubblicazione");
        Libro instance = null;
        int expResult = 0;
        int result = instance.getAnnoPubblicazione();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrezzo method, of class Libro.
     */
    @Test
    public void testGetPrezzo() {
        System.out.println("getPrezzo");
        Libro instance = null;
        double expResult = 0.0;
        double result = instance.getPrezzo();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsura method, of class Libro.
     */
    @Test
    public void testGetUsura() {
        System.out.println("getUsura");
        Libro instance = null;
        String expResult = "";
        String result = instance.getUsura();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTitolo method, of class Libro.
     */
    @Test
    public void testSetTitolo() {
        System.out.println("setTitolo");
        String titolo = "";
        Libro instance = null;
        instance.setTitolo(titolo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAutore method, of class Libro.
     */
    @Test
    public void testSetAutore() {
        System.out.println("setAutore");
        String autore = "";
        Libro instance = null;
        instance.setAutore(autore);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIsbn method, of class Libro.
     */
    @Test
    public void testSetIsbn() {
        System.out.println("setIsbn");
        String isbn = "";
        Libro instance = null;
        instance.setIsbn(isbn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCopie method, of class Libro.
     */
    @Test
    public void testSetCopie() {
        System.out.println("setCopie");
        int numCopie = 0;
        Libro instance = null;
        instance.setCopie(numCopie);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAnnoPubblicazione method, of class Libro.
     */
    @Test
    public void testSetAnnoPubblicazione() {
        System.out.println("setAnnoPubblicazione");
        int anno = 0;
        Libro instance = null;
        instance.setAnnoPubblicazione(anno);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrezzo method, of class Libro.
     */
    @Test
    public void testSetPrezzo() {
        System.out.println("setPrezzo");
        double prezzo = 0.0;
        Libro instance = null;
        instance.setPrezzo(prezzo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUsura method, of class Libro.
     */
    @Test
    public void testSetUsura() {
        System.out.println("setUsura");
        String usura = "";
        Libro instance = null;
        instance.setUsura(usura);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Libro.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Libro instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Libro.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Libro instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
