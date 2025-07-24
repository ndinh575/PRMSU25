package com.example.prmsu25.data.network.dto;

public class VerifyEmailRequest {
    private final String email;
    private final String verificationCode;
    private final String password;

    public VerifyEmailRequest(String email, String verificationCode, String password) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.password = password;
    }
}
