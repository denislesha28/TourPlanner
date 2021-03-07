package TourPlanner;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

    public class Controller implements Initializable {

        // custom ViewModel
        public MainViewModel viewModel = new MainViewModel();

        // fx:id and use intelliJ to create field in controller
        public TextField InputTextField;
        public TextField OutputDestinationField;
        public TextField OutputDurationField;

        public Controller()
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
    }
