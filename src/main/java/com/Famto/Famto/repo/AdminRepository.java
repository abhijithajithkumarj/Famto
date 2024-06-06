package com.Famto.Famto.repo;

import com.Famto.Famto.entity.Admin;
import com.Famto.Famto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByUsername(String name);
}
