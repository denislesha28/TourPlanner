package TestingPackage;

import DataAccessLayer.DatabaseHandler;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;

public class DatabaseHandlerTest {

    @Spy
    @InjectMocks
    private DatabaseHandler dbHandler = DatabaseHandler.getDatabaseInstance();

    @Mock private Connection mockConnection;
    @Mock private DatabaseHandler mockHandler;

    public DatabaseHandlerTest() throws SQLException, IOException {
    }

    @Test
    public void testConnectionDBInstance() throws SQLException, IOException {
        //Arrange
        Mockito.when(dbHandler.getDatabaseInstance()).thenReturn(mockHandler);
        Mockito.when(dbHandler.getConnection()).thenReturn(mockConnection);
        //Act
        mockHandler=DatabaseHandler.getDatabaseInstance();
        Connection connection=mockHandler.getConnection();
        //Assert
        Assert.assertEquals(mockConnection,connection);
        //Mockito.times number of calls to mocker
        //Mockito.verify(dbHandler.getConnection(),Mockito.times(1));
    }


}
