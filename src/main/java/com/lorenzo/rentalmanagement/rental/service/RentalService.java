package com.lorenzo.rentalmanagement.rental.service;

import com.lorenzo.rentalmanagement.rental.dto.request.RentalRequest;
import com.lorenzo.rentalmanagement.rental.dto.response.RentalResponse;
import java.util.List;

public interface RentalService {
    RentalResponse create(RentalRequest request);
    List<RentalResponse> findAll(Long userId, String role);
    RentalResponse findById(Long id);
    RentalResponse update(Long id, RentalRequest request);
    void deleteById(Long id);
}