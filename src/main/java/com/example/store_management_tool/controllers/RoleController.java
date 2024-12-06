package com.example.store_management_tool.controllers;

import com.example.store_management_tool.data.entities.Role;
import com.example.store_management_tool.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {
    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @PostMapping("/role")
    public ResponseEntity<Role> addRole (@RequestBody Role role) {
        var response = service.addRole(role);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
