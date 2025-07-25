package com.example.prmsu25.data.model;

import java.util.List;

public class RecruiterContact {
    // TODO: Add the rest of the fields to match with backend response
    private User recruiter;
    private List<Job> jobs;

    public User getRecruiter() { return recruiter; }
    public List<Job> getJobs() { return jobs; }
}
