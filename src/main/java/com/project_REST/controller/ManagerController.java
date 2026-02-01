package com.project_REST.controller;

import com.project_REST.entity.LeaveRequest;

import com.project_REST.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @GetMapping("/{mgrId}/viewleaves")
    public List<LeaveRequest> team(@PathVariable String mgrId) {
        return managerService.viewTeamLeaves(mgrId);
    }

    @PutMapping("/leave/{id}/approve")
    public LeaveRequest approve(@PathVariable Long id) {
        return managerService.approve(id);
    }

    @PutMapping("/leave/{id}/reject")
    public LeaveRequest reject(@PathVariable Long id) {
        return managerService.reject(id);
    }
}

