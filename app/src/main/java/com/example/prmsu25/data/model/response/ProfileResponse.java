package com.example.prmsu25.data.model.response;

import com.example.prmsu25.data.model.User;

public class ProfileResponse {
    private boolean success;
    private User data;

    public ProfileResponse() {
    }

    public ProfileResponse(boolean success, User data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public User getData() {
        return data;
    }
}
