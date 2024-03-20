package com.javalincrud.util;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javalincrud.model.Message;
import com.javalincrud.model.Room;
import com.javalincrud.model.User;
import com.javalincrud.model.DAO.RoomDAO;

public class RoomDAOImpl implements RoomDAO {

    @Override
    public Room getRoomById() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoomById'");
    }

    @Override
    public List<Room> getAllRooms() throws SQLException {
        Connection con = DatabaseCon.getConnection();
        ArrayList<Room> allRooms = new ArrayList<Room>();
        String sql = "SELECT * FROM rooms";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int roomId = rs.getInt(1);
            Room newRoom = new Room(roomId);
            allRooms.add(newRoom);
        }
        return allRooms;
    }

    @Override
    public List<Message> getAllRoomMsgs() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRoomMsgs'");
    }

    @Override
    public List<User> getAllRoomUsers() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRoomUsers'");
    }

    @Override
    public void addUserToRoom(User user) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addUserToRoom'");
    }

    @Override
    public void addMsgToRoom(Message msg) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addMsgToRoom'");
    }

    @Override
    public int createRoom() throws SQLException {
        Connection con = DatabaseCon.getConnection();
        String sql = "INSERT INTO rooms ()  VALUES ()";
        int createdRoomId = -1;
        PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            createdRoomId = rs.getInt(1);
        }
        return createdRoomId;
    }

    @Override
    public void deleteRoomByRoomId(int roomId) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRoomByRoomId'");
    }

    @Override
    public List<Message> getFormattedMsgsInRoomId(int roomId) throws SQLException {
        Connection con = DatabaseCon.getConnection();
        ArrayList<Message> allFormattedMsgs = new ArrayList<Message>();
        String sql = "SELECT m.text, u.username, m.timestamp " +
                "FROM messages m " +
                "INNER JOIN rooms r ON m.roomId = r.roomId " +
                "INNER JOIN users u ON m.userId = u.userId " +
                "WHERE m.roomId = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, roomId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String text = rs.getString("text");
            String username = rs.getString("username");
            Timestamp ts = rs.getTimestamp("timestamp");
            Message retMsg = new Message(text, username, ts);
            allFormattedMsgs.add(retMsg);
        }
        return allFormattedMsgs;

    }
}
