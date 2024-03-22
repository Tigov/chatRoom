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

    public Room(int id, int numberOfUsers) {
        this.id = id;
        numberOfRooms++;
        numberOfUsersInRoom = numberOfUsers;
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

    public void setNumberOfUsersInRoom(int number) {
        this.numberOfUsersInRoom = number;
    }

    public void incNumberOfUsers() {
        this.numberOfUsersInRoom++;
    }

    public void decNumberOfUsers() {
        this.numberOfUsersInRoom--;
    }

    public List<Message> getAllRoomMessages() {
        return allRoomMessages;
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", numberOfUsersInRoom=" + numberOfUsersInRoom + "]";
    }

}
