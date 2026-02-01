package com.project_REST.controller;

import com.project_REST.entity.LeaveRequest;
import com.project_REST.service.ManagerService;

import java.util.List;
import java.util.Scanner;

public class ManagerConsole {

    private final ManagerService managerService;

    public ManagerConsole(ManagerService managerService) {
        this.managerService = managerService;
    }

    public void start(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- MANAGER APPROVAL CENTER ---");
            System.out.println("1. View Team Members");
            System.out.println("2. Approve/Reject Leave");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {


                    System.out.print("Enter your Manager ID: ");
                    String mId = scanner.nextLine();



                    try {
                        List<LeaveRequest> leaves = managerService.viewTeamLeaves(mId);

                        if (leaves.isEmpty()) {
                            System.out.println("No leave requests found for your team.");
                            return;
                        }

                        System.out.println("\n--- PENDING TEAM LEAVES ---");
                        // Formatting the header: ID, Employee Name, Start Date, Status
                        System.out.printf("%-5s | %-15s | %-12s | %-10s%n", "ID", "Employee", "Start Date", "Status");
                        System.out.println("------------------------------------------------------------");

                        for (LeaveRequest lr : leaves) {
                            System.out.printf("%-5d | %-15s | %-12s | %-10s%n",
                                    lr.getId(),
                                    lr.getEmployee().getName(),
                                    lr.getStartDate(),
                                    lr.getStatus());
                        }

                        // After viewing, ask if they want to take action
                        System.out.println("\n1. Approve a Request");
                        System.out.println("2. Reject a Request");
                        System.out.println("0. Back");
                        System.out.print("Choice: ");
                        int action = Integer.parseInt(scanner.nextLine());

                        if (action == 1 || action == 2) {
                            System.out.print("Enter Leave Request ID: ");
                            Long reqId = Long.parseLong(scanner.nextLine());

                            if (action == 1) {
                                managerService.approve(reqId);
                                System.out.println("✅ Request #" + reqId + " Approved.");
                            } else {
                                managerService.reject(reqId);
                                System.out.println("❌ Request #" + reqId + " Rejected.");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }


            } else if (choice == 2) {
                System.out.print("Enter Leave Request ID: ");
                Long rid = scanner.nextLong();
                System.out.print("1 to Approve, 2 to Reject: ");
                int decision = scanner.nextInt();
                if (decision == 1) managerService.approve(rid);
                else managerService.reject(rid);
                System.out.println("Processed successfully.");
            } else if (choice == 0) {
                back = true;
            }
        }
    }
}