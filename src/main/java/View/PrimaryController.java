package View;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.itextpdf.text.DocumentException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private UserInputValidator userInputValidator;


    public PrimaryController() throws SQLException, IOException {
        System.out.println("Controller generated");
        Configurator.initialize(null, "TourPlannerLog4j.conf.xml");
        log = LogManager.getLogger(PrimaryController.class);
        userInputValidator = new UserInputValidator();

    }


    @FXML
    public void addTour(ActionEvent actionEvent) throws SQLException {
        System.out.println("Controller generate new Tour");
        viewModel.addTour();
    }

    @FXML
    public void deleteTour(ActionEvent actionEvent)throws SQLException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        System.out.println("Controller deleting Tour "+item);
        viewModel.deleteTour(item);

    }

    @FXML
    public void updateTour(ActionEvent actionEvent)throws SQLException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        System.out.println("Controller updating Tour "+item);
        boolean tourName = userInputValidator.validateInputText(this.tourName);
        boolean routeInformation = userInputValidator.validateInputText(this.routeInformation);
        boolean tourDescription = userInputValidator.validateInputText(this.tourDescription);
        if(tourName && routeInformation && tourDescription ) {
            viewModel.updateTour(item);
        }

    }

    @FXML
    public void displayTourInfo(Event event) throws SQLException, URISyntaxException, IOException, ExecutionException, InterruptedException {
        displayTourDetails(event);
        displayTourRoute(event);
    }

    @FXML
    public void displayTourDetails(Event actionEvent) throws SQLException {
        System.out.println("Showing Details for selected Element");
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        viewModel.displayTourAttributes(item);
    }

    @FXML
    public void displayTourRoute(Event event) throws URISyntaxException, IOException, ExecutionException, InterruptedException, SQLException {
        if (routeTab==null) {
            return;
        }
        if (routeTab.isSelected()) {
            String item = (String) tourList.getSelectionModel().getSelectedItem();
            viewModel.displayTourRoute(item);
        }
    }

    @FXML
    public void updateTourRoute(ActionEvent actionEvent) throws SQLException, URISyntaxException, IOException, ExecutionException, InterruptedException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        viewModel.updateTourRoute(item);
    }

    @FXML
    public void exportPdf(ActionEvent actionEvent) throws SQLException, DocumentException, IOException, URISyntaxException, ExecutionException, InterruptedException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        viewModel.exportPdf(item);
    }

    @FXML
    public void searchTours(ActionEvent actionEvent) throws SQLException {
        viewModel.searchTours();

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.debug("Binding all fields to mainViewModel");
        System.out.println("Controller init/created");
        tourList.setItems(viewModel.tourList);
        tourName.textProperty().bindBidirectional(viewModel.tourNameProperty());
        tourDistance.textProperty().bindBidirectional(viewModel.tourDistanceProperty());
        tourDescription.textProperty().bindBidirectional(viewModel.tourDescriptionProperty());
        routeInformation.textProperty().bindBidirectional(viewModel.routeInformationProperty());
        fromDestination.textProperty().bindBidirectional(viewModel.fromDestinationProperty());
        toDestination.textProperty().bindBidirectional(viewModel.toDestinationProperty());
        tourImage.imageProperty().bindBidirectional(viewModel.tourImageProperty());
        searchField.textProperty().bindBidirectional(viewModel.searchFieldProperty());

    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


}
