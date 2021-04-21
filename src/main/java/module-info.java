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

    opens MainPackage to javafx.fxml;
    exports MainPackage;
    exports TestingPackage;
    exports ServerPackage;
    opens ServerPackage to javafx.fxml;
}