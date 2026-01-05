package com.flowmanage.controller;

import com.flowmanage.dto.request.LoginRequest;
import com.flowmanage.dto.request.RegisterRequest;
import com.flowmanage.dto.response.ApiResponse;
import com.flowmanage.dto.response.LoginResponse;
import com.flowmanage.service.AuthService;
import com.flowmanage.service.RefreshTokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
