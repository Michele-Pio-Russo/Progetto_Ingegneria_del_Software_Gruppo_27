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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TabellaLibroModel {
        private ObservableList<Libro> libri; /// @brief Questa ObservableList è una lista che contiene tutti i libri
        private final String FILE_BINARIO = "libri.bin";

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
        try{
                caricaDaBinario();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
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
   /**
     * @brief Salvataggio su file binario tramite ObjectOutputStream.
     *  Viene salvata una List<Libro>.
     * 
     * @return void
     */
    public void salvaSuBinario() {
        List<Libro> lista = new ArrayList<>(libri);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_BINARIO))) {
            out.writeObject(lista);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
         /**
     * @brief Caricamento da file binario.
     * Formato atteso: una List<Libro> serializzata.
     * 
     * @return void
     */
     public void caricaDaBinario() throws IOException, ClassNotFoundException {
        File file = new File(FILE_BINARIO);
        if (!file.exists()) {
            throw new FileNotFoundException("File binario non trovato: " + FILE_BINARIO);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {

            Object obj = in.readObject();

            List<Libro> lista = (List<Libro>) obj;
            for (Object o : lista) {
                if (o instanceof Libro) {
                    libri.add((Libro) o);
                }
            }
        }
    }
}

