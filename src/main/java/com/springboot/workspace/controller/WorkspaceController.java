package com.springboot.workspace.controller;

import com.springboot.workspace.service.WorkspaceService;
import com.springboot.workspace.model.Workspace;
import com.springboot.workspace.model.WorkspaceType;
import com.springboot.workspace.model.WorkspaceInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public ResponseEntity<List<Workspace>> listWorkspaces(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) WorkspaceType type,
            @RequestParam(required = false) LocalDateTime createdAfter,
            @RequestParam(required = false) LocalDateTime createdBefore) {
        List<Workspace> workspaces = workspaceService.getAllWorkspaces(status, type, createdAfter, createdBefore);
        return ResponseEntity.ok(workspaces);
    }

    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(@RequestBody WorkspaceInput input) {
        Workspace workspace = new Workspace();
        // Set properties from input
        workspace.setId(UUID.randomUUID());
        workspace.setName(input.getName());
        workspace.setDescription(input.getDescription());
        workspace.setType(input.getType());
        workspace.setStatus(input.getStatus());
        workspace.setAddress(input.getAddress());
        workspace.setCreatedAt(LocalDateTime.now());
        workspace.setUpdatedAt(LocalDateTime.now());
        // Save workspace
        Workspace savedWorkspace = workspaceService.createWorkspace(workspace);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkspace);
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<Workspace> getWorkspace(@PathVariable String workspaceId) {
        try {
            UUID id = UUID.fromString(workspaceId);
            Workspace workspace = workspaceService.getWorkspace(id);

            if (workspace != null) {
                return ResponseEntity.ok(workspace);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            // If the workspaceId is not a valid UUID
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<Workspace> updateWorkspace(@PathVariable String workspaceId, @RequestBody Workspace update) {
        try {
            UUID id = UUID.fromString(workspaceId);

            // First, check if the workspace exists
            Workspace existingWorkspace = workspaceService.getWorkspace(id);
            if (existingWorkspace == null) {
                return ResponseEntity.notFound().build();
            }

            // Update the workspace
            Workspace updatedWorkspace = workspaceService.updateWorkspace(id, update);

            // Return the updated workspace
            return ResponseEntity.ok(updatedWorkspace);
        } catch (IllegalArgumentException e) {
            // If the workspaceId is not a valid UUID
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable String workspaceId) {
        try {
            UUID id = UUID.fromString(workspaceId);
            boolean deleted = workspaceService.deleteWorkspace(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }}

