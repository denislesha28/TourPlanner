package ServerPackage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public void addTour(Tour Tour){ tours.add(Tour); }

    public boolean containsTour(String tourName) {
        for (Tour tour : tours ){
            if (tourName.equals(tour.getTourName())){
                return true;
            }
        }
        return false;
    }

    public void deleteTour(String tourName){
        for (Tour tour : tours ){
            if (tourName.equals(tour.getTourName())){
                tours.remove(tour);
            }
        }
    }


}
