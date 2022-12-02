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

    public static void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
