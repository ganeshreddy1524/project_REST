package com.project_REST;

import com.project_REST.entity.LeaveRequest;
import com.project_REST.entity.LeaveStatus;
import com.project_REST.repository.LeaveRequestRepository;
import com.project_REST.service.ManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ManagerServiceTest {

    @Autowired
    private ManagerService managerService;

    @MockitoBean
    private LeaveRequestRepository leaveRepo;

    @Test
    void approveLeave_shouldSetStatusApproved() {

        // given
        LeaveRequest leave = new LeaveRequest();
        leave.setId(1L);
        leave.setStatus(LeaveStatus.PENDING);

        when(leaveRepo.findById(1L)).thenReturn(Optional.of(leave));
        when(leaveRepo.save(any(LeaveRequest.class)))
                .thenAnswer(i -> i.getArgument(0));

        // when
        LeaveRequest result = managerService.approve(1L);

        // then
        assertEquals(LeaveStatus.APPROVED, result.getStatus());
        verify(leaveRepo).findById(1L);
        verify(leaveRepo).save(leave);
    }

    @Test
    void rejectLeave_shouldSetStatusRejected() {

        // given
        LeaveRequest leave = new LeaveRequest();
        leave.setId(2L);
        leave.setStatus(LeaveStatus.PENDING);

        when(leaveRepo.findById(2L)).thenReturn(Optional.of(leave));
        when(leaveRepo.save(any(LeaveRequest.class)))
                .thenAnswer(i -> i.getArgument(0));

        // when
        LeaveRequest result = managerService.reject(2L);

        // then
        assertEquals(LeaveStatus.REJECTED, result.getStatus());
        verify(leaveRepo).findById(2L);
        verify(leaveRepo).save(leave);
    }

    @Test
    void approveLeave_shouldThrowException_whenLeaveNotFound() {

        // given
        when(leaveRepo.findById(99L)).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class,
                () -> managerService.approve(99L));

        verify(leaveRepo).findById(99L);
        verify(leaveRepo, never()).save(any());
    }
}