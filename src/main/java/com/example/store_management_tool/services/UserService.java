package com.example.store_management_tool.services;

import com.example.store_management_tool.data.dtos.UserRequestDto;
import com.example.store_management_tool.data.entities.Role;
import com.example.store_management_tool.data.entities.StoreUser;
import com.example.store_management_tool.data.exceptions.UserAlreadyExistsException;
import com.example.store_management_tool.data.exceptions.UserNotFoundException;
import com.example.store_management_tool.data.repositories.RoleRepository;
import com.example.store_management_tool.data.repositories.UserRepository;
import com.example.store_management_tool.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> addUser(UserRequestDto user) {
        String role = Boolean.TRUE.equals(user.isAdmin()) ? "ROLE_ADMIN" : "ROLE_USER";
        Role userRole = roleRepository.findByAuthority(role).orElseThrow(() -> new NoSuchElementException(("Authority not present")));

        StoreUser userToBeAdded = new StoreUser();
        userToBeAdded.setUsername(user.getUsername());
        userToBeAdded.setPassword(passwordEncoder.encode(user.getPassword()));
        userToBeAdded.setRole(userRole);
        userRepository.save(userToBeAdded);

        return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<StoreUser> getUserByUsername(String username) {
        if (userRepository.findByUsername(username).isEmpty())
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);

        return new ResponseEntity<>(userRepository.findByUsername(username).get(), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteUser(int id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    public ResponseEntity<List<StoreUser>> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}
