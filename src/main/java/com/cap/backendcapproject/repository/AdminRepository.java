package com.cap.backendcapproject.repository;

import com.cap.backendcapproject.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Admin findByEmail(String email);
    public Admin findByUsername(String username);
}
