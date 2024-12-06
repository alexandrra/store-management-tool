package com.example.store_management_tool.controllers;

import com.example.store_management_tool.data.dtos.UserRequestDto;
import com.example.store_management_tool.data.entities.StoreUser;
import com.example.store_management_tool.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<StoreUser> addUser(@RequestBody UserRequestDto user) {
        var response = userService.addUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/users")
    public ResponseEntity<List<StoreUser>> getUsers() {
        var response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("user/{id}")
    public ResponseEntity<StoreUser> getUserById(@PathVariable int id) {
        var response = userService.getUserById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
