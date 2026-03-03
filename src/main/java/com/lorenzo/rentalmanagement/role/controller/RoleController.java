package com.lorenzo.rentalmanagement.role.controller;

import com.lorenzo.rentalmanagement.role.dto.response.RoleResponse;
import com.lorenzo.rentalmanagement.role.service.RoleService;
import com.lorenzo.rentalmanagement.role.service.impl.RoleServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<List<RoleResponse>> getAll() {
        return ResponseEntity.ok(roleService.findAll());
    }
}