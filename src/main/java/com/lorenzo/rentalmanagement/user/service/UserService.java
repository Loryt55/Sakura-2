package com.lorenzo.rentalmanagement.user.service;

import com.lorenzo.rentalmanagement.property.exception.ResourceNotFoundException;
import com.lorenzo.rentalmanagement.user.domain.entity.Role;
import com.lorenzo.rentalmanagement.user.domain.entity.User;
import com.lorenzo.rentalmanagement.user.dto.response.UserResponse;
import com.lorenzo.rentalmanagement.user.dto.request.UserRequest;
import com.lorenzo.rentalmanagement.user.dto.request.UserUpdateRequest;
import com.lorenzo.rentalmanagement.user.mapper.UserMapper;
import com.lorenzo.rentalmanagement.user.repository.RoleRepository;
import com.lorenzo.rentalmanagement.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAllByActiveTrue()
                .stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return UserMapper.toResponseDTO(findUserOrThrow(id));
    }

    public UserResponse createUser(UserRequest request) {
        Role role = findRoleOrThrow(request.getRoleId());

        User user = UserMapper.toEntity(request, role);
        user.setActive(true);
        user.setCreatedAt(LocalDate.now());

        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = findUserOrThrow(id);
        Role role = findRoleOrThrow(request.getRoleId());

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setRole(role);

        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    public void deleteById(Long id) {
        User user = findUserOrThrow(id);
        user.setActive(false);
        userRepository.save(user);
    }

    // --- metodi privati di supporto ---

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    private Role findRoleOrThrow(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + roleId + " not found"));
    }
}