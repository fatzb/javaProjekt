module com.example.demo8hamster {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo8hamster to javafx.fxml;
    exports com.example.demo8hamster;
}