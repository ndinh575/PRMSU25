package com.example.prmsu25.data.model.response;

import com.example.prmsu25.data.model.RecruiterContact;
import java.util.List;

public class RecruiterContactsResponse {
    private boolean success;
    private String message;
    private List<RecruiterContact> data;

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public List<RecruiterContact> getData() { return data; }
}
