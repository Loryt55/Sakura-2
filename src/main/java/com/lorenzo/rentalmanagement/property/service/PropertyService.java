package com.lorenzo.rentalmanagement.property.service;

import com.lorenzo.rentalmanagement.property.model.Property;
import com.lorenzo.rentalmanagement.property.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository repository;

    public PropertyService(PropertyRepository repository) {
        this.repository = repository;
    }

    public List<Property> getAllProperties() {
        return repository.findAll();
    }

    public Optional<Property> getPropertyById(Long id) {
        return repository.findById(id);
    }

    public Property createProperty(Property property) {
        return repository.save(property);
    }

    public Optional<Property> updateProperty(Long id, Property property) {
        return repository.findById(id).map(p -> {
            p.setName(property.getName());
            p.setAddress(property.getAddress());
            p.setPricePerMonth(property.getPricePerMonth());
            return repository.save(p);
        });
    }

    public void deleteProperty(Long id) {
        repository.deleteById(id);
    }
}