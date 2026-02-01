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

    
    private LeaveRequestRepository leaveRepo;


    private UserRepository userRepo;
    @Autowired
    EmployeeService(LeaveRequestRepository leaveRepo,UserRepository userRepo){
        this.leaveRepo=leaveRepo;
        this.userRepo=userRepo;
    }


    public LeaveRequest applyLeave(String empId, LeaveRequest request) {
        User emp = userRepo.findById(empId).orElseThrow();
        request.setEmployee(emp);
        request.setStatus(LeaveStatus.PENDING);
        request.setStartDate(request.getStartDate());
        request.setEndDate(request.getEndDate());
        return leaveRepo.save(request);
    }

    public List<LeaveRequest> viewLeaves(String employeeId) {
        // 1. Fetch the data and store it in a variable named 'leaves'
        List<LeaveRequest> leaves = leaveRepo.findByEmployee_Id(employeeId);

        // 2. Perform the check BEFORE returning
        if (leaves.isEmpty()) {
            throw new RuntimeException("No leave requests found for you.");
        }

        // 3. Finally, return the list
        return leaves;
    }
}

