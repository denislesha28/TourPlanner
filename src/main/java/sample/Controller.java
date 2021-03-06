package sample;


import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

    public class Controller implements Initializable {

        // create custom viewmodel
        public MainViewModel viewModel = new MainViewModel();

        // add fx:id and use intelliJ to create field in controller
        public TextField InputTextField;
        public TextField OutputTextField;

        public Controller()
        {
            System.out.println("Controller generated");
        }

        @FXML
        public void calculateOutput(ActionEvent actionEvent) {
            System.out.println("Controller calculate");
            viewModel.calculateOutputString();
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            System.out.println("Controller init");
            InputTextField.textProperty().bindBidirectional(viewModel.inputProperty());
            OutputTextField.textProperty().bindBidirectional(viewModel.outputProperty());
        }
    }
