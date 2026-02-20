package com.lorenzo.rentalmanagement.user.service;

import com.lorenzo.rentalmanagement.user.domain.entity.User;
import com.lorenzo.rentalmanagement.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public Optional<User> updateUser(Long id, User user) {
        return repository.findById(id).map(p -> {
            p.setFirstName(user.getFirstName());
            p.setLastName(user.getLastName());
            p.setPhoneNumber(user.getPhoneNumber());
            p.setEmail(user.getEmail());
            p.setPassword(user.getPassword());
            p.setRole(user.getRole());
            p.setActive(user.getActive());
            return repository.save(p);
        });
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}