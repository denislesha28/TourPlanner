package TestingPackage;

import ServerPackage.Model;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ModelTest {
    @Test
    public void testBackendTours() throws SQLException, IOException {
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
