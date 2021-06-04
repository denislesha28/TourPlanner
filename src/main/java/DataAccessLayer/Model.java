package DataAccessLayer;

import DataAccessLayer.Database.BackendTourLogManager;
import DataAccessLayer.Database.BackendTourManager;
import DataAccessLayer.Local.LocalTourList;
import Datatypes.Tour;
import Datatypes.TourLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class Model {

    //private List<String> tours;
    private final Logger log = LogManager.getLogger(Model.class);;
    private BackendTourManager backendTourManager;
    private LocalTourList localTourList;
    private BackendTourLogManager backendTourLogManager;
    private static Model instance=null;
    private static Model testInstance=null;


    private Model() throws SQLException, IOException {
        localTourList = LocalTourList.getTourListManagerInstance();
        backendTourManager=new BackendTourManager();
        backendTourManager.getAllToursFromBackend(localTourList);
        backendTourLogManager = new BackendTourLogManager();
        log.debug("Tours pulled from Database and saved locally");
        log.debug("DAL Layer logic instantiated");
        //tours=new ArrayList<>();
    }

    private Model(boolean Test){
        if (Test){
            localTourList = LocalTourList.getTourListManagerInstance();
            backendTourManager=null;
        }
    }


    public static Model getModelInstance() throws SQLException, IOException {
        if(instance==null){
            instance=new Model();
        }
        return instance;
    }

    public static Model getTestModelInstance() {
        if(testInstance==null){
            testInstance=new Model(true);
        }
        return testInstance;
    }


    public List<String> getTours() {
        log.debug("DAL Layer return all Tours");
        return localTourList.getToursUI();
    }

    public List<Tour> getToursDetails(){
        return localTourList.getTours();
    }

    public void addTour(String tourName) throws SQLException {
        localTourList.addTour(new Tour(tourName));
        backendTourManager.createTour(tourName);
        log.debug("DAL Layer add Tour in Backend");
    }

    public void deleteTour(String tourName) throws SQLException {
        if (tourName==null){
            return;
        }
        localTourList.deleteTour(tourName);
        backendTourManager.deleteTour(tourName);
        log.debug("DAL Layer delete Tour in Backend");
    }

    public Tour getTourDetails(int tourID,String tourName) throws SQLException {
        Tour returnTour= localTourList.getTour(tourName);
        if(returnTour != null){
            return returnTour;
        }
        log.debug("DAL Layer return TourDetails unconditionally");
        return backendTourManager.getTourDetails(tourID,tourName);

    }

    public void updateTour(String actualTourName,String tourDescription, String desTourName
            ,String routeInformation, double tourDistance) throws SQLException {
        backendTourManager.updateTour(actualTourName,tourDescription,desTourName,
                routeInformation,tourDistance);
        localTourList.updateTour(actualTourName,tourDescription,desTourName,
                routeInformation,tourDistance);
        log.debug("DAL Layer update TourDetails unconditionally");
        backendTourManager.updateTourVectorToken(desTourName);
        log.debug("Update Vector for Tour Indexing");
    }

    public String generateTourRandomName() {
        while (true) {
            String randomPart=generateRandomString();
            String tourName="Tour_"+randomPart;
            if (!localTourList.containsTour(tourName)){
                log.info("Generated random name for new Tour");
                return tourName;
            }
        }
    }

    private String generateRandomString(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        log.debug("Generated random String");
        return generatedString;
    }

    public void updateTourRoute(String tourName,String from,String to) throws SQLException {
        localTourList.updateTourRoute(tourName,from,to);
        backendTourManager.updateTourRoute(tourName,from,to);
        log.debug("DAL Layer update TourRoute unconditionally");
        backendTourManager.updateTourVectorToken(tourName);
        log.debug("Update Vector for Tour Indexing");
    }

    public List<String> fullTextSearch(String input) throws SQLException {
        List<String> searchedTours = backendTourManager.getToursFromSearch(input);
        return searchedTours;
    }

    public void updateDistance(String tourName,Double distance) throws SQLException {
        localTourList.updateTourDistance(tourName,distance);
        backendTourManager.updateTourDistance(tourName,distance);
    }

    public void addTourLog(String tourName) throws SQLException {
        backendTourLogManager.addTourLog(backendTourManager.getTourID(tourName));
    }

    public void deleteTourLog(String timestamp) throws SQLException {
        backendTourLogManager.deleteTourLog(timestamp);
    }

    public List<TourLog> getAllTourLogs(String tourName) throws SQLException {
        return backendTourLogManager.getAllTourLogs(backendTourManager.getTourID(tourName));
    }

    public TourLog getTourLog(String timestamp) throws SQLException {
        return backendTourLogManager.getTourLog(timestamp);
    }

    public void updateTourLog(String timestamp,TourLog tourLog) throws SQLException {
        backendTourLogManager.updateTourLog(timestamp,tourLog);
        backendTourLogManager.updateTourLogVector(timestamp,tourLog);
    }


}
