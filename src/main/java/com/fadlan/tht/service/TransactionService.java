package com.fadlan.tht.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fadlan.tht.dto.ServiceDto;
import com.fadlan.tht.dto.TransactionDto;
import com.fadlan.tht.dto.response.TransactionResponse;
import com.fadlan.tht.exception.InsufficientBalanceException;
import com.fadlan.tht.repository.BalanceRepository;
import com.fadlan.tht.repository.ServiceRepository;
import com.fadlan.tht.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final BalanceRepository balanceRepository;
    private final TransactionRepository transactionRepository;
    private final ServiceRepository serviceRepository; // to fetch service info

    @Transactional
    public TransactionResponse processTransaction(Long userId, String serviceCode) {
        ServiceDto service = serviceRepository.findByCode(serviceCode)
                .orElseThrow(() -> new IllegalArgumentException("Service atau Layanan tidak ditemukan"));

        Long currentBalance = balanceRepository.findByUserId(userId)
                .orElse(0L);

        if (currentBalance < service.getServiceTariff()) {
            throw new InsufficientBalanceException();
        }

        balanceRepository.updateBalance(userId, -service.getServiceTariff());

        String invoiceNumber = "INV" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "-"
                + userId;

        transactionRepository.insertPayment(
                userId,
                invoiceNumber,
                service.getServiceName(), // ini akan masuk ke description
                service.getServiceTariff());

        TransactionResponse response = new TransactionResponse();
        response.setInvoiceNumber(invoiceNumber);
        response.setServiceCode(service.getServiceCode());
        response.setServiceName(service.getServiceName());
        response.setTransactionType("PAYMENT");
        response.setTotalAmount(service.getServiceTariff());
        response.setCreatedOn(LocalDateTime.now());

        return response;
    }

    public List<TransactionDto> getTransactionHistory(Long userId, int offset, int limit) {
        return transactionRepository.findHistoryByUserId(userId, offset, limit);
    }
}
