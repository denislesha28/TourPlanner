package mainPackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable {

    // custom ViewModel
    public MainViewModel viewModel = new MainViewModel();

    // fx:id and use intelliJ to create field in controller
    public TextField InputTextField;
    public TextField OutputDestinationField;
    public TextField OutputDurationField;

    public PrimaryController()
    {
        System.out.println("Controller generated");
    }

    @FXML
    public void calculateDestination(ActionEvent actionEvent) {
        System.out.println("Controller generate Destination");
        viewModel.calculateDestination();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Controller init/created");
        InputTextField.textProperty().bindBidirectional(viewModel.inputProperty());
        OutputDestinationField.textProperty().bindBidirectional(viewModel.outputDestinationProperty());
        OutputDurationField.textProperty().bindBidirectional(viewModel.outputDurationProperty());
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
