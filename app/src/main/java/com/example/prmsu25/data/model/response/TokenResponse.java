package com.example.prmsu25.data.model.response;

public class TokenResponse {
    private boolean success;
    private String message;
    private String data;
    private JobResponse.Pagination pagination;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
