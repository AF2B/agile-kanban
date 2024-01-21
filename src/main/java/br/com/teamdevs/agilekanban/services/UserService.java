package br.com.teamdevs.agilekanban.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.teamdevs.agilekanban.exception.CustomException;
import br.com.teamdevs.agilekanban.model.User;
import br.com.teamdevs.agilekanban.repository.UserRepository;

@Service
public class UserService {
    private UserRepository repository;
    
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void save(User user) {
        repository.save(user);
    }

    public List<User> findAll() {
        var data = repository.findAll();
        
        if (data == null || data.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "Users not found.");
        }

        return data;
    }

    public User update(String id, User request) {
        return repository.update(id, request);
    }

    public User find(String id) {
        User data = repository.findById(id);

        if (data == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "User not found.");
        }

        return data;
    }

    public void remove(String id) {
        repository.delete(id);
    }
}
