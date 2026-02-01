package com.project_REST.controller;

import com.project_REST.dto.CreateUserRequest;
import com.project_REST.entity.Role;
import com.project_REST.entity.User;
import com.project_REST.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public class AdminConsole {

    private final AdminService adminService;

    @Autowired
    public AdminConsole(AdminService adminService) {
        this.adminService = adminService;

    }

    public void start(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1. List All Users");
            System.out.println("2. Create New Employee");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choice: ");

            // Check if input is an integer to avoid crashes
            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminService.getAllUsers().forEach(u ->
                            System.out.println("ID: " + u.getId() + " | Name: " + u.getName() + " | Role: " + u.getRole()));
                    break;
                case 2:
                    handleCreateEmployee(scanner);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void handleCreateEmployee(Scanner scanner) {
        CreateUserRequest req = new CreateUserRequest();

        System.out.print("Enter ID: ");
        req.setId(scanner.nextLine());

        System.out.print("Enter Name: ");
        req.setName(scanner.nextLine());

        System.out.print("Enter Email: ");
        req.setEmail(scanner.nextLine());

        // --- NEW PASSWORD PROMPT ---
        System.out.print("Enter Password: ");
        req.setPassword(scanner.nextLine());

        System.out.print("Assign manager");
        req.setManagerId(scanner.nextLine());

        // --- FIXED ROLE LOGIC ---
        System.out.print("Enter Role (ADMIN, EMPLOYEE, MANAGER): ");
        String roleInput = scanner.nextLine().toUpperCase().trim();

        try {
            // Conversion from String to Enum happens here
            Role selectedRole = Role.valueOf(roleInput);
            req.setRole(selectedRole);

            adminService.createEmployee(req);
            System.out.println("User created successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: '" + roleInput + "' is not a valid Role. User not created.");
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }
}