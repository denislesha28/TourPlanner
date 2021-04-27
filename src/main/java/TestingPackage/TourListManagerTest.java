package TestingPackage;

import ServerPackage.Tour;
import ServerPackage.TourListManager;
import org.junit.Assert;
import org.junit.Test;

import java.security.PublicKey;
import java.util.HashMap;

public class TourListManagerTest {

    @Test
    public void testAddTour(){
        //Arrange
        TourListManager tourListManager=TourListManager.getTourListManagerInstance();
        //Act
        tourListManager.addTour(new Tour("testTour1"));
        HashMap<String,String> tour=tourListManager.getTour("testTour1");
        //Assert
        Assert.assertEquals("testTour1",tour.get("tourName"));
    }

    @Test
    public void testDeleteTour(){
        //Arrange
        TourListManager tourListManager=TourListManager.getTourListManagerInstance();
        //Act
        tourListManager.addTour(new Tour("testTour1"));
        tourListManager.deleteTour("testTour1");
        HashMap<String,String> tour=tourListManager.getTour("testTour1");
        //Assert
        Assert.assertEquals(null,tour);
    }

    @Test
    public void testFalseDeleteTour(){
        //Arrange
        TourListManager tourListManager=TourListManager.getTourListManagerInstance();
        //Act
        boolean validation=tourListManager.deleteTour("testTour1");
        //Assert
        Assert.assertEquals(false,validation);
    }

    @Test
    public void testFalseUpdateTour(){
        //Arrange
        TourListManager tourListManager=TourListManager.getTourListManagerInstance();
        //Act
        boolean validation=tourListManager.updateTour("testTour1","test",
                "newTourTest","test",111);
        //Assert
        Assert.assertEquals(false,validation);
    }

    @Test
    public void testUpdateTour(){
        //Arrange
        TourListManager tourListManager=TourListManager.getTourListManagerInstance();
        //Act
        tourListManager.addTour(new Tour("testTour1"));
        boolean validation=tourListManager.updateTour("testTour1","test",
                "newTourTest","test",111);
        HashMap<String,String> updatedTour= tourListManager.getTour("newTourTest");
        //Assert
        Assert.assertEquals(true,validation);
        Assert.assertEquals("newTourTest",updatedTour.get("tourName"));
        Assert.assertEquals("test",updatedTour.get("routeInformation"));
        Assert.assertEquals("test",updatedTour.get("tourDescription"));
        Assert.assertEquals("111.0",updatedTour.get("tourDistance"));
        tourListManager.deleteTour("testTour1");
    }


}
