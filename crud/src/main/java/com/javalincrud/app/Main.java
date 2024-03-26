package com.javalincrud.app;

import io.javalin.Javalin;

import com.javalincrud.controller.AppController;

import java.sql.SQLException;

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

    }
}
