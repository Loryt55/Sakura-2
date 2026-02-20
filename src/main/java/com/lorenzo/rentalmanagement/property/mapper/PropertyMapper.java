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
                request.getPricePerMonth(),
                request.getActive()
        );
    }

    public static PropertyResponse toResponseDTO(Property property) {
        return new PropertyResponse(
                property.getId(),
                property.getName(),
                property.getAddress(),
                property.getCity(),
                property.getRooms(),
                property.getPricePerMonth(),
                property.getActive(),
                property.getCreatedAt()
        );
    }
}
