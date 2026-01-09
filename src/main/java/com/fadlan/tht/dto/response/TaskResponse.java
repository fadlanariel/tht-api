package com.fadlan.tht.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.fadlan.tht.entity.TaskStatus;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        Instant createdAt) {
}