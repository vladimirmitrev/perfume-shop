package com.app.perfumeshop.model.dto.user;

import com.app.perfumeshop.model.entity.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserViewDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private List<UserRole> roles;

    public UserViewDTO() {
    }

    public Long getId() {
        return id;
    }

    public UserViewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserViewDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserViewDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserViewDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserViewDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public UserViewDTO setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }
    public List<String> getRolesNames() {
        List<String> roleEnumNames = new ArrayList<>();
        roles.forEach(role ->
                roleEnumNames.add(role.getUserRole().name()));
        return roleEnumNames;
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();

        if(getFirstName() != null) {
            fullName.append(getFirstName());
        }

        if(getLastName() != null) {
            if (!fullName.isEmpty()) {
                fullName.append(" ");
            }
            fullName.append(getLastName());
        }

        return fullName.toString();
    }

    public boolean hasRoleEmployee(){
        return getRolesNames().contains("EMPLOYEE");
    }
    public boolean hasRoleUser(){
        return getRolesNames().contains("USER");
    }
}
