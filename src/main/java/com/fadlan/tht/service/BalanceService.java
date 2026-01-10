package com.fadlan.tht.service;

import com.fadlan.tht.dto.UserDto;
import com.fadlan.tht.repository.BalanceRepository;
import com.fadlan.tht.repository.TransactionRepository;
import com.fadlan.tht.repository.UserRepository;

import lombok.RequiredArgsConstructor;

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
        // update balance
        balanceRepository.updateBalance(userId, amount);

        // simpan transaksi
        transactionRepository.saveTransaction(userId, amount, "TOPUP");

        // return new balance
        return getBalanceByUserId(userId);
    }
}