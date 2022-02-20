package com.projectsapi.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;
 
import javax.persistence.*;
 
@Entity
@Table(name = "users")
public class User {
 
    @Id
    private String username;
    private String password;
    private boolean enabled;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "authorities", joinColumns = { @JoinColumn(name = "username")},
	inverseJoinColumns = {@JoinColumn(name = "authority")})
	private List<Role> roles = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
 }