package ru.ylab.service;

import ru.ylab.model.User;
import ru.ylab.model.Role;
import ru.ylab.out.repository.UserRepository;
import java.util.List;



public class UserService {

    private User authorizedUser;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthorizedUser() {
        return authorizedUser;
    }

    public void registerUser(User user) {
        userRepository.registerUser(user);
    }

    public void updateUser(User user, String password, String firstName, String lastName, String phone, String email, Role role) {
        if (authorizedUser.getRole() == Role.ADMIN) {
            userRepository.updateUser(user, password, firstName, lastName, phone, email, role);
        }
        else{
            System.out.println("You cannot change data of users");
        }
    }

    public void deleteUser(User user) {
        if (authorizedUser.getRole() == Role.ADMIN) {
            userRepository.deleteUser(user);
        }
        else{
            System.out.println("You cannot delete users");
        }
    }

    public void loginUser(String username, String password) {
        for (User user : userRepository.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                authorizedUser = user;
                System.out.println("User logged in successfully");
            }
            else{
                System.out.println("Error! Check your username and password");
            }
        }

    }

    public void logoutUser() {
        authorizedUser = null;
    }

    public List<User> getUsers() {
        if (authorizedUser.getRole() == Role.ADMIN) {
            return userRepository.getUsers();
        } else if (authorizedUser.getRole() == Role.MANAGER) {
            return userRepository.getClients();
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }
    

    public List<User> getUsersSortedByFirstName() {
        if (authorizedUser.getRole() == Role.ADMIN) {
            return userRepository.getUsersSortedByFirstName();
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }
    public List<User> getUsersSortedByLastName() {
        if (authorizedUser.getRole() == Role.ADMIN) {
            return userRepository.getUsersSortedByLastName();
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<User> getUsersSortedByEmail() {
        if (authorizedUser.getRole() == Role.ADMIN) {
            return userRepository.getUsersSortedByEmail();
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<User> getUsersSortedByPurchasesCount() {
        if (authorizedUser.getRole() == Role.ADMIN) {
            return userRepository.getUsersSortedByPurchasesCount();
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<User> filterUsersByFirstName(String firstname) {
        if (authorizedUser.getRole() == Role.ADMIN) {
            return userRepository.filterUsersByFirstName(firstname);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<User> filterUsersByLastName(String lastname) {
        if (authorizedUser.getRole() == Role.ADMIN) {
            return userRepository.filterUsersByLastName(lastname);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<User> filterUsersByEmail(String email) {
        if (authorizedUser.getRole() == Role.ADMIN) {
            return userRepository.filterUsersByEmail(email);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public List<User> filterUsersByPurchasesCount(int number_of_purchases) {
        if (authorizedUser.getRole() == Role.ADMIN) {
            return userRepository.filterUsersByPurchasesCount(number_of_purchases);
        } else {
            System.out.println("You don't have the right to access this function");
            return null;
        }
    }

    public int getLastUserId(User user) {
        return userRepository.getLastUserId(user);
    }

}
