package View;

import BusinessLayer.*;
import BusinessLayer.Exceptions.*;
import Datatypes.Tour;
import Datatypes.TourLog;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;


public class MainViewModel {

    private TourListManager tourListManager;
    private TourLogManager tourLogManager;
    private final Logger log;
    PDFExporter pdfExporter=null;

    private ObservableList<String> tourList;
    private ObservableList<TourLog> tourLogs;
    private ObservableList<String> tourRatings;
    private ObjectProperty<ObservableList<String>> tourListView;

    MapApiHttpHandler mapApiHttpHandler = new MapApiHttpHandler();
    JsonExporter jsonExporter = new JsonExporter();

    private final StringProperty tourName = new SimpleStringProperty("");
    private final StringProperty tourDistance = new SimpleStringProperty("");
    private final StringProperty tourDescription = new SimpleStringProperty("");
    private final StringProperty routeInformation = new SimpleStringProperty("");
    private final StringProperty fromDestination = new SimpleStringProperty("");
    private final StringProperty toDestination = new SimpleStringProperty("");
    private final StringProperty searchField = new SimpleStringProperty("");

    private final StringProperty logAuthor = new SimpleStringProperty("");
    private final StringProperty logReport = new SimpleStringProperty("");
    private final StringProperty logDistance = new SimpleStringProperty("");
    private final StringProperty logDuration = new SimpleStringProperty("");



    private final StringProperty logSpeed = new SimpleStringProperty("");
    private final StringProperty logRemarks = new SimpleStringProperty("");
    private final StringProperty logJoule = new SimpleStringProperty("");
    private final StringProperty logWeather = new SimpleStringProperty("");

    private final ObjectProperty<javafx.scene.image.Image> tourImage = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<TourLog>> tourLogsTable = new SimpleObjectProperty<>();



