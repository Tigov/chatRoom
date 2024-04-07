package com.javalincrud.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DatabaseCon {
    private static final String URL = "jdbc:mysql://localhost:3306/javaTesting";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public DatabaseCon() {

    }

    public static Connection getConnection() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(URL, USER, PASSWORD);
        return con;
    }

    // public static void fillDatabase() throws SQLException { //only run this once
    // when database is empty
    // Connection con = DatabaseCon.getConnection();

    // Room mainRoom = new Room(1);
    // for (int i = 15; i < 1001; i++) {
    // String username = "User" + i;
    // String password = "Password" + i;
    // User randomUser = new User(i, username, password);
    // System.out.println(username + " " + password);

    // String messageText = "Message Text " + i;
    // Message randomMsg = new Message(mainRoom, randomUser, messageText);
    // System.out.println(messageText);

    // String sql = "INSERT INTO users (username,password) VALUES (?, ?)";
    // PreparedStatement ps = con.prepareStatement(sql,
    // PreparedStatement.RETURN_GENERATED_KEYS);
    // ps.setString(1, randomUser.getUsername());
    // ps.setString(2, randomUser.getPassword());
    // ps.executeUpdate();
    // int userInsertedDatabaseId = 0;
    // ResultSet rs = ps.getGeneratedKeys();
    // if (rs.next()) {
    // userInsertedDatabaseId = rs.getInt(1);
    // }

    // String messageSQL = "INSERT INTO messages (userId, text) VALUES (?, ?)";
    // PreparedStatement msgPs = con.prepareStatement(messageSQL);
    // msgPs.setInt(1, userInsertedDatabaseId);
    // msgPs.setString(2, randomMsg.getText());
    // msgPs.executeUpdate();
    // }

    // }
}
