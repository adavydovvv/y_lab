package ru.ylab.out.repository;

import ru.ylab.model.Role;
import ru.ylab.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public void registerUser(User user) {
        if (user.getRole() == Role.ADMIN) {
            System.out.println("You can't register admin user");
        }
        else{
            for (User user1 : users) {
                if (user1.getUsername().equals(user.getUsername())) {
                    System.out.println("Username is already in use");
                }
                else {
                    users.add(user);
                    System.out.println("User added successfully");
                }
            }
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

    public void updateUser(User user, String password, String firstName, String lastName, String phone, String email, Role role) {
        if (role == Role.ADMIN) {
            System.out.println("Users cannot be assigned as administrators");
        }
        else{
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setEmail(email);

            System.out.println("User data has been successfully updated");
        }
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

    public int getLastUserId(User user) {
        return users.toArray().length;
    }
}


