package com.lorenzo.rentalmanagement.property.service.impl;

import com.lorenzo.rentalmanagement.common.exception.ErrorMessages;
import com.lorenzo.rentalmanagement.property.domain.entity.Property;
import com.lorenzo.rentalmanagement.property.dto.request.PropertyRequest;
import com.lorenzo.rentalmanagement.property.dto.response.PropertyResponse;
import com.lorenzo.rentalmanagement.property.exception.ResourceNotFoundException;
import com.lorenzo.rentalmanagement.property.mapper.PropertyMapper;
import com.lorenzo.rentalmanagement.property.repository.PropertyRepository;
import com.lorenzo.rentalmanagement.property.service.PropertyService;
import com.lorenzo.rentalmanagement.user.domain.entity.User;
import com.lorenzo.rentalmanagement.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PropertyResponse create(PropertyRequest propertyRequest) {
        User owner = userRepository.findById(propertyRequest.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with id %d not found", propertyRequest.getOwnerId())));

        Property property = PropertyMapper.toEntity(propertyRequest);
        property.setActive(true);
        property.setOwner(owner);
        property.setCreatedAt(LocalDate.now());

        Property savedProperty = propertyRepository.save(property);
        return PropertyMapper.toResponseDTO(savedProperty);
    }

    @Override
    public List<PropertyResponse> findAll(Long userId, String role) {

        List<Property> properties;

        if (role.equals("OWNER")) {
            properties = propertyRepository.findAllByOwner_IdAndActiveTrue(userId);
        } else {
            properties = propertyRepository.findAllByActiveTrue();
        }

        return properties.stream()
                .map(PropertyMapper::toResponseDTO)
                .toList();
    }

    @Override
    public PropertyResponse findById(Long id) {
        return PropertyMapper.toResponseDTO(findPropertyOrThrow(id));
    }

    @Override
    public PropertyResponse update(Long id, PropertyRequest propertyRequest) {
        Property propertyExisting = findPropertyOrThrow(id);

        User owner = userRepository.findById(propertyRequest.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with id %d not found", propertyRequest.getOwnerId())));

        propertyExisting.setOwner(owner);
        propertyExisting.setName(propertyRequest.getName());
        propertyExisting.setAddress(propertyRequest.getAddress());
        propertyExisting.setCity(propertyRequest.getCity());
        propertyExisting.setRooms(propertyRequest.getRooms());
        propertyExisting.setPricePerMonth(propertyRequest.getPricePerMonth());
        propertyExisting.setUpdatedAt(LocalDate.now());

        Property updatedProperty = propertyRepository.save(propertyExisting);

        return PropertyMapper.toResponseDTO(updatedProperty);
    }

    @Override
    public void deleteById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property with id " + id + " not found"));

        property.setActive(false);
        propertyRepository.save(property);
    }

    private Property findPropertyOrThrow(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessages.PROPERTY_NOT_FOUND, id)
                ));
    }
}
