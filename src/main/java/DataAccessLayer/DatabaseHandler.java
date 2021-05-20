package DataAccessLayer;

import BusinessLayer.MapApiHttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DatabaseHandler implements IDAL {

    private HashMap <String,String> connectionData;
    private Connection connection;
    private static DatabaseHandler instance=null;
    private final Logger log;

    private DatabaseHandler() throws SQLException, IOException {
        log = LogManager.getLogger(DatabaseHandler.class);
        // List of connection parameters
        connectionData= new HashMap<String, String>();
        // ObjectMapper to conver json lines to String
        ObjectMapper mapper = new ObjectMapper();
        // convert JSON file to map
        Map<?, ?> readValues = mapper.readValue(Paths.get("config.json").toFile(), Map.class);
        // read lines into connection Data
        for (Map.Entry<?, ?> entry : readValues.entrySet()) {
            connectionData.put(entry.getKey().toString(),entry.getValue().toString());
        }
        log.info("Read Connection Attributes from config file");
        // Build Connenction
        initialize();

    }


    @Override
    public void initialize() throws SQLException {
        connection = DriverManager.getConnection(
                connectionData.get("jdbcURL"),
                connectionData.get("username"),
                connectionData.get("password"));

        System.out.println("Database Connected");
        log.info("Database Connected");
    }

    public static DatabaseHandler getDatabaseInstance() throws SQLException, IOException {
        if(instance==null){
            instance=new DatabaseHandler();
        }
        return  instance;
    }

    @Override
    public Connection getConnection(){
        log.debug("Connection Instance accessed");
        return connection;
    }
    @Override
    public HashMap<String,String> getConnectionData (){
        return connectionData;
    }

}
