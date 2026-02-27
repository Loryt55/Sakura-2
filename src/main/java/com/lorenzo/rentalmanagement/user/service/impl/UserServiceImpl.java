package com.lorenzo.rentalmanagement.user.service.impl;

import com.lorenzo.rentalmanagement.property.exception.ResourceNotFoundException;
import com.lorenzo.rentalmanagement.role.domain.entity.Role;
import com.lorenzo.rentalmanagement.user.domain.entity.User;
import com.lorenzo.rentalmanagement.user.dto.request.UserRequest;
import com.lorenzo.rentalmanagement.user.dto.request.UserUpdateRequest;
import com.lorenzo.rentalmanagement.user.dto.response.UserResponse;
import com.lorenzo.rentalmanagement.user.mapper.UserMapper;
import com.lorenzo.rentalmanagement.role.repository.RoleRepository;
import com.lorenzo.rentalmanagement.user.repository.UserRepository;
import com.lorenzo.rentalmanagement.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAllByActiveTrue()
                .stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponse findById(Long id) {
        return UserMapper.toResponseDTO(findUserOrThrow(id));
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        Role role = findRoleOrThrow(request.getRoleId());

        User user = UserMapper.toEntity(request, role);
        user.setActive(true);
        user.setCreatedAt(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
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

    @Override
    public void deleteById(Long id) {
        User user = findUserOrThrow(id);
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> findAllByRole(String roleName) {
        return userRepository.findAllByRole_NameAndActiveTrue(roleName)
                .stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    private Role findRoleOrThrow(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id " + roleId + " not found"));
    }
}