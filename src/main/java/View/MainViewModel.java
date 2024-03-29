package View;

import BusinessLayer.*;
import BusinessLayer.Exceptions.*;
import BusinessLayer.Exporting.JsonExporter;
import BusinessLayer.Exporting.PDFExporter;
import Datatypes.Tour;
import Datatypes.TourLog;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import javafx.beans.value.ChangeListener;


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
    private final ObjectProperty<SelectionModel> tourListSelectionProperty = new SimpleObjectProperty<>();

    private String selectedListItem=null;
    private TourLog selectedLog=null;
    private int selectedRating=0;
    private Tab selectedTab=null;

    public MainViewModel() throws TourLogManagerException, TourListManagerException, MapApiHandlerException, JsonExporterException {
        Configurator.initialize(null, "TourPlannerLog4j.conf.xml");
        log = LogManager.getLogger(MainViewModel.class);
        tourListManager = new TourListManager();
        tourLogManager = new TourLogManager();
        tourList = FXCollections.observableArrayList(tourListManager.getTours());
        tourListView = new SimpleObjectProperty<>(tourList);
        tourRatings = FXCollections.observableArrayList(new ArrayList<>(List.of("1","2","3","4","5")));
    }


    public ChangeListener<String> getSelectedTourChange(){
        return (observableValue,oldValue,newValue)->{
            selectedListItem = newValue;
        };
    }

    public ChangeListener<TourLog> getSelectedTourLogChange(){
        return (observableValue,oldValue,newValue)->{
            selectedLog = newValue;
        };
    }

    public ChangeListener<Tab> getSelectedTabChange(){
        return (observableValue,oldValue,newValue)->{
            selectedTab = newValue;
        };
    }


    public ChangeListener<String> getSelectedRatingChange(){
        return (observableValue,oldValue,newValue)->{
            selectedRating = Integer.valueOf(newValue);

        };
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

    public ObjectProperty<SelectionModel> tourListSelectionProperty(){
        return tourListSelectionProperty;
    }

    public ObservableList<String> getTourRatingsList(){
        return tourRatings;
    }

    public ObservableList<String> getTourList() {
        return tourList;
    }



    public void addTour() throws TourListManagerException {
        String newTourName=tourListManager.generateTourRandomName();
        tourListManager.addTour(newTourName);
        tourList.add(newTourName);
        log.debug("MVM Tour Insertion");
    }

    public void deleteTour() throws TourListManagerException {
        tourListManager.deleteTour(selectedListItem);
        tourList.remove(selectedListItem);
        log.debug("MVM Tour Deletion");
    }

    public void updateTour() throws TourListManagerException {
        if(selectedListItem == null){
            return;
        }
        String tourName=this.tourName.getValue();
        String tourDescription=this.tourDescription.getValue();
        String routeInformation=this.routeInformation.getValue();
        double tourDistance= Double.parseDouble(this.tourDistance.getValue());
        tourListManager.updateTour(selectedListItem,tourDescription,tourName,routeInformation,tourDistance);
        if (tourName.equals(selectedListItem)){
            return;
        }
        int curr_pos=tourList.indexOf(selectedListItem);
        tourList.set(curr_pos,tourName);
        log.debug("MVM Tour Updated");
    }

    private void setTourPicture(String from,String to,String tourName) throws MapApiHandlerException {
        Image srcGif = new Image("file:src/main/resources/View/loading_2.gif");
        tourImage.set(srcGif);
        mapApiHttpHandler.sendMapApiRequest(tourImage,from,to,tourName);
        log.debug("MVM send TourPicture request");
    }

    public void displayTourAttributes() throws TourListManagerException {
        if (selectedTab == null ||selectedTab.textProperty().get().equals("Description")) {

        }

        if (selectedListItem==null ){
            return;
        }

        Tour tourDetails=tourListManager.getTourAttributes(selectedListItem);
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

    public void displayTourRoute() throws TourListManagerException, MapApiHandlerException {
        if(selectedTab == null || !selectedTab.textProperty().get().equals("Route")){
            return;
        }
        if (selectedListItem==null ){
            return;
        }
        Tour tourDetails=tourListManager.getTourAttributes(selectedListItem);
        String routeFrom=tourDetails.getTourFrom();
        String routeTo=tourDetails.getTourTo();
        if (routeFrom.isEmpty() || routeTo.isEmpty()){
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

    public void updateTourRoute() throws TourListManagerException, MapApiHandlerException {
        if (selectedListItem==null){
            return;
        }
        String routeFrom=fromDestination.get();
        String routeTo=toDestination.get();
        tourListManager.updateTourRoute(selectedListItem,routeFrom,routeTo);
        setTourPicture(routeFrom,routeTo,selectedListItem);
        log.debug("MVM update TourRoute");
    }

    public void exportPdf() throws PDFExporterException {
        if (pdfExporter == null){
            pdfExporter = new PDFExporter();
        }
        if (selectedListItem == null){
            pdfExporter.exportToursPdf();
        }
        else {
            pdfExporter.exportTourPdf(selectedListItem);
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

    public void addTourLog() throws TourLogManagerException {
        if (selectedListItem != null){
            tourLogManager.addTourLog(selectedListItem);
            getAllTourLogs();
        }
    }

    public void deleteTourLog() throws TourLogManagerException {
        if (selectedLog != null && selectedListItem != null){
            tourLogManager.deleteTourLog(selectedLog.getTimestamp());
            getAllTourLogs();
        }
    }

    public void getAllTourLogs() throws TourLogManagerException {
        if (selectedListItem != null){
            tourLogs=FXCollections.observableArrayList(tourLogManager.getAllTourLogs(selectedListItem));
            tourLogsTable.set(tourLogs);
        }
    }

    public void displayTourLog() throws TourLogManagerException {
        if(selectedTab == null || !selectedTab.textProperty().get().equals("Logs")){
            return;
        }
        if (selectedLog != null){
            TourLog tourLog = tourLogManager.getTourLog(selectedLog.getTimestamp());
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

    public void updateTourLog() throws TourLogManagerException {
        if (selectedListItem.equals("")){
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
        tourLog.setRating(selectedRating);
        tourLog.setAvgSpeed(Double.valueOf(logSpeed.get()));
        tourLogManager.updateTourLog(selectedLog.getTimestamp(),tourLog);
        getAllTourLogs();
    }

    public void exportJson() throws JsonExporterException {
        jsonExporter.exportJson(selectedListItem);
    }


}
