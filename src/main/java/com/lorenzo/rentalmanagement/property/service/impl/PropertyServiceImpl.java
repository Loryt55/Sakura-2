package com.lorenzo.rentalmanagement.property.service.impl;

import com.lorenzo.rentalmanagement.property.domain.entity.Property;
import com.lorenzo.rentalmanagement.property.dto.request.PropertyRequest;
import com.lorenzo.rentalmanagement.property.dto.response.PropertyResponse;
import com.lorenzo.rentalmanagement.property.mapper.PropertyMapper;
import com.lorenzo.rentalmanagement.property.repository.PropertyRepository;
import com.lorenzo.rentalmanagement.property.service.PropertyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public PropertyResponse create(PropertyRequest propertyRequest) {
        Property property = PropertyMapper.toEntity(propertyRequest);
        Property savedProperty = propertyRepository.save(property);
        return PropertyMapper.toResponseDTO(savedProperty);
    }

    @Override
    public List<PropertyResponse> findAll() {
        return propertyRepository.findAll()
                .stream()
                .map(PropertyMapper::toResponseDTO)
                .toList();
    }

    @Override
    public PropertyResponse findById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property with id " + id + " not found"));
        return PropertyMapper.toResponseDTO(property);
    }

    @Override
    public PropertyResponse update(Long id, PropertyRequest propertyRequest){
        Property propertyExisting = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property with id " + id + " not found"));

        propertyExisting.setName(propertyRequest.getName());
        propertyExisting.setAddress(propertyRequest.getAddress());
        propertyExisting.setCity(propertyRequest.getCity());
        propertyExisting.setRooms(propertyRequest.getRooms());
        propertyExisting.setPricePerMonth(propertyRequest.getPricePerMonth());
        propertyExisting.setActive(propertyRequest.getActive());

        Property updatedProperty = propertyRepository.save(propertyExisting);

        return  PropertyMapper.toResponseDTO(updatedProperty);
    }

    @Override
    public void deleteById(Long id) {
        propertyRepository.deleteById(id);
    }
}
