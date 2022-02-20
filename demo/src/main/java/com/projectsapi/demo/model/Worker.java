package com.projectsapi.demo.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "workers")
public class Worker {
    @Id
    @NotNull
    private Integer id;

    @Column
    @NotNull
    private String name;

    @Column(name="last_name")
    @NotNull
    private String lastName;

    @Column(name="second_last_name")
    @NotNull
    private String secondLastName;

    @Column(name="username")
    @NotNull
    private String username;


    public Worker() {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
}