package ru.ylab.service;

import ru.ylab.model.User;
import ru.ylab.utils.Role;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class UserService {

    private List<User> users = new ArrayList<>();

    public void registerUser(User user) {
        users.add(user);
    }

    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers(User user) {
        if (user.getRole() == Role.ADMIN){
            return users;
        }
        else {
            System.out.println("You dont have the right to access this function");
            return null;
        }
    }

    public List<User> getUsersSortedByName(User user) {
        if (user.getRole() == Role.ADMIN) {
            return users.stream()
                    .sorted(Comparator.comparing(User::getFirstName))
                    .toList();
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<User> getUsersSortedByEmail(User user) {
        if (user.getRole() == Role.ADMIN) {
            return users.stream()
                    .sorted(Comparator.comparing(User::getEmail))
                    .toList();
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<User> getUsersSortedByPurchasesCount(User user) {
        if (user.getRole() == Role.ADMIN) {
            return users.stream()
                    .sorted(Comparator.comparing(User::getNumber_of_purchases))
                    .toList();
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

}
