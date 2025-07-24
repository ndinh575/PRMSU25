package com.example.prmsu25.data.model.enums;

public enum Experience {
    KHONG_YEU_CAU("Không yêu cầu"),
    DUOI_1_NAM("Dưới 1 năm"),
    MOT_NAM("1 năm"),
    HAI_NAM("2 năm"),
    BA_NAM("3 năm"),
    BON_NAM("4 năm"),
    NAM_NAM("5 năm"),
    TREN_5_NAM("Trên 5 năm");

    private final String value;
    Experience(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}