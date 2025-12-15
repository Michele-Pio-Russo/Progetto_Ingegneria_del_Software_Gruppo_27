module it.unisa.diem.oop.progetto_ing_software_libreria {
    
    // Dipendenze dai moduli JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; 
    
    // 1. APERTURA per FXML (Controller) e Data Binding (TableView/ObservableList)
    
    // Apri il pacchetto principale contenente la main class, le classi di servizio e le risorse FXML.
    // L'apertura a javafx.graphics è necessaria per avviare l'applicazione.
    opens it.unisa.diem.oop.progetto_ing_software_libreria to javafx.fxml, javafx.graphics;
    
    // Apri tutti i pacchetti contenenti Controller e classi Model usate nel Data Binding (TableView/Controls).
    // Questo è il punto cruciale che risolve l'IllegalAccessException. 
    opens Libro to javafx.fxml, javafx.base; 
    opens Utente to javafx.fxml, javafx.base;
    opens Prestito to javafx.fxml, javafx.base;
    
    // Esporta il pacchetto principale (dove si trova la MainApp)
    exports it.unisa.diem.oop.progetto_ing_software_libreria;
}