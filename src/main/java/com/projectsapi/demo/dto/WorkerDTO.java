package com.projectsapi.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class WorkerDTO {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp="^[A-Za-z]*$", message = "Invalid Input")
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp="^[A-Za-z]*$", message = "Invalid Input")
    private String lastName;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp="^[A-Za-z]*$", message = "Invalid Input")
    private String secondLastName;

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

}