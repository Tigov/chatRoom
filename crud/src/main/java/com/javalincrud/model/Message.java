package com.javalincrud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Timestamp;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private int msgId;

    @ManyToOne
    private int userId;

    @ManyToOne
    private int roomId;

    private User user;
    private String text;
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());// use the .toString of this;

    public Message(Room room, User owner, String text) {
        this.user = owner;
        this.userId = owner.getId();
        this.text = text;
        this.roomId = room.getId();
    }

    public Message(int messageId, int ownerId, String msgText, Timestamp ts) {
        this.msgId = messageId;
        this.userId = ownerId;
        this.text = msgText;
        this.timestamp = ts;
    }

    public void setId(int id) {
        this.msgId = id;
    }

    public int getId() {
        return this.msgId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public User getOwner() {
        return this.user;
    }

    @Override
    public String toString() {
        return "Message [msgId=" + msgId + ", userId=" + userId + ", text=" + text + ", timestamp=" + timestamp + "]";
    }

}
