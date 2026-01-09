package com.fadlan.tht.service;

import com.fadlan.tht.dto.request.RegisterRequest;
import com.fadlan.tht.exception.ApiException;
import com.fadlan.tht.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already registered");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        Long userId = userRepository.insertUser(
                request.getEmail(),
                hashedPassword);

        userRepository.initBalance(userId);
    }
}
