module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.controllers to javafx.fxml;
    opens com.example.Model to javafx.base;
    exports com.example;
}
