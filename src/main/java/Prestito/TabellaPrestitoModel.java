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

import Prestito.Prestito;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TabellaPrestitoModel {
        private ObservableList<Prestito> prestiti; /// @brief Questa ObservableList è una lista che contiene tutti i prestiti

        
        
                /**
 * @brief Costruttore della classe TabellaPrestitoModel
 *
 * Questo metodo inizializza la Observable List che contiene i prestiti
 *
 * @post Inizializza l'oggetto
 *
 * 
 */
        public TabellaPrestitoModel() {
        prestiti = FXCollections.observableArrayList();
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

    }
}
