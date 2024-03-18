package com.javalincrud.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.javalincrud.model.Room;
import com.javalincrud.model.User;
import com.javalincrud.util.MessageDAOImpl;
import com.javalincrud.util.RoomDAOImpl;
import com.javalincrud.util.UserDAOImpl;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class AppController {

    private static MessageDAOImpl msgDao = new MessageDAOImpl();
    private static UserDAOImpl userDao = new UserDAOImpl();
    private static RoomDAOImpl roomDao = new RoomDAOImpl();

    public void addRoutes(Javalin app) {
        app.get("/", ctx -> ctx.redirect("/LoginPage/login.html"));

        app.post("/login", AppController::handleLogin);
        app.post("/signup", AppController::handleSignUp);

        app.get("/rooms", AppController::handleGetAllRooms);

    }

    public static void handleLogin(Context ctx) throws SQLException {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        System.out.println(username + password);

        User foundUser = userDao.findUserByUsernamePass(username, password);
        if (foundUser != null) {
            ctx.redirect("mainViewPage/index.html");
        }
        ctx.result("Incorrect login credentials");
    }

    public static void handleSignUp(Context ctx) throws SQLException {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        System.out.println(username + password);

        User newUser = new User(username, password);
        int userId = userDao.createUser(newUser);
        if (userId == -1) {
            System.err.println("Error creating user");
        }
        newUser.setId(userId);
        System.out.println(newUser.toString());

        ctx.redirect("/LoginPage/login.html");
    }

    public static void handleGetAllRooms(Context ctx) throws SQLException {
        ArrayList<Room> allRooms = new ArrayList<Room>();
        allRooms = (ArrayList<Room>) roomDao.getAllRooms();
        System.out.println("getAllRooms called with" + allRooms.toString());
        ctx.json(allRooms);
    }
}
