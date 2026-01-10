package com.fadlan.tht.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApi(ApiException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(500)
                .body(Map.of("message", "Internal server error"));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientBalance(
            InsufficientBalanceException e) {

        return ResponseEntity.badRequest().body(Map.of(
                "status", 103,
                "message", e.getMessage(),
                "data", null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException e) {

        return ResponseEntity.badRequest().body(Map.of(
                "status", 102,
                "message", e.getMessage(),
                "data", null));
    }
}
