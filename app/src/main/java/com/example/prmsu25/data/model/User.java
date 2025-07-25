package com.example.prmsu25.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName(value = "_id", alternate = "id")
    private String id;
    private String name;
    private String email;

    private String phone;

    private String city;

    private String district;
    private String accountType;
    private int points;

    private String avatarUrl;


    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getAccountType() { return accountType; }
    public int getPoints() { return points; }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
