package com.javalincrud.app;

import io.javalin.Javalin;
import com.javalincrud.util.DatabaseCon;
import com.javalincrud.util.MessageDAOImpl;
import com.javalincrud.util.UserDAOImpl;
import com.javalincrud.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection con = DatabaseCon.getConnection();
        if (con != null) {
            System.out.println("Connected to database");
        }
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles(staticFiles -> {
                staticFiles.directory = "/static";
            });
            config.enableWebjars();
        }).start(8080);
        app.get("/", ctx -> ctx.redirect("/index.html"));

        MessageDAOImpl messageDaoImpl = new MessageDAOImpl();
        UserDAOImpl userDaoImpl = new UserDAOImpl();

        User userFound = userDaoImpl.findUserByUsernamePass("User17", "Password17");
        System.out.println(userFound.toString());

        // ArrayList<User> allUsers = (ArrayList<User>) userDaoImpl.getAllUsers();
        // System.out.println(allUsers.toString());
        // int createdUserId = userDaoImpl.createUser(new User(15, "check", "ing"));
        // System.out.println(createdUserId);
        
    }
}
