package com.cap.backendcapproject.repository;

import com.cap.backendcapproject.entities.Admin;
import com.cap.backendcapproject.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    public Candidate findByEmail(String email);
    public Candidate findByUsername(String username);
}
