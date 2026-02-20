package com.lorenzo.rentalmanagement.user.mapper;

import com.lorenzo.rentalmanagement.user.domain.entity.Role;
import com.lorenzo.rentalmanagement.user.domain.entity.User;
import com.lorenzo.rentalmanagement.user.dto.request.UserRequest;
import com.lorenzo.rentalmanagement.user.dto.response.UserResponse;

public class UserMapper {

    private UserMapper() {}

    public static User toEntity(UserRequest request, Role role) {
        return new User.Builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(role)
                .build();
    }

    public static UserResponse toResponseDTO(User user) {
        return new UserResponse.Builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .roleName(user.getRole().getName())
                .createdAt(user.getCreatedAt())
                .build();
    }
}