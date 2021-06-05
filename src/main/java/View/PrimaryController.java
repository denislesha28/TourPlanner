package View;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import BusinessLayer.Exceptions.MapApiHandlerException;
import BusinessLayer.Exceptions.PDFExporterException;
import BusinessLayer.Exceptions.TourListManagerException;
import BusinessLayer.Exceptions.TourLogManagerException;
import Datatypes.InputTypes;
import Datatypes.TourLog;
import com.itextpdf.text.DocumentException;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class PrimaryController implements Initializable {

    // custom ViewModel
    private final Logger log;
    public MainViewModel viewModel = new MainViewModel();


    // fx:id and use intelliJ to create field in controller
    public ListView tourList;
    public Tab attributesTab;
    public TextField tourName;
    public TextField tourDistance;
    public TextField searchField;
    public TextArea tourDescription;
    public TextArea routeInformation;
    public TextField fromDestination;
    public TextField toDestination;
    public ImageView tourImage;

    public Tab routeTab;
    public TabPane selectionTab;
    public Tab logTab;

    public TableView tourLogsTable;
    public TableColumn authorColumn;
    public TableColumn dateColumn;
    public TableColumn durationColumn;
    public TableColumn ratingColumn;
    public TableColumn remarkColumn;
    public TableColumn weatherColumn;

    public TextField logAuthor;
    public TextField logDistance;
    public TextField logTotalTime;
    public ChoiceBox logRating;
    public TextField logSpeed;
    public TextArea logReport;
    public TextArea logRemarks;
    public TextField logWeather;
    public TextField logJoule;

    private UserInputValidator userInputValidator;


    public PrimaryController() throws TourListManagerException, TourLogManagerException, MapApiHandlerException {
        System.out.println("Controller generated");
        Configurator.initialize(null, "TourPlannerLog4j.conf.xml");
        log = LogManager.getLogger(PrimaryController.class);
        userInputValidator = new UserInputValidator();

    }


    @FXML
    public void addTour(ActionEvent actionEvent) throws TourListManagerException {
        System.out.println("Controller generate new Tour");
        viewModel.addTour();
    }

    @FXML
    public void deleteTour(ActionEvent actionEvent) throws TourListManagerException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        System.out.println("Controller deleting Tour "+item);
        viewModel.deleteTour(item);

    }

    @FXML
    public void updateTour(ActionEvent actionEvent) throws TourListManagerException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        System.out.println("Controller updating Tour "+item);
        boolean tourName = userInputValidator.validateInputText(this.tourName, InputTypes.MUST);
        boolean routeInformation = userInputValidator.validateInputText(this.routeInformation, InputTypes.OPTIONAL);
        boolean tourDescription = userInputValidator.validateInputText(this.tourDescription, InputTypes.OPTIONAL);
        if(tourName && routeInformation && tourDescription ) {
            viewModel.updateTour(item);
        }

    }

    @FXML
    public void displayTourInfo(Event event) throws TourListManagerException, MapApiHandlerException, TourLogManagerException {
        Tab activeTab = selectionTab.getSelectionModel().getSelectedItem();
        if(activeTab == routeTab){
            displayTourRoute(event);
        }
        else {
            displayTourDetails(event);
        }
        getAllTourLogs(event);

    }

    @FXML
    public void displayTourDetails(Event actionEvent) throws TourListManagerException {
        System.out.println("Showing Details for selected Element");
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        viewModel.displayTourAttributes(item);
    }

    @FXML
    public void displayTourRoute(Event event) throws TourListManagerException, MapApiHandlerException {
        if (routeTab==null) {
            return;
        }
        if (routeTab.isSelected()) {
            String item = (String) tourList.getSelectionModel().getSelectedItem();
            viewModel.displayTourRoute(item);
        }
    }

    @FXML
    public void updateTourRoute(ActionEvent actionEvent) throws TourListManagerException, MapApiHandlerException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        viewModel.updateTourRoute(item);
    }

    @FXML
    public void exportPdf(ActionEvent actionEvent) throws PDFExporterException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        viewModel.exportPdf(item);
    }

    @FXML
    public void searchTours(ActionEvent actionEvent) throws TourListManagerException {
        viewModel.searchTours();
    }

    @FXML
    public void addTourLog(ActionEvent actionEvent) throws TourLogManagerException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        viewModel.addTourLog(item);
    }

    @FXML
    public void deleteTourLog(ActionEvent actionEvent) throws TourLogManagerException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        viewModel.deleteTourLog(item,getTableSelection());
    }

    @FXML
    private void getAllTourLogs(Event event) throws TourLogManagerException {
        if (tourLogsTable==null) {
            return;
        }
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        viewModel.getAllTourLogs(item);
    }

    @FXML
    private void displayTourLog(Event event) throws TourLogManagerException {
        if(logTab.isSelected()) {
            viewModel.displayTourLog(getTableSelection());
        }
    }

    @FXML
    public void updateTourLog(ActionEvent actionEvent) throws TourLogManagerException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        String rating = (String) logRating.getValue();
        boolean logAuthor = userInputValidator.validateInputText(this.logAuthor,InputTypes.MUST);
        boolean logReport = userInputValidator.validateInputText(this.logReport,InputTypes.MUST);
        boolean logSpecialRemarks = userInputValidator.validateInputText(this.logRemarks,InputTypes.OPTIONAL);
        boolean logWeather = userInputValidator.validateInputText(this.logWeather,InputTypes.OPTIONAL);
        boolean logDistance = userInputValidator.validateInputText(this.logDistance, InputTypes.NUMBER);
        boolean logDuration = userInputValidator.validateInputText(this.logTotalTime, InputTypes.NUMBER);
        boolean logSpeed = userInputValidator.validateInputText(this.logSpeed, InputTypes.NUMBER);
        if(logAuthor && logSpecialRemarks && logReport && logWeather && logDistance && logDuration && logSpeed){
            viewModel.updateTourLog(item,getTableSelection(),Integer.valueOf(rating));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.debug("Binding all fields to mainViewModel");
        System.out.println("Controller init/created");
        tourList.setItems(viewModel.getTourList());
        initializeLogTable();
        tourLogsTable.itemsProperty().bindBidirectional(viewModel.tourLogsTableProperty());
        tourName.textProperty().bindBidirectional(viewModel.tourNameProperty());
        tourDistance.textProperty().bindBidirectional(viewModel.tourDistanceProperty());
        tourDescription.textProperty().bindBidirectional(viewModel.tourDescriptionProperty());
        routeInformation.textProperty().bindBidirectional(viewModel.routeInformationProperty());
        fromDestination.textProperty().bindBidirectional(viewModel.fromDestinationProperty());
        toDestination.textProperty().bindBidirectional(viewModel.toDestinationProperty());
        tourImage.imageProperty().bindBidirectional(viewModel.tourImageProperty());
        searchField.textProperty().bindBidirectional(viewModel.searchFieldProperty());

        logAuthor.textProperty().bindBidirectional(viewModel.logAuthorProperty());
        logDistance.textProperty().bindBidirectional(viewModel.logDistanceProperty());
        logTotalTime.textProperty().bindBidirectional(viewModel.logDurationProperty());
        logRating.setItems(viewModel.getTourRatingsList());
        logSpeed.textProperty().bindBidirectional(viewModel.logSpeedProperty());
        logReport.textProperty().bindBidirectional(viewModel.logReportProperty());
        logRemarks.textProperty().bindBidirectional(viewModel.logRemarksProperty());
        logWeather.textProperty().bindBidirectional(viewModel.logWeatherProperty());
        logJoule.textProperty().bindBidirectional(viewModel.logJouleProperty());

    }

    private String getTableSelection(){
        if(tourLogsTable.getSelectionModel().getSelectedCells() == null){
            return "";
        }
        TablePosition tablePosition = (TablePosition) tourLogsTable.getSelectionModel().getSelectedCells().get(0);
        int row = tablePosition.getRow();
        TourLog item = (TourLog) tourLogsTable.getItems().get(row);
        // choose the date column
        TableColumn col = (TableColumn) tourLogsTable.getColumns().get(1);
        String tourLogName = (String) col.getCellObservableValue(item).getValue();
        return tourLogName;
    }

    private void initializeLogTable(){
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        weatherColumn.setCellValueFactory(new PropertyValueFactory<>("weather"));
    }




}
