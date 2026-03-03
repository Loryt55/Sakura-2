package com.lorenzo.rentalmanagement.rental.service.impl;

import com.lorenzo.rentalmanagement.property.domain.entity.Property;
import com.lorenzo.rentalmanagement.common.exception.ResourceNotFoundException;
import com.lorenzo.rentalmanagement.property.repository.PropertyRepository;
import com.lorenzo.rentalmanagement.rental.domain.entity.Rental;
import com.lorenzo.rentalmanagement.rental.dto.request.RentalRequest;
import com.lorenzo.rentalmanagement.rental.dto.response.RentalResponse;
import com.lorenzo.rentalmanagement.rental.mapper.RentalMapper;
import com.lorenzo.rentalmanagement.rental.repository.RentalRepository;
import com.lorenzo.rentalmanagement.rental.service.RentalService;
import com.lorenzo.rentalmanagement.user.domain.entity.User;
import com.lorenzo.rentalmanagement.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public RentalServiceImpl(RentalRepository rentalRepository,
                             PropertyRepository propertyRepository,
                             UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RentalResponse create(RentalRequest request) {
        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Property with id %d not found", request.getPropertyId())));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with id %d not found", request.getUserId())));

        BigDecimal totalPrice = calculateTotalPrice(
                request.getStartDate(),
                request.getEndDate(),
                property.getPricePerMonth()
        );

        Rental rental = new Rental(
                property,
                user,
                request.getStartDate(),
                request.getEndDate(),
                totalPrice,
                true
        );

        return RentalMapper.toResponseDTO(rentalRepository.save(rental));
    }

    @Override
    public List<RentalResponse> findAll(Long userId, String role) {

        List<Rental> rentals;

        if (role.equals("OWNER")) {
            rentals = rentalRepository.findAllByProperty_Owner_IdAndActiveTrue(userId);
        } else if (role.equals("TENANT")) {
            rentals = rentalRepository.findAllByTenant_IdAndActiveTrue(userId);
        } else {
            rentals = rentalRepository.findAllByActiveTrue();
        }
        return rentals
                .stream()
                .map(RentalMapper::toResponseDTO)
                .toList();
    }

    @Override
    public RentalResponse findById(Long id) {
        return RentalMapper.toResponseDTO(findRentalOrThrow(id));
    }

    @Override
    public RentalResponse update(Long id, RentalRequest request) {
        Rental rental = findRentalOrThrow(id);

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Property with id %d not found", request.getPropertyId())));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with id %d not found", request.getUserId())));

        BigDecimal totalPrice = calculateTotalPrice(
                request.getStartDate(),
                request.getEndDate(),
                property.getPricePerMonth()
        );

        rental.setProperty(property);
        rental.setTenant(user);
        rental.setStartDate(request.getStartDate());
        rental.setEndDate(request.getEndDate());
        rental.setTotalPrice(totalPrice);

        return RentalMapper.toResponseDTO(rentalRepository.save(rental));
    }

    @Override
    public void deleteById(Long id) {
        Rental rental = findRentalOrThrow(id);
        rental.setActive(false);
        rentalRepository.save(rental);
    }

    private Rental findRentalOrThrow(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Rental with id %d not found", id)));
    }

    private BigDecimal calculateTotalPrice(
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal pricePerMonth) {

        long days = ChronoUnit.DAYS.between(startDate, endDate);

        if (days <= 0) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        BigDecimal dailyRate = pricePerMonth
                .divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP);

        return dailyRate.multiply(BigDecimal.valueOf(days));
    }
}