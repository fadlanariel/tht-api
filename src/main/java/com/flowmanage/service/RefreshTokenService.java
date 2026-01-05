package com.flowmanage.service;

import com.flowmanage.entity.RefreshToken;
import com.flowmanage.repository.RefreshTokenRepository;
import com.flowmanage.security.TokenHashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
 
    private static final long REFRESH_TOKEN_TTL_SECONDS = 7 * 24 * 60 * 60;

    private final RefreshTokenRepository refreshTokenRepository;

    public String createRefreshToken(UUID userId) {
        String rawToken = UUID.randomUUID().toString() + UUID.randomUUID();

        RefreshToken refreshToken = RefreshToken.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .tokenHash(TokenHashUtil.hash(rawToken))
                .expiresAt(Instant.now().plusSeconds(REFRESH_TOKEN_TTL_SECONDS))
                .revoked(false)
                .createdAt(Instant.now())
                .build();

        refreshTokenRepository.save(refreshToken);

        return rawToken;
    }
}