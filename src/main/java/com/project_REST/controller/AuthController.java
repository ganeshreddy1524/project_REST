package com.project_REST.controller;

import com.project_REST.dto.LoginRequest;
import com.project_REST.dto.LoginResponse;
import com.project_REST.entity.User;
import com.project_REST.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) { // 👈 Defined here
        try {
            // 1. Use the variable to authenticate
            User user = authService.authenticate(loginRequest.getId(), loginRequest.getPassword());

            // 2. Create the filtered response (Id and Name only)
            LoginResponse response = new LoginResponse();
            response.setId(user.getId());
            response.setName(user.getName());
            response.setRole(user.getRole().toString());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
