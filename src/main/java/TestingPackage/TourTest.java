package TestingPackage;

import ServerPackage.Tour;
import org.junit.Assert;
import org.junit.Test;

public class TourTest {

    @Test
    public void createTourTest(){
        //Arrange
        int tourID=1;
        String tourName="testName";
        double tourDistance=111;
        String tourDescription="testDescription";
        String routeInformation="testRoute";
        //Act
        Tour tour=new Tour(tourID,tourDistance,tourName,tourDescription,routeInformation);
        //Assert
        Assert.assertEquals(tourName,tour.getTourName());
        Assert.assertEquals(tourID,tour.getTourID());
        Assert.assertEquals(tourDescription,tour.getTourDescription());
        Assert.assertEquals(routeInformation,tour.getRouteInformation());
    }
}
