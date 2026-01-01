package com.flowmanage.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> validationErrors;

    public ApiErrorResponse() {}

    public ApiErrorResponse(
            int status,
            String error,
            String message,
            String path,
            List<String> validationErrors
    ) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.validationErrors = validationErrors;
    }

    // getters & setters
    // (boleh pakai Lombok jika ada)
}
