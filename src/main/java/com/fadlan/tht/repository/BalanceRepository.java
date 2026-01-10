package com.fadlan.tht.repository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BalanceRepository {

    private final JdbcTemplate jdbcTemplate;

    public Optional<Long> findByUserId(Long userId) {
        String sql = "SELECT balance FROM balances WHERE user_id = ?";
        List<Long> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getLong("balance"),
                userId
        );
        return result.stream().findFirst();
    }

    public void updateBalance(Long userId, Long amount) {
        String sql = "UPDATE balances SET balance = balance + ?, updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";
        jdbcTemplate.update(sql, amount, userId);
    }
}
