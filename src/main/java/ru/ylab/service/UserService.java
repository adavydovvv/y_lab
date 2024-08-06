package ru.ylab.service;

import ru.ylab.config.AppConfig;
import ru.ylab.model.User;
import ru.ylab.model.Role;
import ru.ylab.out.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;



public class UserService{

    AppConfig appConfig = AppConfig.getInstance();
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthorizedUser() {
        return appConfig.getAuthorizedUser();
    }

    public void registerUser(User user) {
        userRepository.registerUser(user);
    }

    public void updateUserRole(User user, Role role) {
        userRepository.updateUserRole(user, role);
    }

    public void updateUserPassword(User user, String password) {
        userRepository.updateUserPassword(user, password);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    public void loginUser(String username, String password) {
        boolean userFound = false;
        for (User user : userRepository.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                appConfig.setAuthorizedUser(user);
                System.out.println("User logged in successfully");
                userFound = true;
                break;
            }
        }
        if (!userFound) {
            System.out.println("Error! Check your username and password");
        }
    }

    public void logoutUser() {
        appConfig.setAuthorizedUser(null);
    }

    public List<String> getUsers() {
        List<String> userNames = new ArrayList<>();
        for (User user : userRepository.getUsers()) {
            userNames.add("ID: " + user.getUserId() + " " + user.getFirstName() + " " + user.getLastName() + ", username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone());
        }
        return userNames;
    }

    public List<User> getUsersForTests() {
        return userRepository.getUsers();
    }
    

    public List<String> getUsersSortedByFirstName() {
        List<String> userNames = new ArrayList<>();
        for (User user : userRepository.getUsersSortedByFirstName()) {
            userNames.add("ID: " + user.getUserId() + " " + user.getFirstName() + " " + user.getLastName() + ", username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone());
        }
        return userNames;
    }
    public List<String> getUsersSortedByLastName() {
        List<String> userNames = new ArrayList<>();
        for (User user : userRepository.getUsersSortedByLastName()) {
            userNames.add("ID: " + user.getUserId() + " " + user.getFirstName() + " " + user.getLastName() + ", username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone());
        }
        return userNames;

    }

    public List<String> getUsersSortedByEmail() {
        List<String> userNames = new ArrayList<>();
        for (User user : userRepository.getUsersSortedByEmail()) {
            userNames.add("ID: " + user.getUserId() + " " + user.getFirstName() + " " + user.getLastName() + ", username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone());
        }
        return userNames;

    }

    public List<String> getUsersSortedByPurchasesCount() {
        List<String> userNames = new ArrayList<>();
        for (User user : userRepository.getUsersSortedByPurchasesCount()) {
            userNames.add("ID: " + user.getUserId() + " " + user.getFirstName() + " " + user.getLastName() + ", username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone());
        }
        return userNames;

    }

    public List<String> filterUsersByFirstName(String firstname) {
        List<String> userNames = new ArrayList<>();
        for (User user : userRepository.filterUsersByFirstName(firstname)) {
            userNames.add("ID: " + user.getUserId() + " " + user.getFirstName() + " " + user.getLastName() + ", username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone());
        }
        return userNames;

    }

    public List<String> filterUsersByLastName(String lastname) {
        List<String> userNames = new ArrayList<>();
        for (User user : userRepository.filterUsersByLastName(lastname)) {
            userNames.add("ID: " + user.getUserId() + " " + user.getFirstName() + " " + user.getLastName() + ", username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone());
        }
        return userNames;

    }

    public List<String> filterUsersByEmail(String email) {
        List<String> userNames = new ArrayList<>();
        for (User user : userRepository.filterUsersByEmail(email)) {
            userNames.add("ID: " + user.getUserId() + " " + user.getFirstName() + " " + user.getLastName() + ", username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone());
        }
        return userNames;

    }

    public List<String> filterUsersByPurchasesCount(int number_of_purchases) {
        List<String> userNames = new ArrayList<>();
        for (User user : userRepository.filterUsersByPurchasesCount(number_of_purchases)) {
            userNames.add("ID: " + user.getUserId() + " " + user.getFirstName() + " " + user.getLastName() + ", username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Phone: " + user.getPhone());
        }
        return userNames;

    }

    public int getLastUserId() {
        return userRepository.getLastUserId();
    }

    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }


}
