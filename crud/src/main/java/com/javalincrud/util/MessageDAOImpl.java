package com.javalincrud.util;

import java.util.ArrayList;
import java.util.List;

import com.javalincrud.model.Message;
import com.javalincrud.model.Room;
import com.javalincrud.model.User;
import com.javalincrud.model.DAO.MessageDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public Message getMessageById(int id) throws SQLException {
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

    public List<Message> getAllMessagesFromRoom(int id) throws SQLException {
        Connection con = DatabaseCon.getConnection();
        ArrayList<Message> foundMsgs = new ArrayList<Message>();

        String sql = "SELECT * FROM messages WHERE roomId = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Message newMsg = null;
            int msgId = rs.getInt("msgId");
            int userId = rs.getInt("userId");
            String text = rs.getString("text");
            Timestamp timestamp = rs.getTimestamp("timestamp");
            newMsg = new Message(msgId, userId, text, timestamp);
            if (newMsg != null) {
                foundMsgs.add(newMsg);
            }
            System.out.println(newMsg.toString());
        }
        return foundMsgs;
    }

    @Override
    public void deleteMessageById(int id) throws SQLException {
    }

    @Override
    public void updateMessageText(String text) throws SQLException {
    }

    @Override
    public int createMessage(String text, int userId, int roomId) throws SQLException {
        Connection con = DatabaseCon.getConnection();
        String sql = "INSERT INTO messages (userId,roomId,text) VALUES (?,?,?)";
        int createdMsgId = -1;
        PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, userId);
        ps.setInt(2, roomId);
        ps.setString(3, text);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            createdMsgId = rs.getInt(1);
        }
        return createdMsgId;
    }

}
