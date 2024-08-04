package ru.ylab.out.repository;

import ru.ylab.model.Role;
import ru.ylab.model.User;
import ru.ylab.out.data.InMemoryDatabase;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {

    private List<User> users = InMemoryDatabase.getInstance().getUsers();

    public void registerUser(User user) {
        if (user.getRole() == Role.ADMIN) {
            System.out.println("You can't register admin user");
        } else {
            for (User existingUser : users) {
                if (existingUser.getUsername().equals(user.getUsername())) {
                    System.out.println("Username is already in use");
                    return;
                }
            }
            users.add(user);
            System.out.println("User added successfully");
        }
    }


    public void deleteUser(User user) {
        if (user.getRole() == Role.ADMIN) {
            System.out.println("You can't delete admin user");
        }
        else{
            users.remove(user);
        }

    }

    public void updateUserRole(User user, Role role) {
        if (role == Role.ADMIN) {
            System.out.println("Users cannot be assigned as administrators");
        }
        else{
            user.setRole(role);
            System.out.println("User data has been successfully updated");
        }
    }

    public void updateUserPassword(User user, String password) {
        user.setPassword(password);

        System.out.println("User data has been successfully updated");

    }

    public List<User> getUsers() {
        return users;
    }

    public List<User> getClients() {
        return users.stream()
                .filter(u -> u.getRole() == Role.CLIENT)
                .collect(Collectors.toList());
    }

    public List<User> getUsersSortedByFirstName() {
        return users.stream()
                .sorted(Comparator.comparing(User::getFirstName))
                .toList();
        }

    public List<User> getUsersSortedByLastName() {
        return users.stream()
                .sorted(Comparator.comparing(User::getLastName))
                .toList();

    }

    public List<User> getUsersSortedByEmail() {
        return users.stream()
                .sorted(Comparator.comparing(User::getEmail))
                .toList();

    }

    public List<User> getUsersSortedByPurchasesCount() {
        return users.stream()
                .sorted(Comparator.comparing(User::getNumber_of_purchases))
                .toList();

    }

    public List<User> filterUsersByFirstName(String firstname) {
        return users.stream()
                .filter(user -> user.getFirstName().equals(firstname))
                .toList();

    }

    public List<User> filterUsersByLastName(String lastname) {
        return users.stream()
                .filter(user -> user.getLastName().equals(lastname))
                .toList();

    }

    public List<User> filterUsersByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .toList();

    }

    public List<User> filterUsersByPurchasesCount(int number_of_purchases) {
        return users.stream()
                .filter(user -> user.getNumber_of_purchases() == (number_of_purchases))
                .toList();

    }

    public int getLastUserId() {
        return users.size();
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}


