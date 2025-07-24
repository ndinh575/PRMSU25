package com.example.prmsu25.data.model;

public class ResumeItem {
    private String fileName;
    private String fileUrl;
    private String uploadedDate;


    public ResumeItem(String fileName, String fileUrl, String uploadedDate) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.uploadedDate = uploadedDate;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getUploadedDate() {
        return uploadedDate;
    }

    // (Optional: setters nếu cần chỉnh sửa sau này)
}
