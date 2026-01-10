package com.fadlan.tht.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fadlan.tht.dto.TransactionDto;
import com.fadlan.tht.dto.request.TransactionRequest;
import com.fadlan.tht.dto.response.ApiResponse;
import com.fadlan.tht.dto.response.TransactionHistoryResponse;
import com.fadlan.tht.dto.response.TransactionResponse;
import com.fadlan.tht.exception.InsufficientBalanceException;
import com.fadlan.tht.security.AuthenticatedUser;
import com.fadlan.tht.security.SecurityUtils;
import com.fadlan.tht.service.TransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Tag(name = "Module Transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionResponse>> createTransaction(
            @RequestBody TransactionRequest request) {

        AuthenticatedUser currentUser = SecurityUtils.getCurrentUser();

        TransactionResponse transaction = transactionService.processTransaction(currentUser.getId(),
                request.serviceCode());

        return ResponseEntity.ok(
                new ApiResponse<>(0, "Transaksi berhasil", transaction));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<TransactionHistoryResponse>> getTransactionHistory(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {

        AuthenticatedUser currentUser = SecurityUtils.getCurrentUser();
        long userId = currentUser.getId();

        List<TransactionDto> history =
                transactionService.getTransactionHistory(userId, offset, limit);

        TransactionHistoryResponse data = new TransactionHistoryResponse(
                offset,
                limit,
                history
        );

        return ResponseEntity.ok(
                new ApiResponse<>(0, "Get History Berhasil", data)
        );
    }

}
