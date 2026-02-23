package com.lorenzo.rentalmanagement.rental.mapper;

import com.lorenzo.rentalmanagement.rental.domain.entity.Rental;
import com.lorenzo.rentalmanagement.rental.dto.response.RentalResponse;

public class RentalMapper {

    private RentalMapper() {}

    public static RentalResponse toResponseDTO(Rental rental) {
        return new RentalResponse.Builder()
                .id(rental.getId())
                .propertyName(rental.getProperty().getName())
                .propertyCity(rental.getProperty().getCity())
                .userFullName(rental.getTenant().getFirstName() + " " + rental.getTenant().getLastName())
                .startDate(rental.getStartDate())
                .endDate(rental.getEndDate())
                .totalPrice(rental.getTotalPrice())
                .build();
    }
}