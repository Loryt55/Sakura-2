package com.lorenzo.rentalmanagement.property.repository;

import com.lorenzo.rentalmanagement.property.domain.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

}