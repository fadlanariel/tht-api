package com.fadlan.tht.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fadlan.tht.dto.request.LoginRequest;
import com.fadlan.tht.dto.request.RegisterRequest;
import com.fadlan.tht.dto.response.LoginResponse;
import com.fadlan.tht.entity.User;
import com.fadlan.tht.exception.BadRequestException;
import com.fadlan.tht.repository.UserRepository;
import com.fadlan.tht.security.JwtService;

import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                    new BadRequestException("Invalid email or password")
                );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        String accessToken = jwtService.generateToken(user.getId(), user.getEmail());

        String refreshToken = refreshTokenService.createRefreshToken(user.getId(), user.getEmail());

        return new LoginResponse(accessToken, refreshToken);
    }
}
