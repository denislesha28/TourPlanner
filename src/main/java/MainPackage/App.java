package MainPackage;

import ServerPackage.DatabaseHandler;
import ServerPackage.MapApiHttpHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.concurrent.CompletableFuture;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        /* Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();*/
        // todo set layout based on screen size
        scene = new Scene(loadFXML("mainUI_v2"), 980  , 700);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        try {
            DatabaseHandler dbHandler=DatabaseHandler.getDatabaseInstance();

            // create a client
            //HttpClient client = HttpClient.newHttpClient();

            // create a request
            /*HttpRequest request = HttpRequest.newBuilder(
                    URI.create("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"))
                    .header("accept", "application/json")
                    .build();*/

            // use the client to send the request

            MapApiHttpHandler mapApiHttpHandler=new MapApiHttpHandler();
            var response=mapApiHttpHandler.sendRequest2();

            launch();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}