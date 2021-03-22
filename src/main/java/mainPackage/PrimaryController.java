package mainPackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable {

    // custom ViewModel
    public MainViewModel viewModel = new MainViewModel();

    // fx:id and use intelliJ to create field in controller
    public ListView tourList;

    public PrimaryController()
    {
        System.out.println("Controller generated");
    }

    @FXML
    public void addTour(ActionEvent actionEvent) {
        System.out.println("Controller generate new Tour");
        viewModel.addTour();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Controller init/created");
        tourList.itemsProperty().bindBidirectional(viewModel.tourListProperty());
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
