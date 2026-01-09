package com.fadlan.tht.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fadlan.tht.entity.Task;
import com.fadlan.tht.entity.TaskStatus;
import com.fadlan.tht.exception.TaskNotFoundException;
import com.fadlan.tht.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final ProjectService projectService;

    public TaskService(
            TaskRepository taskRepository,
            ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
    }

    /* ============================
       CREATE
       ============================ */

    @Transactional
    public Task createTask(
        UUID projectId,
        UUID userId,
        String title,
        String description
    ) {
        projectService.validateOwnership(projectId, userId);

        Task task = new Task();
        task.setProjectId(projectId);
        task.setTitle(title);
        task.setDescription(description);

        return taskRepository.save(task);
    }
    
    /*
     * ============================
     * READ
     * ============================
     */

    @Transactional(readOnly = true)
    public Page<Task> getTasksByProject(UUID projectId, UUID userId, Pageable pageable) {
        projectService.validateOwnership(projectId, userId);

        return taskRepository.findAllByProjectId(projectId, pageable);
    }

    @Transactional(readOnly = true)
    public Task getTaskById(
            UUID projectId,
            UUID taskId,
            UUID userId) {
        projectService.validateOwnership(projectId, userId);

        return taskRepository.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(TaskNotFoundException::new);
    }

    /*
     * ============================
     * UPDATE
     * ============================
     */

    @Transactional
    public Task updateTask(
            UUID projectId,
            UUID taskId,
            UUID userId,
            String title,
            String description,
            TaskStatus status) {
        projectService.validateOwnership(projectId, userId);

        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(TaskNotFoundException::new);

        if (title != null) {
            task.setTitle(title);
        }
        if (description != null) {
            task.setDescription(description);
        }
        if (status != null) {
            task.setStatus(status);
        }

        return task; // Hibernate dirty checking
    }

    /*
     * ============================
     * DELETE
     * ============================
     */

    @Transactional
    public void deleteTask(
            UUID projectId,
            UUID taskId,
            UUID userId) {
        projectService.validateOwnership(projectId, userId);

        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(TaskNotFoundException::new);

        taskRepository.delete(task);
    }
}
