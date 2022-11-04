package util;

import databaseStuff.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsefulFunctions {

    public static String getNick(String id){
        DBManager dbManager = new DBManager();
        ResultSet rs;
        try {
            dbManager.connect();
            try {
                rs = dbManager.sendSQLwithResult("SELECT * FROM users WHERE id = '" + id + "'");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (rs.getString(1) == null) {
                    return "User";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            rs = dbManager.sendSQLwithResult("SELECT * FROM users WHERE id = '" + id + "'");
            String nickname = rs.getString("nickname");
            dbManager.disconnect();
            return nickname;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
