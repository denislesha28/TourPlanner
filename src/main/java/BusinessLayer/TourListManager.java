package BusinessLayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TourListManager {

    private List<Tour> tours;
    private final Logger log;
    private static TourListManager instance=null;

    //public Tour getTour
    TourListManager(){
        Configurator.initialize(null, "TourPlannerLog4j.conf.xml");
        log = LogManager.getLogger(TourListManager.class);
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
        log.debug("Getting all local Tours");
        return tourNames;
    }

    public boolean addTour(Tour Tour){
        if (tours.contains(Tour)){
            return false;
        }
        tours.add(Tour);
        log.debug("add Tour to local Tours");
        return true;
    }

    public boolean containsTour(String tourName) {
        for (Tour tour : tours ){
            if (tourName.equals(tour.getTourName())){
                log.debug("Tour was found in local List");
                return true;
            }
        }
        log.debug("Tour was not found in local List");
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
            log.error("Tour was not found in local List");
            return false;
        }
        tours.remove(toRemove);
        log.debug("Tour was removed from local List");
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
                tourDetails.put("from", tour.getTourFrom());
                tourDetails.put("to", tour.getTourTo());
                log.debug("Return TourInfo for Tour");
                return tourDetails;
            }
        }
        log.debug("Tour was not found in local List");
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
                log.debug("Tour Details updated in local List");
                return true;
            }
        }
        log.debug("Tour was not found in local List");
        return false;
    }

    public boolean updateTourRoute(String tourName,String from,String to){
        for (Tour tour : tours ) {
            if (tourName.equals(tour.getTourName())) {
                tour.setTourTo(to);
                tour.setTourFrom(from);
                log.debug("Tour Route updated in local List");
                return true;
            }
        }
        log.debug("Tour was not found in local List");
        return false;
    }


}
