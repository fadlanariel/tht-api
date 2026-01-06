package com.flowmanage.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowmanage.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findAllByOwnerId(UUID ownerId);
    Optional<Project> findByIdAndOwnerId(UUID id, UUID ownerId);
}
