package MainPackage;

import java.util.ArrayList;
import java.util.List;

public class Model {


    private String destination;
    private double durationMin;
    private List<String> tours;
    public Model(){
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

}
