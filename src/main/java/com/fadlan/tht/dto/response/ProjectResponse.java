package com.fadlan.tht.dto.response;

import java.util.UUID;

import com.fadlan.tht.entity.Project;

public record ProjectResponse(
    UUID id,
    String name,
    String description
) {
    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
            project.getId(),
            project.getName(),
            project.getDescription()
        );
    }
}
