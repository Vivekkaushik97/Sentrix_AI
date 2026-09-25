package com.sentrix.ai.investigation.dto;

import java.util.UUID;

public class InvestigationCreateDto {
    private String title;
    private String description;
    private String priority;
    private String owner;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
}
