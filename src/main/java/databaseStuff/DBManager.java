package databaseStuff;

import java.sql.*;
import java.util.Objects;

public class DBManager {
    static Connection conn = null;

    public static void connect(){
        try {
            // db parameters
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            String url = Objects.requireNonNull(classLoader.getResource("Database.db")).toString();
            conn = DriverManager.getConnection("jdbc:sqlite:" + url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void sendSQL(String sql){
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet sendSQLwithResult(String sql){
        Statement statement;
        try {
            statement = conn.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addUser(DCUser user){
        sendSQL("INSERT INTO users (id, name, nickname, currency) VALUES (" + user.id + ", '" + user.name + "', '" + user.nickname + "', 0)");
    }

    public static DCUser getUserByID(String ID){
        ResultSet rs = sendSQLwithResult("SELECT * FROM users WHERE id = " + ID);
        try {
            if(rs.next()){
                return new DCUser(rs.getString("id"), rs.getString("name"), rs.getString("nickname"), rs.getInt("currency"));
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addCurrency(String ID, int amount){
        sendSQL("UPDATE users SET currency = currency + " + amount + " WHERE id = " + ID);
    }
    public static void setNickname(String ID, String nickname){
        sendSQL("UPDATE users SET nickname = '" + nickname + "' WHERE id = " + ID);
    }

    public static void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
