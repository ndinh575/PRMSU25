package com.example.prmsu25.data.model;

import java.util.List;

public class JobDetail {
    private String id;
    private String employerName;
    private String employerId;
    private String title;
    private String description;
    private String requirements;
    private String experience;
    private String salary;
    private String deliveryTime;
    private String priorityLevel;
    private String createdAt;
    private String deadline;
    private int quantity;
    private String level;
    private String industry;
    private String position;
    private String location;
    private List<String> keywords;
    private List<String> skills;
    private int views;
    private String status;

    public JobDetail() {
    }

    public JobDetail(String id, String employerName, String employerId,
                     String title, String description, String requirements,
                     String experience, String salary, String deliveryTime,
                     String priorityLevel, String createdAt, int quantity,
                     String deadline, String level, String industry,
                     String position, String location, List<String> keywords,
                     List<String> skills, int views, String status) {
        this.id = id;
        this.employerName = employerName;
        this.employerId = employerId;
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.experience = experience;
        this.salary = salary;
        this.deliveryTime = deliveryTime;
        this.priorityLevel = priorityLevel;
        this.createdAt = createdAt;
        this.quantity = quantity;
        this.deadline = deadline;
        this.level = level;
        this.industry = industry;
        this.position = position;
        this.location = location;
        this.keywords = keywords;
        this.skills = skills;
        this.views = views;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
