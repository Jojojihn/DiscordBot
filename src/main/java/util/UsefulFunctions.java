package util;

import java.sql.ResultSet;
import java.sql.SQLException;

import static databaseStuff.DBManager.sendSQLwithResult;

public class UsefulFunctions {

    public static String getNick(String id) {
        ResultSet rs;
        rs = sendSQLwithResult("SELECT * FROM users WHERE id = '" + id + "'");
        try {
            if (rs.getString(1) == null) {
                return "User";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs = sendSQLwithResult("SELECT * FROM users WHERE id = '" + id + "'");
        String nickname;
        try {
            nickname = rs.getString("nickname");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nickname;
    }

}
