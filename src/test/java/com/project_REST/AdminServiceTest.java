package com.project_REST;
import com.project_REST.dto.CreateUserRequest;
import com.project_REST.entity.Role;
import com.project_REST.entity.User;
import com.project_REST.repository.UserRepository;
import com.project_REST.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void createEmployee_withManager_success() {

        // Arrange
        CreateUserRequest req = new CreateUserRequest();
        req.setId("4");
        req.setName("reddy");
        req.setEmail("reddy@gmail.com");
        req.setRole(Role.EMPLOYEE);
        req.setManagerId("2");

        User manager = new User();
        manager.setId("2");
        manager.setRole(Role.MANAGER);

        when(userRepository.findById("2")).thenReturn(Optional.of(manager));
        when(userRepository.save(any(User.class)))
                .thenAnswer(i -> i.getArgument(0));

        // Act
        User result = adminService.createEmployee(req);

        // Assert
        assertEquals("4", result.getId());
        assertEquals(manager, result.getManager());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void addUser_withoutManager_shouldSaveUser() {

        // Arrange
        CreateUserRequest req = new CreateUserRequest();
        req.setId("5");
        req.setName("NoManager");
        req.setEmail("nomgr@gmail.com");
        req.setRole(Role.EMPLOYEE);
        req.setManagerId(null);

        User savedUser = new User();
        savedUser.setId("5");
        savedUser.setName("NoManager");
        savedUser.setEmail("nomgr@gmail.com");
        savedUser.setRole(Role.EMPLOYEE);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = adminService.createEmployee(req);

        // Assert
        assertNotNull(result);
        assertNull(result.getManager());

        verify(userRepository, never()).findById(any());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getAllUsers_shouldReturnList() {

        // Arrange
        User u1 = new User();
        u1.setId("1");

        User u2 = new User();
        u2.setId("2");

        when(userRepository.findAll()).thenReturn(List.of(u1, u2));

        // Act
        List<User> users = adminService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }
}