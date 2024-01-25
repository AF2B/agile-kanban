package br.com.teamdevs.agilekanban.repository;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

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

    private final MongoTemplate mongoTemplate;

    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void save(User user) {
        mongoTemplate.save(user);
    } 

    public Optional<List<User>> findAll() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.project()
                .andExclude("_id")
                .andInclude("username", "password", "email", "role", "projects", "createdTasks", "assignedTasks")
        );
        
        List<User> users = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, User.class).getMappedResults();
        return Optional.ofNullable(users);
    }

    public Optional<User> findById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("Invalid ObjectId format");
        }
    
        ObjectId objectId = new ObjectId(id);
        User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(objectId)), User.class);
        return Optional.ofNullable(user);
    }

    public User update(String id, User requestDTO) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
        Update update = new Update();
    
        updateIfPresent(update, "username", requestDTO.getUsername());
        updateIfPresent(update, "email", requestDTO.getEmail());
        updateIfPresent(update, "password", requestDTO.getPassword());
        updateRoleIfPresent(update, requestDTO.getRole());
        updateListIfPresent(update, "projects", requestDTO.getProjects());
        updateListIfPresent(update, "createdTasks", requestDTO.getCreatedTasks());
        updateListIfPresent(update, "assignedTasks", requestDTO.getAssignedTasks());
    
        mongoTemplate.findAndModify(query, update, User.class);
        return mongoTemplate.findById(new ObjectId(id), User.class);
    } // PADRÃO

    public Optional<List<User>> search(String username, String email) {
        Criteria criteria = new Criteria();
        boolean isUsernameEmpty = StringUtils.isEmpty(username);
        boolean isEmailEmpty = StringUtils.isEmpty(email);
        
        if (isUsernameEmpty && isEmailEmpty) {
            return Optional.empty();
        }
        
        if (!isUsernameEmpty) {
            criteria = criteria.and("username").regex("^" + username, "i");
        }
        if (!isEmailEmpty) {
            criteria = criteria.and("email").is(email);
        }
        
        Query query = new Query(criteria);
        List<User> result = mongoTemplate.find(query, User.class);
        return Optional.ofNullable(result);
    }
    

    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
    
        mongoTemplate.remove(query, User.class);
    }

    private void updateIfPresent(Update update, String key, String value) {
    Optional.ofNullable(value)
            .filter(s -> !s.isBlank())
            .ifPresent(s -> update.set(key, s));
    }

    private void updateRoleIfPresent(Update update, String role) {
        Optional.ofNullable(role)
                .filter(s -> !s.isBlank())
                .ifPresent(s -> update.set("role", s.toUpperCase()));
    }

    private void updateListIfPresent(Update update, String key, List<?> list) {
        Optional.ofNullable(list)
                .filter(l -> !l.isEmpty())
                .ifPresent(l -> update.set(key, l));
    }
}
