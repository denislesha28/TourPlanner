package mainPackage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainViewModel {
    private final StringProperty input = new SimpleStringProperty("");
    private final StringProperty outputDestination = new SimpleStringProperty("At home!");
    private final StringProperty outputDuration = new SimpleStringProperty("---");
    private Model model=new Model();

    public StringProperty inputProperty() {
        System.out.println("VM: get input field");
        return input;
    }

    public StringProperty outputDestinationProperty() {
        System.out.println("VM: set output Destination field");
        return outputDestination;
    }

    public StringProperty outputDurationProperty() {
        System.out.println("VM: set output Time field");
        return outputDuration;
    }

    public void calculateDestination() {
        System.out.println("VM: print Destination");
        String destination=this.input.get();
        this.outputDestination.set(destination);
        model.setDestination(destination);
        this.input.set("");
    }

    public void calculateDuration() {
        this.outputDestination.set("23 minutes");
        model.setDurationMin(23.24);
    }
}
