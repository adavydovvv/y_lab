package ru.ylab.model;

import ru.ylab.utils.Role;

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    private String phone;
    private String email;
    private int number_of_purchases;

    public User(String username, String password, String phone, String email, int number_of_purchases) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.number_of_purchases = number_of_purchases;
    }

    public User(String username, String password, Role role, String phone, String email, int number_of_purchases) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.number_of_purchases = number_of_purchases;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumber_of_purchases() {
        return number_of_purchases;
    }

    public void setNumber_of_purchases(int number_of_purchases) {
        this.number_of_purchases = number_of_purchases;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
