package DataAccessLayer.Database;

import java.io.IOException;
import java.sql.SQLException;

public class DALFactory {
    private static boolean useMock=false;

    public static void useMock(){
        useMock=true;
    }

    public static IDAL getDAL() throws SQLException, IOException {
        if(useMock){
            return DatabaseHandlerMock.getDatabaseInstance();

        }
        else{
            return DatabaseHandler.getDatabaseInstance();
        }
    }
}
