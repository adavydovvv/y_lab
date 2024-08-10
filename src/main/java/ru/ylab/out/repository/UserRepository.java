package ru.ylab.out.repository;

import ru.ylab.config.AppConfig;
import ru.ylab.model.Role;
import ru.ylab.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UserRepository {

    AppConfig appConfig = AppConfig.getInstance();

    public void registerUser(User user) throws SQLException {

        String sql = "INSERT INTO carshop.users (id, username, password, first_name, last_name, phone_number, email, role) " +
                "VALUES (nextval('public.user_id_seq'), ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getRole().name());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when adding a user: " + e.getMessage());
        }
    }

    public void deleteUser(String username){

        String sql = "DELETE FROM carshop.users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred");
        }

    }

    public void loginUser(String username, String password) {
        String sql = "SELECT * FROM carshop.users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"), Role.valueOf(rs.getString("role")) ,rs.getString("phone_number"), rs.getString("email"));
                    appConfig.setAuthorizedUser(user);
                    System.out.println("User logged in successfully");
                } else {
                    System.out.println("Error! Check your username and password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred");
        }
    }


    public void updateUserRole(String username, Role role) {
        if (role == Role.ADMIN) {
            System.out.println("Users cannot be assigned as administrators");
        } else {
            String sql = "UPDATE carshop.users SET role = ? WHERE username = ?";

            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, role.name());
                stmt.setString(2, username);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("User data has been successfully updated");
                } else {
                    System.out.println("User not found or no changes made");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Database error occurred");
            }
        }
    }

    public void updateUserPurchasesCount(User user) {
        String sql = "UPDATE carshop.users SET number_of_purchases = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getNumber_of_purchases());
            stmt.setInt(2, user.getUserId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("User not found or no changes made");
            }
        } catch (SQLException e) {
            System.out.println("User purchases count update failed: " + e.getMessage());
        }
    }

    public void updateUserPassword(User user, String password) {
        String sql = "UPDATE carshop.users SET password = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, password);
            stmt.setInt(2, user.getUserId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User password has been successfully updated");
            } else {
                System.out.println("User not found or no changes made");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred");
        }
    }


    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM carshop.users";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5438/carshop","admin", "ylab");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"), Role.valueOf(rs.getString("role")) ,rs.getString("phone_number"), rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error occurred");
        }

        return users;
    }



    public List<User> getUsersSortedByFirstName() {
        return getUsers().stream()
                .sorted(Comparator.comparing(User::getFirstName))
                .toList();
    }

    public List<User> getUsersSortedByLastName() {
        return getUsers().stream()
                .sorted(Comparator.comparing(User::getLastName))
                .toList();

    }

    public List<User> getUsersSortedByEmail() {
        return getUsers().stream()
                .sorted(Comparator.comparing(User::getEmail))
                .toList();

    }

    public List<User> getUsersSortedByPurchasesCount() {
        return getUsers().stream()
                .sorted(Comparator.comparing(User::getNumber_of_purchases))
                .toList();

    }

    public List<User> filterUsersByFirstName(String firstname) {
        return getUsers().stream()
                .filter(user -> user.getFirstName().equals(firstname))
                .toList();

    }

    public List<User> filterUsersByLastName(String lastname) {
        return getUsers().stream()
                .filter(user -> user.getLastName().equals(lastname))
                .toList();

    }

    public List<User> filterUsersByEmail(String email) {
        return getUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .toList();

    }

    public List<User> filterUsersByPurchasesCount(int number_of_purchases) {
        return getUsers().stream()
                .filter(user -> user.getNumber_of_purchases() == (number_of_purchases))
                .toList();

    }

    public int getLastUserId() {
        return getUsers().size();
    }

    public User getUserByUsername(String username) {
        for (User user : getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User getUserById(int id) {
        for (User user : getUsers()) {
            if (user.getUserId() == id) {
                return user;
            }
        }
        return null;
    }


}


