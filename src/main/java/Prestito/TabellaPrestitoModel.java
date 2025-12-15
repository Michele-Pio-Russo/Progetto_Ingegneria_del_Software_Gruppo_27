/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @file TabellaPrestitoModel.java
 * @brief Questo file contiene il model della tabella che gestisce i prestiti della libreria
 *
 * Il model permette di aggiungere e rimuovere prestiti
 *
 * @author Gerardo Russo
 * @date 8 dicembre 2025
 * @version 1.0
 */
package Prestito;

import Libro.Libro;
import Prestito.Prestito;
import Utente.Utente;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TabellaPrestitoModel {
        private ObservableList<Prestito> prestiti; /// @brief Questa ObservableList è una lista che contiene tutti i prestiti
        private final String FILE_BINARIO = "prestiti.bin";
        
               /**
 * @brief Costruttore della classe TabellaPrestitoModel
 *
 * Questo metodo inizializza la Observable List che contiene i prestiti
 *
 * @post Inizializza l'oggetto
 *
 * 
 */
        public TabellaPrestitoModel()
        {
            prestiti = FXCollections.observableArrayList();
            try{
                caricaDaBinario();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
                    
        }
    
 /**
 * @brief Metodo getter dei prestiti
 *
 * Questo metodo permette di prendere i prestiti dalla Observable List
 *
 * @pre Deve esserci almeno un prestito
 * @post Deve restituire l'Observable List che contiene i prestiti
 *
 * @return Ritorna tutta l'Observable List
 */
    public ObservableList<Prestito> getPrestiti() {
        return prestiti;
        //
    }

     /**
 * @brief Metodo che permette di aggiungere un prestito
 *
 * Questo metodo permette aggiungere un prestito alla Observable List
 *
 * @pre Deve esserci un prestito da aggiungere
 * @post Il prestito deve essere presente nell'Observable List
 *
 * @param[in] utente Utente che vuole ottenere un prestito
 * @param[in] libro Il libro da dover prestare all'utente
 * @param[in] scad La data di scadenza del prestito
 * 
 * @return void

 */
    public void aggiungiPrestito(Utente utente, Libro libro, LocalDate scad) { 
            prestiti.add(new Prestito(utente, libro, scad));
    }

    
 /**
 * @brief Metodo che permette di rimuovere un prestito
 *
 * Questo metodo permette rimuovere un prestito dalla Observable List
 *
 * @pre Deve esserci un prestito da rimuovere
 * @post Il prestito è stato rimosso dall'Observable List
 *
 * @param[in] p prestito da rimuovere
 * 
 * @return void

 */
    public void rimuoviPrestito(Prestito p) { 
        prestiti.remove(p);
    }
        /**
     * @brief Salvataggio su file binario tramite ObjectOutputStream.
     * Viene salvata una List<Prestito>.
     * 
     * @return void
     */
    public void salvaSuBinario()  {
        List<Prestito> lista = new ArrayList<>(prestiti);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_BINARIO))) {
            out.writeObject(lista);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
        /**
     * @brief Caricamento da file binario.
     * Formato atteso: una List<Prestito> serializzata.
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

            List<Prestito> lista = (List<Prestito>) obj;
            for (Object o : lista) {
                if (o instanceof Prestito) {
                    prestiti.add((Prestito) o);
                }
            }
       
        }
    }
}
