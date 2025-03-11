package ru.ylab.service;

import org.springframework.stereotype.Service;
import ru.ylab.dto.UserDTO;
import ru.ylab.model.User;
import ru.ylab.model.Role;
import ru.ylab.out.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void registerUser(UserDTO user) {
        userRepository.registerUser(user);
    }

    public void updateUserRole(String username, Role role) {
        userRepository.updateUserRole(username, role);
    }

    public void updateUserPassword(User user, String password) {
        userRepository.updateUserPassword(user, password);
    }

    public void deleteUser(String username) {
        userRepository.deleteUser(username);
    }

    public void loginUser(String username, String password) {
        userRepository.loginUser(username, password);
    }

    public List<String> getUsers() {
        return userRepository.getUsers().stream()
                .map(user -> String.format("ID: %d %s %s, username: %s, Email: %s, Phone: %s",
                        user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public List<User> getUsersForTests() {
        return userRepository.getUsers();
    }

    public List<String> getUsersSortedByFirstName() {
        return userRepository.getUsersSortedByFirstName().stream()
                .map(user -> String.format("ID: %d %s %s, username: %s, Email: %s, Phone: %s",
                        user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public List<String> getUsersSortedByLastName() {
        return userRepository.getUsersSortedByLastName().stream()
                .map(user -> String.format("ID: %d %s %s, username: %s, Email: %s, Phone: %s",
                        user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public List<String> getUsersSortedByEmail() {
        return userRepository.getUsersSortedByEmail().stream()
                .map(user -> String.format("ID: %d %s %s, username: %s, Email: %s, Phone: %s",
                        user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public List<String> getUsersSortedByPurchasesCount() {
        return userRepository.getUsersSortedByPurchasesCount().stream()
                .map(user -> String.format("ID: %d %s %s, username: %s, Email: %s, Phone: %s",
                        user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public List<String> filterUsersByFirstName(String firstname) {
        return userRepository.filterUsersByFirstName(firstname).stream()
                .map(user -> String.format("ID: %d %s %s, username: %s, Email: %s, Phone: %s",
                        user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public List<String> filterUsersByLastName(String lastname) {
        return userRepository.filterUsersByLastName(lastname).stream()
                .map(user -> String.format("ID: %d %s %s, username: %s, Email: %s, Phone: %s",
                        user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public List<String> filterUsersByEmail(String email) {
        return userRepository.filterUsersByEmail(email).stream()
                .map(user -> String.format("ID: %d %s %s, username: %s, Email: %s, Phone: %s",
                        user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public List<String> filterUsersByPurchasesCount(int number_of_purchases) {
        return userRepository.filterUsersByPurchasesCount(number_of_purchases).stream()
                .map(user -> String.format("ID: %d %s %s, username: %s, Email: %s, Phone: %s",
                        user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUsersSorted(String sortBy) {
        List<User> users = getUsersSortedInternal(sortBy);
        return convertToDTO(users);
    }

    public List<UserDTO> getUsersFiltered(String filterBy, String filterValue) {
        List<User> users = getUsersFilteredInternal(filterBy, filterValue);
        return convertToDTO(users);
    }

    private List<User> getUsersSortedInternal(String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "firstname":
                return userRepository.getUsersSortedByFirstName();
            case "lastname":
                return userRepository.getUsersSortedByLastName();
            case "email":
                return userRepository.getUsersSortedByEmail();
            case "purchases":
                return userRepository.getUsersSortedByPurchasesCount();
            default:
                throw new IllegalArgumentException("Invalid sort parameter: " + sortBy);
        }
    }

    private List<User> getUsersFilteredInternal(String filterBy, String filterValue) {
        switch (filterBy.toLowerCase()) {
            case "firstname":
                return userRepository.filterUsersByFirstName(filterValue);
            case "lastname":
                return userRepository.filterUsersByLastName(filterValue);
            case "email":
                return userRepository.filterUsersByEmail(filterValue);
            case "purchases":
                return userRepository.filterUsersByPurchasesCount(Integer.parseInt(filterValue));
            default:
                throw new IllegalArgumentException("Invalid filter parameter: " + filterBy);
        }
    }

    private List<UserDTO> convertToDTO(List<User> users) {
        return users.stream()
                .map(user -> new UserDTO(
                        user.getUserId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhone()))
                .collect(Collectors.toList());
    }



    public int getLastUserId() {
        return userRepository.getLastUserId();
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public void updateUserPurchasesCount(User user) {
        userRepository.updateUserPurchasesCount(user);
    }

    private User convertToUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRole(userDTO.getRole());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
