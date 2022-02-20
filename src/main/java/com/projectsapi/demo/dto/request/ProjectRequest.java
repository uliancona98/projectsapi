package com.projectsapi.demo.dto.request;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projectsapi.demo.model.ProjectStatus;

import io.swagger.v3.oas.annotations.media.Schema;


public class ProjectRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    
    @NotNull
    @NotBlank
    @Size(min = 1, max = 250)
    private String description;

    @NotNull
    @DecimalMin("0.0") 
    private Double expectedTime;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;

    @NotNull
    @Schema(type = "string", allowableValues = { "ready", "active", "finished", "paused", "disabled"})
    private ProjectStatus state;

    @NotNull
	private Set<Integer> developers = new HashSet<>();

    @NotNull
    private Integer projectOwner;

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

    public Set<Integer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Integer> developers) {
        this.developers = developers;
    }

    public Integer getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(Integer projectOwner) {
        this.projectOwner = projectOwner;
    }
    
}