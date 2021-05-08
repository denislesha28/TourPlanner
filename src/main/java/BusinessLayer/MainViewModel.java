package BusinessLayer;

import DataAccessLayer.Model;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class MainViewModel {

    private Model model=Model.getModelInstance();
    MapApiHttpHandler mapApiHttpHandler = new MapApiHttpHandler();
    public ObservableList<String> tourList = FXCollections.observableArrayList(model.getTours());
    private final ObjectProperty<ObservableList<String>> tourListView = new SimpleObjectProperty<>(tourList);
    private final StringProperty tourName = new SimpleStringProperty("");
    private final StringProperty tourDistance = new SimpleStringProperty("");
    private final StringProperty tourDescription = new SimpleStringProperty("");
    private final StringProperty routeInformation = new SimpleStringProperty("");
    private final StringProperty fromDestination = new SimpleStringProperty("");
    private final StringProperty toDestination = new SimpleStringProperty("");
    private final ObjectProperty<javafx.scene.image.Image> tourImage = new SimpleObjectProperty<>();

    public StringProperty tourNameProperty() {
        System.out.println("VM: init tourName field");
        return tourName;
    }

    public StringProperty tourDistanceProperty() {
        System.out.println("VM: init tourDistance field");
        return tourDistance;
    }

    public StringProperty tourDescriptionProperty() {
        System.out.println("VM: init tourDescription field");
        return tourDescription;
    }

    public StringProperty routeInformationProperty() {
        System.out.println("VM: init routeInformation field");
        return routeInformation;
    }

    public StringProperty fromDestinationProperty(){
        System.out.println("VM: init fromDestination field");
        return fromDestination;
    }

    public StringProperty toDestinationProperty(){
        System.out.println("VM: init toDestination field");
        return toDestination;
    }

    public ObjectProperty<javafx.scene.image.Image> tourImageProperty(){
        System.out.println("Image init");
        return tourImage;
    }
    public MainViewModel() throws SQLException, IOException {}

    /*public Property tourListProperty() {
        System.out.println("VM: get Tour ListView");
        return tourListView;
    }*/

    public void addTour() throws SQLException {
        String newTourName=model.generateTourRandomName();
        model.addTour(newTourName);
        tourList.add(newTourName);
        this.tourListView.set(tourList);
    }

    public void deleteTour(String item) throws SQLException {
        model.deleteTour(item);
        tourList.remove(item);
    }

    public void updateTour(String item) throws SQLException {
        String tourName=this.tourName.getValue();
        String tourDescription=this.tourDescription.getValue();
        String routeInformation=this.routeInformation.getValue();
        double tourDistance= Double.parseDouble(this.tourDistance.getValue());
        model.updateTour(item,tourDescription,tourName,routeInformation,tourDistance);
        if (tourName.equals(item)){
            return;
        }
        int curr_pos=tourList.indexOf(item);
        tourList.set(curr_pos,tourName);
    }

    private void setTourPicture(String from,String to) throws URISyntaxException, IOException, ExecutionException, InterruptedException {
        Image srcGif = new Image("file:src/main/resources/MainPackage/loading_2.gif");
        tourImage.set(srcGif);
        mapApiHttpHandler.sendMapApiRequest(tourImage,from,to);
    }

    public void displayTourAttributes(String item) throws SQLException {
        if (item==null){
            return;
        }
        HashMap<String,String> tourDetails=model.getTourDetails(0,item);
        if(tourDetails!=null) {
            tourName.set(tourDetails.get("tourName"));
            tourDistance.set(tourDetails.get("tourDistance"));
            tourDescription.set(tourDetails.get("tourDescription"));
            routeInformation.set(tourDetails.get("routeInformation"));
        }else {
            tourName.set("----------");
            tourDistance.set("----------");
            tourDescription.set("----------");
            routeInformation.set("----------");
        }
    }

    public void displayTourRoute(String item) throws SQLException, URISyntaxException, IOException, ExecutionException, InterruptedException {
        if (item==null){
            return;
        }
        HashMap<String,String> tourDetails=model.getTourDetails(0,item);
        String routeFrom=tourDetails.get("from");
        String routeTo=tourDetails.get("to");
        if (routeFrom==null || routeTo==null){
            return;
        }
        fromDestination.set(routeFrom);
        toDestination.set(routeTo);
        setTourPicture(fromDestination.get(),toDestination.get());
    }

    public void updateTourRoute(String item) throws SQLException, URISyntaxException, IOException, ExecutionException, InterruptedException {
        if (item==null){
            return;
        }
        String routeFrom=fromDestination.get();
        String routeTo=toDestination.get();
        model.updateTourRoute(item,routeFrom,routeTo);
        setTourPicture(routeFrom,routeTo);
    }

}
