module com.example.bankstudenta {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires  java.sql;



    opens com.example.bankstudenta to javafx.fxml;
    exports com.example.bankstudenta;
}