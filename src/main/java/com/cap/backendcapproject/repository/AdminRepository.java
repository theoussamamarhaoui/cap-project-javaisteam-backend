package com.cap.backendcapproject.repository;

import com.cap.backendcapproject.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
