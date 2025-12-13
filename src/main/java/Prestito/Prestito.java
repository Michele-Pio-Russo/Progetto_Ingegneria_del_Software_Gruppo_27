/**
 * @file Prestito.java
 * @brief Questo file contiene gli attributi, il costruttore e i metodi setter, getter e toString della classe Prestito
 *
 * Questa classe permette di istanziare un oggetto Prestito, i metodi setter e getter permettono di
 * ottenere e modificare informazioni relative agli attributi, inoltre il metodo toString permette di stampare 
 * le informazioni relative alla classe Prestito
 *
 * @author Gianmichele Trotta
 * @date 8 Dicembre 2025
 * @version 1.0
 */
package Prestito;
import java.io.Serializable;
import Libro.Libro;
import Utente.Utente;
import java.time.LocalDate;

public class Prestito implements Serializable{

    private Utente utente;  /// @brief Attributo di tipo Utente, gestisce l'utente interessato al prestito
    private Libro libro;        /// @brief Attributo di tipo Libro, gestisce il libro interessato al prestito
    private LocalDate dataDiScadenza;   ///@brief Data di scadenza del prestito

 /**
 * @brief Costruttore per creare un nuovo oggetto Prestito
 *
 * Inizializza un prestito associando l'utente, il libro e la data di scadenza
 * Il prestito ha inizio nel momento in cui viene creato
 *
 * @param[in] utente L'oggetto Utente che prende in prestito il libro
 * @param[in] libro L'oggetto Libro che viene dato in prestito
 * @param[in] dataDiScadenza La data entro la quale il libro deve essere restituito
 */
    
    public Prestito(Utente utente, Libro libro, LocalDate dataDiScadenza) {
        this.utente = utente;
        this.libro = libro;
        this.dataDiScadenza = dataDiScadenza;
    }

 /**
 * @brief Metodo che permette di ottenere le informazioni relative al nome di un utente
 * 
 * @return utente Le informazioni relative al nome dell'utente a cui viene fatto il prestito
 */
    
    public String getNome() {
        return utente.getNome();
    }
    
    
 /**
 * @brief Metodo che permette di cambaire il nome di un utente
 * 
 * @return void
 */
    
    public void setNome(String nome) {
        utente.setNome(nome);
    }
    
    
 /**
 * @brief Metodo che permette di ottenere le informazioni relative al cognome di un utente di un prestito
 * 
 * @return utente Le informazioni relative al cognome dell'utente a cui viene fatto il prestito
 */
    
    public String getCognome() {
        return utente.getCognome();
    }
    
    
     /**
 * @brief Metodo che permette di cambaire il cognome di un utente di un prestito
 * 
 * @return void
 */
    
    public void setCognome(String cognome) {
        utente.setCognome(cognome);
    }

  /**
 * @brief Metodo che permette di ottenere le informazioni relative al titolo di un libro
 * 
 * @return libro Le informazioni relative al libro preso in prestito
 */
    
    public String getTitolo() {
        return libro.getTitolo();
    }
    
    
        /**
 * @brief Metodo che permette di cambaire il titolo di un libro di un prestito
 * 
 * @return void
 */
    
    public void setTitolo(String titolo) {
        libro.setTitolo(titolo);
    }
 
    
      /**
 * @brief Metodo che permette di ottenere le informazioni relative all'isbn di un libro
 * 
 * @return libro Le informazioni relative al libro preso in prestito
 */
    
    public String getIsbn() {
        return libro.getIsbn();
    }

 /**
 * @brief Metodo che permette di ottenere la data di scadenza del prestito
 * 
 * @return dataDiScadenza La data di scadenza in formato LocalDate
 */
    
            /**
 * @brief Metodo che permette di cambaire il titolo di un libro di un prestito
 * 
 * @return void
 */
    
    public void setIsbn(String isbn) {
        libro.setIsbn(isbn);
    }
    
    
    
    public LocalDate getDataDiScadenza() {
        return dataDiScadenza;
    }

/**
 * @brief Imposta o aggiorna la data di scadenza del prestito.
 *
 * @param[in] dataDiScadenza La nuova data di scadenza
 * 
 * @return void
 */
    
    public void setDataDiScadenza(LocalDate dataDiScadenza) {
        this.dataDiScadenza = dataDiScadenza;
    }

    /**
 * @brief Stampa a video le informazioni relative al prestito
 * 
 * Stampa tutte le informazioni relative all'utente a cui è stato fatto il prestito, al libro preso in prestito e la 
 * data di scadenza di quest ultimo
 *
 * @return Informazioni dell'utente, del libro e data di scadenza del prestito.
 */
    
    @Override
    public String toString() {
        return "Prestito{" +
                "utente=" + utente +
                ", libro=" + libro +
                ", dataDiScadenza=" + dataDiScadenza +
                '}';
    }
    
     @Override
    public int hashCode(){
        return 17 + libro.getIsbn().hashCode() + utente.getNome().hashCode();
    }
    
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
        return this.getNome().equals(((Prestito)obj).getNome()) && this.getIsbn().equals(((Prestito)obj).getIsbn());    }
}
