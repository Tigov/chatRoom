package com.javalincrud.model.DAO;

import com.javalincrud.model.Message;
import com.javalincrud.model.User;

import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {
    Message findMessageById(int id) throws SQLException;

    List<Message> getAllMessages() throws SQLException;

    List<Message> getAllMessagesFromUser(int id) throws SQLException;

    void deleteMessageById(int id) throws SQLException;

    void updateMessageText(String text) throws SQLException;

    void createMessage(Message msg, User owner) throws SQLException;

  

}