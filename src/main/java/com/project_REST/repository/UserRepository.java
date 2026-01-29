package com.project_REST.repository;

import com.project_REST.entity.Role;
import com.project_REST.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByRole(Role role);
    List<User> findByManagerId(String managerId);
}

