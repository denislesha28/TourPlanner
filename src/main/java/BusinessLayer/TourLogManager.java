package BusinessLayer;

import DataAccessLayer.Model;
import Datatypes.Tour;
import Datatypes.TourLog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TourLogManager {
    private Model model;

    public TourLogManager() throws SQLException, IOException {
        model=Model.getModelInstance();
    }

    public TourLogManager(boolean test) {
        if (test) {
            model = Model.getTestModelInstance();
        }
    }

    public void addTourLog(String tourName) throws SQLException {
        model.addTourLog(tourName);
    }

    public void deleteTourLog(String timestamp) throws SQLException {
        model.deleteTourLog(timestamp);
    }

    public List<TourLog> getAllTourLogs(String tourName) throws SQLException {
        if(model.getAllTourLogs(tourName)==null){
            return Collections.emptyList();
        }
        return model.getAllTourLogs(tourName);
    }

    public TourLog getTourLog (String timestamp) throws SQLException {
        return model.getTourLog(timestamp);
    }
}
