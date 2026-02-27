package com.lorenzo.rentalmanagement.rental.repository;

import com.lorenzo.rentalmanagement.rental.domain.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByActiveTrue();

    List<Rental> findAllByProperty_Owner_IdAndActiveTrue(Long ownerId);

    List<Rental> findAllByTenant_IdAndActiveTrue(Long tenantId);
}