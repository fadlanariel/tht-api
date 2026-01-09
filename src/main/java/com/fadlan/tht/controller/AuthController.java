package com.fadlan.tht.controller;

import com.fadlan.tht.dto.request.LoginRequest;
import com.fadlan.tht.dto.request.RegisterRequest;
import com.fadlan.tht.dto.response.LoginResponse;
import com.fadlan.tht.dto.response.RegisterResponse;
import com.fadlan.tht.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Membership", description = "Module untuk registrasi, login, dan profile user")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(
                new RegisterResponse("Registration successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
