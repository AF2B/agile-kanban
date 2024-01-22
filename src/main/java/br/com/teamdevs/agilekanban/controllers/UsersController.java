package br.com.teamdevs.agilekanban.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.teamdevs.agilekanban.dto.UserResponseDTO;
import br.com.teamdevs.agilekanban.model.User;
import br.com.teamdevs.agilekanban.services.UserService;
import br.com.teamdevs.agilekanban.services.ValidationsController.UserValidations.UserValidationPropertiesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Validated
@Tag(name = "Usuários", description = "Endpoints para gerenciar usuários")
public class UsersController {
    private UserService userService;
    private UserValidationPropertiesService userValidationPropertiesService;

    public UsersController(UserService serviceInj, UserValidationPropertiesService userValidationPropertiesServiceInj) {
        this.userService = serviceInj;
        this.userValidationPropertiesService = userValidationPropertiesServiceInj;
    }

    @Operation(
        summary = "Verificar o status da aplicação",
        description = "Endpoint para verificar o status da aplicação."
    )
    @GetMapping("/health-check")
    public ResponseEntity<HttpStatus> healthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
       summary = "Listar todos os usuários",
       description = "Endpoint para listar todos os usuários cadastrados."
   )
   @ApiResponses(value = {
       @ApiResponse(responseCode = "200", description = "Lista de usuários", content = {
           @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))
       })
   })
   @GetMapping("/users")
   public ResponseEntity<UserResponseDTO> GetAllUsers() {
        var data = userService.findAll();
        UserResponseDTO response = new UserResponseDTO(data);

        return ResponseEntity.status(200).body(response);
   }

   @Operation(
        summary = "Obter informações de um usuário",
        description = "Endpoint para obter informações de um usuário pelo ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        User data = userService.find(id);
        return ResponseEntity.ok(data);
    }

   @Operation(
       summary = "Criar um novo usuário",
       description = "Endpoint para criar um novo usuário."
   )
   @ApiResponses(value = {
       @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
       @ApiResponse(responseCode = "400", description = "Requisição inválida")
   })
   @PostMapping("/users")
    public ResponseEntity<HttpStatus> CreateUser(@RequestBody User user) {
        // if (user.getUsername() == null && user.getUsername().isEmpty()) {
        //     throw new CustomException(HttpStatus.BAD_REQUEST.value(), "blabla...");
        // }
        // validations above should be in a service.
        
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
        summary = "Atualizar um usuário",
        description = "Endpoint para atualizar um usuário existente."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User requestDTO) {
        User updatedUser = userService.update(id, requestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(
        summary = "Remover um usuário",
        description = "Endpoint para remover um usuário pelo ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> remove(@PathVariable String id) {
        userService.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
        summary = "",
        description = ""
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "", description = ""),
        @ApiResponse(responseCode = "", description = "")
    })
    @GetMapping("/users/search")
    public ResponseEntity<UserResponseDTO> customSearch(
        @RequestParam(required = false) String username, 
        @RequestParam(required = false) String email
        ) {
            List<User> data = userService.search(username, email);

            UserResponseDTO response = new UserResponseDTO(data);

            return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}