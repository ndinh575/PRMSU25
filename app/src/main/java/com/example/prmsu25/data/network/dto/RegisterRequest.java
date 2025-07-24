package com.example.prmsu25.data.network.dto;

public class RegisterRequest {
    private final String name;
    private final String email;
    private final String password;
    private final String accountType;

    public RegisterRequest(String name, String email, String password, String accountType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }
}
