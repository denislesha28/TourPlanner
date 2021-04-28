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
import java.nio.file.Path;

public class MapApiHttpHandler {

    public String sendRequest(){
        try {
            String key="sbA2AG4PAtKsucb54CDBLp8YOsxS8oL1";
            String from="Wien";
            String to="Graz";
            String requestUrl="http://open.mapquestapi.com/directions/v2/route" +
                    "?key=" +key+"&from="+from+"&to="+to;
            URI uri=new URI(requestUrl);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .headers("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            return response.body();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Path sendRequest2() {

        String resp = sendRequest();
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
        System.out.println(ulLat);
        System.out.println(ulLng);

        try {
            String key="sbA2AG4PAtKsucb54CDBLp8YOsxS8oL1";
            String requestUrl="https://www.mapquestapi.com/staticmap/v5/map?key="+key+"&size=640,480" +
                    "&defaultMarker=none&zoom=11&session="+sessionId+"&boundingBox="+ulLat+","+ulLng+","+lrLat+","+lrLng;
            URI uri=new URI(requestUrl);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .headers("Content-Type", "image/jpeg")
                    .GET()
                    .build();

            HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());

            BufferedImage image= ImageIO.read(response.body());
            File outputfile = new File("image.jpg");
            ImageIO.write(image, "jpg", outputfile);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }



}
