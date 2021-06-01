module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires postgresql;
    requires java.sql;
    requires jdk.jsobject;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.opentest4j;
    requires org.mockito;
    requires java.net.http;
    requires org.json;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires itextpdf;

    exports View;
    exports TestingPackage;
    exports DataAccessLayer;
    opens DataAccessLayer to javafx.fxml, mockito.all;
    exports BusinessLayer;
    opens BusinessLayer to javafx.fxml, mockito.all;
    opens View to javafx.fxml, mockito.all;
    exports DataAccessLayer.Database;
    opens DataAccessLayer.Database to javafx.fxml, mockito.all;
    exports DataAccessLayer.Local;
    opens DataAccessLayer.Local to javafx.fxml, mockito.all;
}