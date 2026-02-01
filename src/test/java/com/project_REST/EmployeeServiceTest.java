package com.project_REST;
import com.project_REST.entity.LeaveRequest;
import com.project_REST.entity.LeaveStatus;
import com.project_REST.entity.User;
import com.project_REST.repository.LeaveRequestRepository;
import com.project_REST.repository.UserRepository;
import com.project_REST.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockitoBean
    private LeaveRequestRepository leaveRepo;

    @MockitoBean
    private UserRepository userRepo;

    @Test
    void applyLeave_shouldSaveLeaveRequest() {
        // Arrange
        User emp = new User();
        emp.setId("1");
        emp.setName("Ganesh");

        LeaveRequest request = new LeaveRequest();
        request.setReason("Vacation");
        request.setStartDate(LocalDate.of(2026, 2, 15));
        request.setEndDate(LocalDate.of(2026, 2, 18));

        when(userRepo.findById("1")).thenReturn(Optional.of(emp));
        when(leaveRepo.save(any(LeaveRequest.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        LeaveRequest saved = employeeService.applyLeave("1", request);

        // Assert
        assertNotNull(saved);
        assertEquals(LeaveStatus.PENDING, saved.getStatus());
        assertEquals(emp, saved.getEmployee());

        verify(userRepo).findById("1");
        verify(leaveRepo).save(request);
    }

    @Test
    void viewLeaves_shouldReturnEmployeeLeaves() {
        // Arrange
        LeaveRequest lr = new LeaveRequest();
        lr.setId(1L);

        // ✅ FIXED: Using the correct method name 'findByEmployee_Id'
        when(leaveRepo.findByEmployee_Id("1"))
                .thenReturn(List.of(lr));

        // Act
        List<LeaveRequest> leaves = employeeService.viewLeaves("1");

        // Assert
        assertEquals(1, leaves.size());
        // ✅ FIXED: Verifying the correct method call
        verify(leaveRepo).findByEmployee_Id("1");
    }

    @Test
    void viewLeaves_shouldThrowExceptionWhenEmpty() {
        // Arrange
        // ✅ Test the logic we added to throw an exception if leaves are empty
        when(leaveRepo.findByEmployee_Id("1")).thenReturn(List.of());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            employeeService.viewLeaves("1");
        });

        assertEquals("No leave requests found for you.", exception.getMessage());
    }
}