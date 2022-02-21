package edu.itk.project.security.dto;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class ProjectRequest {
    private String name;
    
    private String description;

    private Double expectedTime;

    private Date startDate;

    private ProjectStatus state;

	private Set<Integer> developers = new HashSet<>();

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