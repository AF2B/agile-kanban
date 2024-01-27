package br.com.teamdevs.agilekanban.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.teamdevs.agilekanban.controllers.UsersController;
import br.com.teamdevs.agilekanban.dto.UserResponseDTO;
import br.com.teamdevs.agilekanban.model.User;
import br.com.teamdevs.agilekanban.services.UserService;
import br.com.teamdevs.agilekanban.services.validations.uservalidations.UserValidationPropertiesService;

@SpringBootTest
public class UsersControllerTest {
    private UsersController controller;
    private UserService service;
    private UserValidationPropertiesService userValidationPropertiesService;

    @BeforeEach
    public void setUp() {
        this.service = mock(UserService.class);
        this.controller = new UsersController(service, userValidationPropertiesService);
    }

    @Test
    public void testHealthCheck() {
        ResponseEntity<HttpStatus> sut = controller.healthCheck();
        assertEquals(HttpStatus.OK, sut.getStatusCode());
    }

    @Test
    public void getAllUsers_ShouldReturnOkStatus_WhenUsersExist() {
        List<User> expectedData = createTestUsersList();
        when(service.findAll()).thenReturn(expectedData);

        ResponseEntity<UserResponseDTO> response = controller.GetAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedData.size(), response.getBody().getData().size());
    } // PADRÃO

    @Test
    public void testFindByIdSuccess() {
        List<String> mockList = new ArrayList<>();
        mockList.add("teste");

        User user = User.builder()
            .username("Test")
            .email("teste@gmail.com")
            .password("senhaf0rt3")
            .role("USER")
            .projects(mockList)
            .createdTasks(mockList)
            .assignedTasks(mockList)
            .build();

        when(service.find(anyString())).thenReturn(user);
        ResponseEntity<User> response = controller.findById("123");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateUser() {
        List<String> mockList = new ArrayList<>();
        mockList.add("teste");

        User user = User.builder()
            .username("Test")
            .email("teste@gmail.com")
            .password("senhaf0rt3")
            .role("USER")
            .projects(mockList)
            .createdTasks(mockList)
            .assignedTasks(mockList)
            .build();

        doNothing().when(service).save(any(User.class));
        ResponseEntity<HttpStatus> response = controller.CreateUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateUser() {
        List<String> mockList = new ArrayList<>();
        mockList.add("teste");

        User user = User.builder()
            .username("Test")
            .email("teste@gmail.com")
            .password("senhaf0rt3")
            .role("USER")
            .projects(mockList)
            .createdTasks(mockList)
            .assignedTasks(mockList)
            .build();

        User userUpdated = User.builder()
        .username("Teste")
        .email("teste@gmail.com")
        .password("senhaf0rt3")
        .role("USER")
        .projects(mockList)
        .createdTasks(mockList)
        .assignedTasks(mockList)
        .build();

        when(service.update(anyString(), any(User.class))).thenReturn(user);
        ResponseEntity<User> response = controller.updateUser("123", userUpdated);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveUser() {
        doNothing().when(service).remove(anyString());
        ResponseEntity<Void> response = controller.remove("123");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
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
}
