package ServerPackage;

public class Tour {

    private int tourID;
    private String tourName;
    private double tourDistance;
    private String tourDescription;
    private String routeInformation;

    Tour(String tourName){
        this.tourName=tourName;
        this.tourID=0;
        this.tourDistance=0;
        this.tourDescription="";
        this.routeInformation="";
    }

    Tour(int tourID,double tourDistance,
         String tourName,String tourDescription,String routeInformation){
        this.tourID=tourID;
        this.tourName=tourName;
        this.tourDistance=tourDistance;
        this.tourDescription=tourDescription;
        this.routeInformation=routeInformation;
    }


    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public double getTourDistance() {
        return tourDistance;
    }

    public void setTourDistance(double tourDistance) {
        this.tourDistance = tourDistance;
    }

    public String getTourDescription() {
        return tourDescription;
    }

    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
    }

    public String getRouteInformation() {
        return routeInformation;
    }

    public void setRouteInformation(String routeInformation) {
        this.routeInformation = routeInformation;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "tourID=" + tourID +
                ", tourName='" + tourName + '\'' +
                ", tourDistance=" + tourDistance +
                ", tourDescription='" + tourDescription + '\'' +
                ", routeInformation='" + routeInformation + '\'' +
                '}';
    }



}
