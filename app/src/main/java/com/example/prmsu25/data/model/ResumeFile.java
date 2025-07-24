package com.example.prmsu25.data.model;

public class ResumeFile {
    private String path;
    private String contentType;

    public ResumeFile() {
    }

    public ResumeFile(String path, String contentType) {
        this.path = path;
        this.contentType = contentType;
    }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
}
