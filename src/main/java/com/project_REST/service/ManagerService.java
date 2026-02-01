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
public class ManagerService {


    private UserRepository userRepo;


    private LeaveRequestRepository leaveRepo;
    @Autowired
    ManagerService(UserRepository userRepo,LeaveRequestRepository leaveRepo){
        this.userRepo=userRepo;
        this.leaveRepo=leaveRepo;
    }

    public List<LeaveRequest> viewTeamLeaves(String managerId) {

        return leaveRepo.findByEmployee_Manager_Id(managerId);
    }

    public LeaveRequest approve(Long requestId) {
        LeaveRequest req = leaveRepo.findById(requestId).orElseThrow();
        req.setStatus(LeaveStatus.APPROVED);
        return leaveRepo.save(req);
    }

    public LeaveRequest reject(Long requestId) {
        LeaveRequest req = leaveRepo.findById(requestId).orElseThrow();
        req.setStatus(LeaveStatus.REJECTED);
        return leaveRepo.save(req);
    }
}

