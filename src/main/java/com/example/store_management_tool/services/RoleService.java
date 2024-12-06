package com.example.store_management_tool.services;

import com.example.store_management_tool.data.entities.Role;
import com.example.store_management_tool.data.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Role addRole(Role role) {
        return repository.save(role);
    }
}
