package ServerPackage;


import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class MapApiHttpHandler {
    String key;

    public MapApiHttpHandler() {
        key = "sbA2AG4PAtKsucb54CDBLp8YOsxS8oL1";
    }

    public Image sendMapApiRequest(String from, String to) throws URISyntaxException, ExecutionException, InterruptedException, IOException {
        String requestFromToResult = sendFromToRequest(from, to);
        Image image = sendImageRequest(requestFromToResult);
        return image;
    }


     public String sendFromToRequest(String from, String to) throws URISyntaxException, ExecutionException, InterruptedException {

        String requestUrl = "http://open.mapquestapi.com/directions/v2/route" +
                "?key=" + key + "&from=" + from + "&to=" + to;
        URI uri = new URI(requestUrl);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .headers("Content-Type", "application/json")
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> asyncResponse = HttpClient.newBuilder()
                .build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());


        HttpResponse<String> response = asyncResponse.get();
        return response.body();

    }

    public Image sendImageRequest(String requestFromToResponse) throws IOException, ExecutionException, InterruptedException, URISyntaxException {

        String resp = requestFromToResponse;
        JSONObject jsonObject = new JSONObject(resp);
        JSONObject route = jsonObject.getJSONObject("route");
        String sessionId = route.getString("sessionId");
        JSONObject boundingBox = route.getJSONObject("boundingBox");
        JSONObject ul = boundingBox.getJSONObject("ul");
        JSONObject lr = boundingBox.getJSONObject("lr");
        Double ulLat = ul.getDouble("lat");
        Double ulLng = ul.getDouble("lng");
        Double lrLat = lr.getDouble("lat");
        Double lrLng = lr.getDouble("lng");
        System.out.println(jsonObject);

        String requestUrl = "https://www.mapquestapi.com/staticmap/v5/map?key=" + key + "&size=700,400" +
                "&defaultMarker=none&zoom=11&session=" + sessionId + "&boundingBox=" + ulLat + "," + ulLng + "," + lrLat + "," + lrLng;
        URI uri = new URI(requestUrl);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .headers("Content-Type", "image/jpeg")
                .GET()
                .build();

        CompletableFuture<HttpResponse<InputStream>> responseAsync = HttpClient.newBuilder()
                .build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofInputStream());

        HttpResponse<InputStream> response = responseAsync.get();
        BufferedImage bufferedImage= ImageIO.read(response.body());
        Image image = SwingFXUtils.toFXImage(bufferedImage,null);
 /*
        File outputFile = new File("image.jpg");
        ImageIO.write(bufferedImage, "jpg", outputFile);
*/
        return image;

    }

}
