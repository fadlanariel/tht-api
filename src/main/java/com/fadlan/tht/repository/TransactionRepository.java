package com.fadlan.tht.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fadlan.tht.dto.TransactionDto;

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

    public void insertTopup(Long userId, String invoiceNumber, Long amount) {
        String sql = "INSERT INTO transactions(user_id, invoice_number, transaction_type, description, amount) " +
                     "VALUES (?, ?, 'TOPUP', 'Top Up balance', ?)";
        jdbcTemplate.update(sql, userId, invoiceNumber, amount);
    }

    public void insertPayment(Long userId, String invoiceNumber, String description, Long amount) {
        String sql = "INSERT INTO transactions(user_id, invoice_number, transaction_type, description, amount) " +
                "VALUES (?, ?, 'PAYMENT', ?, ?)";
        jdbcTemplate.update(sql, userId, invoiceNumber, description, amount);
    }

    public List<TransactionDto> findHistoryByUserId(Long userId, int offset, int limit) {
        String sql = "SELECT invoice_number, transaction_type, description, amount AS total_amount, created_at AS created_on " +
                     "FROM transactions WHERE user_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?";

        RowMapper<TransactionDto> mapper = (rs, rowNum) -> new TransactionDto(
                rs.getString("invoice_number"),
                rs.getString("transaction_type"),
                rs.getString("description"),
                rs.getLong("total_amount"),
                rs.getTimestamp("created_on").toLocalDateTime()
        );

        return jdbcTemplate.query(sql, mapper, userId, limit, offset);
    }
}
