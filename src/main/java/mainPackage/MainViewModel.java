package mainPackage;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class MainViewModel {

    private Model model=new Model();
    private ObservableList<String> tourList = FXCollections.observableArrayList(model.getTours());
    private final ObjectProperty<ObservableList<String>> tourListView = new SimpleObjectProperty<>(tourList);




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
