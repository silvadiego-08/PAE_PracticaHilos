module ni.edu.uam.practicahilos {
    requires javafx.controls;
    requires javafx.fxml;

    opens ni.edu.uam.practicahilos to javafx.fxml;
    exports ni.edu.uam.practicahilos;
    exports ni.edu.uam.practicahilos.app to javafx.graphics;
}