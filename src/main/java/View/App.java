package View;

import BusinessLayer.PDFExporter;
import com.itextpdf.text.DocumentException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException, SQLException, DocumentException, URISyntaxException, ExecutionException, InterruptedException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-70;
        // todo set layout based on screen size
        scene = new Scene(loadFXML("mainUI_v2"), width  , height);
        stage.setScene(scene);
        stage.show();
        PDFExporter pdfExporter = new PDFExporter();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}