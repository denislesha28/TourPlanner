package TourPlanner;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainViewModel {
    private final StringProperty input = new SimpleStringProperty("");
    private final StringProperty output = new SimpleStringProperty("Hello VM!");
    private Model model=new Model();

    public StringProperty inputProperty() {
        System.out.println("VM: get input field");
        return input;
    }

    public StringProperty outputProperty() {
        System.out.println("VM: get output field");
        return output;
    }

    public void calculateDestination() {
        System.out.println("VM: print Destination");
        String destination=this.input.get();
        this.output.set(destination);
        model.setDestination(destination);
        this.input.set("");

    }
}
