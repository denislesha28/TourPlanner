package TestingPackage;

import DataAccessLayer.Model;
import org.junit.Assert;
import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;

public class ModelTest {

    @Test
    public void testToursCreation() throws SQLException, IOException {
        //Arrange
        Model dataModelBackend = Model.getModelInstance();
        String tourTest="TestTour";
        List<String> tourList;
        //Act
        dataModelBackend.addTour(tourTest);
        tourList=dataModelBackend.getTours();
        dataModelBackend.deleteTour(tourTest); // Cleanup
        //Assert
        Assert.assertEquals(tourTest,tourList.get(tourList.size()-1));
    }

    @Test
    public void testRandomGeneration() {
        //Arrange
        Model dataModelBackend = Model.getTestModelInstance();
        String randomName="";
        String generatedName = "";
        Boolean matches=false;
        //Act
        for (int i=0;i<100;i++){
            generatedName = dataModelBackend.generateTourRandomName();
            if(randomName.equals(generatedName)){
                matches=true;
                break;
            }
        }
        //Assert
        Assert.assertEquals(false,matches);
    }

/* todo after improving TourListManager */
    @Test
    public void testTourUpdate() throws SQLException, IOException {
        //Arrange
        Model dataModelBackend = Model.getModelInstance();
        String tourTest="TestTour";
        HashMap<String,String> tourDetails;
        //Act
        dataModelBackend.addTour(tourTest);
        dataModelBackend.updateTour(tourTest,"testDescription"
                ,"NewName","TestRoute",100);
        tourDetails=dataModelBackend.getTourDetails(0,"NewName");
        dataModelBackend.deleteTour("NewName");
        //Assert
        Assert.assertEquals("NewName",tourDetails.get("tourName"));
        Assert.assertEquals("testDescription",tourDetails.get("tourDescription"));
        Assert.assertEquals("TestRoute",tourDetails.get("routeInformation"));
        Assert.assertEquals("100.0",tourDetails.get("tourDistance"));
    }

    @Test
    public void testGetTourRoute() throws SQLException, IOException {
        //Arrange
        Model dataModelBackend = Model.getModelInstance();
        String tourTest="TestTour";
        List<String> tourList;
        //Act
        dataModelBackend.addTour(tourTest);
        dataModelBackend.updateTourRoute(tourTest,"Wien","Berlin");
        HashMap<String,String> tourDetails = dataModelBackend.getTourDetails(0,tourTest);
        dataModelBackend.deleteTour(tourTest);
        //Assert
        Assert.assertEquals("Wien",tourDetails.get("from"));
        Assert.assertEquals("Berlin",tourDetails.get("to"));

    }


}
