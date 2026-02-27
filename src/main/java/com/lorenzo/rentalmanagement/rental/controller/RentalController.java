package com.lorenzo.rentalmanagement.rental.controller;

import com.lorenzo.rentalmanagement.rental.dto.request.RentalRequest;
import com.lorenzo.rentalmanagement.rental.dto.response.RentalResponse;
import com.lorenzo.rentalmanagement.rental.service.RentalService;
import com.lorenzo.rentalmanagement.rental.service.impl.RentalServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalServiceImpl rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<RentalResponse> create(@Valid @RequestBody RentalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<RentalResponse>> getAll() {
        Long userId = getAuthenticatedUserId();
        String role = getAuthenticatedRole();
        return ResponseEntity.ok(rentalService.findAll(userId, role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody RentalRequest request) {
        return ResponseEntity.ok(rentalService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rentalService.deleteById(id);
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