package com.flowmanage.service;

import com.flowmanage.entity.Task;
import com.flowmanage.entity.TaskStatus;
import com.flowmanage.exception.ProjectNotFoundException;
import com.flowmanage.exception.TaskNotFoundException;
import com.flowmanage.repository.ProjectRepository;
import com.flowmanage.repository.TaskRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(
        TaskRepository taskRepository,
        ProjectRepository projectRepository
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }
    
    /*
     * ============================
     * INTERNAL AUTH GUARD
     * ============================
     */

    private void validateProjectOwnership(UUID projectId, UUID ownerId) {
        if (!projectRepository.existsByIdAndOwnerId(projectId, ownerId)) {
            throw new ProjectNotFoundException();
        }
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
        validateProjectOwnership(projectId, userId);

        Task task = new Task();
        task.setProjectId(projectId);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(TaskStatus.DONE);

        return taskRepository.save(task);
    }
    
    /*
     * ============================
     * READ
     * ============================
     */

    @Transactional(readOnly = true)
    public List<Task> getTasksByProject(
            UUID projectId,
            UUID userId) {
        validateProjectOwnership(projectId, userId);
        return taskRepository.findAllByProjectId(projectId);
    }

    @Transactional(readOnly = true)
    public Task getTaskById(
            UUID projectId,
            UUID taskId,
            UUID userId) {
        validateProjectOwnership(projectId, userId);

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
            Boolean completed) {
        validateProjectOwnership(projectId, userId);

        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(TaskNotFoundException::new);

        if (title != null) {
            task.setTitle(title);
        }
        if (description != null) {
            task.setDescription(description);
        }
        if (completed != null) {
            task.setStatus(TaskStatus.DONE);
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
        validateProjectOwnership(projectId, userId);

        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(TaskNotFoundException::new);

        taskRepository.delete(task);
    }
}
