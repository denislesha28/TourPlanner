package BusinessLayer;

import BusinessLayer.Exceptions.TourListManagerException;
import DataAccessLayer.Exceptions.ModelOperationException;
import Datatypes.Tour;
import DataAccessLayer.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TourListManager {

    private Model model;

    public TourListManager() throws TourListManagerException {
        try {
            model=Model.getModelInstance();
        } catch (ModelOperationException e) {
            throw new TourListManagerException("Could not get DAL ModelInterface",e);
        }
    }
    public TourListManager(boolean test) {
        if (test) {
            model = Model.getTestModelInstance();
        }
    }

    public void addTour (String tourName) throws TourListManagerException {
        try {
            model.addTour(tourName);
        } catch (ModelOperationException e) {
            throw new TourListManagerException("Could not add Tour Distance",e);
        }
    }

    public void deleteTour (String tourName) throws TourListManagerException {
        try {
            model.deleteTour(tourName);
        } catch (ModelOperationException e) {
            throw new TourListManagerException("Could not delete Tour",e);
        }
    }

    public void updateTour (String currentTour,String tourDescription,String tourName,String
                            routeInformation, Double tourDistance) throws TourListManagerException {

        try {
            model.updateTour(currentTour,tourDescription,tourName,routeInformation,tourDistance);
        } catch (ModelOperationException e) {
            throw new TourListManagerException("Could not update TourAttributes",e);
        }
    }

    public Tour getTourAttributes(String tourName) throws TourListManagerException {
        Tour tourDetails= null;
        try {
            tourDetails = model.getTourDetails(0,tourName);
        } catch (ModelOperationException e) {
            throw new TourListManagerException("Could not get TourAttributes",e);
        }
        return tourDetails;
    }

    public Tour getTourRoute(String tourName) throws TourListManagerException {
        Tour tourDetails= null;
        try {
            tourDetails = model.getTourDetails(0,tourName);
        } catch (ModelOperationException e) {
            throw new TourListManagerException("Could get TourRoute",e);
        }
        return tourDetails;
    }

    public void updateTourRoute(String tourName,String routeFrom,String routeTo) throws TourListManagerException {
        try {
            model.updateTourRoute(tourName,routeFrom,routeTo);
        } catch (ModelOperationException e) {
            throw new TourListManagerException("Could not update Tour Route",e);
        }
    }

    public List<String> getTours(){
        if(model.getTours()==null){
            return Collections.emptyList();
        }
        return model.getTours();
    }

    public String generateTourRandomName(){
        return model.generateTourRandomName();
    }

    public List<String> fullTextSearch(String input) throws TourListManagerException {
        try {
            return model.fullTextSearch(input);
        } catch (ModelOperationException e) {
            throw new TourListManagerException("Could not perform fullTextSearch",e);
        }
    }

    public void updateTourDistance(String tourName,Double distance) throws TourListManagerException {
        try {
            model.updateDistance(tourName,distance);
        } catch (ModelOperationException e) {
           throw new TourListManagerException("Could not update Tour Distance",e);
        }
    }

    public List<Tour> getAllTourAttributes(){
       return model.getAllToursDetails();
    }
}
