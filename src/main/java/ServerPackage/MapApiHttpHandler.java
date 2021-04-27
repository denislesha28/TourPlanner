package ServerPackage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

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



}
