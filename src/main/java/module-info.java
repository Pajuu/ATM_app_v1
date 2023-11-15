module com.example.bankstudenta {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires  java.sql;
    requires jbcrypt;


    opens com.example.bankstudenta to javafx.fxml;
    exports com.example.bankstudenta;
    exports User;
    opens User to javafx.fxml;
    exports Transaction;
    opens Transaction to javafx.fxml;
    exports DB;
    opens DB to javafx.fxml;
}