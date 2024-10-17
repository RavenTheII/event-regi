package com.example.eventregistration.adminservice.repository;

import com.example.eventregistration.adminservice.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    // No additional methods needed here for basic CRUD
}
