package com.project_REST.controller;

import com.project_REST.entity.User;
import com.project_REST.service.AdminService;
import com.project_REST.service.AuthService;
import com.project_REST.service.EmployeeService;
import com.project_REST.service.ManagerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MainConsoleApp implements CommandLineRunner {

    // 1. Define the service variables
    private final AuthService authService;
    private final AdminService adminService;
    private final EmployeeService employeeService;
    private final ManagerService managerService;

    // 2. Inject them through the constructor
    public MainConsoleApp(AuthService authService, AdminService adminService,
                          EmployeeService employeeService, ManagerService managerService) {
        this.authService = authService;
        this.adminService = adminService;
        this.employeeService = employeeService;
        this.managerService = managerService;
    }

    @Override
    public void run(String... args) {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            // Initialize sub-consoles
            AdminConsole adminUI = new AdminConsole(adminService);
            EmployeeConsole employeeUI = new EmployeeConsole(employeeService);
            ManagerConsole managerUI = new ManagerConsole(managerService);

            while (true) {
                System.out.println("\n--- LOGIN ---");
                System.out.print("ID: ");
                String id = scanner.nextLine();
                System.out.print("Password: ");
                String pass = scanner.nextLine();

                try {

                    User user = authService.authenticate(id, pass);

                    System.out.println("Welcome " + user.getName());

                    // Route to sub-menus based on role
                    switch (user.getRole()) {
                        case ADMIN -> adminUI.start(scanner);
                        case MANAGER -> managerUI.start(scanner);
                        case EMPLOYEE -> employeeUI.start(scanner);
                    }
                } catch (Exception e) {
                    System.out.println("❌ Error: " + e.getMessage());
                }
            }
        }).start();
    }
}