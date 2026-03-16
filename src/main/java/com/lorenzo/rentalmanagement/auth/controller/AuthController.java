package com.lorenzo.rentalmanagement.auth.controller;

import com.lorenzo.rentalmanagement.auth.dto.LoginRequest;
import com.lorenzo.rentalmanagement.auth.dto.LoginResponse;
import com.lorenzo.rentalmanagement.auth.service.impl.AuthServiceImpl;
import com.lorenzo.rentalmanagement.security.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceImpl authService;
    private final RateLimiterService rateLimiterService;

    public AuthController(AuthServiceImpl authService, RateLimiterService rateLimiterService) {
        this.authService = authService;
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request) {

        String ipAddress = getClientIp(request);

        if (!rateLimiterService.tryConsume(ipAddress)) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Too many login attempts. Please try again later."
            );
        }

        return ResponseEntity.ok(authService.login(loginRequest));
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
