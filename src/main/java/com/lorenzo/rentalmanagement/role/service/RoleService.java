package com.lorenzo.rentalmanagement.role.service;

import com.lorenzo.rentalmanagement.role.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> findAll();
}
