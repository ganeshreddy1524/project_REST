package com.project_REST.service;

import com.project_REST.dto.CreateUserRequest;
import com.project_REST.entity.User;
import com.project_REST.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {


    private UserRepository userRepo;
    @Autowired
    AdminService(UserRepository userRepo){
        this.userRepo=userRepo;
    }

    public User createEmployee(CreateUserRequest req) {
        User user = new User();
        user.setId(req.getId());
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setRole(req.getRole());
        user.setPassword(req.getPassword());
        user.setManager(user.getManager());

        if (req.getManagerId() != null) {
            User manager = userRepo.findById(req.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            user.setManager(manager);
        }

        return userRepo.save(user); // ✅ ONE argument
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}

