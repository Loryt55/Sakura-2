package com.lorenzo.rentalmanagement.rental.service.impl;

import com.lorenzo.rentalmanagement.property.domain.entity.Property;
import com.lorenzo.rentalmanagement.property.repository.PropertyRepository;
import com.lorenzo.rentalmanagement.rental.domain.entity.Rental;
import com.lorenzo.rentalmanagement.rental.dto.request.RentalRequest;
import com.lorenzo.rentalmanagement.rental.dto.response.RentalResponse;
import com.lorenzo.rentalmanagement.rental.mapper.RentalMapper;
import com.lorenzo.rentalmanagement.rental.repository.RentalRepository;
import com.lorenzo.rentalmanagement.rental.service.RentalService;
import com.lorenzo.rentalmanagement.property.exception.ResourceNotFoundException;
import com.lorenzo.rentalmanagement.user.domain.entity.User;
import com.lorenzo.rentalmanagement.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<RentalResponse> findAll() {
        return rentalRepository.findAll()
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

    // --- metodi privati ---

    private Rental findRentalOrThrow(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Rental with id %d not found", id)));
    }

    private BigDecimal calculateTotalPrice(
            java.time.LocalDate startDate,
            java.time.LocalDate endDate,
            BigDecimal pricePerMonth) {

        long months = ChronoUnit.MONTHS.between(startDate, endDate);

        LocalDate afterFullMonths = startDate.plusMonths(months);
        if (afterFullMonths.isBefore(endDate)) {
            months++;
        }

        return pricePerMonth.multiply(BigDecimal.valueOf(months));
    }
}