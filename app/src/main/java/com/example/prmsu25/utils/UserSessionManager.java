package com.example.prmsu25.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.prmsu25.data.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserSessionManager {
    private static final String PREF_NAME = "user_session_pref";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_TYPE = "user_type";
    private static final String KEY_USER_POINTS = "user_points";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public UserSessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save login session
    public void saveUserSession(String id) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_ID, id);
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

    public String getUserType() {
        return sharedPreferences.getString(KEY_USER_TYPE, null);
    }

    public int getUserPoints() {
        return sharedPreferences.getInt(KEY_USER_POINTS, 0);
    }

    // Clear session
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
