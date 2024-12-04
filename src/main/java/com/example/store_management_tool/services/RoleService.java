package com.example.store_management_tool.services;

import com.example.store_management_tool.data.entities.Role;
import com.example.store_management_tool.data.repositories.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<String> addRole(Role role) {
        repository.save(role);
        return new ResponseEntity<>("Role added successfully", HttpStatus.CREATED);
    }
}
