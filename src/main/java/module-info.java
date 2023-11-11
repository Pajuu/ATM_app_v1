module com.example.bankstudenta {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;


    opens com.example.bankstudenta to javafx.fxml;
    exports com.example.bankstudenta;
}