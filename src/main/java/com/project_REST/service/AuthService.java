package com.project_REST.service;

import com.project_REST.entity.User;
import com.project_REST.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private UserRepository userRepo;
    public AuthService(UserRepository userRepo){
        this.userRepo=userRepo;
    }
    public User authenticate(String id, String password) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password!");
        }
        return user;
    }

}
