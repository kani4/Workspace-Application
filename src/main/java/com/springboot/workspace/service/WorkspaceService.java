package com.springboot.workspace.service;

import com.springboot.workspace.model.Workspace;
import com.springboot.workspace.model.WorkspaceType;
import com.springboot.workspace.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    public List<Workspace> getAllWorkspaces(String status, WorkspaceType type, LocalDateTime createdAfter, LocalDateTime createdBefore) {
        return workspaceRepository.findAll().stream()
                .filter(workspace -> status == null || (workspace.getStatus() != null && workspace.getStatus().equalsIgnoreCase(status)))
                .filter(workspace -> type == null || (workspace.getType() != null && workspace.getType().name().equalsIgnoreCase(String.valueOf(type))))
                .filter(workspace -> createdAfter == null || (workspace.getCreatedAt() != null && workspace.getCreatedAt().isAfter(createdAfter)))
                .filter(workspace -> createdBefore == null || (workspace.getCreatedAt() != null && workspace.getCreatedAt().isBefore(createdBefore)))
                .collect(Collectors.toList());
    }

    public Workspace createWorkspace(Workspace workspace) {
        workspace.setCreatedAt(LocalDateTime.now());
        workspace.setUpdatedAt(LocalDateTime.now());
        return workspaceRepository.save(workspace);
    }

    public Workspace getWorkspace(UUID id) {
        return workspaceRepository.findById(id);
    }

    public Workspace updateWorkspace(UUID id, Workspace updatedWorkspace) {
        Workspace existingWorkspace = workspaceRepository.findById(id);
        if (existingWorkspace == null) {
            return null;
        }
        updatedWorkspace.setId(id);
        updatedWorkspace.setCreatedAt(existingWorkspace.getCreatedAt());
        updatedWorkspace.setUpdatedAt(LocalDateTime.now());
        return workspaceRepository.save(updatedWorkspace);
    }

    public boolean deleteWorkspace(UUID id) {
        if (workspaceRepository.existsById(id)) {
            workspaceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}