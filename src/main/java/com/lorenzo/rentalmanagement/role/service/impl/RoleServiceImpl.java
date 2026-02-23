package com.lorenzo.rentalmanagement.role.service.impl;

import com.lorenzo.rentalmanagement.role.dto.response.RoleResponse;
import com.lorenzo.rentalmanagement.role.repository.RoleRepository;
import com.lorenzo.rentalmanagement.role.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleResponse> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(role -> new RoleResponse(role.getId(), role.getName()))
                .toList();
    }
}
