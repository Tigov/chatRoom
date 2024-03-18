package com.javalincrud.util;

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
    public void createRoom(Room room) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRoom'");
    }

    @Override
    public void deleteRoomByRoomId(int roomId) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRoomByRoomId'");
    }

}
