package br.com.teamdevs.agilekanban.service;

import java.util.List;
import java.util.Collections;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.teamdevs.agilekanban.exception.CustomException;
import br.com.teamdevs.agilekanban.model.User;
import br.com.teamdevs.agilekanban.services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserService service;

    @BeforeEach
    public void setUp() {
        this.service = mock(UserService.class);
    }

    @Test
    public void testSaveUser() {
        User user = createTestUser();
        service.save(user);
        verify(service, times(1)).save(user);
    }

    @Test
    public void testFindAllUsersSuccess() {
        List<User> users = createTestUsersList();
        when(service.findAll()).thenReturn(users);

        List<User> result = service.findAll();
        assertEquals(users, result);
    }

    @Test
    public void testFindAllUsersEmpty() {
        when(service.findAll()).thenThrow(CustomException.class);

        assertThrows(CustomException.class, () -> service.findAll());
    }

    @Test
    public void testUpdateUser() {
        String userId = "123";
        User user = createTestUser();
        when(service.update(userId, user)).thenReturn(user);

        User result = service.update(userId, user);
        assertEquals(user, result);
    }

    @Test
    public void testFindUserByIdSuccess() {
        String userId = "123";
        User user = createTestUser();
        when(service.find(userId)).thenReturn(user);

        User result = service.find(userId);
        assertEquals(user, result);
    }

    @Test
    public void testFindUserByIdNotFound() {
        when(service.find("123")).thenThrow(CustomException.class);

        assertThrows(CustomException.class, () -> service.find("123"));
    }

    @Test
    public void testRemoveUser() {
        String userId = "123";
        doNothing().when(service).remove(userId);

        service.remove(userId);
        verify(service, times(1)).remove(userId);
    }

    private List<User> createTestUsersList() {
        User user = User.builder()
                .username("TestUser")
                .email("testuser@example.com")
                .password("TestPassword1!")
                .role("USER")
                .projects(Collections.singletonList("TestProject"))
                .createdTasks(Collections.singletonList("Task1"))
                .assignedTasks(Collections.singletonList("Task1"))
                .build();
    
        return Arrays.asList(user, user);
    }

    private User createTestUser() {
        User user = User.builder()
                .username("TestUser")
                .email("testuser@example.com")
                .password("TestPassword1!")
                .role("USER")
                .projects(Collections.singletonList("TestProject"))
                .createdTasks(Collections.singletonList("Task1"))
                .assignedTasks(Collections.singletonList("Task1"))
                .build();

        return user;
    }
}
