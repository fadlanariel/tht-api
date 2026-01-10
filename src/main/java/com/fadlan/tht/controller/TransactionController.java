package com.fadlan.tht.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadlan.tht.dto.request.TransactionRequest;
import com.fadlan.tht.dto.response.TransactionResponse;
import com.fadlan.tht.exception.InsufficientBalanceException;
import com.fadlan.tht.security.AuthenticatedUser;
import com.fadlan.tht.security.SecurityUtils;
import com.fadlan.tht.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTransaction(@RequestBody TransactionRequest request) {
        AuthenticatedUser currentUser = SecurityUtils.getCurrentUser();

        try {
            TransactionResponse transaction = transactionService.processTransaction(currentUser.getId(),
                    request.serviceCode());

            return ResponseEntity.ok(Map.of(
                    "status", 0,
                    "message", "Transaksi berhasil",
                    "data", transaction));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 102,
                    "message", e.getMessage(),
                    "data", null));
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 103,
                    "message", e.getMessage(),
                    "data", null));
        }
    }
}
