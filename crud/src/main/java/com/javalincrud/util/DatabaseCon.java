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

}
