package databaseStuff;

import java.sql.*;

public class DBManager {
    Connection conn = null;

    public void connect() throws SQLException {
        try {
            // db parameters
            String url = null;
            url = getClass().getClassLoader().getResource("Database.db").toString();
            conn = DriverManager.getConnection("jdbc:sqlite:" + url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendSQL(String sql) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute(sql);
    }

    public ResultSet sendSQLwithResult(String sql) throws SQLException{
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        return result;
    }

    public void disconnect() throws SQLException{
        conn.close();
    }
}
