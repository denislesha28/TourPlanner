package DataAccessLayer.Database;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHandlerMock implements IDAL {

    private HashMap<String,String> connectionData;
    private Connection connection;
    private static DatabaseHandlerMock instance=null;

    private DatabaseHandlerMock() throws SQLException, IOException {
        // List of connection parameters
        connectionData= new HashMap<String, String>();
        connection=null;
        // ObjectMapper to conver json lines to String
        ObjectMapper mapper = new ObjectMapper();
        // convert JSON file to map
        Map<?, ?> readValues = mapper.readValue(Paths.get("config.json").toFile(), Map.class);
        // read lines into connection Data
        for (Map.Entry<?, ?> entry : readValues.entrySet()) {
            connectionData.put(entry.getKey().toString(),entry.getValue().toString());
        }
        // Build Connenction
        initialize();

    }

    @Override
    public void initialize() throws SQLException {
        System.out.println("Mock Database Connected");
    }

    public static DatabaseHandlerMock getDatabaseInstance() throws SQLException, IOException {
        if(instance==null){
            instance=new DatabaseHandlerMock();
        }
        return  instance;
    }

    @Override
    public Connection getConnection(){
        return connection;
    }
    @Override
    public HashMap<String,String> getConnectionData (){
        return connectionData;
    }

}
