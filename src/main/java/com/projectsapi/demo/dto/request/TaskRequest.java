package com.projectsapi.demo.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.projectsapi.demo.model.TaskStatus;
import com.projectsapi.demo.model.TaskType;


public class TaskRequest {
    
    @NotNull
    @NotBlank
    @Size(min = 1, max = 250)
    private String description;

    @NotNull
    private TaskType type;

    @NotNull
    private Double duration;

    @NotNull
    private TaskStatus state;

    @NotNull
    private Integer idTask;

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

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }
    
}