package com.project_REST.repository;

import com.project_REST.entity.Role;
import com.project_REST.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByRole(Role role);
    List<User> findByManagerId(String managerId);
    Optional<User> findByEmail(String email);
}

