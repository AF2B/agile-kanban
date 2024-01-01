package br.com.teamdevs.agilekanban.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teamdevs.agilekanban.dto.UserRequestDTO;
import br.com.teamdevs.agilekanban.dto.UserResponseDTO;
import br.com.teamdevs.agilekanban.exception.CustomException;
import br.com.teamdevs.agilekanban.model.User;
import br.com.teamdevs.agilekanban.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Validated
public class UsersController {
    @Autowired
    private UserService service;

    @GetMapping("/health-check")
    public ResponseEntity<HttpStatus> healthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

   @GetMapping("/users")
   public ResponseEntity<UserResponseDTO> Get() {
        var data = service.findAll();

        if (data == null) {
            throw new CustomException(LocalDateTime.now(), 
                HttpStatus.NOT_FOUND.value(), 
                "Users not found",
                HttpStatus.NOT_FOUND.getReasonPhrase());
        }

        UserResponseDTO response = new UserResponseDTO(data);

        return ResponseEntity.status(200).body(response);
   }

   @PostMapping("/users")
    public ResponseEntity<HttpStatus> Post(@Valid @RequestBody UserRequestDTO user) {
        if (user.getUsername().equals("")) {
            throw new CustomException(LocalDateTime.now(), 
                HttpStatus.BAD_REQUEST.value(), 
                "Username cannot be blank",
                HttpStatus.BAD_REQUEST.getReasonPhrase());
        }
        // validations above should be in a service.
        
        service.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
