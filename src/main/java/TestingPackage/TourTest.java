package TestingPackage;

import BusinessLayer.Tour;
import org.junit.Assert;
import org.junit.Test;

public class TourTest {
    // Arrange
    int tourID=1;
    String tourName="testName";
    double tourDistance=111;
    String tourDescription="testDescription";
    String routeInformation="testRoute";
    // Act
    Tour tour=new Tour(tourID,tourDistance,tourName,tourDescription,routeInformation);

    @Test
    public void createTourIDTest(){
        //Assert
        Assert.assertEquals(tourID,tour.getTourID());
    }
    @Test
    public void createTourNameTest(){
        Assert.assertEquals(tourName,tour.getTourName());
    }
    @Test
    public void createTourDescriptionTest(){
        Assert.assertEquals(tourDescription,tour.getTourDescription());
    }
    @Test
    public void createTourRouteInfoTest(){
        Assert.assertEquals(routeInformation,tour.getRouteInformation());
    }

}
