package com.example.prmsu25.data.model;

import java.util.Date;

public class Conversation {
    private String receiverId;
    private String name;
    private String email;
    private String lastMessage;
    private Date lastMessageAt;

    // Getters and Setters
    public String getReceiverId() { return receiverId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getLastMessage() { return lastMessage; }
    public Date getLastMessageAt() { return lastMessageAt; }
}
