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
    EmployeeService employeeService;

    @PostMapping("/{empId}/leave")
    public LeaveRequest apply(@PathVariable String empId, @RequestBody LeaveRequest req) {
        return employeeService.applyLeave(empId, req);
    }

    @GetMapping("/{empId}/leaves")
    public List<LeaveRequest> view(@PathVariable String empId) {
        return employeeService.viewLeaves(empId);
    }
}

