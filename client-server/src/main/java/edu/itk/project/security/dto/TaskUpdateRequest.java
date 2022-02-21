package edu.itk.project.security.dto;


public class TaskUpdateRequest {

    private String description;

    private TaskType type;

    private Double duration;

    private TaskStatus state;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public TaskStatus getState() {
        return state;
    }

    public void setState(TaskStatus state) {
        this.state = state;
    }
    
}