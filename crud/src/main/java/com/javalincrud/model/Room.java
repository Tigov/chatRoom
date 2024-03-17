package com.javalincrud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Room {
    @Id
    private int id;
    private static int numberOfRooms = 0;
    private int numberOfUsersInRoom;

    @ManyToMany
    private List<Message> allRoomMessages;
    @ManyToMany
    private List<User> allCurrentUsersInRoom;

    public Room(int id) {
        this.id = id;
        numberOfRooms++;
        numberOfUsersInRoom = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getNumberOfUsersInRoom() {
        return this.numberOfUsersInRoom;
    }

    public List<Message> getAllRoomMessages() {
        return allRoomMessages;
    }

    public void setAllRoomMessages(List<Message> allRoomMessages) {
        this.allRoomMessages = allRoomMessages;
    }

    public List<User> getAllCurrentUsersInRoom() {
        return allCurrentUsersInRoom;
    }

    public void setAllCurrentUsersInRoom(List<User> allCurrentUsersInRoom) {
        this.allCurrentUsersInRoom = allCurrentUsersInRoom;
    }

    public void deleteRoom(Room room) {
        numberOfRooms--;
    }

    public void addUserToRoom(User user) {
        this.allCurrentUsersInRoom.add(user);
        this.numberOfUsersInRoom++;
    }

    public void removeUserFromRoom(User user) {
        for (int i = 0; i < this.allCurrentUsersInRoom.size(); i++) {
            if (this.allCurrentUsersInRoom.get(i).getId() == user.getId()) {
                this.allCurrentUsersInRoom.remove(i);
                this.numberOfUsersInRoom--;
            }
        }
    }

    public void postMessageToRoom(Message msg) {
        this.allRoomMessages.add(msg);
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", numberOfUsersInRoom=" + numberOfUsersInRoom + "]";
    }

}
