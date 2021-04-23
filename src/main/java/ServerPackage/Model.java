package ServerPackage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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

    public String generateTourRandomName() {
        while (true) {
            String randomPart=generateRandomString();
            String tourName="Tour_"+randomPart;
            if (!tourListManager.containsTour(tourName)){
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

        return generatedString;
    }

}
