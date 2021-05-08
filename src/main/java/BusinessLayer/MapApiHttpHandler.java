package BusinessLayer;


import javafx.beans.property.ObjectProperty;
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


    public void sendMapApiRequest(ObjectProperty<Image> tourImage, String from, String to) throws URISyntaxException, ExecutionException, InterruptedException, IOException {
        sendFromToRequest(tourImage,from, to);
    }


    public void sendFromToRequest(ObjectProperty<Image> tourImage,String from, String to) throws URISyntaxException, ExecutionException, InterruptedException {

        String requestUrl = "http://open.mapquestapi.com/directions/v2/route" +
                "?key=" + key + "&from=" + from + "&to=" + to;
        URI uri = new URI(requestUrl);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .headers("Content-Type", "application/json")
                .GET()
                .build();

        CompletableFuture asyncResponse = HttpClient.newBuilder()
                .build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(stringHttpResponse -> stringHttpResponse.body())
                .thenApply(input -> {
                    try {
                        return sendImageRequest(tourImage,input);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    return null;
                });


    }

    public int sendImageRequest(ObjectProperty<Image> tourImage,String requestFromToResponse) throws IOException, ExecutionException, InterruptedException, URISyntaxException {

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

        CompletableFuture<Void> voidCompletableFuture = HttpClient.newBuilder()
                .build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(inputStreamHttpResponse -> inputStreamHttpResponse.body())
                .thenApply(input -> {
                    try {
                        return ImageIO.read(input);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .thenApply((BufferedImage bufferedImage) -> SwingFXUtils.toFXImage(bufferedImage,null))
                .thenAccept(t -> tourImage.set(t));
        return 0;
        //HttpResponse<InputStream> response = (HttpResponse<InputStream>) responseAsync.get();
        //BufferedImage bufferedImage = ImageIO.read(response.body());
        //Image image = SwingFXUtils.toFXImage(bufferedImage,null);
        //tourImage.set(image);
    }

}
