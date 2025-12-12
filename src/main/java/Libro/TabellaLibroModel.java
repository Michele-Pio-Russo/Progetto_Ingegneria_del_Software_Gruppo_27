/**
 * @file TabellaLibroModel.java
 * @brief Questo file contiene il model della tabella che gestisce i libri della libreria
 *
 * Questo model permette di aggiungere e rimuovere libri
 *
 * @author Gerardo Russo
 * @date 8 dicembre 2025
 * @version 1.0
 */
package Libro;

import Libro.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TabellaLibroModel {
        private ObservableList<Libro> libri; /// @brief Questa ObservableList è una lista che contiene tutti i libri
       

        /**
 * @brief Costruttore della classe TabellaLibroModel
 *
 * Questo metodo inizializza la Observable List che contiene i libri
 *
 * @post Inizializza l'oggetto
 *
 * 
 */
    public TabellaLibroModel() {
        libri = FXCollections.observableArrayList();
    }
 /**
 * @brief Metodo getter dei libri
 *
 * Questo metodo permette di prendere i libri dalla Observable List
 *
 * @pre Deve esserci almeno un libro
 * @post Deve restituire l'Observable List che contiene i libri
 *
 * @return Ritorna tutta l'Observable List
 */
  public ObservableList<Libro> getLibri() {
      return libri;
    }

  
  
   /**
 * @brief Metodo che permette di aggiungere un libro
 *
 * Questo metodo permette aggiungere un libro alla Observable List
 *
 * @pre Deve esserci un libro da aggiungere
 * @post Il libro deve essere presente nell'Observable List
 *
 * @param[in] titolo Il titolo del libro
 * @param[in] autore  L'autore del libro
 * @param[in] isbn Codice ISBN del libro
 * @param[in] numCopie Numero di copie attualmente disponibili per quel libro
 * @param[in] anno Anno di pubblicazione del libro
 * @param[in] prezzo Prezzo del libro
 * @param[in] usura stato di usura del libro
 * 
 * @return void

 */
    public void aggiungiLibro(String titolo, String autore, String isbn, int anno, double prezzo, String usura, int numCopie) {
            libri.add(new Libro(titolo, autore, isbn, anno, prezzo, usura, numCopie));
    }

 /**
 * @brief Metodo che permette di rimuovere un libro
 *
 * Questo metodo permette rimuovere un libro dalla Observable List
 *
 * @pre Il libro da rimuovere deve essere disponibile
 * @post Il libro è stato rimosso dall'Observable List
 *
 * @param[in] lib Libro da rimuovere
 * 
 * @return void
 */
    public void rimuoviLibro(Libro lib) { 
            libri.remove(lib);
    }
}