package com.javalincrud.controller;

import com.javalincrud.util.MessageDAOImpl;
import com.javalincrud.util.UserDAOImpl;

import io.javalin.http.Context;

public class AppController {

    private MessageDAOImpl ms = new MessageDAOImpl();
    private UserDAOImpl us = new UserDAOImpl();

    public static void getAllMessagesFromUserId(Context ctx) {

    }
}
