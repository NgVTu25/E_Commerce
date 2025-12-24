package com.ecommerce.vn.controllers;

import com.ecommerce.vn.dtos.AuthResponse;
import com.ecommerce.vn.dtos.LoginRequest;
import com.ecommerce.vn.dtos.RegisterRequest;
import com.ecommerce.vn.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        // Remove "Bearer " prefix if present
        if (refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
