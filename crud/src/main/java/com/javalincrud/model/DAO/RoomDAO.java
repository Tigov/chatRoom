package com.javalincrud.model.DAO;

import com.javalincrud.model.Room;
import com.javalincrud.model.Message;
import com.javalincrud.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface RoomDAO {
    Room getRoomById() throws SQLException;

    List<Message> getAllRoomMsgs() throws SQLException;

    List<User> getAllRoomUsers() throws SQLException;

    List<Room> getAllRooms() throws SQLException;

    void addUserToRoom(User user) throws SQLException;

    void addMsgToRoom(Message msg) throws SQLException;

    int createRoom() throws SQLException;

    void deleteRoomByRoomId(int roomId) throws SQLException;

    List<Message> getFormattedMsgsInRoomId(int roomId) throws SQLException;

}
