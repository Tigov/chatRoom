package com.javalincrud.model.DAO;

import com.javalincrud.model.Room;

import java.sql.SQLException;
import java.util.List;

public interface RoomDAO {



    List<Room> getAllRooms() throws SQLException;

    int createRoom() throws SQLException;

    List<Object> getFormattedMsgsInRoomId(int roomId) throws SQLException;

    int getNumberOfUsersInRoom(int roomId) throws SQLException;

    int incNumberOfUsersInRoom(int roomId) throws SQLException;

    int decNumberOfUsersInRoom(int roomId) throws SQLException;

}
