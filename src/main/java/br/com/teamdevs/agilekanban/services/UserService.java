package br.com.teamdevs.agilekanban.services;

import java.util.List;
import java.util.Optional;

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
        // encrypt password before save in database
        

        repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll()
                         .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "Usuário não encontrado"));
    }

    public User update(String id, User request) {
        validateUserExistence(id);
        return repository.update(id, request);
    }

    public User find(String id) {
        Optional<User> data = repository.findById(id);
        return data.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "Usuário não encontrado"));
    }

    public void remove(String id) {
        validateUserExistence(id);
        repository.delete(id);
    }

    public List<User> search(String username, String email) {
        return repository.search(username, email)
                         .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value() ,"Usuário não encontrado"));
    }

    private User validateUserExistence(String id) {
        return repository.findById(id)
                         .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), "Usuário não encontrado"));
    }
}
