package com.ecommerce.vn.services;

import com.ecommerce.vn.dtos.AuthResponse;
import com.ecommerce.vn.dtos.LoginRequest;
import com.ecommerce.vn.dtos.RegisterRequest;
import com.ecommerce.vn.models.entitis.Role;
import com.ecommerce.vn.models.entitis.User;
import com.ecommerce.vn.repositories.RoleRepository;
import com.ecommerce.vn.repositories.UserRepository;
import com.ecommerce.vn.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        // Create new user
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .fullName(request.getFullName())
                .enabled(true)
                .build();

        // Assign roles
        List<Role> roles = new ArrayList<>();
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                roles.add(role);
            }
        } else {
            // Default role
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role ROLE_USER not found"));
            roles.add(userRole);
        }
        user.setRoles(roles);

        // Save user
        User savedUser = userRepository.save(user);

        // Generate tokens
        String accessToken = jwtTokenProvider.generateToken(savedUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(savedUser);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .roles(savedUser.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        // Load user details
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate tokens
        String accessToken = jwtTokenProvider.generateToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();
    }

    public AuthResponse refreshToken(String refreshToken) {
        String username = jwtTokenProvider.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!jwtTokenProvider.isTokenValid(refreshToken, user)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newAccessToken = jwtTokenProvider.generateToken(user);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();
    }
}
