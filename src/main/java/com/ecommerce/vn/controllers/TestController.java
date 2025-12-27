package com.ecommerce.vn.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/encode-password")
    public ResponseEntity<Map<String, String>> encodePassword(@RequestParam String password) {
        String encoded = passwordEncoder.encode(password);
        Map<String, String> response = new HashMap<>();
        response.put("plainPassword", password);
        response.put("encodedPassword", encoded);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-password")
    public ResponseEntity<Map<String, Object>> verifyPassword(
            @RequestParam String plainPassword,
            @RequestParam String encodedPassword) {
        boolean matches = passwordEncoder.matches(plainPassword, encodedPassword);
        Map<String, Object> response = new HashMap<>();
        response.put("plainPassword", plainPassword);
        response.put("encodedPassword", encodedPassword);
        response.put("matches", matches);
        return ResponseEntity.ok(response);
    }
}
