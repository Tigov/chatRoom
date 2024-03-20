package com.javalincrud.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.javalincrud.model.Room;
import com.javalincrud.model.User;
import com.javalincrud.model.Message;
import com.javalincrud.util.JWTSecurity;
//import com.javalincrud.util.MessageDAOImpl;
import com.javalincrud.util.RoomDAOImpl;
import com.javalincrud.util.UserDAOImpl;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;

public class AppController {

    // private static MessageDAOImpl msgDao = new MessageDAOImpl();
    private static UserDAOImpl userDao = new UserDAOImpl();
    private static RoomDAOImpl roomDao = new RoomDAOImpl();

    public void addRoutes(Javalin app) {
        app.before("/rooms*", ctx -> {
            String token = ctx.header("Authorization");
            if (token == null || !JWTSecurity.validateToken(token)) {
                throw new UnauthorizedResponse("Unauthorized");
            }
        });
        app.before("/get*", ctx -> {
            String token = ctx.header("Authorization");
            if (token == null || !JWTSecurity.validateToken(token)) {
                throw new UnauthorizedResponse("Unauthorized");
            }
        });
        app.get("/", ctx -> ctx.redirect("/LoginPage/login.html"));
        app.post("/login", AppController::handleLogin);
        app.post("/signup", AppController::handleSignUp);

        app.get("/rooms", AppController::handleGetAllRooms);
        app.get("/rooms/{roomId}/messages", AppController::handleGetMessagesInRoomId);

        app.post("/rooms/{roomId}/postMessage", AppController::handlePostMessage);
        app.get("/get/userById/{userId}", AppController::handleGetUserById);

    }

    public static void handleLogin(Context ctx) throws SQLException {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        System.out.println(username + password);

        User foundUser = userDao.getUserByUsernamePass(username, password);
        if (foundUser != null) {
            String token = JWTSecurity.generateToken(username);
            ctx.result(token);
            return;
        } else {
            ctx.result("Incorrect login credentials");
        }
    }

    public static void handleSignUp(Context ctx) throws SQLException {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        System.out.println(username + password);

        User newUser = new User(username, password);
        int userId = userDao.createUser(newUser.getUsername(), newUser.getPassword());
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

    public static void handleGetMessagesInRoomId(Context ctx) throws SQLException {
        int roomId = Integer.parseInt(ctx.pathParam("roomId"));
        ArrayList<Message> allMsgs = new ArrayList<Message>();
        allMsgs = (ArrayList<Message>) roomDao.getFormattedMsgsInRoomId(roomId);
        ctx.json(allMsgs);
    }

    public static void handleGetUserById(Context ctx) throws SQLException {
        int userId = Integer.parseInt(ctx.pathParam("userId"));
        User foundUser = userDao.getUserById(userId);
        System.out.println(foundUser.toString());
        ctx.json(foundUser);
    }

    public static void handlePostMessage(Context ctx) throws SQLException {

    }
}
