package com.lorenzo.rentalmanagement.rental.repository;

import com.lorenzo.rentalmanagement.rental.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    // JpaRepository fornisce già tutti i metodi CRUD
}