package testingPackage;

import mainPackage.Model;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ModelTest {
    @Test
    public void testBackendTours(){
        //Arrange
        Model dataModelBackend = new Model();
        String tourTest="TestTour";
        List<String> tourList;
        //Act
        dataModelBackend.addTour(tourTest);
        tourList=dataModelBackend.getTours();
        //Assert
        Assert.assertEquals(tourTest,tourList.get(tourList.size()-1));
        dataModelBackend.deleteTour(tourTest); // Cleanup
    }




}
