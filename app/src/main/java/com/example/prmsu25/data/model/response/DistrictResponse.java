package com.example.prmsu25.data.model.response;

import com.example.prmsu25.data.model.District;

import java.util.List;

public class DistrictResponse {
    private String name;
    private int code;
    private List<District> districts;

    public DistrictResponse() {
    }

    public DistrictResponse(String name, int code, List<District> districts) {
        this.name = name;
        this.code = code;
        this.districts = districts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
