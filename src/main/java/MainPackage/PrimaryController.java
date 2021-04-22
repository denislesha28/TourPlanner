package MainPackage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable {

    // custom ViewModel
    public MainViewModel viewModel = new MainViewModel();

    // fx:id and use intelliJ to create field in controller
    public ListView tourList;
    public Tab attributesTab;
    public TextField tourName;
    public TextField tourDistance;
    public TextArea tourDescription;
    public TextArea routeInformation;

    public PrimaryController() throws SQLException, IOException {
        System.out.println("Controller generated");
    }

    @FXML
    public void addTour(ActionEvent actionEvent) throws SQLException {
        System.out.println("Controller generate new Tour");
        viewModel.addTour();
    }

    @FXML
    public void displayTourDetails(Event event) throws SQLException {
        System.out.println("Showing Details for selected Element");
        String item=(String) tourList.getSelectionModel().getSelectedItem();
        viewModel.displayTourAttributes(item);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Controller init/created");
        tourList.setItems(viewModel.tourList);
        tourName.textProperty().bindBidirectional(viewModel.tourNameProperty());
        tourDistance.textProperty().bindBidirectional(viewModel.tourDistanceProperty());
        tourDescription.textProperty().bindBidirectional(viewModel.tourDescriptionProperty());
        routeInformation.textProperty().bindBidirectional(viewModel.routeInformationProperty());
        // set cell item type tourList.setCellFactory();
        // no need to bind listview bidirectionally because the listview is and observable list
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


}
