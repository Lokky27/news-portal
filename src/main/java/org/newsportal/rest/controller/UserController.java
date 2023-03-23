package org.newsportal.rest.controller;

import org.newsportal.service.UserService;
import org.newsportal.service.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news-portal")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll().orElseThrow(() -> new RuntimeException("Cannot execute all users")));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(userService.getById(id).orElseThrow(() -> new RuntimeException("User doesn't exists in a system")));
    }

    @GetMapping("/users/username")
    public ResponseEntity<User> getUserByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.getByUsername(username).orElseThrow(() -> new RuntimeException("User doesn't exists in a system")));
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user).orElseThrow(() -> new RuntimeException("Cannot create user")));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<User> changeUserById(@PathVariable("userId") Long id,
                                               @RequestBody User userToUpdate) {
        return ResponseEntity.ok(userService.changeUserById(id, userToUpdate).orElseThrow(() -> new RuntimeException("User doesn't exists in a system")));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Boolean> removeUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.removeUserById(userId).orElseThrow(() ->
                new RuntimeException("User doesn't exists")));
    }
}
