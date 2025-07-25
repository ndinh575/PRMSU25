package com.example.prmsu25.data.model.response;

import com.example.prmsu25.data.model.Conversation;

import java.util.List;

public class ConversationResponse {
    private boolean success;
    private String message;

    private List<Conversation> data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Conversation> getData() {
        return data;
    }
}
