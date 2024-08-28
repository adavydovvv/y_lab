package ru.ylab.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ylab.dto.UserDTO;
import ru.ylab.model.Role;
import ru.ylab.model.User;
import ru.ylab.service.UserService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}/purchases")
    public ResponseEntity<Void> updateUserPurchasesCount(@PathVariable String username, @RequestBody User user) {
        userService.updateUserPurchasesCount(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) throws SQLException {
        userService.registerUser(userDTO);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestParam String username, @RequestParam String password) {
        userService.loginUser(username, password);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{username}/password")
    public ResponseEntity<Void> updateUserPassword(@PathVariable String username, @RequestBody String password) {
        userService.updateUserPassword(userService.getUserByUsername(username), password);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}/role")
    public ResponseEntity<Void> updateUserRole(@PathVariable String username, @RequestBody Role role) {
        userService.updateUserRole(username, role);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<String>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<UserDTO>> getUsersSorted(@RequestParam String sortBy) {
        return ResponseEntity.ok(userService.getUsersSorted(sortBy));
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<UserDTO>> getUsersFiltered(@RequestParam String filterBy, @RequestParam String filterValue) {
        return ResponseEntity.ok(userService.getUsersFiltered(filterBy, filterValue));
    }

    @GetMapping("/last-id")
    public ResponseEntity<Integer> getLastUserId() {
        return ResponseEntity.ok(userService.getLastUserId());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/tests")
    public ResponseEntity<List<User>> getUsersForTests() {
        return ResponseEntity.ok(userService.getUsersForTests());
    }
}
