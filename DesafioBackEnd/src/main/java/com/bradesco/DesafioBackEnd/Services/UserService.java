package com.bradesco.DesafioBackEnd.Services;

import com.bradesco.DesafioBackEnd.DTOs.UserDTO;
import com.bradesco.DesafioBackEnd.Entities.UserEntity;
import com.bradesco.DesafioBackEnd.Enums.UserTypeEnum;
import com.bradesco.DesafioBackEnd.Repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity createUser(UserDTO userDTO) {
        return saveUser(userDTO, null);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado")));
    }

    public UserEntity updateUser(Long id, UserDTO userDTO) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado");
        }
        return saveUser(userDTO, id);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado");
        }
        userRepository.deleteById(id);
    }

    private UserEntity saveUser(UserDTO userDTO, Long id) {

        //Tratamento de dados

        // Verifica se o usuário existe ao atualizar ou criar
        UserEntity user = id == null ? new UserEntity() : userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado"));

        // Validações manuais para garantir mensagens no Postman ou swagger
        if (userDTO.getFullName() == null || userDTO.getFullName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insira o Nome Completo, por favor.");
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insira o Email, por favor.");
        } else if (!userDTO.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de Email inválido!");
        }

        if (userDTO.getPhone() == null || userDTO.getPhone().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insira o número de telefone, por favor.");
        } else if (!userDTO.getPhone().matches("\\+\\d{1,3}\\s\\d{2}\\s\\d{4,5}-\\d{4}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Número de telefone deve ser no formato: +CC XX XXXXX-XXXX");
        }

        if (userDTO.getBirthDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insira a data de nascimento, por favor.");
        }

        if (userDTO.getUserType() == null || userDTO.getUserType().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de usuário deve ser: ADMIN, EDITOR, VIEWER");
        }

        try {
            user.setUserType(UserTypeEnum.valueOf(userDTO.getUserType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de usuário inválido. Deve ser: ADMIN, EDITOR ou VIEWER.");
        }


        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setBirthDate(userDTO.getBirthDate());

        return userRepository.save(user);
    }
}
