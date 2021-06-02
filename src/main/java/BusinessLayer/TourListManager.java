package BusinessLayer;

import Components.Tour;
import DataAccessLayer.Database.DALFactory;
import DataAccessLayer.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TourListManager {

    private Model model;

    public TourListManager() throws SQLException, IOException {
        model=Model.getModelInstance();
    }
    public TourListManager(boolean test) {
        if (test) {
            model = Model.getTestModelInstance();
        }
    }

    public void addTour (String tourName) throws SQLException {
        model.addTour(tourName);
    }

    public void deleteTour (String tourName) throws SQLException {
        model.deleteTour(tourName);
    }

    public void updateTour (String currentTour,String tourDescription,String tourName,String
                            routeInformation, Double tourDistance) throws SQLException {

        model.updateTour(currentTour,tourDescription,tourName,routeInformation,tourDistance);
    }

    public Tour getTourAttributes(String tourName) throws SQLException {
        Tour tourDetails=model.getTourDetails(0,tourName);
        return tourDetails;
    }

    public Tour getTourRoute(String tourName) throws SQLException {
        Tour tourDetails= model.getTourDetails(0,tourName);
        return tourDetails;
    }

    public void updateTourRoute(String tourName,String routeFrom,String routeTo) throws SQLException {
        model.updateTourRoute(tourName,routeFrom,routeTo);
    }

    public List<String> getTours(){
        return model.getTours();
    }

    public String generateTourRandomName(){
        return model.generateTourRandomName();
    }

    public List<String> fullTextSearch(String input) throws SQLException {
        return model.fullTextSearch(input);
    }

    public void updateTourDistance(String tourName,Double distance) throws SQLException {
        model.updateDistance(tourName,distance);
    }
}
