package com.example.store_management_tool.services;

import com.example.store_management_tool.data.UserRequestDto;
import com.example.store_management_tool.data.entities.Role;
import com.example.store_management_tool.data.entities.StoreUser;
import com.example.store_management_tool.data.repositories.RoleRepository;
import com.example.store_management_tool.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> addUser(UserRequestDto user) {
        try {
            String role = Boolean.TRUE.equals(user.isAdmin()) ? "ROLE_ADMIN" : "ROLE_USER";
            Role userRole = roleRepository.findByAuthority(role).orElseThrow(() -> new NoSuchElementException(("Authority not present")));

            StoreUser userToBeAdded = new StoreUser();
            userToBeAdded.setUsername(user.getUsername());
            userToBeAdded.setPassword(passwordEncoder.encode(user.getPassword()));
            userToBeAdded.setRole(userRole);
            userRepository.save(userToBeAdded);

            return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Error while adding user", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<StoreUser> getUserByUsername(String username) {
        try
        {
            if (userRepository.findByUsername(username).isPresent())
                return new ResponseEntity<>(userRepository.findByUsername(username).get(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new StoreUser(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> deleteUser(int id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Error while deleting user", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<StoreUser>> getAllUsers() {
        try
        {
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
}
