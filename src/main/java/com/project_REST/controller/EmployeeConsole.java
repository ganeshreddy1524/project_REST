package com.project_REST.controller;

import com.project_REST.entity.LeaveRequest;
import com.project_REST.service.EmployeeService;

import java.time.LocalDate;
import java.util.Scanner;

public class EmployeeConsole {

    private final EmployeeService employeeService;

    public EmployeeConsole(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void start(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- EMPLOYEE PORTAL ---");
            System.out.println("1. Apply for Leave");
            System.out.println("2. View My Leaves");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter your Emp ID: ");
                String id = scanner.nextLine();
                LeaveRequest req = new LeaveRequest();
                System.out.print("Reason for leave: ");
                req.setReason(scanner.nextLine());

                System.out.print("Enter Start Date (YYYY-MM-DD): ");
                String dateString = scanner.nextLine();

                try {
                    // Parsing the String to LocalDate
                    LocalDate d = LocalDate.parse(dateString);
                    req.setStartDate(d);
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("❌ Invalid Date format! Please use YYYY-MM-DD.");
                    // Handle the error (e.g., return or ask again)
                }
                System.out.print("Enter End Date (YYYY-MM-DD): ");
                String endDateString = scanner.nextLine();
                try {
                    // Parsing the String to LocalDate
                    LocalDate d = LocalDate.parse(endDateString);
                    req.setEndDate(d);
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("❌ Invalid Date format! Please use YYYY-MM-DD.");
                    // Handle the error (e.g., return or ask again)
                }

                employeeService.applyLeave(id, req);
                System.out.println("Application submitted.");
            } else if (choice == 2) {
                System.out.print("Enter your Emp ID: ");
                String id = scanner.nextLine();
                employeeService.viewLeaves(id).forEach(l ->
                        System.out.println("Reason: " + l.getReason() + " | Status: " + l.getStatus()));
            } else if (choice == 0) {
                back = true;
            }
        }
    }
}