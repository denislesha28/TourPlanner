package MainPackage;


import BusinessLayer.MainViewModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
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
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.util.Source;

public class PrimaryController implements Initializable {

    // custom ViewModel
    private final Logger log;
    public MainViewModel viewModel = new MainViewModel();


    // fx:id and use intelliJ to create field in controller
    public ListView tourList;
    public Tab attributesTab;
    public TextField tourName;
    public TextField tourDistance;
    public TextArea tourDescription;
    public TextArea routeInformation;
    public TextField fromDestination;
    public TextField toDestination;
    public ImageView tourImage;
    public Tab routeTab;

    public PrimaryController() throws SQLException, IOException {
        System.out.println("Controller generated");
        Configurator.initialize(null, "TourPlannerLog4j.conf.xml");
        log = LogManager.getLogger(PrimaryController.class);
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
        viewModel.updateTour(item);
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
        if(routeTab.isSelected()) {
            String item = (String) tourList.getSelectionModel().getSelectedItem();
            viewModel.displayTourRoute(item);
        }
    }

    @FXML
    public void updateTourRoute(ActionEvent actionEvent) throws SQLException, URISyntaxException, IOException, ExecutionException, InterruptedException {
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        viewModel.updateTourRoute(item);
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

    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


}
