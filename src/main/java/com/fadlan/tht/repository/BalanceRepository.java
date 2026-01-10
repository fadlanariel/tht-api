package com.fadlan.tht.repository;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BalanceRepository {

    private final JdbcTemplate jdbcTemplate;

    public long findByUserId(Long userId) {
        String sql = "SELECT balance FROM balances WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, userId);
    }
}
