package ServerPackage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TourListManager {

    private List<Tour> tours;
    private static TourListManager instance=null;

    //public Tour getTour
    TourListManager(){
        tours=new ArrayList<>();
    }

    public static TourListManager getTourListManagerInstance(){
        if(instance==null){
            instance=new TourListManager();
        }
        return  instance;
    }


    public List<String> getTours() {
        List<String> tourNames=new ArrayList<String>();
        for (Tour tour : tours ){
            tourNames.add(tour.getTourName());
        }
        return tourNames;
    }

    public boolean addTour(Tour Tour){
        if (tours.contains(Tour)){
            return false;
        }
        tours.add(Tour);
        return true;
    }

    public boolean containsTour(String tourName) {
        for (Tour tour : tours ){
            if (tourName.equals(tour.getTourName())){
                return true;
            }
        }
        return false;
    }

    public boolean deleteTour(String tourName){
        Tour toRemove = null;
        for (Tour tour : tours ){
            if (tourName.equals(tour.getTourName())){
                toRemove=tour;
            }
        }
        if(toRemove==null){
            return false;
        }
        tours.remove(toRemove);
        return true;
    }

    public HashMap<String,String> getTour(String tourName){
        HashMap<String,String> tourDetails = new HashMap<String, String>();
        for (Tour tour : tours ){
            if (tourName.equals(tour.getTourName())){
                tourDetails.put("tourName", tour.getTourName());
                tourDetails.put("tourDescription", tour.getTourDescription());
                tourDetails.put("tourDistance", String.valueOf(tour.getTourDistance()));
                tourDetails.put("routeInformation", tour.getRouteInformation());
                return tourDetails;
            }
        }
        return null;
    }

    public boolean updateTour(String actualTourName,String tourDescription, String desTourName
            ,String routeInformation, double tourDistance){
        for (Tour tour : tours ){
            if (actualTourName.equals(tour.getTourName())){
                tour.setTourName(desTourName);
                tour.setTourDescription(tourDescription);
                tour.setTourDistance(tourDistance);
                tour.setRouteInformation(routeInformation);
                return true;
            }
        }
        return false;
    }


}
