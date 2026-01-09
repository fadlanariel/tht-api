package com.fadlan.tht.util;

import com.fadlan.tht.dto.response.TaskResponse;
import com.fadlan.tht.entity.Task;

public class TaskMapper {

    private TaskMapper() {
    }

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt());
    }
}