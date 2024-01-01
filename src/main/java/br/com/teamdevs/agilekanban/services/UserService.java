package br.com.teamdevs.agilekanban.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teamdevs.agilekanban.dto.UserRequestDTO;
import br.com.teamdevs.agilekanban.dto.UserResponseDTO;
import br.com.teamdevs.agilekanban.model.User;
import br.com.teamdevs.agilekanban.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void save(UserRequestDTO user) {
        repository.save(user);
    }

    public List<User> findAll() {
        var data = repository.findAll();
        
        return data;
    }
}
