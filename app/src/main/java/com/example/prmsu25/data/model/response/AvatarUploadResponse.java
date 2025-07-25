package com.example.prmsu25.data.model.response;

public class AvatarUploadResponse {
    private boolean success;
    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private String avatarUrl;

        public String getAvatarUrl() {
            return avatarUrl;
        }
    }
}
