package ru.ylab.in.controller;

import ru.ylab.model.Role;
import ru.ylab.model.User;
import ru.ylab.service.UserService;

import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void registerUser(User user){
        userService.registerUser(user);
    }

    public void loginUser(String username, String password){
        userService.loginUser(username, password);
    }

    public void updateUser(User user, String password, String firstName, String lastName, String phone, String email, Role role){
        userService.updateUser(user, password, firstName, lastName, phone, email, role);
    }

    public void logoutUser(){
        userService.logoutUser();
    }

    public List<User> getUsers(){
        return userService.getUsers();
    }

    public List<User> getUsersSortedByFirstName(){
        return userService.getUsersSortedByFirstName();
    }

    public List<User> getUsersSortedByLastName(){
        return userService.getUsersSortedByLastName();
    }

    public List<User> getUsersSortedByEmail(){
        return userService.getUsersSortedByEmail();
    }

    public List<User> getUsersSortedByPurchasesCount(){
        return userService.getUsersSortedByPurchasesCount();
    }

    public List<User> filterUsersByFirstName(String firstname){
        return userService.filterUsersByFirstName(firstname);
    }

    public List<User> filterUsersByLastName(String lastname){
        return userService.filterUsersByLastName(lastname);
    }

    public List<User> filterUsersByEmail(String email){
        return userService.filterUsersByEmail(email);
    }

    public List<User> filterUsersByPurchasesCount(int purchasesCount){
        return userService.filterUsersByPurchasesCount(purchasesCount);
    }

    public int getLastUserId(User user){
        return userService.getLastUserId(user);
    }
}
