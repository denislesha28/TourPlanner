package ServerPackage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class BackendTourManager {
    DatabaseHandler dbInstance;
    public BackendTourManager() throws SQLException, IOException {
        dbInstance=DatabaseHandler.getDatabaseInstance();
    }

    public int createTour(String tourName,String tourDescription,
                           String routeInformation, double tourDistance) throws SQLException {
        String sqlInsert="insert into \"TourPlanner\".tour (\"name\", \"tourDescription\", \"routeInformation\", \"tourDistance\")\n" +
                "values (?,?,?,?) RETURNING \"id\";";
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(sqlInsert);
        preparedStatement.setString(1,tourName);
        preparedStatement.setString(2,tourDescription);
        preparedStatement.setString(3,routeInformation);
        preparedStatement.setDouble(4,tourDistance);
        ResultSet resultSet=preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("id");
        }
        return 0;
    }

    public HashMap<String, String> getTourDetails(int tourID, String tourName) throws SQLException {
        HashMap<String,String> tourDetails = new HashMap<String, String>();

        String selectSql="select *\n" +
                "from \"TourPlanner\".tour\n" +
                "WHERE \"id\" = ? OR \"name\" = ?";
        if(tourID==0){
            tourID=-1;
        }
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(selectSql);
        preparedStatement.setInt(1,tourID);
        preparedStatement.setString(2,tourName);
        ResultSet resultSet=preparedStatement.executeQuery();
        if (resultSet.next()) {
            tourDetails.put("tourName", resultSet.getString("name"));
            tourDetails.put("tourDescription", resultSet.getString("tourDescription"));
            tourDetails.put("tourDistance", resultSet.getString("tourDistance"));
            tourDetails.put("routeInformation", resultSet.getString("routeInformation"));
        }
        else {
            return null;
        }
        return tourDetails;
    }

}
