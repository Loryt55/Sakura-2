package com.lorenzo.rentalmanagement.rental.service.impl;

import com.lorenzo.rentalmanagement.rental.domain.entity.Rental;
import com.lorenzo.rentalmanagement.rental.repository.RentalRepository;
import com.lorenzo.rentalmanagement.property.domain.entity.Property;
import com.lorenzo.rentalmanagement.rental.service.RentalService;
import com.lorenzo.rentalmanagement.user.domain.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository repository;

    public RentalServiceImpl(RentalRepository repository) {
        this.repository = repository;
    }

    public List<Rental> getAllBookings() {
        return repository.findAll();
    }

    public Optional<Rental> getBookingById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Rental createBooking(Rental rental) {
        // Recupera property completa dal DB
        Property property = repository.findById(rental.getProperty().getId())
                .orElseThrow(() -> new RuntimeException("Property not found with id " + rental.getProperty().getId())).getProperty();
        rental.setProperty(property);

        // Recupera user completo dal DB
        User user = repository.findById(rental.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + rental.getUser().getId())).getUser();
        rental.setUser(user);


        return repository.save(rental);
    }


    public Optional<Rental> updateBooking(Long id, Rental updatedRental) {
        return repository.findById(id).map(existing -> {
            existing.setProperty(updatedRental.getProperty());
            existing.setUser(updatedRental.getUser());
            existing.setStartDate(updatedRental.getStartDate());
            existing.setEndDate(updatedRental.getEndDate());
            existing.setActive(updatedRental.getActive());
            return repository.save(existing);
        });
    }

    public void deleteBooking(Long id) {
        repository.deleteById(id);
    }

    // METODO PRIVATO: calcola totalPrice basato sui mesi

}