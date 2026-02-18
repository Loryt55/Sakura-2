package com.lorenzo.rentalmanagement.property.service;

import com.lorenzo.rentalmanagement.property.dto.request.PropertyRequest;
import com.lorenzo.rentalmanagement.property.dto.response.PropertyResponse;

import java.util.List;

public interface PropertyService {

    PropertyResponse create(PropertyRequest propertyRequest);

    List<PropertyResponse> findAll();

    PropertyResponse findById(Long id);

    PropertyResponse update(Long id, PropertyRequest propertyRequest);

    void deleteById(Long id);
}