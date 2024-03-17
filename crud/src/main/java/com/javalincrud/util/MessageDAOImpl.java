package com.javalincrud.util;

import java.util.List;

import com.javalincrud.model.Message;
import com.javalincrud.model.User;
import com.javalincrud.model.DAO.MessageDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public Message findMessageById(int id) throws SQLException {
        Connection con = DatabaseCon.getConnection();
        Message msg = null;
        String sql = "SELECT * FROM messages WHERE messages.msgId = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int msgId = rs.getInt("msgId");
            int userId = rs.getInt("userId");
            String text = rs.getString("text");
            Timestamp timestamp = rs.getTimestamp("timestamp");
            msg = new Message(msgId, userId, text, timestamp);
        }
        return msg;
    }

    @Override
    public List<Message> getAllMessages() throws SQLException {
        return null;
    }

    public List<Message> getAllMessagesFromUser(int id) throws SQLException {
        return null;
    }

    @Override
    public void deleteMessageById(int id) throws SQLException {
    }

    @Override
    public void updateMessageText(String text) throws SQLException {
    }

    @Override
    public void createMessage(Message msg, User owner) throws SQLException {
    }

}
