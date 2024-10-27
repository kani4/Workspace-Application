package com.springboot.workspace.repository;

import org.springframework.stereotype.Repository;
import com.springboot.workspace.model.Workspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WorkspaceRepository {
    private final Map<UUID, Workspace> workspaces = new ConcurrentHashMap<>();

    public List<Workspace> findAll() {
        return new ArrayList<>(workspaces.values());
    }

    public Workspace findById(UUID id) {
        return workspaces.get(id);
    }

    public Workspace save(Workspace workspace) {
        if (workspace.getId() == null) {
            workspace.setId(UUID.randomUUID());
        }
        workspaces.put(workspace.getId(), workspace);
        return workspace;
    }

    public void deleteById(UUID id) {
        workspaces.remove(id);
    }

    public boolean existsById(UUID id) {
        return workspaces.containsKey(id);
    }
}