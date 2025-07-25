package com.example.prmsu25.data.model.enums;

public enum Level {
    NHAN_VIEN("Nhân viên"),
    TRUONG_NHOM("Trưởng nhóm"),
    TRUONG_PHO_PHONG("Trưởng/Phó phòng"),
    QUAN_LY_GIAM_SAT("Quản lý / Giám sát"),
    TRUONG_CHI_NHANH("Trưởng chi nhánh"),
    PHO_GIAM_DOC("Phó giám đốc"),
    GIAM_DOC("Giám đốc"),
    THUC_TAP_SINH("Thực tập sinh");

    private final String value;
    Level(String value) { this.value = value; }
    public String getValue() { return value; }
}