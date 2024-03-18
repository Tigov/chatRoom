package com.javalincrud.app;

import io.javalin.Javalin;
import com.javalincrud.util.DatabaseCon;
import com.javalincrud.util.MessageDAOImpl;
import com.javalincrud.util.UserDAOImpl;
import com.javalincrud.controller.AppController;
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
        AppController appController = new AppController();
        appController.addRoutes(app);

    }
}
