package com.fadlan.tht.dto.request;

import com.fadlan.tht.entity.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(

        @Size(max = 100) String title,

        @Size(max = 500) String description,

        TaskStatus status) {
}