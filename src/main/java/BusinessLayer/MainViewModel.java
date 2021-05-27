package BusinessLayer;

import DataAccessLayer.Model;
import DataAccessLayer.Tour;
import com.itextpdf.text.DocumentException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;


public class MainViewModel {

    private Model model=Model.getModelInstance();
    private final Logger log;
    PDFExporter pdfExporter=null;
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

    public MainViewModel() throws SQLException, IOException {
        Configurator.initialize(null, "TourPlannerLog4j.conf.xml");
        log = LogManager.getLogger(MainViewModel.class);
    }

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


    /*public Property tourListProperty() {
        System.out.println("VM: get Tour ListView");
        return tourListView;
    }*/

    public void addTour() throws SQLException {
        String newTourName=model.generateTourRandomName();
        model.addTour(newTourName);
        tourList.add(newTourName);
        this.tourListView.set(tourList);
        log.debug("MVM Tour Insertion");
    }

    public void deleteTour(String item) throws SQLException {
        model.deleteTour(item);
        tourList.remove(item);
        log.debug("MVM Tour Deletion");
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
        log.debug("MVM Tour Updated");
    }

    private void setTourPicture(String from,String to) throws URISyntaxException, IOException, ExecutionException, InterruptedException {
        Image srcGif = new Image("file:src/main/resources/FrontEnd/loading_2.gif");
        tourImage.set(srcGif);
        mapApiHttpHandler.sendMapApiRequest(tourImage,from,to);
        log.debug("MVM send TourPicture request");
    }

    public void displayTourAttributes(String item) throws SQLException {
        if (item==null){
            return;
        }
        Tour tourDetails=model.getTourDetails(0,item);
        if(tourDetails!=null) {
            tourName.set(tourDetails.getTourName());
            tourDistance.set(String.valueOf(tourDetails.getTourDistance()));
            tourDescription.set(tourDetails.getTourDescription());
            routeInformation.set(tourDetails.getRouteInformation());
        }else {
            tourName.set("----------");
            tourDistance.set("----------");
            tourDescription.set("----------");
            routeInformation.set("----------");
        }
        log.info("display TourAttributes on UI");
    }

    public void displayTourRoute(String item) throws SQLException, URISyntaxException, IOException, ExecutionException, InterruptedException {
        if (item==null){
            return;
        }
        Tour tourDetails=model.getTourDetails(0,item);
        String routeFrom=tourDetails.getTourFrom();
        String routeTo=tourDetails.getTourTo();
        if (routeFrom==null || routeTo==null){
            return;
        }
        fromDestination.set(routeFrom);
        toDestination.set(routeTo);
        log.info("set TourRoute for Tour");
        setTourPicture(fromDestination.get(),toDestination.get());
        log.debug("start AsyncCall for TourImage");
    }

    public void updateTourRoute(String item) throws SQLException, URISyntaxException, IOException, ExecutionException, InterruptedException {
        if (item==null){
            return;
        }
        String routeFrom=fromDestination.get();
        String routeTo=toDestination.get();
        model.updateTourRoute(item,routeFrom,routeTo);
        setTourPicture(routeFrom,routeTo);
        log.debug("MVM update TourRoute");
    }

    public void exportPdf(String item) throws SQLException, IOException, DocumentException, URISyntaxException, ExecutionException, InterruptedException {
        if (pdfExporter == null){
            pdfExporter = new PDFExporter();
        }
        if (item == null){
            pdfExporter.exportToursPdf();
        }
        else {
            pdfExporter.exportTourPdf(item);
        }

    }

}
