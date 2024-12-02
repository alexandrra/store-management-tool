package com.example.store_management_tool.controllers;

import com.example.store_management_tool.data.entities.Role;
import com.example.store_management_tool.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoleController {
    @Autowired
    private RoleService service;

    @PostMapping("/role")
    public ResponseEntity<String> addRole (@RequestBody Role role) {
        return service.addRole(role);
    }
}
