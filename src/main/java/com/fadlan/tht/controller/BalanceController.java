package com.fadlan.tht.controller;

import com.fadlan.tht.security.AuthenticatedUser;
import com.fadlan.tht.security.SecurityUtils;
import com.fadlan.tht.service.BalanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

        long balance = balanceService.getBalanceByEmail(currentUser.getEmail());

        return ResponseEntity.ok(Map.of(
                "status", 0,
                "message", "Get Balance Berhasil",
                "data", Map.of("balance", balance)
        ));
    }

}
