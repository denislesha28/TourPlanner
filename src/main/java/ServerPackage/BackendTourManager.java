package ServerPackage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BackendTourManager {
    DatabaseHandler dbInstance;
    public BackendTourManager() throws SQLException, IOException {
        dbInstance=DatabaseHandler.getDatabaseInstance();
    }

    public void createTour(String tourName,String tourDescription,
                           String routeInformation, double tourDistance) throws SQLException {
        String sqlInsert="insert into \"TourPlanner\".tour (\"name\", \"tourDescription\", \"routeInformation\", \"tourDistance\")\n" +
                "values (?,?,?,?);";
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(sqlInsert);
        preparedStatement.setString(1,tourName);
        preparedStatement.setString(2,tourDescription);
        preparedStatement.setString(3,routeInformation);
        preparedStatement.setDouble(4,tourDistance);
        preparedStatement.execute();
    }
}
