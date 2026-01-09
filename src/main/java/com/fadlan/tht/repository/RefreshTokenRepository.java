package com.fadlan.tht.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fadlan.tht.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByTokenHash(String tokenHash);

    void deleteAllByUserId(UUID userId);

    Optional<RefreshToken> findByTokenHashAndRevokedFalse(String tokenHash);
}
