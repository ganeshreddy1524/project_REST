package com.project_REST.controller;

import com.project_REST.dto.CreateUserRequest;
import com.project_REST.entity.User;
import com.project_REST.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/employee")
    public User addEmployee(@RequestBody CreateUserRequest request) {
        return adminService.createEmployee(request);
    }

    @GetMapping("/users")
    public List<User> all() {
        return adminService.getAllUsers();
    }
}

