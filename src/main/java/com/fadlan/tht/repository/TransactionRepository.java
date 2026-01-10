package com.fadlan.tht.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public void saveTransaction(Long userId, Long amount, String type) {
        String sql = """
                    INSERT INTO transactions(user_id, amount, type)
                    VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(sql, userId, amount, type);
    }
}
