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
        UserEntity user = id == null ? new UserEntity() : userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário com ID " + id + " não encontrado")
        );

        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setBirthDate(userDTO.getBirthDate());

        // Validação para userTypeEnum
        try {
            user.setUserType(UserTypeEnum.valueOf(userDTO.getUserType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de usuário inválido. Deve ser: ADMIN, EDITOR ou VIEWER.");
        }

        return userRepository.save(user);
    }
}
