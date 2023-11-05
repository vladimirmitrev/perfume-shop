package com.app.perfumeshop.model.dto;


import com.app.perfumeshop.validation.user.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@FieldMatch(
        firstField = "password",
        secondField = "confirmPassword",
        message = "Passwords should match!"
)
public class UserRegisterDTO {

    @NotBlank(message = "First name cannot be empty!")
    @Size(min = 2, max = 20, message = "First name length must be between 2 and 20 characters!")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty!")
    @Size(min = 2, max = 20, message = "Last name length must be between 2 and 20 characters!")
    private String lastName;


    @NotBlank(message = "Username cannot be empty!")
    @Size(min = 4, max = 20, message = "Username length must be between 4 and 20 characters!")
    @UniqueUsername(message = "This username is already taken!")
    private String username;

    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Email should be valid!")
    @UniqueUserEmail(message = "This email is already taken!")
    private String email;

    @ValidPassword(message = "Password should be between 8 and 20 characters and should contains a least one digit!")
    private String password;

    @ValidPassword(message = "Password should be between 8 and 20 characters and should contains a least one digit!")
    private String confirmPassword;

    @ValidPhoneNumber(message = "Phone number should starts with 0 and should be 10 digits!")
    private String phoneNumber;


    public String getFirstName() {
        return firstName;
    }

    public UserRegisterDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegisterDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
