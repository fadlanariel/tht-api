package com.fadlan.tht.repository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fadlan.tht.dto.UserDto;

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

    public Optional<UserDto> findByEmail(String email) {
        String sql = """
            SELECT id, email, password
            FROM users
            WHERE email = ?
        """;

        List<UserDto> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new UserDto(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password")
                ),
                email
        );

        return result.stream().findFirst();
    }

    public void save(String email, String password) {
        String sql = """
                    INSERT INTO users (email, password)
                    VALUES (?, ?)
                """;
        jdbcTemplate.update(sql, email, password);
    }
}
