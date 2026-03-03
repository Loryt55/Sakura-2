package com.lorenzo.rentalmanagement.property.controller;

import com.lorenzo.rentalmanagement.property.dto.request.PropertyRequest;
import com.lorenzo.rentalmanagement.property.dto.response.PropertyResponse;
import com.lorenzo.rentalmanagement.property.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<PropertyResponse> create(@Valid @RequestBody PropertyRequest propertyRequest) {
        PropertyResponse propertyRespons = propertyService.create(propertyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyRespons);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER') or hasRole('TENANT')")
    public ResponseEntity<List<PropertyResponse>> getAll() {

        Long userId = getAuthenticatedUserId();
        String role = getAuthenticatedRole();

        return ResponseEntity.ok(propertyService.findAll(userId, role));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER') or hasRole('TENANT')")
    public ResponseEntity<PropertyResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<PropertyResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody PropertyRequest propertyRequest) {

        return ResponseEntity.ok(propertyService.update(id, propertyRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
    public ResponseEntity<PropertyResponse> delete(@PathVariable Long id) {
        propertyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Long getAuthenticatedUserId() {
        return (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private String getAuthenticatedRole() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");
    }
}

