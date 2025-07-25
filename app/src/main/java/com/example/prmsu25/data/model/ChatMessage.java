package com.example.prmsu25.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ChatMessage {
    @SerializedName("_id")
    private String id;
    private String senderId;
    private String receiverId;
    private String message;
    private Date sentAt;
    @SerializedName("is_read")
    private boolean isRead;

    // Getters and Setters
    public String getId() { return id; }
    public String getSenderId() { return senderId; }
    public String getReceiverId() { return receiverId; }
    public String getMessage() { return message; }
    public Date getSentAt() { return sentAt; }
    public boolean isRead() { return isRead; }
}
