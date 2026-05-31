package com.ecommerce.vn.controllers;

import com.ecommerce.vn.config.CacheConfig;
import com.ecommerce.vn.config.CorsConfig;
import com.ecommerce.vn.dtos.AuthResponse;
import com.ecommerce.vn.dtos.LoginRequest;
import com.ecommerce.vn.dtos.RegisterRequest;
import com.ecommerce.vn.security.JwtAuthenticationFilter;
import com.ecommerce.vn.security.JwtTokenProvider;
import com.ecommerce.vn.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({CacheConfig.class, CorsConfig.class})
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    void register_returnsAuthResponse() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .username("newuser")
                .password("secret12")
                .email("new@example.com")
                .build();

        AuthResponse response = AuthResponse.builder()
                .accessToken("access-token")
                .refreshToken("refresh-token")
                .username("newuser")
                .email("new@example.com")
                .roles(List.of("ROLE_USER"))
                .build();

        when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.username").value("newuser"));

        verify(authService).register(any(RegisterRequest.class));
    }

    @Test
    void register_withInvalidEmail_returnsBadRequest() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .username("u")
                .password("short")
                .email("not-an-email")
                .build();

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_returnsAuthResponse() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .username("admin")
                .password("admin123")
                .build();

        AuthResponse response = AuthResponse.builder()
                .accessToken("access-token")
                .refreshToken("refresh-token")
                .username("admin")
                .roles(List.of("ROLE_ADMIN"))
                .build();

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"));

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    void refreshToken_stripsBearerPrefix() throws Exception {
        AuthResponse response = AuthResponse.builder()
                .accessToken("new-access")
                .refreshToken("refresh-token")
                .username("admin")
                .build();

        when(authService.refreshToken("refresh-token")).thenReturn(response);

        mockMvc.perform(post("/api/auth/refresh")
                        .header("Authorization", "Bearer refresh-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("new-access"));

        verify(authService).refreshToken(eq("refresh-token"));
    }
}
