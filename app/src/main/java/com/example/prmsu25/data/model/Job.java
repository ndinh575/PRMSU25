package com.example.prmsu25.data.model;

public class Job {
    public String id;
    public String title;
    public String employerName;
    public String description;
    public String location;
    public String experience;
    public String industry;
    public String position;
    public String level;
    public String salary;
    public String createdAt;

    public Job() {
    }

    public Job(String id, String title, String employerName, String description, String location, String experience, String industry, String position, String level, String salary, String createdAt) {
        this.id = id;
        this.title = title;
        this.employerName = employerName;
        this.description = description;
        this.location = location;
        this.experience = experience;
        this.industry = industry;
        this.position = position;
        this.level = level;
        this.salary = salary;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
