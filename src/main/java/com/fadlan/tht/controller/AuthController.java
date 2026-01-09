package com.fadlan.tht.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.fadlan.tht.dto.request.RegisterRequest;
import com.fadlan.tht.dto.response.ApiResponse;
import com.fadlan.tht.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return new ApiResponse<>("User registered successfully");
    }

}
