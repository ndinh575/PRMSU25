package com.example.prmsu25.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName(value = "_id", alternate = "id")
    private String id;
    private String name;
    private String email;
    private String accountType;
    private int points;

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAccountType() { return accountType; }
    public int getPoints() { return points; }

}
