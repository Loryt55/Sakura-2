package com.lorenzo.rentalmanagement.booking.service;

import com.lorenzo.rentalmanagement.booking.model.Booking;
import com.lorenzo.rentalmanagement.booking.repository.BookingRepository;
import com.lorenzo.rentalmanagement.property.model.Property;
import com.lorenzo.rentalmanagement.user.model.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public List<Booking> getAllBookings() {
        return repository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        // Recupera property completa dal DB
        Property property = repository.findById(booking.getProperty().getId())
                .orElseThrow(() -> new RuntimeException("Property not found with id " + booking.getProperty().getId())).getProperty();
        booking.setProperty(property);

        // Recupera user completo dal DB
        User user = repository.findById(booking.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + booking.getUser().getId())).getUser();
        booking.setUser(user);

        // Ora calcola totalPrice
        calculateTotalPrice(booking);

        return repository.save(booking);
    }


    public Optional<Booking> updateBooking(Long id, Booking updatedBooking) {
        return repository.findById(id).map(existing -> {
            existing.setProperty(updatedBooking.getProperty());
            existing.setUser(updatedBooking.getUser());
            existing.setStartDate(updatedBooking.getStartDate());
            existing.setEndDate(updatedBooking.getEndDate());
            existing.setActive(updatedBooking.getActive());
            calculateTotalPrice(existing);
            return repository.save(existing);
        });
    }

    public void deleteBooking(Long id) {
        repository.deleteById(id);
    }

    // METODO PRIVATO: calcola totalPrice basato sui mesi
    private void calculateTotalPrice(Booking booking) {
        //todo bug per cui non collega una property
        if (booking.getProperty() != null &&
                booking.getStartDate() != null &&
                booking.getEndDate() != null) {


            long months = ChronoUnit.MONTHS.between(
                    booking.getStartDate().withDayOfMonth(1),
                    booking.getEndDate().withDayOfMonth(1)
            ) + 1; // +1 per includere il mese iniziale

            booking.setTotalPrice(booking.getProperty().getPricePerMonth() * months);
        }
    }
}