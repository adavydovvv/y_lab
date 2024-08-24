package ru.ylab.out.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ylab.dto.UserDTO;
import ru.ylab.model.Role;
import ru.ylab.model.User;

import java.sql.*;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerUser(UserDTO user) {
        String sql = "INSERT INTO carshop.users (id, username, password, first_name, last_name, phone_number, email, role) " +
                "VALUES (nextval('public.user_id_seq'), ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(),
                user.getPhone(), user.getEmail(), user.getRole().name());
    }

    public void deleteUser(String username) {
        String sql = "DELETE FROM carshop.users WHERE username = ?";
        jdbcTemplate.update(sql, username);
    }

    public void loginUser(String username, String password) {
        String sql = "SELECT * FROM carshop.users WHERE username = ? AND password = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{username, password}, this::mapRowToUser);
        if (user != null) {
            System.out.println("User logged in successfully");
        } else {
            System.out.println("Error! Check your username and password");
        }
    }

    public void updateUserRole(String username, Role role) {
        if (role == Role.ADMIN) {
            System.out.println("Users cannot be assigned as administrators");
        } else {
            String sql = "UPDATE carshop.users SET role = ? WHERE username = ?";
            jdbcTemplate.update(sql, role.name(), username);
        }
    }

    public void updateUserPurchasesCount(User user) {
        String sql = "UPDATE carshop.users SET number_of_purchases = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getNumber_of_purchases(), user.getUserId());
    }

    public void updateUserPassword(User user, String password) {
        String sql = "UPDATE carshop.users SET password = ? WHERE id = ?";
        jdbcTemplate.update(sql, password, user.getUserId());
    }

    public List<User> getUsers() {
        String sql = "SELECT * FROM carshop.users";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }


    public List<User> getUsersSortedByFirstName() {
        String sql = "SELECT * FROM carshop.users ORDER BY first_name";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    public List<User> getUsersSortedByLastName() {
        String sql = "SELECT * FROM carshop.users ORDER BY last_name";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    public List<User> getUsersSortedByEmail() {
        String sql = "SELECT * FROM carshop.users ORDER BY email";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    public List<User> getUsersSortedByPurchasesCount() {
        String sql = "SELECT * FROM carshop.users ORDER BY number_of_purchases";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM carshop.users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, this::mapRowToUser);
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM carshop.users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToUser);
    }

    public List<User> filterUsersByFirstName(String firstname) {
        String sql = "SELECT * FROM carshop.users WHERE first_name = ?";
        return jdbcTemplate.query(sql, new Object[]{firstname}, this::mapRowToUser);
    }

    public List<User> filterUsersByLastName(String lastname) {
        String sql = "SELECT * FROM carshop.users WHERE last_name = ?";
        return jdbcTemplate.query(sql, new Object[]{lastname}, this::mapRowToUser);
    }

    public List<User> filterUsersByEmail(String email) {
        String sql = "SELECT * FROM carshop.users WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, this::mapRowToUser);
    }

    public List<User> filterUsersByPurchasesCount(int number_of_purchases) {
        String sql = "SELECT * FROM carshop.users WHERE number_of_purchases = ?";
        return jdbcTemplate.query(sql, new Object[]{number_of_purchases}, this::mapRowToUser);
    }

    public int getLastUserId() {
        String sql = "SELECT COUNT(*) FROM carshop.users";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                Role.valueOf(rs.getString("role")),
                rs.getString("phone_number"),
                rs.getString("email")
        );
    }
}
