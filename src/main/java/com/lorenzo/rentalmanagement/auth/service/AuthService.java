package com.lorenzo.rentalmanagement.auth.service;

import com.lorenzo.rentalmanagement.auth.dto.LoginRequest;
import com.lorenzo.rentalmanagement.auth.dto.LoginResponse;
import com.lorenzo.rentalmanagement.security.JwtUtil;
import com.lorenzo.rentalmanagement.user.domain.entity.User;
import com.lorenzo.rentalmanagement.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailAndActiveTrue(loginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getRole().getName());

        return new LoginResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole().getName(),
                token
        );

        //TODO: non gestito il caso se sono presenti più utenti con la stessa email
        //TODO: fixare il comportamente che al premere velocemente e ripetutamente il save nel front si salvi piu volte la stessa entita
    }
}
