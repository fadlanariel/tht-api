package com.fadlan.tht.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.fadlan.tht.dto.response.LoginResponse;
import com.fadlan.tht.entity.RefreshToken;
import com.fadlan.tht.repository.RefreshTokenRepository;
import com.fadlan.tht.security.JwtService;
import com.fadlan.tht.security.TokenHashUtil;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
 
    private static final long REFRESH_TOKEN_TTL_SECONDS = 7 * 24 * 60 * 60;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public String createRefreshToken(UUID userId, String email) {

        String rawToken = UUID.randomUUID().toString() + UUID.randomUUID();

        RefreshToken token = RefreshToken.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .email(email)
                .tokenHash(TokenHashUtil.hash(rawToken))
                .expiresAt(Instant.now().plusSeconds(REFRESH_TOKEN_TTL_SECONDS))
                .revoked(false)
                .createdAt(Instant.now())
                .build();

        refreshTokenRepository.save(token);

        return rawToken;
    }

    public LoginResponse refresh(String rawRefreshToken) {

        String hash = TokenHashUtil.hash(rawRefreshToken);

        RefreshToken token = refreshTokenRepository
                .findByTokenHashAndRevokedFalse(hash)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        token.setRevoked(true);
        refreshTokenRepository.save(token);

        String newAccessToken = jwtService.generateToken(token.getUserId(), token.getEmail());

        String newRefreshToken = createRefreshToken(token.getUserId(), token.getEmail());

        return new LoginResponse(newAccessToken, newRefreshToken);
    }
}