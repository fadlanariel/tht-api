package com.fadlan.tht.service;

import com.fadlan.tht.repository.BalanceRepository;
import com.fadlan.tht.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final TransactionRepository transactionRepository;

    public Long getBalanceByUserId(Long userId) {
        return balanceRepository.findByUserId(userId)
                .orElse(0L);
    }

    @Transactional
    public Long topUpBalance(Long userId, Long amount) {
        balanceRepository.updateBalance(userId, amount);

        String invoiceNumber = "INV" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "-" + userId;

        transactionRepository.insertTopup(userId, invoiceNumber, amount);

        return getBalanceByUserId(userId);
    }

}