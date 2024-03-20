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
    private String text;
    private String username;
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());// use the .toString of this;

    public Message(Room room, String text) {
        this.text = text;
        this.roomId = room.getId();
    }

    public Message(String text, String username, Timestamp ts) {
        this.text = text;
        this.username = username;
        this.timestamp = ts;
    }

    public Message(int messageId, int userId, String msgText, Timestamp ts) {
        this.msgId = messageId;
        this.userId = userId;
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

    public int getUserId() {
        return this.userId;
    }

    public void setMsgUsername(String username) {
        this.username = username;
    }

    public String getMsgUsername() {
        return this.username;
    }

    @Override
    public String toString() {
        return "Message [msgId=" + msgId + ", userId=" + userId + ", text=" + text + ", timestamp=" + timestamp + "]";
    }

}
