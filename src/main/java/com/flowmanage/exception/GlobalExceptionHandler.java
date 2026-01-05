package com.flowmanage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flowmanage.dto.response.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidationException(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                                .toList();

                ApiErrorResponse response = new ApiErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "VALIDATION_ERROR",
                                "Request validation failed",
                                request.getRequestURI(),
                                errors);

                return ResponseEntity.badRequest().body(response);
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ApiErrorResponse> handleBadRequest(
                        BadRequestException ex,
                        HttpServletRequest request) {

                ApiErrorResponse response = new ApiErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                "BAD_REQUEST",
                                ex.getMessage(),
                                request.getRequestURI(),
                                null);

                return ResponseEntity.badRequest().body(response);
        }

        // Optional: last safety net (JANGAN expose detail)
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleGenericException(
                        Exception ex,
                        HttpServletRequest request) {

                ApiErrorResponse response = new ApiErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "INTERNAL_SERVER_ERROR",
                                "Unexpected server error",
                                request.getRequestURI(),
                                null);

                return ResponseEntity.internalServerError().body(response);
        }
}
