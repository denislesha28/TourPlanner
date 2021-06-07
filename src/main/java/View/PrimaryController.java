package View;


import java.net.URL;
import java.util.ResourceBundle;

import BusinessLayer.Exceptions.*;
import Datatypes.InputTypes;
import Datatypes.TourLog;
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


    public PrimaryController() throws TourListManagerException, TourLogManagerException, MapApiHandlerException, JsonExporterException {
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
        System.out.println("Controller deleting Tour ");
        viewModel.deleteTour();

    }

    @FXML
    public void updateTour(ActionEvent actionEvent) throws TourListManagerException {
        System.out.println("Controller updating Tour");
        boolean tourName = userInputValidator.validateInputText(this.tourName, InputTypes.MUST);
        boolean routeInformation = userInputValidator.validateInputText(this.routeInformation, InputTypes.OPTIONAL);
        boolean tourDescription = userInputValidator.validateInputText(this.tourDescription, InputTypes.OPTIONAL);
        if(tourName && routeInformation && tourDescription ) {
            viewModel.updateTour();
        }

    }

    @FXML
    public void displayTourInfo(Event event) throws TourListManagerException, MapApiHandlerException, TourLogManagerException {
        displayTourRoute(event);
        displayTourDetails(event);
        getAllTourLogs(event);

    }

    @FXML
    public void displayTourDetails(Event actionEvent) throws TourListManagerException {
        System.out.println("Showing Details for selected Element");
        viewModel.displayTourAttributes();
    }

    @FXML
    public void displayTourRoute(Event event) throws TourListManagerException, MapApiHandlerException {
        viewModel.displayTourRoute();
    }

    @FXML
    public void updateTourRoute(ActionEvent actionEvent) throws TourListManagerException, MapApiHandlerException {
        viewModel.updateTourRoute();
    }

    @FXML
    public void exportPdf(ActionEvent actionEvent) throws PDFExporterException {
        viewModel.exportPdf();
    }

    @FXML
    public void searchTours(ActionEvent actionEvent) throws TourListManagerException {
        viewModel.searchTours();
    }

    @FXML
    public void addTourLog(ActionEvent actionEvent) throws TourLogManagerException {
        viewModel.addTourLog();
    }

    @FXML
    public void deleteTourLog(ActionEvent actionEvent) throws TourLogManagerException {
        viewModel.deleteTourLog();
    }

    @FXML
    private void getAllTourLogs(Event event) throws TourLogManagerException {
        viewModel.getAllTourLogs();
    }

    @FXML
    private void displayTourLog(Event event) throws TourLogManagerException {
        viewModel.displayTourLog();

    }

    @FXML
    public void updateTourLog(ActionEvent actionEvent) throws TourLogManagerException {

        boolean logAuthor = userInputValidator.validateInputText(this.logAuthor,InputTypes.MUST);
        boolean logReport = userInputValidator.validateInputText(this.logReport,InputTypes.MUST);
        boolean logSpecialRemarks = userInputValidator.validateInputText(this.logRemarks,InputTypes.OPTIONAL);
        boolean logWeather = userInputValidator.validateInputText(this.logWeather,InputTypes.OPTIONAL);
        boolean logDistance = userInputValidator.validateInputText(this.logDistance, InputTypes.NUMBER);
        boolean logDuration = userInputValidator.validateInputText(this.logTotalTime, InputTypes.NUMBER);
        boolean logSpeed = userInputValidator.validateInputText(this.logSpeed, InputTypes.NUMBER);
        if(logAuthor && logSpecialRemarks && logReport && logWeather && logDistance && logDuration && logSpeed){
            viewModel.updateTourLog();
        }

    }

    @FXML
    public void exportJson(ActionEvent actionEvent) throws JsonExporterException{
        viewModel.exportJson();
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

        tourList.getSelectionModel().selectedItemProperty().addListener(viewModel.getSelectedTourChange());
        tourLogsTable.getSelectionModel().selectedItemProperty().addListener(viewModel.getSelectedTourLogChange());

        selectionTab.getSelectionModel().selectedItemProperty().addListener(viewModel.getSelectedTabChange());

        logAuthor.textProperty().bindBidirectional(viewModel.logAuthorProperty());
        logDistance.textProperty().bindBidirectional(viewModel.logDistanceProperty());
        logTotalTime.textProperty().bindBidirectional(viewModel.logDurationProperty());
        logRating.setItems(viewModel.getTourRatingsList());
        logRating.getSelectionModel().selectedItemProperty().addListener(viewModel.getSelectedRatingChange());
        logSpeed.textProperty().bindBidirectional(viewModel.logSpeedProperty());
        logReport.textProperty().bindBidirectional(viewModel.logReportProperty());
        logRemarks.textProperty().bindBidirectional(viewModel.logRemarksProperty());
        logWeather.textProperty().bindBidirectional(viewModel.logWeatherProperty());
        logJoule.textProperty().bindBidirectional(viewModel.logJouleProperty());

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
