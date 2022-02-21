package edu.itk.project.security.dto;

import java.sql.Date;

public class ProjectDTO {
    private String name;
    

    private String description;

    private Double expectedTime;

    private Double usedTime;

    private Date startDate;

    private ProjectStatus state;

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