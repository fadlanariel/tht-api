package com.fadlan.tht.controller;

import com.fadlan.tht.dto.request.TopUpRequest;
import com.fadlan.tht.security.AuthenticatedUser;
import com.fadlan.tht.security.SecurityUtils;
import com.fadlan.tht.service.BalanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "Module Transaction")
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/balance")
    @Operation(summary = "Get Balance", description = "API Balance Private (memerlukan Token)")
    public ResponseEntity<Map<String, Object>> getBalance() {
        AuthenticatedUser currentUser = SecurityUtils.getCurrentUser();

        long balance = balanceService.getBalanceByUserId(currentUser.getId());

        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Get Balance Berhasil",
                "data", Map.of("balance", balance)
        ));
    }

    @PostMapping("/topup")
    @Operation(summary = "Top Up Balance", description = "API Topup Private (memerlukan Token)")
    public ResponseEntity<Map<String, Object>> topUp(@RequestBody TopUpRequest request) {
        AuthenticatedUser currentUser = SecurityUtils.getCurrentUser();

        Long amount = request.amount();

        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 102,
                    "message", "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0",
                    "data", null
            ));
        }

        Long newBalance = balanceService.topUpBalance(currentUser.getId(), amount);

        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Top Up Balance berhasil",
                "data", Map.of("balance", newBalance)
        ));
    }
}
