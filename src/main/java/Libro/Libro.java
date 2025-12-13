/**
 * @file Libro.java
 * @brief Questo file contiene gli attributi, il costruttore e i metodi setter, getter e toString della classe Libro
 *
 * Questa classe permette di istanziare un oggetto Libro, i metodi setter e getter permettono di
 * ottenere e modificare informazioni relative agli attributi, inoltre il metodo toString permette di stampare 
 * le informazioni relative alla classe Libro.
 *
 * @author Gianmichele Trotta
 * @date 8 Dicembre 2025
 * @version 1.0
 */
package Libro;

import java.io.Serializable;

public class Libro implements Serializable{

    private String titolo;  ///@brief Il titolo del libro
    private String autore;  ///@brief L'autore del libro
    private String isbn;    ///@brief Il codice ISBN del libro
    private int copie = 1;   ///@brief Il numero di copie disponibili del libro
    private int annoPublicazione;   ///@brief L'anno di pubblicazione del libro
    private double prezzo;   ///@brief Il prezzo del libro
    private String usura;   ///@brief Lo stato di usura del libro

  /**
 * @brief Costruttore per creare un nuovo oggetto Libro
 *
 * Inizializza un Libro associando il titolo, l'autore, il codice ISBN, l'anno di pubblicazione, il prezzo, lo
 * stato di usura e il numero di copie disponibili
 * Il libro esiste nel sistema dal momento in cui viene creato
 *
 * @param[in] titolo Il titolo del libro
 * @param[in] autore L'autore del libro
 * @param[in] isbn Il codice ISBN del libro
 * @param[in] numCopie Il numero di copie disponibili del libro
 * @param[in] annoPubblicazione L'anno di pubblicazione del libro
 * @param[in] prezzo Il prezzo del libro
 * @param[in] usura Lo stato di usura del libro
 */
    
    public Libro(String titolo, String autore, String isbn, int annoPublicazione, double prezzo, String usura, int numCopie) {
        this.titolo = titolo;
        this.autore = autore;
        this.isbn = isbn;
        this.annoPublicazione = annoPublicazione;
        this.prezzo = prezzo;
        this.usura = usura;
        this.copie = numCopie; 
    }

  /**
 * @brief Metodo che permette di ottenere il titolo del libro
 * 
 * @return titolo Il titolo del libro in formato String
 */
    
    public String getTitolo() {
        return titolo;
    }

  /**
 * @brief Metodo che permette di ottenere l'autore del libro
 * 
 * @return autore L'autore del libro in formato String
 */
    
    public String getAutore() {
        return autore;
    }

  /**
 * @brief Metodo che permette di ottenere il codice ISBN del libro
 * 
 * @return isbn Il codice ISBN del libro in formato String
 */
    
    public String getIsbn() {
        return isbn;
    }

  /**
 * @brief Metodo che permette di ottenere il numero di copie del libro
 * 
 * @return numeroCopie Il numero di copie del libro in formato int
 */
    
    public int getCopie() {
        return copie;
    }

  /**
 * @brief Metodo che permette di ottenere l'anno di pubblicazione del libro
 * 
 * @return annoPubblicazione l'anno di pubblicazione del libro in formato int
 */
    
    public int getAnnoPubblicazione() {
        return annoPublicazione;
    }

  /**
 * @brief Metodo che permette di ottenere il prezzo del libro
 * 
 * @return prezzo Il prezzo del libro in formato float
 */
    
    public double getPrezzo() {
        return prezzo;
    }

  /**
 * @brief Metodo che permette di ottenere lo stato di usura del libro
 * 
 * @return usura Lo stato di usura del libro in formato String
 */
    
    public String getUsura() {
        return usura;
    }

  /**
 * @brief Imposta o aggiorna il titolo del libro 
 *
 * @param[in] titolo Il nuovo titolo del libro
 * 
 * @return void
 */
    
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

   /**
 * @brief Imposta o aggiorna l'autore del libro 
 *
 * @param[in] autore Il nuovo autore del libro
 * 
 * @return void
 */
    
    public void setAutore(String autore) {
        this.autore = autore;
    }

   /**
 * @brief Imposta o aggiorna il codice ISBN del libro 
 *
 * @param[in] isbn Il nuovo codice ISBN del libro
 * 
 * @return void
 */
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

   /**
 * @brief Imposta o aggiorna il numero di copie del libro 
 *
 * @param[in] numCopie Il nuovo numero di copie del libro
 * 
 * @return void
 */
    
    public void setCopie(int numCopie) {
        this.copie = numCopie;
    }

   /**
 * @brief Imposta o aggiorna l'anno di pubblicazione del libro 
 *
 * @param[in] annoPubblicazione Il nuovo anno di pubblicazione del libro
 * 
 * @return void
 */
    
    public void setAnnoPubblicazione(int anno) { 
        this.annoPublicazione = anno;
    }

  /**
 * @brief Imposta o aggiorna il prezzo del libro 
 *
 * @param[in] prezzo Il nuovo prezzo del libro
 * 
 * @return void
 */
    
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

  /**
 * @brief Imposta o aggiorna lo stato di usura del libro 
 *
 * @param[in] usura Il nuovo stato di usura del libro
 * 
 * @return void
 */
    
    public void setUsura(String usura) {
        this.usura = usura;
    }

  /**
 * @brief Stampa a video le informazioni relative al libro
 * 
 * Stampa tutte le informazioni relative al libro registrato
 *
 * @return Titolo, autore, codice ISBN, numero di copie disponibili, anno di pubblicazione, prezzo 
 * e stato di usura del libro
 */
    
    
    /**
 * @brief Controlla l'uguaglianza dell'oggetto passato con questo
 * 
 *
 * @return True o False dell'uguaglianza.
 */
    @Override
    public boolean equals(Object obj)
    {
        if(obj==null)
            return false;
        if(obj==this)
            return true;
        if(!(obj.getClass() == this.getClass()))
            return false;
        return this.getIsbn().equals(((Libro)obj).getIsbn());    
    }
    
    @Override
    public String toString() {
        return "Libro{" +
                "titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", isbn='" + isbn + '\'' +
                ", numCopie=" + copie +
                ", annoPublicazione=" + annoPublicazione +
                ", prezzo=" + prezzo +
                ", usura='" + usura + '\'' +
                '}';
    }
}
