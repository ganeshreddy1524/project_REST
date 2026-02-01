package com.project_REST.dto;

import lombok.Data;

@Data // This generates getters and setters automatically
public class LoginRequest {
    private String id;
    private String password;
}
