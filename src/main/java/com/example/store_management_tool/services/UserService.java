package com.example.store_management_tool.services;

import com.example.store_management_tool.data.dtos.UserRequestDto;
import com.example.store_management_tool.data.entities.Role;
import com.example.store_management_tool.data.entities.StoreUser;
import com.example.store_management_tool.data.exceptions.UserNotFoundException;
import com.example.store_management_tool.data.repositories.RoleRepository;
import com.example.store_management_tool.data.repositories.UserRepository;
import com.example.store_management_tool.utils.Constants;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public StoreUser addUser(UserRequestDto user) {
        String role = Boolean.TRUE.equals(user.isAdmin()) ? "ROLE_ADMIN" : "ROLE_USER";
        Role userRole = roleRepository.findByAuthority(role).orElseThrow(() -> new NoSuchElementException(("Authority not present")));

        StoreUser userToBeAdded = new StoreUser();
        userToBeAdded.setUsername(user.getUsername());
        userToBeAdded.setPassword(passwordEncoder.encode(user.getPassword()));
        userToBeAdded.setRole(userRole);
        userRepository.save(userToBeAdded);

        return userToBeAdded;
    }

    public StoreUser getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(Constants.USER_NOT_FOUND));
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public List<StoreUser> getAllUsers() {
        return userRepository.findAll();
    }
}
