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
    public ResponseEntity<Map<String, Object>> createTransaction(@RequestBody TransactionRequest request) {
        AuthenticatedUser currentUser = SecurityUtils.getCurrentUser();

        try {
            TransactionResponse transaction = transactionService.processTransaction(currentUser.getId(),
                    request.serviceCode());

            return ResponseEntity.ok(Map.of(
                    "status", 0,
                    "message", "Transaksi berhasil",
                    "data", transaction));
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 103,
                    "message", e.getMessage(),
                    "data", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 102,
                    "message", e.getMessage(),
                    "data", null));
        }

    }

    @GetMapping("/history")
    public ResponseEntity<Map<String, Object>> getTransactionHistory(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        AuthenticatedUser currentUser = SecurityUtils.getCurrentUser();
        long userId = currentUser.getId();

        List<TransactionDto> history = transactionService.getTransactionHistory(userId, limit, offset);

        Map<String, Object> data = new HashMap<>();
        data.put("offset", offset != null ? offset : 0);
        data.put("limit", limit != null ? limit : history.size());
        data.put("records", history);

        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Get History Berhasil",
                "data", data
        ));
    }
}
