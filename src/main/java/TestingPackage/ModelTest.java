package TestingPackage;

import ServerPackage.Model;
import ServerPackage.TourListManager;
import org.junit.Assert;
import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;

public class ModelTest {

    @Test
    public void testBackendToursCreate() throws SQLException, IOException {
        //Arrange
        Model dataModelBackend = Model.getModelInstance();
        String tourTest="TestTour";
        List<String> tourList;
        //Act
        dataModelBackend.addTour(tourTest);
        tourList=dataModelBackend.getTours();
        //Assert
        Assert.assertEquals(tourTest,tourList.get(tourList.size()-1));
        dataModelBackend.deleteTour(tourTest); // Cleanup
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

/* todo after improving TourListManager
    @Test
    public void testBackendTourUpdate() throws SQLException, IOException {
        Model dataModelBackend = Model.getModelInstance();
        String tourTest="TestTour";
        List<String> tourList;
        //Act
        dataModelBackend.addTour(tourTest);
        dataModelBackend.updateTour(tourTest,"testDescription"
                ,"NewName","TestRoute",100);
        tourList=dataModelBackend.getTours();
        //Assert
        Assert.assertEquals("NewName",tourList.get(tourList.size()-1));
        dataModelBackend.deleteTour("NewName");
    }
*/


}
