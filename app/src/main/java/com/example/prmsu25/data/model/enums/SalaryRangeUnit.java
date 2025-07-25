package com.example.prmsu25.data.model.enums;
public enum SalaryRangeUnit {
    TRIEU_VND_THANG("triệu VND/tháng"),
    TRIEU_VND_NAM("triệu VND/năm"),
    USD_THANG("USD/tháng"),
    USD_NAM("USD/năm");

    private final String value;
    SalaryRangeUnit(String value) { this.value = value; }
    public String getValue() { return value; }
}