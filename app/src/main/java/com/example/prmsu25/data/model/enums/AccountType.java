package com.example.prmsu25.data.model.enums;

public enum AccountType {
    RECRUITER("Nhà Tuyển Dụng"), APPLICANT("Ứng Viên"), ADMIN("Admin");
    private final String value;
    AccountType(String value) { this.value = value; }
    public String getValue() { return value; }
}