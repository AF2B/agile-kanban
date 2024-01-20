package br.com.teamdevs.agilekanban.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.com.teamdevs.agilekanban.model.User;

@Repository
public class UserRepository {
    private final static String COLLECTION_NAME = "allData";
    private final static String UNWIND_USERS = "users";

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(User user) {
        mongoTemplate.save(user);
    } 

    public List<User> findAll() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.project()
                .andExclude("_id")
                .andInclude("username", "password", "email", "role", "projects", "createdTasks", "assignedTasks")
        );
        
        List<User> users = mongoTemplate.aggregate(aggregation, "allData", User.class).getMappedResults();
        return users;
    }

    public User findById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("Invalid ObjectId format");
        }
    
        ObjectId objectId = new ObjectId(id);
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(objectId)), User.class);
    }

    public User update(String id, User requestDTO) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
        Update update = new Update();

        if (requestDTO.getUsername() != null && !requestDTO.getUsername().isBlank()) {
            update.set("username", requestDTO.getUsername());
        }
        if (requestDTO.getEmail() != null && !requestDTO.getEmail().isBlank()) {
            update.set("email", requestDTO.getEmail());
        }
        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isBlank()) {
            update.set("password", requestDTO.getPassword());
        }
        if (requestDTO.getRole() != null && !requestDTO.getRole().isBlank()) {
            update.set("role", requestDTO.getRole().toUpperCase());
        }
        if (requestDTO.getProjects() != null && !requestDTO.getProjects().isEmpty()) {
            update.set("projects", requestDTO.getProjects());
        }
        if (requestDTO.getCreatedTasks() != null && !requestDTO.getCreatedTasks().isEmpty()) {
            update.set("createdTasks", requestDTO.getCreatedTasks());
        }
        if (requestDTO.getAssignedTasks() != null && !requestDTO.getAssignedTasks().isEmpty()) {
            update.set("assignedTasks", requestDTO.getAssignedTasks());
        }

        mongoTemplate.findAndModify(query, update, User.class);
        return mongoTemplate.findById(id, User.class);
    }

    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
    
        mongoTemplate.remove(query, User.class);
    }
}
