package com.example.prmsu25.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.prmsu25.data.model.User;

public class UserSessionManager {
    private static final String PREF_NAME = "user_session_pref";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_AVATAR_URL = "user_avatar_url";

    private static final String KEY_CITY = "user_city";
    private static final String KEY_DISTRICT = "user_district";
    private static final String KEY_PHONE = "user_phone";
    private static final String KEY_CHAT_TOKEN = "chat_token";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public UserSessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save login session
    public void saveUserSession(User user) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_AVATAR_URL, user.getAvatarUrl());
        editor.putString(KEY_CITY, user.getCity());
        editor.putString(KEY_DISTRICT, user.getDistrict());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.apply();
    }

    // Check if user is logged in
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Get user info
    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getUserAvatarUrl(){return sharedPreferences.getString(KEY_AVATAR_URL, null);}

    public String getUserCity(){
        return sharedPreferences.getString(KEY_CITY, null);
    }

    public String getUserDistrict(){
        return sharedPreferences.getString(KEY_DISTRICT, null);
    }

    public String getUserPhone(){
        return sharedPreferences.getString(KEY_PHONE, null);
    }

    public User getSavedUser(){
        User savedUser = new User();
        savedUser.setName(getUserName());
        savedUser.setAvatarUrl(getUserAvatarUrl());
        savedUser.setCity(getUserCity());
        savedUser.setDistrict(getUserDistrict());
        savedUser.setPhone(getUserPhone());
        savedUser.setEmail(getUserEmail());
        return savedUser;
    }

    public void saveChatToken(String token) {
        editor.putString(KEY_CHAT_TOKEN, token);
        editor.apply();
    }

    public String getChatToken() {
        return sharedPreferences.getString(KEY_CHAT_TOKEN, null);
    }


    // Clear session
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
