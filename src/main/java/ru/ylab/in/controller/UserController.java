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

    public void deleteUser(User user) {
        userService.deleteUser(user);
    }

    public void registerUser(User user){
        userService.registerUser(user);
    }

    public void loginUser(String username, String password){
        userService.loginUser(username, password);
    }

    public void updateUserPassword(User user, String password){
        userService.updateUserPassword(user, password);
    }

    public void updateUserRole(User user, Role role){
        userService.updateUserRole(user, role);
    }

    public void logoutUser(){
        userService.logoutUser();
    }

    public List<String> getUsers(){
        return userService.getUsers();
    }

    public List<String> getUsersSortedByFirstName(){
        return userService.getUsersSortedByFirstName();
    }

    public List<String> getUsersSortedByLastName(){
        return userService.getUsersSortedByLastName();
    }

    public List<String> getUsersSortedByEmail(){
        return userService.getUsersSortedByEmail();
    }

    public List<String> getUsersSortedByPurchasesCount(){
        return userService.getUsersSortedByPurchasesCount();
    }

    public List<String> filterUsersByFirstName(String firstname){
        return userService.filterUsersByFirstName(firstname);
    }

    public List<String> filterUsersByLastName(String lastname){
        return userService.filterUsersByLastName(lastname);
    }

    public List<String> filterUsersByEmail(String email){
        return userService.filterUsersByEmail(email);
    }

    public List<String> filterUsersByPurchasesCount(int purchasesCount){
        return userService.filterUsersByPurchasesCount(purchasesCount);
    }

    public int getLastUserId(){
        return userService.getLastUserId();
    }

    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public List<User> getUsersForTests(){
        return userService.getUsersForTests();
    }
}
