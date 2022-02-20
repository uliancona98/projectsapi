package com.projectsapi.demo.model;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "tasks")
public class Task {
    @EmbeddedId
    @NotNull
    private TaskId taskId;
    
    @Column
    @NotNull
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskType type;

    @Column
    @NotNull
    @DecimalMin("0.0") 
    private Double duration;
    
    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus state;

    public Task() {
    }
    
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

    public TaskId getTaskId() {
        return taskId;
    }

    public void setTaskId(TaskId taskId) {
        this.taskId = taskId;
    }
    
}