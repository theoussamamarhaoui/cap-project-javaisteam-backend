package com.cap.backendcapproject.repository;

import com.cap.backendcapproject.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<AppRole, Long> {
    public AppRole findByRole(String role);
}
