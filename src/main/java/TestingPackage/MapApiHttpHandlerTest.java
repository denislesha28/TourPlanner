package TestingPackage;

import BusinessLayer.MapApiHttpHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;

public class MapApiHttpHandlerTest {
   /* @Test
    public void testFromToRequest() throws URISyntaxException, ExecutionException, InterruptedException {
        //Arrange
        MapApiHttpHandler mapApiHttpHandler=new MapApiHttpHandler();
        //Act
        String response=mapApiHttpHandler.sendFromToRequest("Wien","Graz");
        JSONObject jsonObject = new JSONObject(response);
        JSONObject route = jsonObject.getJSONObject("route");
        String sessionId = route.getString("sessionId");
        JSONObject boundingBox = route.getJSONObject("boundingBox");
        JSONObject ul = boundingBox.getJSONObject("ul");
        JSONObject lr = boundingBox.getJSONObject("lr");
        Double ulLat = ul.getDouble("lat");
        Double ulLng = ul.getDouble("lng");
        Double lrLat = lr.getDouble("lat");
        Double lrLng = lr.getDouble("lng");
        Double ulLatExpected = 48.209644;
        Double ulLngExpected = 15.281363;
        Double lrLatExpected = 47.069416;
        Double lrLngExpected = 16.400137;
        //Assert
        Assert.assertEquals(ulLatExpected,ulLat);
        Assert.assertEquals(ulLngExpected,ulLng);
        Assert.assertEquals(lrLatExpected,lrLat);
        Assert.assertEquals(lrLngExpected,lrLng);
    }
    */


}
