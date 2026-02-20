package com.lorenzo.rentalmanagement.user.repository;

import com.lorenzo.rentalmanagement.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository fornisce già tutti i metodi CRUD
}