    public MainViewModel() throws TourLogManagerException, TourListManagerException, MapApiHandlerException, JsonExporterException {
        Configurator.initialize(null, "TourPlannerLog4j.conf.xml");
        log = LogManager.getLogger(MainViewModel.class);
        tourListManager = new TourListManager();
        tourLogManager = new TourLogManager();
        tourList = FXCollections.observableArrayList(tourListManager.getTours());
        tourListView = new SimpleObjectProperty<>(tourList);
        tourRatings = FXCollections.observableArrayList(new ArrayList<>(List.of("1","2","3","4","5")));
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

    public StringProperty searchFieldProperty(){
        System.out.println("VM: init search field");
        return searchField;
    }


    public StringProperty logAuthorProperty() {
        return logAuthor;
    }


    public StringProperty logReportProperty() {
        return logReport;
    }


    public StringProperty logDistanceProperty() {
        return logDistance;
    }

    public StringProperty logDurationProperty() {
        return logDuration;
    }


    public StringProperty logSpeedProperty() {
        return logSpeed;
    }



    public StringProperty logRemarksProperty() {
        return logRemarks;
    }



    public StringProperty logJouleProperty() {
        return logJoule;
    }


    public StringProperty logWeatherProperty() {
        return logWeather;
    }

    public ObjectProperty<javafx.scene.image.Image> tourImageProperty(){
        System.out.println("Image init");
        return tourImage;
    }

    public ObjectProperty<ObservableList<TourLog>> tourLogsTableProperty(){
        System.out.println("TableView init");
        return tourLogsTable;
    }

    public ObservableList<String> getTourRatingsList(){
        return tourRatings;
    }



    /*public Property tourListProperty() {
        System.out.println("VM: get Tour ListView");
        return tourListView;
    }*/

    public ObservableList<String> getTourList() {
        return tourList;
    }

    public ObservableList<TourLog> getTourLogs() {
        return tourLogs;
    }

    public void addTour() throws TourListManagerException {
        String newTourName=tourListManager.generateTourRandomName();
        tourListManager.addTour(newTourName);
        tourList.add(newTourName);
        log.debug("MVM Tour Insertion");
    }

    public void deleteTour(String item) throws TourListManagerException {
        tourListManager.deleteTour(item);
        tourList.remove(item);
        log.debug("MVM Tour Deletion");
    }

    public void updateTour(String item) throws TourListManagerException {

        String tourName=this.tourName.getValue();
        String tourDescription=this.tourDescription.getValue();
        String routeInformation=this.routeInformation.getValue();
        double tourDistance= Double.parseDouble(this.tourDistance.getValue());
        tourListManager.updateTour(item,tourDescription,tourName,routeInformation,tourDistance);
        if (tourName.equals(item)){
            return;
        }
        int curr_pos=tourList.indexOf(item);
        tourList.set(curr_pos,tourName);
        log.debug("MVM Tour Updated");
    }

    private void setTourPicture(String from,String to,String tourName) throws MapApiHandlerException {
        Image srcGif = new Image("file:src/main/resources/View/loading_2.gif");
        tourImage.set(srcGif);
        mapApiHttpHandler.sendMapApiRequest(tourImage,from,to,tourName);
        log.debug("MVM send TourPicture request");
    }

    public void displayTourAttributes(String item) throws TourListManagerException {
        if (item==null){
            return;
        }
        Tour tourDetails=tourListManager.getTourAttributes(item);
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

    public void displayTourRoute(String item) throws TourListManagerException, MapApiHandlerException {
        if (item==null){
            return;
        }
        Tour tourDetails=tourListManager.getTourAttributes(item);
        String routeFrom=tourDetails.getTourFrom();
        String routeTo=tourDetails.getTourTo();
        if (routeFrom==null || routeTo==null){
            fromDestination.set("");
            toDestination.set("");
            return;
        }
        fromDestination.set(routeFrom);
        toDestination.set(routeTo);
        log.info("set TourRoute for Tour");
        setTourPicture(fromDestination.get(),toDestination.get(),tourDetails.getTourName());
        log.debug("start AsyncCall for TourImage");
    }

    public void updateTourRoute(String item) throws TourListManagerException, MapApiHandlerException {
        if (item==null){
            return;
        }
        String routeFrom=fromDestination.get();
        String routeTo=toDestination.get();
        tourListManager.updateTourRoute(item,routeFrom,routeTo);
        setTourPicture(routeFrom,routeTo,item);
        log.debug("MVM update TourRoute");
    }

    public void exportPdf(String item) throws PDFExporterException {
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

    public void searchTours() throws TourListManagerException {
        List<String> tours=tourListManager.fullTextSearch(searchField.get());
        if(searchField.get().isEmpty()){
            tours = tourListManager.getTours();
            tourList.clear();
            tourList.addAll(tours);
            return;
        }
        tourList.clear();
        tourList.addAll(tours);
    }

    public void addTourLog(String item) throws TourLogManagerException {
        if (item != null){
            tourLogManager.addTourLog(item);
            getAllTourLogs(item);
        }
    }

    public void deleteTourLog(String item,String tableSelection) throws TourLogManagerException {
        if (tableSelection != null && item != null){
            tourLogManager.deleteTourLog(tableSelection);
            getAllTourLogs(item);
        }
    }

    public void getAllTourLogs(String item) throws TourLogManagerException {
        if (item != null){
            tourLogs=FXCollections.observableArrayList(tourLogManager.getAllTourLogs(item));
            tourLogsTable.set(tourLogs);
        }
    }

    public void displayTourLog(String item) throws TourLogManagerException {
        if (item != null){
            TourLog tourLog = tourLogManager.getTourLog(item);
            if (tourLog == null){
                return;
            }
            else {
                logAuthor.set(tourLog.getAuthor());
                logReport.set(tourLog.getLogReport());
                logDistance.set(String.valueOf(tourLog.getTraveledDistance()));
                logDuration.set(String.valueOf(tourLog.getDuration()));
                logRemarks.set(tourLog.getRemarks());
                logJoule.set(String.valueOf(tourLog.getJoule()));
                logWeather.set(tourLog.getWeather());
                logSpeed.set(String.valueOf(tourLog.getAvgSpeed()));
            }
        }
    }

    public void updateTourLog(String item,String timestamp,int rating) throws TourLogManagerException {
        if (item.equals("")){
            return;
        }
        TourLog tourLog = new TourLog();
        tourLog.setAuthor(logAuthor.get());
        tourLog.setLogReport(logReport.get());
        tourLog.setTraveledDistance(Double.valueOf(logDistance.get()));
        tourLog.setDuration(Double.valueOf(logDuration.get()));
        tourLog.setRemarks(logRemarks.get());
        tourLog.setJoule(Integer.valueOf(logJoule.get()));
        tourLog.setWeather(logWeather.get());
        tourLog.setRating(rating);
        tourLog.setAvgSpeed(Double.valueOf(logSpeed.get()));
        tourLogManager.updateTourLog(timestamp,tourLog);
        getAllTourLogs(item);
    }

    public void exportJson(String item) throws JsonExporterException {
        jsonExporter.exportJson(item);
    }


}
