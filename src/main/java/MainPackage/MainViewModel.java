package MainPackage;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainViewModel {

    private Model model=new Model();
    public ObservableList<String> tourList = FXCollections.observableArrayList(model.getTours());
    private final ObjectProperty<ObservableList<String>> tourListView = new SimpleObjectProperty<>(tourList);
    // define cell item type <> insteadd of string



    public Property tourListProperty() {
        System.out.println("VM: get Tour ListView");
        return tourListView;
    }


    public void addTour(){
        model.addTour("TourA");
        tourList.add("TourA");
        this.tourListView.set(tourList);
    }


}
