package ServerPackage;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DatabaseHandler {
    HashMap <String,String> connectionData;
    private Connection connection;

    public DatabaseHandler() throws SQLException, IOException {
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
        // Build Connenction
        connection = DriverManager.getConnection(
                connectionData.get("jdbcURL"),
                connectionData.get("username"),
                connectionData.get("password"));

        System.out.println("Database Connected");
    }


}
