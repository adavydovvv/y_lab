package ru.ylab.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.ylab.model.Role;

public class UserDTO {

    private int userId;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotNull(message = "Role is mandatory")
    private Role role;

    private String phone;

    private int number_of_purchases;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email must not be blank")
    private String email;

    public int getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
    }

    public @NotBlank(message = "Username is mandatory") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is mandatory") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Password is mandatory") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is mandatory") String password) {
        this.password = password;
    }

    public @NotBlank(message = "First name is mandatory") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name is mandatory") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Last name is mandatory") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name is mandatory") String lastName) {
        this.lastName = lastName;
    }

    public @NotNull(message = "Role is mandatory") Role getRole() {
        return role;
    }

    public void setRole(@NotNull(message = "Role is mandatory") Role role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public int getNumber_of_purchases() {
        return number_of_purchases;
    }

    public void setNumber_of_purchases(int number_of_purchases) {
        this.number_of_purchases = number_of_purchases;
    }
}

