package com.example.store_management_tool.services;

import com.example.store_management_tool.data.UserRequestDto;
import com.example.store_management_tool.data.entities.Role;
import com.example.store_management_tool.data.entities.User;
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
public class RoleService {
    @Autowired
    private RoleRepository repository;

    public ResponseEntity<String> addRole(Role role) {
        repository.save(role);
        return new ResponseEntity<>("Role added successfully", HttpStatus.CREATED);
    }
}
