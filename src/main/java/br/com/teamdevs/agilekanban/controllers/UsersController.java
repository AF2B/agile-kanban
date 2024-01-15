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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.teamdevs.agilekanban.dto.UserRequestDTO;
import br.com.teamdevs.agilekanban.dto.UserResponseDTO;
import br.com.teamdevs.agilekanban.exception.CustomException;
import br.com.teamdevs.agilekanban.exception.UserNotFoundException;
import br.com.teamdevs.agilekanban.model.User;
import br.com.teamdevs.agilekanban.services.UserService;
import br.com.teamdevs.agilekanban.services.ValidationsController.UserValidations.UserValidationPropertiesService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Validated
public class UsersController {
    private UserService userService;
    private UserValidationPropertiesService userValidationPropertiesService;

    public UsersController(UserService serviceInj, UserValidationPropertiesService userValidationPropertiesServiceInj) {
        this.userService = serviceInj;
        this.userValidationPropertiesService = userValidationPropertiesServiceInj;
    }

    @GetMapping("/health-check")
    public ResponseEntity<HttpStatus> healthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

   @GetMapping("/users")
   public ResponseEntity<UserResponseDTO> GetAllUsers() {
        var data = userService.findAll();

        if (data == null || data.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "not found");
        }
        UserResponseDTO response = new UserResponseDTO(data);

        return ResponseEntity.status(200).body(response);
   }

   @PostMapping("/users")
    public ResponseEntity<HttpStatus> CreateUser(@RequestBody UserRequestDTO user) {
        // if (user.getUsername() == null && user.getUsername().isEmpty()) {
        //     throw new CustomException(HttpStatus.BAD_REQUEST.value(), "blabla...");
        // }
        // validations above should be in a service.
        
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestParam Long id, UserRequestDTO request) {
        userValidationPropertiesService.validateUserProperties(request.getEmail(), request.getUsername(), request.getPassword());

        User user = userService.update(id);

        boolean isUserNull = (user != null ? true : false);

        if (isUserNull) {
            return ResponseEntity.ok(user);
        } else {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND.value(), "not found user....");
        }
    }
}


