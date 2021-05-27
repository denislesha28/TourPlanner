package TestingPackage;

import DataAccessLayer.Tour;
import DataAccessLayer.TourListManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class TourListManagerTest {

    TourListManager tourListManager=TourListManager.getTourListManagerInstance();

    @Test
    public void testTourAdd(){
        //Arrange
        //Act
        tourListManager.addTour(new Tour("testTour1"));
        String tour=tourListManager.getToursUI().get(tourListManager.getToursUI().size()-1);
        tourListManager.deleteTour("testTour1");
        //Assert
        Assert.assertEquals("testTour1",tour);
    }

    @Test
    public void testGetTour(){
        //Arrange
        //Act
        tourListManager.addTour(new Tour("testTour1"));
        HashMap<String,String> actualTour=tourListManager.getTour("testTour1");
        tourListManager.deleteTour("testTour1");
        //Assert
        Assert.assertEquals("testTour1",actualTour.get("tourName"));
    }

    @Test
    public void testGetNonExistingTour(){
        HashMap<String,String> actualTour=tourListManager.getTour("testTour1");
        //Assert
        Assert.assertEquals(null,actualTour);
    }

    @Test
    public void testTourDelete(){
        //Arrange
        //Act
        tourListManager.addTour(new Tour("testTour1"));
        tourListManager.deleteTour("testTour1");
        HashMap<String,String> tour=tourListManager.getTour("testTour1");
        //Assert
        Assert.assertEquals(null,tour);
    }

    @Test
    public void test_NonExisting_TourDelete(){
        //Arrange
        //Act
        boolean isSuccessful=tourListManager.deleteTour("testTour1");
        //Assert
        Assert.assertEquals(false,isSuccessful);
    }

    @Test
    public void test_NonExisting_TourUpdate(){
        //Arrange
        //Act
        boolean isSuccessful=tourListManager.updateTour("testTour1","test",
                "newTourTest","test",111);
        //Assert
        Assert.assertEquals(false,isSuccessful);
    }

    @Test
    public void testTourUpdate(){
        //Arrange
        //Act
        tourListManager.addTour(new Tour("testTour1"));
        boolean isSuccessful=tourListManager.updateTour("testTour1","test",
                "newTourTest","test",111);
        HashMap<String,String> updatedTour= tourListManager.getTour("newTourTest");
        //Assert
        Assert.assertEquals(true,isSuccessful);
        Assert.assertEquals("newTourTest",updatedTour.get("tourName"));
        Assert.assertEquals("test",updatedTour.get("routeInformation"));
        Assert.assertEquals("test",updatedTour.get("tourDescription"));
        Assert.assertEquals("111.0",updatedTour.get("tourDistance"));
        tourListManager.deleteTour("testTour1");
    }

    @Test
    public void testTourRouteUpdate(){
        //Arrange
        TourListManager tourListManager=TourListManager.getTourListManagerInstance();
        //Act
        tourListManager.addTour(new Tour("testTour1"));
        boolean isSuccessful=tourListManager.updateTourRoute("testTour1","Wien","Berlin");
        HashMap<String,String> updatedTour= tourListManager.getTour("testTour1");
        //Assert
        Assert.assertEquals(true,isSuccessful);
        Assert.assertEquals("Wien",updatedTour.get("from"));
        Assert.assertEquals("Berlin",updatedTour.get("to"));
        tourListManager.deleteTour("testTour1");
    }

    @Test
    public void test_NonExisting_TourRouteUpdate(){
        //Arrange
        //Act
        boolean isSuccessful=tourListManager.updateTourRoute("testTour1","Wien","Berlin");
        //Assert
        Assert.assertEquals(false,isSuccessful);
    }


}
