package br.com.teamdevs.agilekanban.repository;

import java.util.List;
import java.util.Collections;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.mongodb.core.MongoTemplate;
import br.com.teamdevs.agilekanban.model.User;

public class UserRepositoryTest {
    private MongoTemplate mongoTemplate;
    private UserRepository repository;

    @BeforeEach
    public void setUp() {
        this.mongoTemplate = mock(MongoTemplate.class);
        this.repository = new UserRepository(mongoTemplate);
    }

    // TODO

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
