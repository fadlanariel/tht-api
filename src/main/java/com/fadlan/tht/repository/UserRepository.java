package com.fadlan.tht.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public Long insertUser(String email, String hashedPassword) {
        String sql = """
                    INSERT INTO users(email, password)
                    VALUES (?, ?)
                    RETURNING id
                """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                email,
                hashedPassword);
    }

    public void initBalance(Long userId) {
        String sql = "INSERT INTO balances(user_id, balance) VALUES (?, 0)";
        jdbcTemplate.update(sql, userId);
    }
}
