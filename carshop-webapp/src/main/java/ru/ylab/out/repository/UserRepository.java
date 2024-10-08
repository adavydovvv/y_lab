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

    private static final String INSERT_USER_SQL = "INSERT INTO carshop.users (id, username, password, first_name, last_name, phone_number, email, role) " +
            "VALUES (nextval('public.user_id_seq'), ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_USER_SQL = "DELETE FROM carshop.users WHERE username = ?";
    private static final String LOGIN_USER_SQL = "SELECT * FROM carshop.users WHERE username = ? AND password = ?";
    private static final String UPDATE_USER_ROLE_SQL = "UPDATE carshop.users SET role = ? WHERE username = ?";
    private static final String UPDATE_USER_PURCHASES_COUNT_SQL = "UPDATE carshop.users SET number_of_purchases = ? WHERE id = ?";
    private static final String UPDATE_USER_PASSWORD_SQL = "UPDATE carshop.users SET password = ? WHERE id = ?";
    private static final String GET_USERS_SQL = "SELECT * FROM carshop.users";
    private static final String GET_USERS_SORTED_BY_FIRST_NAME_SQL = "SELECT * FROM carshop.users ORDER BY first_name";
    private static final String GET_USERS_SORTED_BY_LAST_NAME_SQL = "SELECT * FROM carshop.users ORDER BY last_name";
    private static final String GET_USERS_SORTED_BY_EMAIL_SQL = "SELECT * FROM carshop.users ORDER BY email";
    private static final String GET_USERS_SORTED_BY_PURCHASES_COUNT_SQL = "SELECT * FROM carshop.users ORDER BY number_of_purchases";
    private static final String GET_USER_BY_USERNAME_SQL = "SELECT * FROM carshop.users WHERE username = ?";
    private static final String GET_USER_BY_ID_SQL = "SELECT * FROM carshop.users WHERE id = ?";
    private static final String FILTER_USERS_BY_FIRST_NAME_SQL = "SELECT * FROM carshop.users WHERE first_name = ?";
    private static final String FILTER_USERS_BY_LAST_NAME_SQL = "SELECT * FROM carshop.users WHERE last_name = ?";
    private static final String FILTER_USERS_BY_EMAIL_SQL = "SELECT * FROM carshop.users WHERE email = ?";
    private static final String FILTER_USERS_BY_PURCHASES_COUNT_SQL = "SELECT * FROM carshop.users WHERE number_of_purchases = ?";
    private static final String GET_LAST_USER_ID_SQL = "SELECT COUNT(*) FROM carshop.users";

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerUser(UserDTO user) {
        jdbcTemplate.update(INSERT_USER_SQL, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(),
                user.getPhone(), user.getEmail(), user.getRole().name());
    }

    public void deleteUser(String username) {
        jdbcTemplate.update(DELETE_USER_SQL, username);
    }

    public void loginUser(String username, String password) {
        User user = jdbcTemplate.queryForObject(LOGIN_USER_SQL, new Object[]{username, password}, this::mapRowToUser);
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
            jdbcTemplate.update(UPDATE_USER_ROLE_SQL, role.name(), username);
        }
    }

    public void updateUserPurchasesCount(User user) {
        jdbcTemplate.update(UPDATE_USER_PURCHASES_COUNT_SQL, user.getNumber_of_purchases(), user.getUserId());
    }

    public void updateUserPassword(User user, String password) {
        jdbcTemplate.update(UPDATE_USER_PASSWORD_SQL, password, user.getUserId());
    }

    public List<User> getUsers() {
        return jdbcTemplate.query(GET_USERS_SQL, this::mapRowToUser);
    }

    public List<User> getUsersSortedByFirstName() {
        return jdbcTemplate.query(GET_USERS_SORTED_BY_FIRST_NAME_SQL, this::mapRowToUser);
    }

    public List<User> getUsersSortedByLastName() {
        return jdbcTemplate.query(GET_USERS_SORTED_BY_LAST_NAME_SQL, this::mapRowToUser);
    }

    public List<User> getUsersSortedByEmail() {
        return jdbcTemplate.query(GET_USERS_SORTED_BY_EMAIL_SQL, this::mapRowToUser);
    }

    public List<User> getUsersSortedByPurchasesCount() {
        return jdbcTemplate.query(GET_USERS_SORTED_BY_PURCHASES_COUNT_SQL, this::mapRowToUser);
    }

    public User getUserByUsername(String username) {
        return jdbcTemplate.queryForObject(GET_USER_BY_USERNAME_SQL, new Object[]{username}, this::mapRowToUser);
    }

    public User getUserById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID_SQL, new Object[]{id}, this::mapRowToUser);
    }

    public List<User> filterUsersByFirstName(String firstname) {
        return jdbcTemplate.query(FILTER_USERS_BY_FIRST_NAME_SQL, new Object[]{firstname}, this::mapRowToUser);
    }

    public List<User> filterUsersByLastName(String lastname) {
        return jdbcTemplate.query(FILTER_USERS_BY_LAST_NAME_SQL, new Object[]{lastname}, this::mapRowToUser);
    }

    public List<User> filterUsersByEmail(String email) {
        return jdbcTemplate.query(FILTER_USERS_BY_EMAIL_SQL, new Object[]{email}, this::mapRowToUser);
    }

    public List<User> filterUsersByPurchasesCount(int number_of_purchases) {
        return jdbcTemplate.query(FILTER_USERS_BY_PURCHASES_COUNT_SQL, new Object[]{number_of_purchases}, this::mapRowToUser);
    }

    public int getLastUserId() {
        return jdbcTemplate.queryForObject(GET_LAST_USER_ID_SQL, Integer.class);
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

