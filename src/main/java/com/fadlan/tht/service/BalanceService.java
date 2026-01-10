package com.fadlan.tht.service;

import com.fadlan.tht.dto.UserDto;
import com.fadlan.tht.dto.response.BalanceResponse;
import com.fadlan.tht.exception.ApiException;
import com.fadlan.tht.repository.BalanceRepository;
import com.fadlan.tht.repository.UserRepository;
import com.fadlan.tht.security.AuthenticatedUser;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final UserRepository userRepository;

    public long getBalanceByEmail(String email) {
        Long userId = userRepository.findByEmail(email)
                .map(UserDto::getId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return balanceRepository.findByUserId(userId);
    }
}