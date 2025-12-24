package com.ecommerce.vn.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    @lombok.Builder.Default
    private String tokenType = "Bearer";
    private String username;
    private String email;
    private List<String> roles;
}
