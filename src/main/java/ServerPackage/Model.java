package ServerPackage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Model {


    private String destination;
    private double durationMin;
    private List<String> tours;
    private BackendTourManager backendTourManager;
    public Model() throws SQLException, IOException {

        backendTourManager=new BackendTourManager();
        tours=new ArrayList<>();
        tours.add("TourB");
        tours.add("TourC");

    }

    public List<String> getTours() {
        return tours;
    }

    public void addTour(String Tour){
        tours.add(Tour);
    }

    public void deleteTour(String Tour){ tours.remove(Tour); }

    public void setTours(List<String> tours) {
        this.tours = tours;
    }

    public double getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(double durationMin) {
        this.durationMin = durationMin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void saveTourBackend(String tourName,String tourDescription,
                                String routeInformation, double tourDistance) throws SQLException {
        backendTourManager.createTour(tourName,tourDescription,routeInformation,tourDistance);
    }

    public HashMap<String,String> getTourDetails(int tourID,String tourName) throws SQLException {
        return backendTourManager.getTourDetails(tourID,tourName);
    }

}
