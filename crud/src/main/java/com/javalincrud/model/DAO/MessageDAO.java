package com.javalincrud.model.DAO;

import com.javalincrud.model.Message;


import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {
    Message getMessageById(int id) throws SQLException;

    List<Message> getAllMessagesFromRoom(int id) throws SQLException;   
     
    int createMessage(String text, int userId, int roomId) throws SQLException;

}