package com.javalincrud.model.DAO;

import com.javalincrud.model.Room;
import com.javalincrud.model.Message;
import com.javalincrud.model.User;

import java.sql.SQLException;
import java.util.List;

public interface RoomDAO {
    Room getRoomById() throws SQLException;

    List<Message> getAllRoomMsgs() throws SQLException;

    List<User> getAllRoomUsers() throws SQLException;

    List<Room> getAllRooms() throws SQLException;

    void addUserToRoom(User user) throws SQLException;

    void addMsgToRoom(Message msg) throws SQLException;

    void createRoom(Room room) throws SQLException;

    void deleteRoomByRoomId(int roomId) throws SQLException;

}
