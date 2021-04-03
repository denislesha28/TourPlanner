module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;

    opens mainPackage to javafx.fxml;
    exports mainPackage;
    exports testingPackage;
}