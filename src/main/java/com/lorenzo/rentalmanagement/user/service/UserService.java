package com.lorenzo.rentalmanagement.user.service;

import com.lorenzo.rentalmanagement.user.dto.request.UserRequest;
import com.lorenzo.rentalmanagement.user.dto.request.UserUpdateRequest;
import com.lorenzo.rentalmanagement.user.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse updateUser(Long id, UserUpdateRequest request);

    void deleteById(Long id);

    List<UserResponse> findAllByRole(String roleName);
}
