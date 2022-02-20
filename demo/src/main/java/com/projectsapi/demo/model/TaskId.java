package com.projectsapi.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class TaskId implements Serializable {
    @Column(name = "id_project", nullable = false)
    private Integer idProject;

    @Column(name = "id_task", nullable = false)
    private Integer idTask;

    // default constructor
    public TaskId(){
        
    }
    public TaskId(Integer idTask, Integer idProject) {
        this.idTask = idTask;
        this.idProject = idProject;
    }
    public Integer getIdProject() {
        return idProject;
    }
    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }
    public Integer getIdTask() {
        return idTask;
    }
    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

}
