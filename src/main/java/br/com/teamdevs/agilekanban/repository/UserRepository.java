package br.com.teamdevs.agilekanban.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.com.teamdevs.agilekanban.exception.InvalidObjectIdException;
import br.com.teamdevs.agilekanban.exception.UserNotFoundException;
import br.com.teamdevs.agilekanban.model.User;
import br.com.teamdevs.agilekanban.repository.utils.UpdateUtils;

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
        
        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, User.class);
        List<User> users = results.getMappedResults();
        return Optional.ofNullable(users.isEmpty() ? null : users);
    }

    public Optional<User> findById(String id) {
        if (!ObjectId.isValid(id)) {
            throw new InvalidObjectIdException(id);
        }
    
        ObjectId objectId = new ObjectId(id);
        User user = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(objectId)), User.class);
        return Optional.ofNullable(user);
    }

    public User update(String id, User requestDTO) {
        if (!ObjectId.isValid(id)) {
            throw new InvalidObjectIdException(id);
        }

        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
        Update update = new Update();

        UpdateUtils.updateIfPresent(update, "username", requestDTO.getUsername());
        UpdateUtils.updateIfPresent(update, "email", requestDTO.getEmail());
        UpdateUtils.updateIfPresent(update, "password", requestDTO.getPassword());
        UpdateUtils.updateEnumIfPresent(update, "role", requestDTO.getRole());
        UpdateUtils.updateListIfPresent(update, "projects", requestDTO.getProjects());
        UpdateUtils.updateListIfPresent(update, "createdTasks", requestDTO.getCreatedTasks());
        UpdateUtils.updateListIfPresent(update, "assignedTasks", requestDTO.getAssignedTasks());

        User updatedUser = mongoTemplate.findAndModify(query, update, User.class);
        if (updatedUser == null) {
            throw new UserNotFoundException("Usuário com ID: " + id + " não encontrado.");
        }
        return updatedUser;
    }

    public Optional<List<User>> search(String username, String email) {
        List<AggregationOperation> operations = new ArrayList<>();
        
        Optional<Criteria> initialCriteria = buildInitialCriteria(username, email);
        initialCriteria.ifPresent(criteria -> operations.add(Aggregation.match(criteria)));

        // vai precisar usar unwind futuramente por conta da quantidade de dados.
        // formato atual deve ser PADRÃO para todos, aumento de performance significativo.
        // operations.add(Aggregation.unwind("arrayField"));

        Aggregation aggregation = Aggregation.newAggregation(operations);
        
        List<User> result = mongoTemplate.aggregate(aggregation, User.class, User.class).getMappedResults();
        
        return Optional.ofNullable(result);
    }
    
    private Optional<Criteria> buildInitialCriteria(String username, String email) {
        Criteria criteria = new Criteria();
        boolean addCriteria = false;

        if (!StringUtils.isEmpty(username)) {
            criteria = criteria.and("username").regex("^" + Pattern.quote(username), "i");
            addCriteria = true;
        }
        if (!StringUtils.isEmpty(email)) {
            criteria = criteria.and("email").is(email);
            addCriteria = true;
        }

        return addCriteria ? Optional.of(criteria) : Optional.empty();
    }

    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
    
        mongoTemplate.remove(query, User.class);
    }
}
