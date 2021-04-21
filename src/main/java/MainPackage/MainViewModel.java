package MainPackage;

import ServerPackage.Model;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;

public class MainViewModel {

    private Model model=new Model();
    public ObservableList<String> tourList = FXCollections.observableArrayList(model.getTours());
    private final ObjectProperty<ObservableList<String>> tourListView = new SimpleObjectProperty<>(tourList);

    public MainViewModel() throws SQLException, IOException {
    }
    // define cell item type <> insteadd of string



    public Property tourListProperty() {
        System.out.println("VM: get Tour ListView");
        return tourListView;
    }


    public void addTour() throws SQLException {
        model.addTour("TourA");
        tourList.add("TourA");
        model.saveTourBackend("TourA","test","test",100);
        this.tourListView.set(tourList);
    }


}
