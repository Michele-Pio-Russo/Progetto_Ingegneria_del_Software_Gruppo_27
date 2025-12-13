/**
 * @file Utente.java
 * @brief Questo file contiene gli attributi, il costruttore e i metodi setter, getter e toString della classe Utente
 *
 * Questa classe permette di istanziare un oggetto Utente, i metodi setter e getter permettono di
 * ottenere e modificare informazioni relative agli attributi, inoltre il metodo toString permette di stampare 
 * le informazioni relative alla classe Utente.
 *
 * @author Gianmichele Trotta
 * @date 8 Dicembre 2025
 * @version 1.0
 */
package Utente;
import java.io.Serializable;
import java.time.LocalDate;


public class Utente implements Serializable{

    private String nome;    ///@brief Il nome dell'utente
    private String cognome;     ///@brief Il cognome dell'utente
    private String matricola;   ///@brief La matricola dell'utente
    private String email;   ///@brief L'email dell'utente
    private LocalDate iscrizione;   ///@brief La data di iscrizione dell'utente
    private int libriInPrestito;    ///@brief Il numero di libri in prestito presi dell'utente

  /**
 * @brief Costruttore per creare un nuovo oggetto Utente
 *
 * Inizializza un utente associando il nome, il cognome, la matricola, l'email, la data d'iscrizione 
 * e il numero di libri che l'utente ha attualmente in prestito
 * L'utente esiste nel sistema dal momento in cui viene creato
 *
 * @param[in] nome Il nome dell'utente
 * @param[in] cognome Il cognome dell'utente
 * @param[in] matricola La matricola dell'utente
 * @param[in] email L'email dell'utente
 * @param[in] iscrizione La data di iscrizione dell'utente
 * @param[in] libriInPrestito Il numero di libri in prestito presi dall'utente
 */
    
    public Utente(String nome, String cognome, String matricola, String email, LocalDate iscrizione) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
        this.iscrizione = iscrizione;
        this.libriInPrestito = 0;
    }

 /**
 * @brief Metodo che permette di ottenere il nome dell'utente
 * 
 * @return nome Il nome dell'utente in formato String
 */
    
    public String getNome() {
        return nome;
    }

 /**
 * @brief Metodo che permette di ottenere il cognome dell'utente
 * 
 * @return cognome Il cognome dell'utente in formato String
 */
    
    public String getCognome() {
        return cognome;
    }

 /**
 * @brief Metodo che permette di ottenere la matricola dell'utente
 * 
 * @return matricola La matricola dell'utente in formato String
 */
    
    public String getMatricola() {
        return matricola;
    }

 /**
 * @brief Metodo che permette di ottenere l'email dell'utente
 * 
 * @return email L'email dell'utente in formato String
 */
    
    public String getEmail() {
        return email;
    }

  /**
 * @brief Metodo che permette di ottenere la data di iscrizione dell'utente
 * 
 * @return iscrizione la data di iscrizione dell'utente in formato LocalDate
 */
    
    public LocalDate getIscrizione() {
        return iscrizione;
    }

  /**
 * @brief Metodo che permette di ottenere il numero di libri presi in prestito dall'utente
 * 
 * @return libriInPrestito Il numero di libri dell'utente in formato int
 */
    
    public int getLibriInPrestito() {
        return libriInPrestito;
    }

 /**
 * @brief Imposta o aggiorna il nome dell'utente 
 *
 * @param[in] nome Il nuovo nome dell'utente
 * 
 * @return void
 */
    
    public void setNome(String nome) {
        this.nome = nome;
    }

  /**
 * @brief Imposta o aggiorna il cognome dell'utente 
 *
 * @param[in] cognome Il nuovo cognome dell'utente
 * 
 * @return void
 */
    
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

  /**
 * @brief Imposta o aggiorna la matricola dell'utente 
 *
 * @param[in] matricola La nuova matricola dell'utente
 * 
 * @return void
 */
    
    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

  /**
 * @brief Imposta o aggiorna l'email dell'utente 
 *
 * @param[in] email La nuova email dell'utente
 * 
 * @return void
 */
    
    public void setEmail(String email) {
        this.email = email;
    }

  /**
 * @brief Imposta o aggiorna la data di iscrizione dell'utente 
 *
 * @param[in] iscrizione La data di iscrizione dell'utente
 * 
 * @return void
 */
    
    public void setIscrizione(LocalDate iscrizione) {
        this.iscrizione = iscrizione;
    }

  /**
 * @brief Imposta o aggiorna il numero di libri presi in prestito dall'utente 
 *
 * @param[in] libriInPrestito Il numero di libri dell'utente
 * 
 * @return void
 */
    
    public void setLibriInPrestito(int libriInPrestito) {
        this.libriInPrestito = libriInPrestito;
    }

 /**
 * @brief Stampa a video le informazioni relative all'utente
 * 
 * Stampa tutte le informazioni relative all'utente registrato
 *
 * @return Nome, cognome, matricola, email, data di iscrizione e numero di libri dell'utente
 */
    
    @Override
    public String toString() {
        return "Studente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", matricola='" + matricola + '\'' +
                ", email='" + email + '\'' +
                ", iscrizione=" + iscrizione +
                ", libriInPrestito=" + libriInPrestito +
                '}';
    }
    
    
     @Override
    public int hashCode(){
        return 17 + matricola.hashCode();
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
        return this.getNome().equals(((Utente)obj).getNome());
    }
}

