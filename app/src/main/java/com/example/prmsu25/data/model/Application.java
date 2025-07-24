package com.example.prmsu25.data.model;

public class Application {
    private Job job;
    private String appliedAt;
    private ResumeFile resumeFile;

    public Application() {
    }

    public Application(Job job, ResumeFile resumeFile, String appliedAt) {
        this.job = job;
        this.resumeFile = resumeFile;
        this.appliedAt = appliedAt;
    }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public String getAppliedAt() { return appliedAt; }
    public void setAppliedAt(String appliedAt) { this.appliedAt = appliedAt; }

    public ResumeFile getResumeFile() { return resumeFile; }
    public void setResumeFile(ResumeFile resumeFile) { this.resumeFile = resumeFile; }
}
