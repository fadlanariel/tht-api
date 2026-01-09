package com.fadlan.tht.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.fadlan.tht.dto.request.LoginRequest;
import com.fadlan.tht.dto.request.RegisterRequest;
import com.fadlan.tht.dto.response.ApiResponse;
import com.fadlan.tht.dto.response.LoginResponse;
import com.fadlan.tht.service.AuthService;
import com.fadlan.tht.service.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return new ApiResponse<>("User registered successfully");
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        return new ApiResponse<>(authService.login(request));
    }

    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refresh(
            @RequestHeader("Authorization") String header) {

        String token = header.replace("Bearer ", "");
        return new ApiResponse<>(refreshTokenService.refresh(token));
    }

}
