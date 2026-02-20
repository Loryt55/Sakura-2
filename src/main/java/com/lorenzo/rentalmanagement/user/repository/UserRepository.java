package com.lorenzo.rentalmanagement.user.repository;

import com.lorenzo.rentalmanagement.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByActiveTrue();
}