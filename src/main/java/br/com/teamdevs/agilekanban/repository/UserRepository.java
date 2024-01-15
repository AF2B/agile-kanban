package br.com.teamdevs.agilekanban.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

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
        var data = User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .email(user.getEmail())
            .role("USER")
            .build();

        // if (data.) {
        //     throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something happen when try to insert a user.");
        // } TODO: must be create save query

        mongoTemplate.save(data);
    } 

    public List<User> findAll() {
        try {
            List<AggregationOperation> aggregationOperations = new ArrayList<>();

            aggregationOperations.add(Aggregation.unwind(UNWIND_USERS));
            aggregationOperations.add(Aggregation.project()
                .and(UNWIND_USERS + ".username").as("username")
                .and(UNWIND_USERS + ".email").as("email")
                .and(UNWIND_USERS + ".role").as("role")
                .and(UNWIND_USERS + ".projects").as("projects")
                .and(UNWIND_USERS + ".createdTasks").as("createdTasks")
                .and(UNWIND_USERS + ".assignedTasks").as("assignedTasks")
                .andExclude(UNWIND_USERS + ".password"));

            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
            List<User> users = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, User.class).getMappedResults();

            return users;
        } catch (Exception e) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "users not found");
        }
    }

    // public User findById() {

    // }

    public User update(Long id, UserRequestDTO request) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.unwind(UNWIND_USERS));

        Query query = new Query(Criteria.where("_id").is(id));

        Update updateData = updateUser(request);

        UpdateResult result = mongoTemplate.updateFirst(query, updateData, User.class);

        if (!result.wasAcknowledged()) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Update operation was not acknowledged");
        }
        return mongoTemplate.findById(id, User.class);
    }

    private Update updateUser(UserRequestDTO request) {
        boolean hasEmail = request.getEmail() != null && !request.getEmail().isEmpty();
        boolean hasUsername = request.getUsername() != null && !request.getUsername().isEmpty();
        boolean hasPassword = request.getPassword() != null && !request.getPassword().isEmpty();

        Map<String, String> dict = new HashMap<>();
        Update update = new Update();

        if (hasEmail) {
            dict.put("email", request.getEmail());
        }
        if (hasUsername) {
            dict.put("username", request.getUsername());
        }
        if (hasPassword) {
            dict.put("password", request.getPassword());
        }

        dict.forEach((key, value) -> {
            if (Objects.nonNull(value)) {
                update.set(key, value);
            }
        });

        return update;
    }

    // public void delete() {

    // }
}
