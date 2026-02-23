package com.lorenzo.rentalmanagement.property.mapper;

import com.lorenzo.rentalmanagement.property.domain.entity.Property;
import com.lorenzo.rentalmanagement.property.dto.request.PropertyRequest;
import com.lorenzo.rentalmanagement.property.dto.response.PropertyResponse;

public class PropertyMapper {
    private PropertyMapper() {
    }


    public static Property toEntity(PropertyRequest request) {
        return new Property(
                request.getName(),
                request.getAddress(),
                request.getCity(),
                request.getRooms(),
                request.getPricePerMonth()
        );
    }

    public static PropertyResponse toResponseDTO(Property property) {
        String ownerFullName = property.getOwner() != null
                ? property.getOwner().getFirstName() + " " + property.getOwner().getLastName()
                : "N/A";
        return new PropertyResponse.Builder()
                .id(property.getId())
                .ownerFullName(ownerFullName)
                .name(property.getName())
                .address(property.getAddress())
                .city(property.getCity())
                .rooms(property.getRooms())
                .pricePerMonth(property.getPricePerMonth())
                .createdAt(property.getCreatedAt())
                .updatedAt(property.getUpdatedAt())
                .build();
    }
}
