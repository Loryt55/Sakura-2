package com.lorenzo.rentalmanagement.auth.service;

import com.lorenzo.rentalmanagement.auth.dto.LoginRequest;
import com.lorenzo.rentalmanagement.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);
}
