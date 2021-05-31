package DataAccessLayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BackendTourManager {
    IDAL dbInstance;
    private final Logger log;

    public BackendTourManager() throws SQLException, IOException {
        dbInstance = DALFactory.getDAL();
        log = LogManager.getLogger(BackendTourManager.class);
    }

    public int createTour(String tourName) throws SQLException {
        String sqlInsert="insert into \"TourPlanner\".tour (\"name\", \"tourDescription\", \"routeInformation\", \"tourDistance\")\n" +
                "values (?,?,?,?) RETURNING \"id\";";
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(sqlInsert);
        preparedStatement.setString(1,tourName);
        preparedStatement.setString(2,"------");
        preparedStatement.setString(3,"------");
        preparedStatement.setDouble(4,100);
        ResultSet resultSet=preparedStatement.executeQuery();
        if (resultSet.next()){
            log.debug("Created Tour Successfully");
            return resultSet.getInt("id");
        }
        return 0;
    }


    public void deleteTour(String tourName) throws SQLException {
        String deleteSql="delete\n" +
                "from \"TourPlanner\".tour\n" +
                "where name=?";
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(deleteSql);
        preparedStatement.setString(1,tourName);
        preparedStatement.executeUpdate();
        log.debug("Tour deleted Successfully");
    }

    public Tour getTourDetails(int tourID, String tourName) throws SQLException {
        Tour tourDetails = new Tour();

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
            tourDetails.setTourName(resultSet.getString("name"));
            tourDetails.setTourDescription(resultSet.getString("tourDescription"));
            tourDetails.setTourDistance(resultSet.getDouble("tourDistance"));
            tourDetails.setRouteInformation(resultSet.getString("routeInformation"));
            tourDetails.setTourFrom(resultSet.getString("from"));
            tourDetails.setTourTo(resultSet.getString("to"));
        }
        else {
            log.error("Tour doesn't exist");
            return null;
        }
        return tourDetails;
    }

    public void getAllToursFromBackend(TourListManager tourListManager) throws SQLException {
        String sqlSelect="select *\n" +
                "from \"TourPlanner\".tour LIMIT 100";
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(sqlSelect);
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            Tour tour=new Tour();
            tour.setTourName(resultSet.getString("name"));
            tour.setTourDistance(resultSet.getDouble("tourDistance"));
            tour.setTourDescription(resultSet.getString("tourDescription"));
            tour.setRouteInformation(resultSet.getString("routeInformation"));
            tour.setTourFrom(resultSet.getString("from"));
            tour.setTourTo(resultSet.getString("to"));
            tourListManager.addTour(tour);
        }
        log.debug("Filled TourListManager with all Tours");
    }

    public void updateTour(String actualTourName,String tourDescription, String desTourName
                        ,String routeInformation, double tourDistance) throws SQLException {
        String updateSql="update \"TourPlanner\".tour\n" +
                "set \"name\"  = ?, \"tourDescription\" = ?, \"routeInformation\" = ?,\n" +
                "    \"tourDistance\" = ? where \"name\" = ?";
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(updateSql);
        preparedStatement.setString(1,desTourName);
        preparedStatement.setString(2,tourDescription);
        preparedStatement.setString(3,routeInformation);
        preparedStatement.setDouble(4,tourDistance);
        preparedStatement.setString(5,actualTourName);
        preparedStatement.executeUpdate();
        log.debug("TourAttributes Updated in Database");
    }

    public void updateTourRoute(String tourName,String from,String to) throws SQLException {
        String sqlInsert="update \"TourPlanner\".tour\n" +
                "set \"from\" = ?, \"to\" = ?\n" +
                "where name = ?";
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(sqlInsert);
        preparedStatement.setString(1,from);
        preparedStatement.setString(2,to);
        preparedStatement.setString(3,tourName);
        preparedStatement.executeUpdate();
        log.debug("TourRoute Updated in Database");
    }

    public void updateTourVectorToken(String tourName) throws SQLException {
        Tour tourDetails = getTourDetails(0,tourName);
        String insertTourText="update \"TourPlanner\".tour " +
                "set \"tourToken\" = to_tsvector(?) " +
                "where name = ?;";
        PreparedStatement preparedStatement = dbInstance.getConnection().prepareStatement(insertTourText);
        preparedStatement.setString(1,tourDetails.getTourName()+" "+tourDetails.getTourDistance()
        +" "+tourDetails.getTourDescription()+" "+tourDetails.getRouteInformation()+" "+tourDetails.getTourTo()
        +" "+tourDetails.getTourFrom());
        preparedStatement.setString(2,tourName);
        preparedStatement.executeUpdate();
    }



}
