package com.lorenzo.rentalmanagement.booking.repository;

import com.lorenzo.rentalmanagement.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // JpaRepository fornisce già tutti i metodi CRUD
}