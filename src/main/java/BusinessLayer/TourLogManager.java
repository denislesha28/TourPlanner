package BusinessLayer;

import BusinessLayer.Exceptions.TourLogManagerException;
import DataAccessLayer.Exceptions.ModelOperationException;
import DataAccessLayer.Model;
import Datatypes.Tour;
import Datatypes.TourLog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TourLogManager {
    private Model model;

    public TourLogManager() throws  TourLogManagerException {
        try {
            model=Model.getModelInstance();
        } catch (ModelOperationException e) {
            throw new TourLogManagerException("Could not get DAL ModelInterface",e);
        }
    }

    public TourLogManager(boolean test) {
        if (test) {
            model = Model.getTestModelInstance();
        }
    }

    public void addTourLog(String tourName) throws TourLogManagerException {
        try {
            model.addTourLog(tourName);
        } catch (ModelOperationException e) {
            throw new TourLogManagerException("Could not add TourLog",e);
        }
    }

    public void deleteTourLog(String timestamp) throws TourLogManagerException {
        try {
            model.deleteTourLog(timestamp);
        } catch (ModelOperationException e) {
            throw new TourLogManagerException("Could not delete TourLog",e);
        }
    }

    public List<TourLog> getAllTourLogs(String tourName) throws TourLogManagerException {
        try {
            if(model.getAllTourLogs(tourName)==null){
                return Collections.emptyList();
            }
            return model.getAllTourLogs(tourName);
        } catch (ModelOperationException e) {
            throw new TourLogManagerException("Could not get All TourLogs for selected Tour",e);
        }
    }

    public TourLog getTourLog (String timestamp) throws TourLogManagerException {
        try {
            return model.getTourLog(timestamp);
        } catch (ModelOperationException e) {
            throw new TourLogManagerException("Could not get TourLog",e);
        }
    }

    public void updateTourLog (String timestamp, TourLog tourLog) throws TourLogManagerException {
        try {
            model.updateTourLog(timestamp,tourLog);
        } catch (ModelOperationException e) {
            throw new TourLogManagerException("Could not update TourLog",e);
        }
    }
}
