package databaseStuff;

import java.sql.*;

public class DBManager {
    static Connection conn = null;

    public static void connect(){
        try {
            // db parameters
            String url = "src/main/resources/Database.db";
            conn = DriverManager.getConnection("jdbc:sqlite:" + url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sendSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT, nickname TEXT, currency INTEGER)");
        sendSQL("CREATE TABLE IF NOT EXISTS shop (id INTEGER PRIMARY KEY, name TEXT, description TEXT, price INTEGER, image TEXT)");
        sendSQL("CREATE TABLE IF NOT EXISTS waifus (name TEXT, description TEXT, rarity TEXT, price INTEGER, image TEXT)");
        sendSQL("CREATE TABLE IF NOT EXISTS roles (id INTEGER PRIMARY KEY, access INTEGER)");
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
        try {
            ResultSet rs = sendSQLwithResult("SELECT * FROM users WHERE id = " + ID);
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

    public static void addShopItem(ShopItem item){
        sendSQL("INSERT INTO shop (name, description, price, image) VALUES ('" + item.name + "', '" + item.description + "', " + item.price + ", '" + item.image + "')");
    }

    public static void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addWaifu(DBWaif waifu){
        sendSQL("INSERT INTO waifus (name, description, rarity, price, image) VALUES ('" + waifu.name + "', '" + waifu.description + "', " + waifu.rarity + ", " + waifu.price + ", '" + waifu.image + "')");
    }

    public static DBWaif getWaifuByName(String name){
        try {
            ResultSet rs = sendSQLwithResult("SELECT * FROM waifus WHERE name = '" + name + "'");
            if(rs.next()){
                return new DBWaif(rs.getString("name"), rs.getString("description"), rs.getInt("rarity"), rs.getInt("price"), rs.getString("image"));
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeWaifu(String name){
        sendSQL("DELETE FROM waifus WHERE name = '" + name + "'");
    }

    public static void addRole(DBRole role){
        sendSQL("INSERT INTO roles (id, access) VALUES (" + role.id + ", " + role.access + ")");
    }

    public static DBRole getRoleByID(String ID){
        try {
            ResultSet rs = sendSQLwithResult("SELECT * FROM roles WHERE id = " + ID);
            if(rs.next()){
                return new DBRole(rs.getString("id"), rs.getInt("access"));
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
