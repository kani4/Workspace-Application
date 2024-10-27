# Workspace Management Application

This is a simple Spring Boot application for managing workspaces.

## Features

- Create new workspaces
- Retrieve workspace details
- Update existing workspaces
- Delete workspaces

## Getting Started

### Prerequisites

- Java 11 or later
- Maven 3.6 or later

The application will start and be available at `http://localhost:8080`.

## API Endpoints

- Create a workspace: `POST /workspaces`
- Get all workspaces: `GET /workspaces`
- Get a specific workspace: `GET /workspaces/{workspaceId}`
- Update a workspace: `PUT /workspaces/{workspaceId}`
- Delete a workspace: `DELETE /workspaces/{workspaceId}`