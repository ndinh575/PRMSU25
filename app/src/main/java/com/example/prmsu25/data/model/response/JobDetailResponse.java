package com.example.prmsu25.data.model.response;

import com.example.prmsu25.data.model.JobDetail;

public class JobDetailResponse {

    private boolean success;
    private String message;
    private JobDetail data;

    public JobDetailResponse() {
    }

    public JobDetailResponse(boolean success, String message, JobDetail data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JobDetail getData() {
        return data;
    }

    public void setData(JobDetail data) {
        this.data = data;
    }
}
