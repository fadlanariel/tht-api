package com.fadlan.tht.service;

import com.fadlan.tht.dto.UserDto;
import com.fadlan.tht.dto.request.LoginRequest;
import com.fadlan.tht.dto.request.RegisterRequest;
import com.fadlan.tht.dto.response.LoginResponse;
import com.fadlan.tht.exception.ApiException;
import com.fadlan.tht.repository.UserRepository;
import com.fadlan.tht.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already registered");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        userRepository.save(request.getEmail(), hashedPassword);
    }

    public LoginResponse login(LoginRequest request) {
        UserDto user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ApiException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ApiException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
}
