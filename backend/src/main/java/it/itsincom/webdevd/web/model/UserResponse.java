package it.itsincom.webdevd.web.model;

import it.itsincom.webdevd.persistence.model.UserRole;

public class UserResponse {
    private int id;
    private String username;
    private String role;
    private String firstName;
    private String secondName;
    private String address;

    public UserResponse(int id, String username, String role, String firstName, String secondName, String address) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
