package com.example.prmsu25.data.model;

public class ResumeApplicationItem {
    private String applicantName;
    private String jobTitle;
    private String cvFileName;
    private String cvUrl;
    private String appliedDate;

    public ResumeApplicationItem(String applicantName, String jobTitle, String cvFileName, String cvUrl, String appliedDate) {
        this.applicantName = applicantName;
        this.jobTitle = jobTitle;
        this.cvFileName = cvFileName;
        this.cvUrl = cvUrl;
        this.appliedDate = appliedDate;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCvFileName() {
        return cvFileName;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public String getAppliedDate() {
        return appliedDate;
    }
}