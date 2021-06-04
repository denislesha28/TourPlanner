package DataAccessLayer.Database;

import Datatypes.Tour;
import Datatypes.TourLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BackendTourLogManager {
    IDAL dbInstance;
    private final Logger log;

    public BackendTourLogManager() throws SQLException, IOException {
        dbInstance = DALFactory.getDAL();
        log = LogManager.getLogger(BackendTourManager.class);
    }

    public void addTourLog(int tourID) throws SQLException {
        String insertSql = "insert into \"TourPlanner\".\"tourLog\" (\"tourID\")\n" +
                "values (?);";
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(insertSql);
        preparedStatement.setInt(1,tourID);
        preparedStatement.executeUpdate();
    }

    public void deleteTourLog(String timestamp) throws SQLException {
        String deleteSql = "delete\n" +
                "from \"TourPlanner\".\"tourLog\"\n" +
                "where \"timestamp\" = ?;";
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(deleteSql);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(timestamp));
        preparedStatement.executeUpdate();
    }

    public List<TourLog> getAllTourLogs(int tourID) throws SQLException {
        String selectSql = "select *\n" +
                "from \"TourPlanner\".\"tourLog\"\n" +
                "where \"tourID\" = ?";
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(selectSql);
        preparedStatement.setInt(1,tourID);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<TourLog> tourLogList = new ArrayList<>();

        while (resultSet.next()){
            TourLog tourLog = new TourLog();
            tourLog.setLogReport(resultSet.getString("logReport"));
            tourLog.setTraveledDistance(resultSet.getDouble("traveledDistance"));
            tourLog.setDuration(resultSet.getDouble("totalTime"));
            tourLog.setDate(resultSet.getString("timestamp"));
            tourLog.setRating(resultSet.getInt("rating"));
            tourLog.setAvgSpeed(resultSet.getDouble("avgSpeed"));
            tourLog.setAuthor(resultSet.getString("author"));
            tourLog.setRemarks(resultSet.getString("specialRemarks"));
            tourLog.setJoule(resultSet.getInt("joule"));
            tourLog.setWeather(resultSet.getString("weather"));
            tourLogList.add(tourLog);
        }
        if(tourLogList.isEmpty()){
            return null;
        }
        return tourLogList;
    }

    public TourLog getTourLog(String timestamp) throws SQLException {
        String selectSql = "select *\n" +
                "from \"TourPlanner\".\"tourLog\"\n" +
                "where \"timestamp\" = ?;";
        PreparedStatement preparedStatement=dbInstance.getConnection().prepareStatement(selectSql);
        preparedStatement.setTimestamp(1, Timestamp.valueOf(timestamp));
        ResultSet resultSet = preparedStatement.executeQuery();
        TourLog tourLog = new TourLog();
        while (resultSet.next()){
            tourLog.setLogReport(resultSet.getString("logReport"));
            tourLog.setTraveledDistance(resultSet.getDouble("traveledDistance"));
            tourLog.setDuration(resultSet.getDouble("totalTime"));
            tourLog.setDate(String.valueOf(resultSet.getTimestamp("timestamp")));
            tourLog.setRating(resultSet.getInt("rating"));
            tourLog.setAvgSpeed(resultSet.getDouble("avgSpeed"));
            tourLog.setAuthor(resultSet.getString("author"));
            tourLog.setRemarks(resultSet.getString("specialRemarks"));
            tourLog.setJoule(resultSet.getInt("joule"));
            tourLog.setWeather(resultSet.getString("weather"));
            return tourLog;
        }
        return null;
    }


}
