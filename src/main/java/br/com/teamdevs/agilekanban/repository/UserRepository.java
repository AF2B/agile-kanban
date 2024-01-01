package br.com.teamdevs.agilekanban.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import br.com.teamdevs.agilekanban.dto.UserRequestDTO;
import br.com.teamdevs.agilekanban.exception.CustomException;
import br.com.teamdevs.agilekanban.model.User;

@Repository
public class UserRepository {
    private final static String COLLECTION_NAME = "allData";
    private final static String UNWIND_USERS = "users";

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(UserRequestDTO user) {
        try {
            var userToSave = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role("USER")
                .build();

            mongoTemplate.save(userToSave);
        } catch (Exception e) {
            throw new CustomException(
                LocalDateTime.now(), 
                HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "Something happen when try to insert a user.", 
                e.getMessage());
        }
    }

    public List<User> findAll() {
        try {
            List<AggregationOperation> aggregationOperation = new ArrayList<>();

            aggregationOperation.add(Aggregation.unwind(UNWIND_USERS));
            aggregationOperation.add(Aggregation.project()
                .and(UNWIND_USERS + ".username").as("username")
                .and(UNWIND_USERS + ".email").as("email")
                .and(UNWIND_USERS + ".role").as("role")
                .and(UNWIND_USERS + ".projects").as("projects")
                .and(UNWIND_USERS + ".createdTasks").as("createdTasks")
                .and(UNWIND_USERS + ".assignedTasks").as("assignedTasks")
                .andExclude(UNWIND_USERS + ".password"));

            Aggregation aggregation = Aggregation.newAggregation(aggregationOperation);
            List<User> users = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, User.class).getMappedResults();

            return users;
        } catch (Exception e) {
            throw new CustomException(
                LocalDateTime.now(), 
                HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "Something happen when try to get all users.", 
                e.getMessage());
        }
    }

    // public User findById() {

    // }

    // public void update() {

    // }

    // public void delete() {

    // }
}
