package MainPackage;

import ServerPackage.Model;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class MainViewModel {

    private Model model=new Model();
    public ObservableList<String> tourList = FXCollections.observableArrayList(model.getTours());
    private final ObjectProperty<ObservableList<String>> tourListView = new SimpleObjectProperty<>(tourList);
    private final StringProperty tourName = new SimpleStringProperty("");
    private final StringProperty tourDistance = new SimpleStringProperty("");
    private final StringProperty tourDescription = new SimpleStringProperty("");
    private final StringProperty routeInformation = new SimpleStringProperty("");

    public StringProperty tourNameProperty() {
        System.out.println("VM: get input field");
        return tourName;
    }

    public StringProperty tourDistanceProperty() {
        System.out.println("VM: set output Destination field");
        return tourDistance;
    }

    public StringProperty tourDescriptionProperty() {
        System.out.println("VM: set output Time field");
        return tourDescription;
    }

    public StringProperty routeInformationProperty() {
        System.out.println("VM: set output Time field");
        return routeInformation;
    }


    public MainViewModel() throws SQLException, IOException {}


    /*public Property tourListProperty() {
        System.out.println("VM: get Tour ListView");
        return tourListView;
    }*/

    public void addTour() throws SQLException {
        model.addTour("TourA");
        tourList.add("TourA");
        this.tourListView.set(tourList);
    }

    public void displayTourAttributes(String item) throws SQLException {
        HashMap<String,String> tourDetails=model.getTourDetails(0,item);
        if(tourDetails!=null) {
            tourName.set(tourDetails.get("tourName"));
            tourDistance.set(tourDetails.get("tourDistance"));
            tourDescription.set(tourDetails.get("tourDescription"));
            routeInformation.set(tourDetails.get("routeInformation"));
        }else {
            tourName.set("----------");
            tourDistance.set("----------");
            tourDescription.set("----------");
            routeInformation.set("----------");
        }

    }




}
