package ServerPackage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Model {

    //private List<String> tours;
    private BackendTourManager backendTourManager;
    private TourListManager tourListManager;
    public Model() throws SQLException, IOException {
        tourListManager=TourListManager.getTourListManagerInstance();
        backendTourManager=new BackendTourManager();
        backendTourManager.getAllToursFromBackend(tourListManager);
        //tours=new ArrayList<>();
    }

    public List<String> getTours() {
        return tourListManager.getTours();
    }

    public void addTour(String tourName) throws SQLException {
        tourListManager.addTour(new Tour(tourName));
        backendTourManager.createTour(tourName);
    }

    public void deleteTour(String tourName){ tourListManager.deleteTour(tourName); }

    public HashMap<String,String> getTourDetails(int tourID,String tourName) throws SQLException {
        return backendTourManager.getTourDetails(tourID,tourName);
    }

}
