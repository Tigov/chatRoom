package com.javalincrud.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.javalincrud.model.Room;
import com.javalincrud.model.User;
import com.javalincrud.model.Message;
import com.javalincrud.util.JWTSecurity;
import com.javalincrud.util.MessageDAOImpl;
import com.javalincrud.util.RoomDAOImpl;
import com.javalincrud.util.UserDAOImpl;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.websocket.WsContext;
import com.google.gson.Gson;

public class AppController {
    private static Map<WsContext, String> userSocketMap = new ConcurrentHashMap<>();
    private static MessageDAOImpl msgDao = new MessageDAOImpl();
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

        app.get("/rooms/getNumUsersFromRoomId/{roomId}", AppController::handleGetNumUsersFromRoom);

        // web socket routes

        app.ws("/rooms/{roomId}/messages", ws -> { // user clicked on a room (joins a room)
            ws.onConnect(ctx -> {// when they connect to a room, get all of that rooms messages and display them
                int roomId = Integer.parseInt(ctx.pathParam("roomId"));

                String token = ctx.queryParam("token");
                User currentSocketUser = userDao.getUserByUniqueUsername(JWTSecurity.extractUsername(token));
                userSocketMap.put(ctx, currentSocketUser.getUsername());

                ArrayList<Object> allMsgs = new ArrayList<>();
                allMsgs = (ArrayList<Object>) roomDao.getFormattedMsgsInRoomId(roomId);
                allMsgs.add(0, roomId); // insert the roomId to the beginning
                Gson gson = new Gson();
                String json = gson.toJson(allMsgs);

                int numUsersInRoom = roomDao.incNumberOfUsersInRoom(roomId);
                ctx.send(json);
                String update = "{\"Update\": true, \"roomId\": " + roomId + ", \"numberOfUsersInRoom\": "
                        + numUsersInRoom + "}";

                // send the update user numbers to all connected clients
                userSocketMap.keySet().stream().filter(context -> context.session.isOpen()).forEach(session -> {
                    session.send(update);
                });
            });
            ws.onClose(ctx -> {
                int roomId = Integer.parseInt(ctx.pathParam("roomId"));
                userSocketMap.remove(ctx);
                int numUsersInRoom = roomDao.decNumberOfUsersInRoom(roomId);

                String update = "{\"Update\": true, \"roomId\": " + roomId + ", \"numberOfUsersInRoom\": "
                        + numUsersInRoom + "}";
                // send the update user numbers to all connected clients
                userSocketMap.keySet().stream().filter(context -> context.session.isOpen()).forEach(session -> {
                    session.send(update);
                });

            });
            ws.onMessage(ctx -> {
                String username = userSocketMap.get(ctx);
                String msg = ctx.message().replace("\"", "");
                User messageOwner = userDao.getUserByUniqueUsername(username);
                int roomId = Integer.parseInt(ctx.pathParam("roomId"));
                Message curMsg = new Message(roomId, messageOwner.getId(), msg);
                int savedMessageId = msgDao.createMessage(curMsg.getText(), messageOwner.getId(), roomId);
                if (savedMessageId == -1) {
                    System.err.println("Could not create message");
                }
                ArrayList<Object> allMsgs = new ArrayList<>(); // the 1st item in the array is the roomId the rest is
                                                               // the message
                allMsgs.add(roomId);
                Message sentMsg = msgDao.getMessageById(savedMessageId);
                sentMsg.setMsgUsername(userSocketMap.get(ctx));
                allMsgs.add(sentMsg);
                Gson gson = new Gson();
                String json = gson.toJson(allMsgs);
                userSocketMap.keySet().stream().filter(context -> context.session.isOpen()).forEach(session -> {
                    session.send(json);
                });
            });
        });
    }

    public static void handleLogin(Context ctx) throws SQLException {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        User foundUser = userDao.getUserByUsernamePass(username, password);
        if (foundUser != null) {
            String token = JWTSecurity.generateToken(username);
            ctx.result(token);
            return;
        } else {
            throw new UnauthorizedResponse("Incorrect login credentials");
        }
    }

    public static void handleSignUp(Context ctx) throws SQLException {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        User newUser = new User(username, password);
        int userId = userDao.createUser(newUser.getUsername(), newUser.getPassword());
        if (userId == -1) {
            System.err.println("Error creating user");
        }
        newUser.setId(userId);

        ctx.redirect("/LoginPage/login.html");
    }

    public static void handleGetAllRooms(Context ctx) throws SQLException {
        ArrayList<Room> allRooms = new ArrayList<Room>();
        allRooms = (ArrayList<Room>) roomDao.getAllRooms();
        ctx.json(allRooms);
    }

    public static void handleGetNumUsersFromRoom(Context ctx) throws SQLException {
        int roomId = Integer.parseInt(ctx.pathParam("roomId"));
        int retNum = roomDao.getNumberOfUsersInRoom(roomId);
        ctx.json("{\"numberOfUsersInRoom\": " + retNum + "}");
    }

}