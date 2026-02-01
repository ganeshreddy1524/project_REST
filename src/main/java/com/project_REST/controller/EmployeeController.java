package com.project_REST.controller;

import com.project_REST.entity.LeaveRequest;
import com.project_REST.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Apply leave
    @PostMapping("/{empId}/leave")
    public LeaveRequest apply(
            @PathVariable String empId,
            @RequestBody LeaveRequest request) {

        return employeeService.applyLeave(empId, request);
    }

    // View employee leaves
    @GetMapping("/{empId}/leave")
    public List<LeaveRequest> view(@PathVariable String empId) {
        return employeeService.viewLeaves(empId);
    }
}
