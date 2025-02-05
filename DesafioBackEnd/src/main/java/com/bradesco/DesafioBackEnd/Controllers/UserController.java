package com.bradesco.DesafioBackEnd.Controllers;

import com.bradesco.DesafioBackEnd.DTOs.UserDTO;
import com.bradesco.DesafioBackEnd.Entities.UserEntity;
import com.bradesco.DesafioBackEnd.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciar o CRUD de usuários")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping
    @Operation(summary = "Criar um novo usuário", description = "Salva um novo usuário no banco de dados.")
    @Validated
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserEntity createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }



    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de usuários cadastrados.")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "Nenhum usuário cadastrado"));
        }
        return ResponseEntity.ok(users);
    }



    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo ID informado.")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado"));
    }



    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário pelo ID.")
    @Validated
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        try {
            UserEntity updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (ResponseStatusException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Não é possível atualizar informações, pois o usuário com ID " + id + " não existe."));
            }
            throw e;
        }
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Remove um usuário pelo ID.")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        if (userService.getUserById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuário com ID " + id + " não encontrado"));
        }
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Usuário com ID " + id + " foi deletado com sucesso"));
    }





    // Tratamento global de erros de validação
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(jakarta.validation.ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Tratamento de erro para requisições inválidas
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(Map.of("error", Objects.requireNonNull(ex.getReason())));
    }
}
