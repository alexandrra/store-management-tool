package com.example.store_management_tool.controllers;

import com.example.store_management_tool.data.UserRequestDto;
import com.example.store_management_tool.data.entities.User;
import com.example.store_management_tool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> addUser(@RequestBody UserRequestDto user) {
        return userService.addUser(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @GetMapping("user/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }
}
