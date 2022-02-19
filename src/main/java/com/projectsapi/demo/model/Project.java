package com.projectsapi.demo.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Column
    @NotNull
    @NotBlank
    @Size(min = 1, max = 250)
    private String description;

    @Column(name="expected_time")
    @NotNull
    private Double expectedTime;

    @Column(name="used_time")
    @DecimalMin("0.0") 
    @NotNull
    private Double usedTime;

    @Column(name="start_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull
    private Date startDate;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProjectStatus state;


	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "projects_developers", joinColumns = { @JoinColumn(name = "id_project")},
	inverseJoinColumns = {@JoinColumn(name = "id_developer")})
    @NotNull
	private Set<Worker> developers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_project_owner", insertable = true, updatable = true)
    @NotNull
    private Worker projectOwner;

    public Project() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Worker getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(Worker projectOwner) {
        this.projectOwner = projectOwner;
    }

    public ProjectStatus getState() {
        return state;
    }

    public void setState(ProjectStatus state) {
        this.state = state;
    }

    public Set<Worker> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Worker> developers) {
        this.developers = developers;
    }

    public Double getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Double usedTime) {
        this.usedTime = usedTime;
    }

}