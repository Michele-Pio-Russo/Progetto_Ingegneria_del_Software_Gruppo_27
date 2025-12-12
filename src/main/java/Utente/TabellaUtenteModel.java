/**
 * @file TabellaUtenteModel.java
 * @brief Questo file contiene il model della tabella che gestisce gli utenti iscritti alla libreria
 *
 * Questo model permette di aggiungere e rimuovere gli utenti
 *
 * @author Gerardo Russo
 * @date 8 dicembre 2025
 * @version 1.0
 */
package Utente;

import Utente.Utente;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TabellaUtenteModel {
        private ObservableList<Utente> utenti; /// @brief Questa ObservableList è una lista che contiene tutti gli utenti
        
        public TabellaUtenteModel(){
            utenti = FXCollections.observableArrayList();
        }
       
        
 /**
 * @brief Metodo getter degli utenti
 *
 * Questo metodo permette di prendere gli utenti dalla Observable List
 *
 * @pre Deve esserci almeno un utente
 * @post Deve restituire l'Observable List che contiene gli utenti
 *
 * @return Ritorna tutta l'Observable List
 */
    public ObservableList<Utente> getPersone() { 
        return utenti;
    }

    
     /**
 * @brief Metodo che permette di aggiungere un utente
 *
 * Questo metodo permette aggiungere un utente alla Observable List
 *
 * @pre Deve esserci un utente da aggiungere
 * @post L'utente deve essere presente nell'Observable List
 *
 * @param[in] nome Il nome dell'utente
 * @param[in] cognome Il cognome dell'utente
 * @param[in] matricola La matricola dell'utente 
 * @param[in] email L'email dell'utente
 * @param[in] iscrizione La data di iscrizione dell'utente 
 * 
 * @return void

 */
    public void aggiungiPersona(String nome, String cognome, String matricola, String email, LocalDate iscrizione) {
        utenti.add(new Utente(nome, cognome, matricola, email, iscrizione));

    }

  /**
 * @brief Metodo che permette di rimuovere un utente
 *
 * Questo metodo permette rimuovere un utente dalla Observable List
 *
 * @pre L'utente da rimuovere deve essere presente nell'Observable List
 * @post L'utente è stato rimosso dall'Observable List
 *
 * @param[in] u L'utente da rimuovere
 * 
 * @return void

 */
    public void rimuoviPersona(Utente u) { 
        utenti.remove(u);
    }
}
