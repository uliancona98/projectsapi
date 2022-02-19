package com.projectsapi.demo.dto;

import java.sql.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projectsapi.demo.model.ProjectStatus;


public class ProjectDTO {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    
    @NotNull
    @NotBlank
    @Size(min = 1, max = 250)
    private String description;

    @NotNull
    private Double expectedTime;

    @NotNull
    private Double usedTime;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProjectStatus state;

    @NotNull
	private WorkerDTO projectOwner;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Double expectedTime) {
        this.expectedTime = expectedTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public ProjectStatus getState() {
        return state;
    }

    public void setState(ProjectStatus state) {
        this.state = state;
    }

    public WorkerDTO getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(WorkerDTO projectOwner) {
        this.projectOwner = projectOwner;
    }

    public Double getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Double usedTime) {
        this.usedTime = usedTime;
    }
}