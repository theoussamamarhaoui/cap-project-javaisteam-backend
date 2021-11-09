package com.cap.backendcapproject.repository;

import com.cap.backendcapproject.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<AppUser, Long> {
    public AppUser findByEmail(String email);
    public List<AppUser> findAllByEmail(String email);
    public AppUser findByUsername(String username);
}
