package com.project_REST.service;

import com.project_REST.entity.LeaveRequest;
import com.project_REST.entity.LeaveStatus;
import com.project_REST.entity.User;
import com.project_REST.repository.LeaveRequestRepository;
import com.project_REST.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private LeaveRequestRepository leaveRepo;

    @Autowired
    private UserRepository userRepo;

    public LeaveRequest applyLeave(String empId, LeaveRequest request) {
        User emp = userRepo.findById(empId).orElseThrow();
        request.setEmployee(emp);
        request.setStatus(LeaveStatus.PENDING);
        return leaveRepo.save(request);
    }

    public List<LeaveRequest> viewLeaves(String empId) {
        return leaveRepo.findByEmployeeId(empId);
    }
}

