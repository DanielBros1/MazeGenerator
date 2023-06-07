module com.mazegenerator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.mazegenerator to javafx.fxml;
    exports com.mazegenerator;
    exports controllers;
    opens controllers to javafx.fxml;
}