package com.javalincrud.app;

import io.javalin.Javalin;
import io.javalin.websocket.WsContext;

import com.javalincrud.util.DatabaseCon;
// import com.javalincrud.util.MessageDAOImpl;
// import com.javalincrud.util.RoomDAOImpl;
// import com.javalincrud.util.UserDAOImpl;
import com.javalincrud.controller.AppController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) throws SQLException {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles(staticFiles -> {
                staticFiles.directory = "/static";
            });
            config.enableWebjars();
        }).start(8080);

        AppController appController = new AppController();
        appController.addRoutes(app);

        // MessageDAOImpl messageDao = new MessageDAOImpl();
        // UserDAOImpl userDao = new UserDAOImpl();
        // RoomDAOImpl roomDao = new RoomDAOImpl();

    }
}
