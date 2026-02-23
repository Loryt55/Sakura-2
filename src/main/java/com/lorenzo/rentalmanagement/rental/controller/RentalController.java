package com.lorenzo.rentalmanagement.rental.controller;

import com.lorenzo.rentalmanagement.rental.model.Rental;
import com.lorenzo.rentalmanagement.rental.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class RentalController {

    private final RentalService service;

    public RentalController(RentalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Rental> createBooking(@RequestBody Rental rental) {
        Rental saved = service.createBooking(rental);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Rental>> getAllBookings() {
        return ResponseEntity.ok(service.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getBookingById(@PathVariable Long id) {
        return service.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateBooking(@PathVariable Long id, @RequestBody Rental rental) {
        return service.updateBooking(id, rental)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        service.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}

