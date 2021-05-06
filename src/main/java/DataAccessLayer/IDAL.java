package DataAccessLayer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;


public interface IDAL {
    public void initialize() throws SQLException;
    public Connection getConnection();
    public HashMap<String,String> getConnectionData();
}
