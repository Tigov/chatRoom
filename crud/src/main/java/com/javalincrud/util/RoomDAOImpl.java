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
import com.javalincrud.model.DAO.RoomDAO;

public class RoomDAOImpl implements RoomDAO {

    @Override
    public List<Room> getAllRooms() throws SQLException {
        Connection con = DatabaseCon.getConnection();
        ArrayList<Room> allRooms = new ArrayList<Room>();
        String sql = "SELECT * FROM rooms";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int roomId = rs.getInt(1);
            int numberOfUsers = rs.getInt(2);
            Room newRoom = new Room(roomId, numberOfUsers);
            allRooms.add(newRoom);
        }
        return allRooms;
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
    public List<Object> getFormattedMsgsInRoomId(int roomId) throws SQLException { // get a list of every message from a
                                                                                   // room
        Connection con = DatabaseCon.getConnection();
        ArrayList<Object> allFormattedMsgs = new ArrayList<>();
        String sql = "SELECT m.text, u.username, m.timestamp " +
                "FROM messages m " +
                "INNER JOIN rooms r ON m.roomId = r.roomId " +
                "INNER JOIN users u ON m.userId = u.userId " +
                "WHERE m.roomId = ? " +
                "ORDER BY m.timestamp ASC";

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

    @Override
    public int getNumberOfUsersInRoom(int roomId) throws SQLException {
        Connection con = DatabaseCon.getConnection();
        String sql = "SELECT rooms.numberOfUsers FROM rooms WHERE roomId = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, roomId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return (int) rs.getInt("numberOfUsers");
        }
        return 0;
    };

    @Override
    public int incNumberOfUsersInRoom(int roomId) throws SQLException {
        Connection con = DatabaseCon.getConnection();

        // Increment the numberOfUsers
        String updateSql = "UPDATE rooms SET numberOfUsers = numberOfUsers + 1 WHERE rooms.roomId = ?";
        PreparedStatement updatePs = con.prepareStatement(updateSql);
        updatePs.setInt(1, roomId);
        updatePs.executeUpdate();

        // Get the updated numberOfUsers
        String selectSql = "SELECT numberOfUsers FROM rooms WHERE rooms.roomId = ?";
        PreparedStatement selectPs = con.prepareStatement(selectSql);
        selectPs.setInt(1, roomId);
        ResultSet rs = selectPs.executeQuery();

        // If the result set is not empty, return the updated numberOfUsers
        if (rs.next()) {
            return rs.getInt("numberOfUsers");
        } else {
            throw new SQLException("Room not found");
        }
    }

    @Override
    public int decNumberOfUsersInRoom(int roomId) throws SQLException {
        Connection con = DatabaseCon.getConnection();

        // decrement the numberOfUsers
        String updateSql = "UPDATE rooms SET numberOfUsers = numberOfUsers - 1 WHERE rooms.roomId = ?";
        PreparedStatement updatePs = con.prepareStatement(updateSql);
        updatePs.setInt(1, roomId);
        updatePs.executeUpdate();

        // Get the updated numberOfUsers
        String selectSql = "SELECT numberOfUsers FROM rooms WHERE rooms.roomId = ?";
        PreparedStatement selectPs = con.prepareStatement(selectSql);
        selectPs.setInt(1, roomId);
        ResultSet rs = selectPs.executeQuery();

        // If the result set is not empty, return the updated numberOfUsers
        if (rs.next()) {
            return rs.getInt("numberOfUsers");
        } else {
            throw new SQLException("Room not found");
        }
    }

}
