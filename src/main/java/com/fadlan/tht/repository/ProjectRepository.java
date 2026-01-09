package com.fadlan.tht.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fadlan.tht.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Page<Project> findAllByOwnerId(UUID ownerId, Pageable pageable);
    Optional<Project> findByIdAndOwnerId(UUID id, UUID ownerId);

    boolean existsByIdAndOwnerId(UUID id, UUID ownerId);
}
